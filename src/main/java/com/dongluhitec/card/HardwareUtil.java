package com.dongluhitec.card;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.mina.core.session.IoSession;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.google.common.base.Strings;

public class HardwareUtil {

	private static final String MSGKEY = "message_prefix";

	private static IoSession currentSession;
	private static String deviceName;
	private static String publicKey;

	public static String checkSubpackage(IoSession session, Object message) {
		String msg = ((String) message).trim();
		if (msg.startsWith("<dongluCarpark")) {
			session.setAttribute(MSGKEY, "");
		}
		// 在此处对数据的分包进行拼接
		String oldValue = (String) session.getAttribute(MSGKEY);
		session.setAttribute(MSGKEY, oldValue + msg);
		// 如果数据已经完整,早返回整个数据
		if (msg.endsWith("</dongluCarpark>")) {
			return (String) session.getAttribute(MSGKEY);
		}
		// 数据不完整,则返回空
		return null;
	}

	public static void responsePublicKey(IoSession session, String message) {
		try {
			Document dom = DocumentHelper.parseText(message);
			Element element = dom.getRootElement().element("publicKey");
			publicKey = element.getStringValue();
			element.setText(RsaEncryptUtil.getInstance().getPublicKey());
			session.write(dom.getRootElement().asXML());
		} catch (Exception e) {
			throw new EncryptException("客户端响应公钥失败", e);
		}
	}

	public static void responsePublicKey_server(IoSession session,
			String message) {
		try {
			Document dom = DocumentHelper.parseText(message);
			Element element = dom.getRootElement().element("publicKey");
			publicKey = element.getStringValue();
		} catch (Exception e) {
			throw new EncryptException("服务端响应公钥失败", e);
		}
	}

	public static void responseDeviceInfo(IoSession session, Document dom) {
		currentSession = session;
		deviceName = dom.getRootElement().element("monitor").element("device")
				.element("deviceName").getText();

		String value = getValue(XmlTypeEnum.发送成功);
		writeMsg(session, value);
	}

	public static void responseSwipeCardInfo(IoSession session, Document dom,
			Document dom2) {
		Element rootElement = dom.getRootElement();
		String deviceName = rootElement.element("device").element("deviceName")
				.getText();

		dom2.getRootElement().addElement("device").addElement("deviceName")
				.setText(deviceName);
		;
		writeMsg(session, dom2.getRootElement().asXML());
	}

	public static void responseDeviceControl(IoSession session, Document dom) {
		String value = getValue(XmlTypeEnum.发送成功);
		writeMsg(session, value);
	}

	public static void requestDeviceControl(Document state2Xml) {
		if (currentSession == null || currentSession.isConnected() == false
				|| Strings.isNullOrEmpty(deviceName)) {
			return;
		}
		Element rootElement = state2Xml.getRootElement();
		Element deviceElement = rootElement.addElement("device");
		deviceElement.addElement("deviceName").setText(deviceName);
		writeMsg(currentSession, rootElement.asXML());
	}

	public static String getValue(XmlTypeEnum xmlTypeEnum) {
		switch (xmlTypeEnum) {
		case 交换密钥:
			return "<dongluCarpark><publicKey>publicKey</publicKey></dongluCarpark>";
		case 发送设备信息:
			String value = "<dongluCarpark>"
					+ "<station><account>dongluhitec</account><password>liuhanzhong</password><stationName>前门岗亭</stationName><stationIP>192.168.1.36</stationIP><stationTime>2014-12-22 12:23:30</stationTime></station>"
					+ "<monitor>"
					+ "<device>"
					+ "<deviceName>设备名称1</deviceName>"
					+ "<deviceInOutType>in</deviceInOutType>"
					+ "<deviceDisplayAndVoiceInside>true</deviceDisplayAndVoiceInside>"
					+ "<deviceDisplayAndVoiceOutside>true</deviceDisplayAndVoiceOutside>"
					+ "<deviceDisplaySupportChinese>true</deviceDisplaySupportChinese>"
					+ "</device>" + "</monitor>" + "</dongluCarpark>";
		case 发送成功:
			return "<dongluCarpark type=\"result\"><result>true</result></dongluCarpark>";
		default:
			break;
		}
		return "";
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
			
			byte[] decodeBuffer = new BASE64Decoder().decodeBuffer(subStr);
			RsaEncryptUtil instance = RsaEncryptUtil.getInstance();
			String decode = instance.decode(new String(decodeBuffer,"ISO-8859-1"));
			String replace = msg.replace(subStr, decode);
			return replace;
		}catch(Exception e){
			throw new EncryptException("解密失败", e);
		}
	}

	public static String encode(String msg) {
		try{
			int indexOf = msg.indexOf(">")+1;
			String subStr = msg.substring(indexOf, msg.length()-16);
			RsaEncryptUtil instance = RsaEncryptUtil.getInstance();
			String encode = instance.encode(subStr, instance.getPublicKey());
			String encode2 = new BASE64Encoder().encode(encode.getBytes("ISO-8859-1"));
			String replace = msg.replace(subStr,encode2);
			return replace;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
	}

}
