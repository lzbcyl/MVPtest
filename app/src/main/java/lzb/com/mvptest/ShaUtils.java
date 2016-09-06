package lzb.com.mvptest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaUtils {
	public static final String ALGORITHM = "SHA-256";

	public static String SHA256Encrypt(String orignal) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (null != md) {
			byte[] origBytes = orignal.getBytes();
			md.update(origBytes);
			byte[] digestRes = md.digest();
			String digestStr = getDigestStr(digestRes);
			return digestStr;
		}

		return null;
	}

	private static String getDigestStr(byte[] origBytes) {
		String tempStr = null;
		StringBuilder stb = new StringBuilder();
		for (int i = 0; i < origBytes.length; i++) {
			tempStr = Integer.toHexString(origBytes[i] & 0xff);
			if (tempStr.length() == 1) {
				stb.append("0");
			}
			stb.append(tempStr);

		}
		return stb.toString();
	}

	/**
	 * 简单异或加密解密算法
	 * 
	 * @param str
	 *            要加密的字符串
	 * @return
	 */
	public static String encode2(String str) {
		int code = 48; // 密钥
		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			charArray[i] = (char) (charArray[i] ^ code);
		}
		return new String(charArray);
	}
}
