package com.dongluhitec.card.clientInUse;

import java.io.UnsupportedEncodingException;
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
import org.apache.mina.util.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.swt.widgets.Display;

import com.dongluhitec.card.CommonUI;
import com.dongluhitec.card.EncryptException;
import com.dongluhitec.card.HardwareUtil;
import com.dongluhitec.card.RSAUtils;
import static com.dongluhitec.card.clientInUse.Constants.DEV_MODE;

public class ClientPresenterClient {

    private static final int PORT = 9008;
    private static String CARDSN = "";
    public ClientUI clientUI;
    private IoAcceptor acceptor;
    private String clientIP;
    private String clientPort;
    private ConnectFuture cf;
    private NioSocketConnector connector;

    public ClientPresenterClient(ClientUI clientUI) {
        this.clientUI = clientUI;
        try {
            acceptor = new NioSocketAcceptor();

            acceptor.getFilterChain().addLast("logger", new LoggingFilter());
            // 指定编码过滤器requestDeviceControl
            // acceptor.getFilterChain().addLast(
            // "codec",
            // new ProtocolCodecFilter(
            // new ObjectSerializationCodecFactory()));
            acceptor.getFilterChain().addLast(
                    "codec",
                    new ProtocolCodecFilter(new TextLineCodecFactory(Charset
                            .forName("GBK"))));
            acceptor.setHandler(new MessageHandler());
            acceptor.getSessionConfig().setReadBufferSize(2048);
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
            acceptor.bind(new InetSocketAddress(PORT));
            clientUI.println("绑定本地服务端口:" + PORT);

        } catch (Exception e) {
            e.printStackTrace();
            CommonUI.error("错误", "开始监听服务器失败!" + PORT);
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
            Object messages = decode(message.toString());
            String checkSubpackage = HardwareUtil.checkSubpackage(session,
                    messages);
            if (checkSubpackage == null) {
                return;
            }
            clientUI.println_encode("收到消息:" + checkSubpackage);
            // checkSubpackage = decode(checkSubpackage);

            if (checkSubpackage
                    .matches("(.*)<deviceName>(.*)</deviceName>(.*)")) {
                checkSubpackage = checkSubpackage.replaceFirst(
                        "<device><deviceName>客户端设备</deviceName></device>", "");
                final Document domCarSN = DocumentHelper
                        .parseText(checkSubpackage);
                // SAXReader saxReader = new SAXReader();
                // Document doc = saxReader.read(checkSubpackage);

                HardwareUtil.requestDeviceControl(domCarSN);
                if (checkSubpackage.startsWith("swipeCard", 21)) {
                    String responseSwipeCardSN = responseSwipeCardSN(session,
                            domCarSN);
                    clientUI.println("发送消息明文:" + responseSwipeCardSN);
                    if (!DEV_MODE) {
                        CARDSN = "";
                    } else {

                    }
                }
                return;
            }

            if (checkSubpackage.startsWith("publicKey", 21)) {
                HardwareUtil.responsePublicKey(session, checkSubpackage);
                return;
            }

            final Document dom = DocumentHelper.parseText(HardwareUtil
                    .decode(checkSubpackage));
            Element rootElement = dom.getRootElement();
            clientUI.println("收到消息明文:" + rootElement.asXML());
            if (checkSubpackage.startsWith("deviceInfo", 21)) {
                String responseDeviceInfo = HardwareUtil.responseDeviceInfo(
                        session, dom);
                clientUI.println("发送消息明文:" + responseDeviceInfo);
                return;
            }

            if (checkSubpackage.startsWith("swipeCard", 21)) {
                Display.getDefault().asyncExec(new Runnable() {

                    public void run() {

                        String responseSwipeCardInfo = HardwareUtil
                                .responseSwipeCardInfo(session, dom,
                                        clientUI.state2Xml());
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
        if (!(connector == null)) {
            connector.dispose();
        }
    }

    public String decode(String str) {
        byte[] bytes;
        bytes = null;
        bytes = str.getBytes(Charset.forName("UTF-8"));
        byte[] decodebytes = Base64.decodeBase64(bytes);
        try {
            str = new String(decodebytes, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void sendDeviceControl() {
        HardwareUtil.requestDeviceControl(clientUI.state2Xml());
    }

    public void sendAD() {
        HardwareUtil.requestAdControl(clientUI.ad2Xml());
    }

    public void connectClient() {
        if (cf != null) {
            cf.cancel();
        }

        try {
            connector = new NioSocketConnector();

            connector.getFilterChain().addLast("logger", new LoggingFilter());
            // 指定编码过滤器
            connector.getFilterChain().addLast(
                    "codec",
                    new ProtocolCodecFilter(
                            new ObjectSerializationCodecFactory()));
            // 设定服务器端的消息处理器:一个 SamplMinaServerHandler 对象,
            connector.setHandler(new MessageHandler());
            // Set connect timeout.
            connector.setConnectTimeoutCheckInterval(30);
            // 连结到服务器:
            cf = connector.connect(new InetSocketAddress(clientIP, Integer
                    .parseInt(clientPort)));
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
        if (connector == null || connector.isDisposed()) {
            connector = null;
            return;
        }

        connector.dispose(false);
    }

    public static String responseSwipeCardSN(IoSession session, Document dom) {

        try {
            Element rootElement = dom.getRootElement();
            Document dom3 = dom;
            dom3.getRootElement().addElement("device")
                    .addElement("cardSerialNumber").setText(CARDSN);
            try {
                session.write(dom3.getRootElement().asXML());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dom3.getRootElement().asXML();
        } catch (Exception e) {
            throw new EncryptException("响应刷卡", e);
        }
    }

    public static synchronized void CardSN(String CardSN) {
        CARDSN = CardSN;
    }

    public static synchronized String getCardSN() {
        if (CARDSN.length() > 8) {
            return CARDSN.substring(0, 8);
        }
        return CARDSN;
    }
}
