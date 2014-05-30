package com.dongluhitec.card.message;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MessageCodecFactory implements ProtocolCodecFactory{
	
	private MessageEncoder messageEncoder = new MessageEncoder();
	private MessageDecoder messageDecoder = new MessageDecoder();

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return messageEncoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return messageDecoder;
	}

	
	
}
