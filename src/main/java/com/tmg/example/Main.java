package com.tmg.example;

import com.tmg.example.aes.ValueEncoderDecoder;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;


/**
 * Created by nikolay.androsovich@gmail.com
 */
public class Main
{
	/** Encrypt value mode */
	private static final String ENCRYPT = "encrypt";

	/** Decrypt value mode */
	private static final String DECRYPT = "decrypt";

	/** Keystore password */
	private static final String STORE_PASS = "DWCyAJc7yxqaUtG6BaMPYx";

	/** Keystore type */
	private static final String STORE_TYPE = "JCEKS";

	/** Secret key alias */
	private static final String KEY_ALIAS = "jceksaes";

	/** Secret key password */
	private static final String KEY_PASS = "BYKbWusn3FcQXpvSJHVcVxeQLDMSUWMYVzKKnQQnD5M";


	public static void main(final String[] args) throws Exception
	{
		final Integer ivSize = Integer.valueOf(args[0]);
		final Integer gcmTagSize = Integer.valueOf(args[1]);
		final String mode = args[2];
		final String testedValue = args[3];

		final ValueEncoderDecoder valueEncoderDecoder = new ValueEncoderDecoder(getKey(), ivSize, gcmTagSize);

		switch (mode)
		{
			case ENCRYPT:
				final String encryptedValue = valueEncoderDecoder.encodeValue(testedValue);
				System.out.println("Encrypted value is: " + encryptedValue);
				break;
			case DECRYPT:
				final String decryptedValue = valueEncoderDecoder.decodeValue(testedValue);
				System.out.println("Decrypted value is: " + decryptedValue);
				break;
			default:
				System.out.println("Mode: " + mode + " isn't supported");
		}
	}

	/**
	 * Gets secret key
	 *
	 * @return secret key
	 */
	private static Key getKey() throws Exception
	{
		return getKeyStore().getKey(KEY_ALIAS, KEY_PASS.toCharArray());
	}

	private static KeyStore getKeyStore() throws Exception
	{
		final InputStream is = Main.class.getResourceAsStream("/aes-keystore.jck");
		final KeyStore keystore = KeyStore.getInstance(STORE_TYPE);
		keystore.load(is, STORE_PASS.toCharArray());
		return keystore;

	}
}
