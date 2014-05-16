package com.dongluhitec.card;

import org.junit.Assert;
import org.junit.Test;

public class RsaEncryptUtilTest {

	/**
	 * 小长度测试
	 */
	@Test
	public void test1(){
		String plainText = "1234567890";

		System.out.println("加密内容:"+plainText);
		System.out.println("加密长度:"+plainText.length());
		
		RsaEncryptUtil instance = RsaEncryptUtil.getInstance();
		byte[] encode = instance.encode(plainText.getBytes(), instance.getPublicKey());
		byte[] decode = instance.decode(encode);
		Assert.assertEquals(plainText, new String(decode));
	}
	
	/**
	 * 更长度的分片测试
	 */
	@Test
	public void test2(){
		String plainText = "";
		for(int i=0;i<500;i++){
			plainText += i;
		}
		System.out.println("加密内容:"+plainText);
		System.out.println("加密长度:"+plainText.length());
		
		RsaEncryptUtil instance = RsaEncryptUtil.getInstance();
		byte[] encode = instance.encode(plainText.getBytes(), instance.getPublicKey());
		byte[] decode = instance.decode(encode);
		Assert.assertEquals(plainText, new String(decode));
	}
	
	@Test
	public void test3(){
		String plainText = "1234567890";

		System.out.println("加密内容:"+plainText);
		System.out.println("加密长度:"+plainText.length());
		
		RsaEncryptUtil instance = RsaEncryptUtil.getInstance();
		String encode = instance.encode(plainText, instance.getPublicKey());
		String decode = instance.decode(encode);
		Assert.assertEquals(plainText, decode);
	}
	
	/**
	 * 更长度的分片测试
	 */
	@Test
	public void test4(){
		String plainText = "";
		for(int i=0;i<3;i++){
			plainText += "<a>123</a>";
		}
		System.out.println("加密明文内容:"+plainText);
		System.out.println("加密明文长度:"+plainText.length());
		
		RsaEncryptUtil instance = RsaEncryptUtil.getInstance();
		String encode = instance.encode(plainText, instance.getPublicKey());
		System.out.println("密文内容:"+encode);
		String decode = instance.decode(encode);
		Assert.assertEquals(plainText, decode);
	}
	
}
