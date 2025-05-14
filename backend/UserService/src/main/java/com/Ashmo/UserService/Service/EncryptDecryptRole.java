package com.Ashmo.UserService.Service;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecryptRole {

    private static final String ALG = "AES";
    private static final SecretKeySpec SEC_KEY = new SecretKeySpec("EncryptDecryptRo".getBytes(), ALG);

    public static String encrypt(String role){

        try {
            Cipher cipher = Cipher.getInstance(ALG);
            cipher.init(cipher.ENCRYPT_MODE, SEC_KEY);
            byte[] encryptedBytes = cipher.doFinal(role.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting role", e);
        }
    }

    public static String decrypt(String encryptedRole){

        try {
            Cipher cipher = Cipher.getInstance(ALG);
            cipher.init(cipher.DECRYPT_MODE, SEC_KEY);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedRole);
            return new String(cipher.doFinal(decodedBytes));
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting role", e);
        }
    }
}
