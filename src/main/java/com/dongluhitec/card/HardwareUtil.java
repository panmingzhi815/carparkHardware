package com.dongluhitec.card;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.mina.core.session.IoSession;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dongluhitec.card.message.Message;
import com.dongluhitec.card.message.MessageType;
import com.google.common.base.Strings;

public class HardwareUtil {

	private static final String MSGKEY = "message_prefix";

	private static IoSession currentSession;
	private static String deviceName;
	public static String he_publicKey;

	private static String session_id;

	public static String checkSubpackage(IoSession session, Object message) {
		String msg = ((String) message).trim();

		if (session.getAttribute(MSGKEY) == null) {
			session.setAttribute(MSGKEY, "");
		}
		// 在此处对数据的分包进行拼接
		String oldValue = (String) session.getAttribute(MSGKEY);
		session.setAttribute(MSGKEY, oldValue + msg);
		// 如果数据已经完整,早返回整个数据
		if (msg.endsWith("</dongluCarpark>")) {
			String result = ((String) session.getAttribute(MSGKEY));
			session.removeAttribute(MSGKEY);
			return result;
		}
		// 数据不完整,则返回空
		return null;
	}

	public static void responsePublicKey(IoSession session, Message message) {
		try {
			Document dom = DocumentHelper.parseText(message.getContent());
			Element element = dom.getRootElement().element("publicKey");
			he_publicKey = element.getStringValue();
			element.setText(RSAUtils.getPublicKeyString());
			String encode = dom.getRootElement().asXML();
			Message msg = new Message(MessageType.交换密钥, encode);
			session.write(msg);
		} catch (Exception e) {
			throw new EncryptException("客户端响应公钥失败", e);
		}
	}

	public static void responsePublicKey_server(IoSession session,
			Message message) {
		try {
			Document dom = DocumentHelper.parseText(message.getContent());
			Element element = dom.getRootElement().element("publicKey");
			he_publicKey = element.getStringValue();
		} catch (Exception e) {
			throw new EncryptException("服务端响应公钥失败", e);
		}
	}

	public static String responseDeviceInfo(IoSession session, Document dom) {
		try{
			currentSession = session;
			
			deviceName = dom.getRootElement().element("monitor").element("device")
					.element("deviceName").getText();

			String value = "<dongluCarpark><result>true</result><session_id>"+session.getId()+"</session_id></dongluCarpark>";
			String encode = encode(value);
			Message msg = new Message(MessageType.成功, encode);
			session.write(msg);
			return value;
		}catch(Exception e){
			throw new EncryptException("响应设备信息失败", e);
		}
	}

	public static String responseSwipeCardInfo(IoSession session, Document dom,
			Document dom2) {
		try{
			if (currentSession == null || currentSession.isConnected() == false
					|| Strings.isNullOrEmpty(deviceName)) {
				return "";
			}
			Element rootElement = dom.getRootElement();
			String deviceName = rootElement.element("device").element("deviceName")
					.getText();

			dom2.getRootElement().addElement("device").addElement("deviceName")
					.setText(deviceName);
			String encode = encode(dom2.getRootElement().asXML());
			Message msg = new Message(MessageType.设备控制, encode);
			currentSession.write(msg);
			return dom2.getRootElement().asXML();
		}catch(Exception e){
			throw new EncryptException("响应刷卡", e);
		}
	}

	public static String responseDeviceControl(IoSession session, Document dom) {
		try{
			String value = "<dongluCarpark type=\"result\"><result>true</result></dongluCarpark>";
			String encode = encode(value);
			Message msg = new Message(MessageType.成功, encode);
			session.write(msg);
			return value;
		}catch(Exception e){
			throw new EncryptException("响应设备控制失败", e);
		}
	}

	public static void requestDeviceControl(Document state2Xml) {
		try{
			if (currentSession == null || currentSession.isConnected() == false
					|| Strings.isNullOrEmpty(deviceName)) {
				return;
			}
			Element rootElement = state2Xml.getRootElement();
			Element deviceElement = rootElement.addElement("device");
			deviceElement.addElement("deviceName").setText(deviceName);
			String encode = rootElement.asXML();
			Message msg = new Message(MessageType.设备控制, encode);
			currentSession.write(msg);
		}catch(Exception e){
			throw new EncryptException("请求设备控制失败", e);
		}
	}

	public static String formatDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static String getLocalIP() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			return addr.getHostAddress().toString();
		} catch (Exception e) {
			return null;
		}

	}

	public static void writeMsg(IoSession ioSession, String msg) {
		try {
			ioSession.write(encode(msg));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String decode(String msg) {
		try{
			int indexOf = msg.indexOf(">")+1;
			String subStr = msg.substring(indexOf, msg.length()-16);
			
			String decrypt = RSAUtils.decrypt(subStr, RSAUtils.getPrivateKey());
			String replace = msg.replace(subStr, decrypt);
			return replace;
		}catch(Exception e){
			throw new EncryptException("解密失败", e);
		}
	}

	public static String encode(String msg) {
		try{
			int indexOf = msg.indexOf(">")+1;
			String subStr = msg.substring(indexOf, msg.length()-16);
			String encrypt = RSAUtils.encrypt(subStr, RSAUtils.getPublicKey(he_publicKey));
			String replace = msg.replace(subStr,encrypt);
			return replace;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
	}

	public static void responseSuccess(IoSession session, Document dom) {
		try{
			Element rootElement = dom.getRootElement();
			Element element = rootElement.element("session_id");
			session_id = element.getText();
		}catch(Exception e){
			
		}
	}

}
