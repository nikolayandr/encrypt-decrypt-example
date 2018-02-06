# encrypt-decrypt-example
The repository contains a project which shows secure but still simple way to encrypt and decrypt data using AES/GCM.
One of the key features is adding IV to the beginning of encrypted values what provides possibilities to use random IV for encrypting values every time when a value is encrypted. Such approach significantly improves resistance of encrypting

# How to build the project
1. Run the command `mvn package`

# How to use generated jar
1. Go to the directory *target*
2. To encrypt `java -jar encrypt-decrypt-example-1.0-SNAPSHOT.jar 12 128 encrypt Hi`
3. To decrypt `java -jar encrypt-decrypt-example-1.0-SNAPSHOT.jar 12 128 decrypt 9afjH796yPm91-L1c4mzhPH9HfW1bKXqzwg2z3ba`

# How to generate keystore
`keytool -genseckey -keystore <keystore-name> -storetype jceks -storepass "<keystore-password>" -keyalg AES -keysize 128 -alias <key-alias> -keypass "<key-password>"`


More information about AES/GCM can be found here: https://en.wikipedia.org/wiki/Galois/Counter_Mode