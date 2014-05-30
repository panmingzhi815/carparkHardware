package com.dongluhitec.card.message;

public enum MessageType {

	交换密钥("00"),设备信息("01"),发送卡号("02"),设备控制("03"),成功("04");
	
	private String code;
	MessageType(String code){
		this.code = code;
	}
	
	public String toString(){
		return code;
	}

	public static MessageType parse(CharSequence subSequence) {
		MessageType[] values = MessageType.values();
		for (MessageType messageType : values) {
			if(messageType.toString().equals(subSequence)){
				return messageType;
			}
		}
		return null;
	}
}
