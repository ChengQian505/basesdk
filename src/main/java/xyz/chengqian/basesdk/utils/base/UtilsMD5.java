package xyz.chengqian.basesdk.utils.base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtilsMD5 {
    /**Determine encrypt algorithm MD5*/
    private static final String ALGORITHM_MD5 = "MD5";
    /**UTF-8 Encoding*/
    private static final String UTF_8 = "UTF-8";

    /**
     * MD5 32bit Encrypt Methods.
     * @param readyEncryptStr ready encrypt string
     * @return String encrypt result string
     * @throws NoSuchAlgorithmException
     * */
    private static String md5_32(String readyEncryptStr) throws NoSuchAlgorithmException{
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = readyEncryptStr.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
    public static String md5Lower16(String encryptStr) throws NoSuchAlgorithmException {
        return md5_32(encryptStr).substring(8, 24).toLowerCase();
    }
    public static String md5Lower32(String encryptStr) throws NoSuchAlgorithmException {
        return md5_32(encryptStr).toLowerCase();
    }

}
