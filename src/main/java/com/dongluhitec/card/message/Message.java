package com.dongluhitec.card.message;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;

public class Message implements Serializable{
	
	private static final long serialVersionUID = -2992809324781810721L;
	
	private MessageType type;
	private Integer length;
	private String content;
	
	public Message(String msg){
		this.type = MessageType.parse(msg.subSequence(0, 2));
		this.content = msg.substring(10).replace("<?xml version=\"1.0\" encoding=\"GBK\"?>", "");
		try {
			this.length = content.getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	} 
	
	public Message(MessageType type,String content) {
		this.type = type;
		this.content = content.replace("<?xml version=\"1.0\" encoding=\"GBK\"?>", "");
	}
	
	public MessageType getType() {
		return type;
	}
	
	public Integer getLength() {
		return length;
	}
	
	public String getContent() {
		return content;
	}

	public byte[] toBytes(){
		String format = String.format("%08d", length);
		return (type+format+content).replaceAll("[\\t\\n\\r]", "").getBytes(Charset.forName("UTF-8"));
	}

	@Override
	public String toString() {
		if(!content.startsWith("<?xml version=\"1.0\" encoding=\"GBK\"?>")){			
			content = "<?xml version=\"1.0\" encoding=\"GBK\"?>"+content;
		}
		try {
			this.length = content.getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String format = String.format("%08d", length);
		return type+format+content;
	}
	
}
