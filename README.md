# encrypt-decrypt-example
The repository contains a project which shows one of the most secure and simplest ways to encrypt and decrypt data using AES/GCM.

# How to generate keystore
`keytool -genseckey -keystore <keystore-name> -storetype jceks -storepass "<keystore-password>" -keyalg AES -keysize 128 -alias <key-alias> -keypass "<key-password>"`