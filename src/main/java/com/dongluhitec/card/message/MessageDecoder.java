package com.dongluhitec.card.message;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.google.common.base.Charsets;

public class MessageDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		//先标记当前buffer的位置
        in.mark();

        //判断当前buffer长度位是否发送过来
        if (in.remaining() <= 10) {
            return false;
        }

        // 有数据时，根据定义好的字节个数判断消息长度  
        byte[] sizeBytes = new byte[10];
        in.get(sizeBytes);
        // 报文长度
        String lengthStr = new String(sizeBytes,Charsets.UTF_8);
        int size = Integer.valueOf(lengthStr.substring(2));

        // 判断报文
        if (size <= in.remaining()) {
            //报文接收完毕
        	byte[] arr = new byte[size];
        	in.get(arr);
        	Message msg = new Message(MessageType.parse(lengthStr.subSequence(0, 2)),size,new String(arr,Charsets.UTF_8));
        	out.write(msg);
            return true;
        }

        // 由于in.get会改变in游标的位置，所以reset到初始位置，也可以in.position(in.markValue());
        in.reset();
        return false;
	}


}
