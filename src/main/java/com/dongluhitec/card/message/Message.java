package com.dongluhitec.card.message;

import java.io.Serializable;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;

public class Message implements Serializable{
	
	private static final long serialVersionUID = -2992809324781810721L;
	
	private MessageType type;
	private Integer length;
	private String content;
	
	public Message(String msg){
		this.type = MessageType.parse(msg.subSequence(0, 2));
		this.length = Integer.valueOf(msg.substring(2, 10));
		this.content = msg.substring(10);
	} 
	
	public Message(MessageType type, Integer length, String content) {
		this.type = type;
		this.length = length;
		this.content = content;
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
		return (type+format+content).replaceAll("[\\t\\n\\r]", "").getBytes(Charsets.UTF_8);
	}

	@Override
	public String toString() {
		String format = String.format("%08d", length);
		return type+format+content;
	}
	
	
}
