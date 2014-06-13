package com.dongluhitec.card;

import org.junit.Assert;
import org.junit.Test;

public class RsaEncryptUtilTest {
	
	@Test
	public void test1() throws Exception{
        String plaintext = "<station><account>donglu</account><password>liuhanzhong</password><stationName>前门岗亭</stationName><stationIP>192.168.1.36</stationIP><stationTime>2014-05-19 10:37:44</stationTime></station><monitor><device><deviceName>测试设备1</deviceName><deviceInOutType>true</deviceInOutType><deviceInOutType>true</deviceInOutType><deviceDisplayAndVoiceOutside>true</deviceDisplayAndVoiceOutside><deviceDisplaySupportChinese>true</deviceDisplaySupportChinese></device></monitor>";
		String cipherText = RSAUtils.encrypt(plaintext, RSAUtils.getPublicKey());
        String plainText = RSAUtils.decrypt(cipherText, RSAUtils.getPrivateKey());
        Assert.assertEquals(plaintext, plainText);
	}
	
	@Test
	public void test2() throws Exception{
        String plaintext = "专门针对中文字符进行测试";
		String cipherText = RSAUtils.encrypt(plaintext, RSAUtils.getPublicKey());
        String plainText = RSAUtils.decrypt(cipherText, RSAUtils.getPrivateKey());
        Assert.assertEquals(plaintext, plainText);
	}
	
	@Test
	public void test3() throws Exception{
        String plaintext = "专门针对长中文字符进行测试";
        for(int i=0;i<10;i++){
        	plaintext += plaintext;
        }
		String cipherText = RSAUtils.encrypt(plaintext, RSAUtils.getPublicKey());
        String plainText = RSAUtils.decrypt(cipherText, RSAUtils.getPrivateKey());
        Assert.assertEquals(plaintext, plainText);
	}
	
	public static void main(String[] args) {
		String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><dongluCarpark session_id=\"1\"><device><deviceName>测试设备1</deviceName></device><cardSerialNumber>NO12345678你</cardSerialNumber><CardReaderID></CardReaderID></dongluCarpark>";
		System.out.println(str.length());
	}
	
}
