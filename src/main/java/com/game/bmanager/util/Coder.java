package com.game.bmanager.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.util.encoders.Hex;

public class Coder {
	public static final String KEY_SHA="SHA";
	public static final String KEY_MD5="MD5";
	/**
	 * Hex加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encryptHex(byte[] data)throws Exception{
		return new String(Hex.encode(data));
	}
	/**
	 * MD5加密
	 * @param data
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance(KEY_MD5);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		md5.update(data);
		return md5.digest();
	}
}
