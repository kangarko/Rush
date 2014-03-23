package net.rush.util;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import java.util.logging.Level;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import net.rush.Server;

/**
 * Utility class for performing encrypted authentication
 */
public class SecurityUtils {

    private static Random random = new Random();

    /**
     * Generate a RSA key pair
     */
    public static KeyPair generateKeyPair() {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);

            keyPair = generator.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            Server.getLogger().log(Level.SEVERE, "Unable to generate RSA key pair: {0}", ex.getMessage());
        }
        return keyPair;
    }

    /**
     * Generate a random verify token
     */
    public static byte[] generateVerifyToken() {
        byte[] token = new byte[4];
        random.nextBytes(token);
        return token;
    }

    /**
     * Generates an AES cipher for use with authentication
     */
    public static Cipher generateAESCipher(int opMode, Key key) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(opMode, key, new IvParameterSpec(key.getEncoded()));
        } catch (GeneralSecurityException ex) {
        	Server.getLogger().log(Level.SEVERE, "Unable to generate AES cipher: {0}", ex.getMessage());
        }
        return cipher;
    }

    /**
     * Generates an RSA cipher for use with authentication
     */
    public static Cipher generateRSACipher(int opMode, Key key) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(opMode, key);
        } catch (GeneralSecurityException ex) {
        	Server.getLogger().log(Level.SEVERE, "Unable to generate RSA cipher: {0}", ex.getMessage());
        }
        return cipher;
    }



    /**
     * Generates a message digest used in hashing
     */
    public static MessageDigest generateSHA1MessageDigest() {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException ex) {
        	Server.getLogger().log(Level.SEVERE, "Unable to generate SHA-1 digest: {0}", ex.getMessage());
        }
        return digest;
    }

    /**
     * Generates an X509 formatted key used in authentication
     */
    public static Key generateX509Key(Key base) {
        Key key = null;
        try {
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(base.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            key = keyFactory.generatePublic(encodedKeySpec);
        } catch (Exception ex) {
        	Server.getLogger().log(Level.SEVERE, "Unable to generate X509 encoded key: {0}", ex.getMessage());
        }
        return key;
    }




}
