package com.example.tecnichalaprobar.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class Utilitarios {

    private static final String key = "Dz95mH1tOySrMlGLhUaAAA==";

    /**
     * Aplica la encriptacion AES a la cadena de texto usando la clave indicada
     * @param datos Cadena a encriptar
     * @return Información encriptada
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encriptar(String datos) throws NoSuchAlgorithmException,
            InvalidKeyException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(key.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] datosEncriptar = datos.getBytes(StandardCharsets.UTF_8);
        byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
        return SpecialCharacter.changeString(Base64.encodeBase64String(bytesEncriptados));
    }

    public static String desencriptar(String pStringEncrypted) {
        pStringEncrypted = SpecialCharacter.deschangeString(pStringEncrypted);
        String result = null;
        try {
            byte[] encryptedBytes = decodeFromBase64(pStringEncrypted);

            if (encryptedBytes.length == 0) {
                String stringEncryted = URLDecoder.decode(pStringEncrypted, "UTF-8");
                encryptedBytes = decodeFromBase64(stringEncryted);
            }

            byte[] keyBytes = java.util.Base64.getDecoder().decode(key.getBytes(StandardCharsets.UTF_8));
            final SecretKeySpec skeySpec = new SecretKeySpec(keyBytes,"AES");

            // Create a new Cipher instance to do decryption using ALGORITHM
            final Cipher encryptCipher= Cipher.getInstance("AES");
            encryptCipher.init(Cipher.DECRYPT_MODE, skeySpec);

            byte[] original = encryptCipher.doFinal(encryptedBytes);

            result = new String(original);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static byte[] decodeFromBase64(String pStringEncrypted) {
        try {
            return java.util.Base64.getDecoder().decode(pStringEncrypted.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalArgumentException e) {
            return new byte[]{};
        }

    }
}
