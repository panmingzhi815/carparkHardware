package com.dongluhitec.card.message;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class MessageEncoder extends ProtocolEncoderAdapter{

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		if(!(message instanceof Message)){
			throw new MessageException("消息编码失败!不能识别当前消息类型");
		}
		
		Message request = (Message) message;
		byte[] bytes = request.toBytes();

		IoBuffer buffer = IoBuffer.allocate(bytes.length, false);
		buffer.put(bytes);

		buffer.flip();
		out.write(buffer);
		out.write(((Message) message).toBytes());
		
	}

}
