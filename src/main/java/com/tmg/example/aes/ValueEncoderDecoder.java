package com.tmg.example.aes;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by nikolay.androsovich@gmail.com
 */
public class ValueEncoderDecoder
{

	/** Since we use block cipher to encrypt/decrypt values we need to specify how to fill up the blocks */
	private static final String PADDING = "NoPadding";

	/** The name of algorithm which was used to create secret key */
	private static final String ALGORITHM = "AES";

	/** Used to configure cipher */
	private static final String TRANSFORMATION = "AES/GCM/";

	/** The name of algorithm which is used to generate random values */
	private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";

	/**
	 * Active key
	 */
	private final Key key;

	/** The length of initialization vector */
	private final int ivLength;

	/** GCM tag size */
	private final int gcmTagSize;

	/**
	 *
	 * @param secretKey
	 *           secret key
	 * @param ivLength
	 *           The length of initialization vector
	 * @param gcmTagSize
	 *           GCM tag size
	 */
	public ValueEncoderDecoder(final Key secretKey, int ivLength, int gcmTagSize)
	{
		this.key = secretKey;
		this.gcmTagSize = gcmTagSize;
		this.ivLength = ivLength;
	}

	/**
	 * Encodes <code>valueToBeEncoded</code> using AES/GCM
	 * 
	 * @param valueToBeEncoded
	 *           value to be encoded
	 * @return encoded value
	 */
	public String encodeValue(final String valueToBeEncoded) throws Exception
	{

		final SecureRandom r = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
		// Generate IV for Encryption. IV always should be unique for each encryption to get max of security
		final byte[] iv = new byte[this.ivLength];
		r.nextBytes(iv);

		final Cipher c = getCipher(iv, Cipher.ENCRYPT_MODE);
		final byte[] es = c.doFinal(valueToBeEncoded.getBytes(StandardCharsets.UTF_8));

		// Construct Output as "IV + CIPHERTEXT"
		final byte[] os = new byte[this.ivLength + es.length];
		System.arraycopy(iv, 0, os, 0, this.ivLength);
		System.arraycopy(es, 0, os, this.ivLength, es.length);

		// Return a Base64 Encoded String
		return Base64.getUrlEncoder().withoutPadding().encodeToString(os);
	}

	/**
	 * Decodes <code>valueToBeDecoded</code> using AES/GCM
	 * 
	 * @param valueToBeDecoded
	 *           value to be decoded
	 * @return decoded value
	 */
	public String decodeValue(final String valueToBeDecoded) throws Exception
	{
		byte[] ivAndEs = Base64.getUrlDecoder().decode(valueToBeDecoded);
		byte[] iv = Arrays.copyOfRange(ivAndEs, 0, this.ivLength);

		final Cipher cipher = getCipher(iv, Cipher.DECRYPT_MODE);
		byte[] es = Arrays.copyOfRange(ivAndEs, this.ivLength, ivAndEs.length);
		byte[] ds = cipher.doFinal(es);
		return new String(ds, StandardCharsets.UTF_8);

	}

	/**
	 * Creates new cipher
	 *
	 * @param iv
	 *           initialization vector
	 * @return cipher, configured for specified mode and IV
	 */
	private Cipher getCipher(final byte[] iv, final int mode) throws Exception
	{
		final Cipher cipher = Cipher.getInstance(TRANSFORMATION + PADDING);
		final SecretKeySpec dks = new SecretKeySpec(this.key.getEncoded(), ALGORITHM);
		cipher.init(mode, dks, new GCMParameterSpec(this.gcmTagSize, iv));
		return cipher;
	}
}
