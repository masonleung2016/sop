package dwz.common.util.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import dwz.common.util.CommonUtils;

/**
 * @Author: LCF
 * @Date: 2020/1/8 15:31
 * @Package: dwz.common.util.encrypt
 */

public final class StringEncryptUtil {
    /**
     * The Default Key.
     */
    public static final String DEFAULT_KEY = "asdfsadf@#$%^$%^%^&*&asdf24243423234";
//	不能使用PBEWithMD5AndTripleDES，只能使用DES
    /**
     *
     */
    private static final String ALGORITHM = "DES";

    public static String encrypt(final String originalString) throws Exception {
        byte[] bEn = encrypt(originalString.getBytes(), DEFAULT_KEY.getBytes());
        return CommonUtils.parseHexStringFromBytes(bEn);
    }

    public static String encrypt(final String originalString, final String key) throws Exception {
        byte[] bEn = encrypt(originalString.getBytes(), key.getBytes());
        return CommonUtils.parseHexStringFromBytes(bEn);
    }

    private static byte[] encrypt(byte[] originalByte, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey keySpec = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, sr);
        return cipher.doFinal(originalByte);

    }

    public static String decrypt(final String encryptedString) throws Exception {
        byte[] bEn = CommonUtils.parseBytesByHexString(encryptedString);
        byte[] orginal = decrypt(bEn, DEFAULT_KEY.getBytes());
        return new String(orginal);
    }

    public static String decrypt(final String encryptedString, final String key) throws Exception {
        byte[] bEn = CommonUtils.parseBytesByHexString(encryptedString);
        byte[] orginal = decrypt(bEn, key.getBytes());
        return new String(orginal);
    }

    private static byte[] decrypt(byte[] encryptedByte, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey keySpec = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, sr);
        return cipher.doFinal(encryptedByte);
    }

    public static void main(String[] args) {
        String username_id = "石鹏皮皮@126.com_ff8080812f0b663d012f0c95d4990016";
        try {
            String cookieValue = StringEncryptUtil.encrypt(username_id);
            System.out.println("encrypt: " + cookieValue);
            String value = StringEncryptUtil.decrypt(cookieValue);
            System.out.println("decrypt: " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
