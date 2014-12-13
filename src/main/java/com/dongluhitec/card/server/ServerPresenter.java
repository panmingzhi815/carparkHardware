package com.dongluhitec.card.server;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.swt.widgets.Display;

import com.dongluhitec.card.CommonUI;
import com.dongluhitec.card.HardwareUtil;
import com.dongluhitec.card.RSAUtils;
import com.dongluhitec.card.message.Message;
import com.dongluhitec.card.message.MessageCodecFactory;
import com.dongluhitec.card.message.MessageType;
import com.google.common.base.Strings;

public class ServerPresenter {

	private static final int PORT = 9124;
	public ServerUI serverUI;
	private IoAcceptor acceptor;
	private String clientIP;
	private String clientPort;
	private ConnectFuture cf;
	private NioSocketConnector connector;
	private String session_id;

	public ServerPresenter(ServerUI serverUI) {
		this.serverUI = serverUI;
		try {
			acceptor = new NioSocketAcceptor();

			acceptor.getFilterChain().addLast("logger", new LoggingFilter());
			//指定编码过滤器 
			acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
			acceptor.setHandler(new AcceptorMessageHandler());
			acceptor.getSessionConfig().setReadBufferSize(2048);
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
			acceptor.bind(new InetSocketAddress(PORT));
			serverUI.println("绑定本地服务端口:" + PORT);
		} catch (Exception e) {
			e.printStackTrace();
			CommonUI.error("错误", "开始监听服务器失败!");
		}
	}

	class AcceptorMessageHandler extends IoHandlerAdapter {

		

		@Override
		public void sessionCreated(IoSession session) throws Exception {
			serverUI.println("创建会话:" + session.getRemoteAddress());
			super.sessionCreated(session);
		}

		@Override
		public void messageReceived(IoSession session, Object message)
				throws Exception {
			String checkSubpackage = HardwareUtil.checkSubpackage(session, message);
			if(Strings.isNullOrEmpty(checkSubpackage)){
				return;
			}
			Message msg = new Message(checkSubpackage);
			serverUI.println("收到消息密文:" + checkSubpackage);
			
			if(msg.getType() == MessageType.交换密钥){
				HardwareUtil.responsePublicKey_server(session,msg);
				return;
			}
			
			final Document dom = DocumentHelper.parseText(HardwareUtil.decode(msg.getContent()));
			
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					serverUI.println_encode("收到消息明文"+dom.getRootElement().asXML());
				}
			});
			
			if(msg.getType() == MessageType.设备控制){
				String responseDeviceControl = HardwareUtil.responseDeviceControl(session,dom);
				serverUI.println_encode("发送消息明文"+responseDeviceControl);
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						try{
							serverUI.state2Xml(dom);
						}catch(Exception e){
							e.printStackTrace();
						}
						
					}
				});
			}
			
			if(msg.getType() == MessageType.成功){
				try{
					Element rootElement = dom.getRootElement();
					Element element = rootElement.element("session_id");
					session_id = element.getText();
				}catch(Exception e){
					
				}
			}
			
			if(msg.getType() == MessageType.广告){
				String responseDeviceControl = HardwareUtil.responseDeviceControl(session,dom);
				serverUI.println_encode("发送消息明文"+responseDeviceControl);
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						serverUI.ad2Xml(dom);
					}
				});
			}
		}

		@Override
		public void messageSent(IoSession session, Object message)
				throws Exception {
			
			serverUI.println("发送消息密文:" + message);
			super.messageSent(session, message);
		}

		@Override
		public void sessionClosed(IoSession session) throws Exception {
			serverUI.println("关闭会话:" + session.getRemoteAddress());
		}

		@Override
		public void exceptionCaught(IoSession session, Throwable cause)
				throws Exception {
			cause.printStackTrace();
			super.exceptionCaught(session, cause);
		}

	}

	public void close() {
		acceptor.dispose();
		connector.dispose();
	}

	public void connectClient() {
		if(cf != null){
			cf.cancel();
		}

		try {
			connector = new NioSocketConnector();

			connector.getFilterChain().addLast("logger", new LoggingFilter());
			//指定编码过滤器 
			connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
			// 设定服务器端的消息处理器:一个 SamplMinaServerHandler 对象,
			connector.setHandler(new AcceptorMessageHandler());
			// Set connect timeout.
			connector.setConnectTimeoutCheckInterval(30);
			// 连结到服务器:
			cf = connector.connect(new InetSocketAddress(
					clientIP, Integer.parseInt(clientPort)));
			cf.awaitUninterruptibly(5000);
		} catch (Exception e) {
			CommonUI.error("错误", "连接失败");
		}
	}

	public void connectService(String ip, String port) {
		this.clientIP = ip;
		this.clientPort = port;
		connectClient();
	}
	
	public void disConnect() {
		if(connector == null || connector.isDisposed()){
			connector = null;
			return;
		}
		
		connector.dispose(false);
	}

	public void sendDeviceInfo(String deviceName, boolean deviceInOutType, boolean deviceDisplayAndVoiceInside, boolean deviceDisplayAndVoiceOutside, boolean deviceDisplaySupportChinese) {
		
		try {
			Document dom = DocumentHelper.createDocument();
			Element rootElement = dom.addElement("dongluCarpark");
			Element station = rootElement.addElement("station");
			station.addElement("account").setText("donglu");
			station.addElement("password").setText( "liuhanzhong");
			station.addElement("stationName").setText( "前门岗亭");
			station.addElement("stationIP").setText( HardwareUtil.getLocalIP());
			station.addElement("stationTime").setText( HardwareUtil.formatDateTime(new Date()));
			
			
			Element monitor = rootElement.addElement("monitor");;
			Element device = monitor.addElement("device");
			device.addElement("deviceName").setText(deviceName);
			device.addElement("deviceInOutType").setText(deviceInOutType+"");
			device.addElement("deviceInOutType").setText(deviceDisplayAndVoiceInside+"");
			device.addElement("deviceDisplayAndVoiceOutside").setText(deviceDisplayAndVoiceOutside+"");
			device.addElement("deviceDisplaySupportChinese").setText(deviceDisplaySupportChinese+"");
			serverUI.println_encode("发送消息明文"+dom.getRootElement().asXML());
			String encode = HardwareUtil.encode(dom.getRootElement().asXML());
			Message msg = new Message(MessageType.设备信息, encode);
			cf.getSession().write(msg.toString());
		} catch (Exception e) {
			CommonUI.error("错误", "发送设备信息失败");
			e.printStackTrace();
		}
	}
	
	
	public void changeDecretKey() {
		try{
			Document document = DocumentHelper.createDocument();
			Element dongluCarpark = document.addElement("dongluCarpark");
			Element publicKey = dongluCarpark.addElement("publicKey");
			publicKey.setText(RSAUtils.getPublicKeyString());
			String encode = document.getRootElement().asXML();
			Message msg = new Message(MessageType.交换密钥, encode);
			cf.getSession().write(msg.toString());
		}catch(Exception e){
			CommonUI.error("错误", "交换密钥失败");
			e.printStackTrace();
		}
	}

	public void sendCardNO(String cardNO,String deviceName) {
		try{
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("dongluCarpark");
			root.addAttribute("session_id", session_id);
			Element deviceElement = root.addElement("device");
			deviceElement.addElement("deviceName").setText(deviceName);
			
			root.addElement("cardSerialNumber").setText(cardNO);
			root.addElement("CardReaderID").setText("");
			serverUI.println_encode("发送消息明文"+document.getRootElement().asXML());
			String encode = HardwareUtil.encode(document.getRootElement().asXML());
			Message msg = new Message(MessageType.发送卡号, encode);
			cf.getSession().write(msg.toString());
		}catch(Exception e){
			CommonUI.error("错误", "发送卡片信息失败");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(RSAUtils.getPublicKeyString());
	}

}
