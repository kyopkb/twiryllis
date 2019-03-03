package twiryllis.cipher;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Classe that encrypt and decrypt using AES
 * 
 * @author suka-kiyo
 * @since 0.1
 */
public class CipherManager {

	/**
	 * To encrypt
	 * 
	 * @param source  the target character string
	 * @param key  the key used for encryption
	 * @param algorithm  the algorithm used for encryption
	 * @return encrypted character string
	 * @since 0.1
	 */
	public static String encrypt(String source, String key, String algorithm) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), algorithm));
		return new String(Base64.getEncoder().encode(cipher.doFinal(source.getBytes())));
	}

	/**
	 * To decrypt
	 * 
	 * @param source  the target character string
	 * @param key  the key used for decryption
	 * @param algorithm  the algorithm used for decryption
	 * @return decrypted character string
	 * @since 0.1
	 */
	public static String decrypt(String encryptSource, String key, String algorithm) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), algorithm));
		return new String(cipher.doFinal(Base64.getDecoder().decode(encryptSource.getBytes())));
	}

}
