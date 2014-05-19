package com.dongluhitec.card.client;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.swt.widgets.Display;

import com.dongluhitec.card.CommonUI;
import com.dongluhitec.card.HardwareUtil;

public class ClientPresenter {

	private static final int PORT = 9123;
	public ClientUI clientUI;
	private IoAcceptor acceptor;

	public ClientPresenter(ClientUI clientUI) {
		this.clientUI = clientUI;
		try {
			acceptor = new NioSocketAcceptor();

			acceptor.getFilterChain().addLast("logger", new LoggingFilter());
			//指定编码过滤器 
			acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
			acceptor.setHandler(new MessageHandler());
			acceptor.getSessionConfig().setReadBufferSize(2048);
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
			acceptor.bind(new InetSocketAddress(PORT));
			clientUI.println("绑定本地服务端口:" + PORT);
		} catch (Exception e) {
			e.printStackTrace();
			CommonUI.error("错误", "开始监听服务器失败!");
		}
	}

	class MessageHandler extends IoHandlerAdapter {

		@Override
		public void sessionCreated(IoSession session) throws Exception {
			clientUI.println("创建会话:" + session.getRemoteAddress());
			super.sessionCreated(session);
		}

		@Override
		public void messageReceived(final IoSession session, Object message)
				throws Exception {
			String checkSubpackage = HardwareUtil.checkSubpackage(session, message);
			if(checkSubpackage == null){
				return;
			}
			clientUI.println_encode("收到消息密文:" + checkSubpackage);
			if(checkSubpackage.startsWith("publicKey", 21)){
				HardwareUtil.responsePublicKey(session,checkSubpackage);
				return;
			}
			
			final Document dom = DocumentHelper.parseText(HardwareUtil.decode(checkSubpackage));
			Element rootElement = dom.getRootElement();
			clientUI.println("收到消息明文:" + rootElement.asXML());
			if(checkSubpackage.startsWith("deviceInfo", 21)){
				String responseDeviceInfo = HardwareUtil.responseDeviceInfo(session,dom);
				clientUI.println("发送消息明文:"+responseDeviceInfo);
				return;
			}
			
			if(checkSubpackage.startsWith("swipeCard",21)){
				Display.getDefault().asyncExec(new Runnable() {
					
					public void run() {
						String responseSwipeCardInfo = HardwareUtil.responseSwipeCardInfo(session,dom,clientUI.state2Xml());
						clientUI.println("发送消息明文:" + responseSwipeCardInfo);
					}
				});				
				return;
			}
		}

		@Override
		public void messageSent(IoSession session, Object message)
				throws Exception {
			clientUI.println_encode("发送消息:" + message);
		}

		@Override
		public void sessionClosed(IoSession session) throws Exception {
			clientUI.println("关闭会话:" + session.getRemoteAddress());
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
	}

	public void sendDeviceControl() {
		HardwareUtil.requestDeviceControl(clientUI.state2Xml());
	}

}
