# J-Crypt
> By JayMar921
### Encryption/Decryption - University Project
J-Crypt is a Java Program that encrypt/decrypts a String. You can use your own key for strong encryption, there is also an option for generating a key.
For best Encryption, I highly recommend to make your own keys that is 15 up characters in length, it should contain Symbols, Upper/Lower case Characters and numbers. example: `Th!$keyI$N0t$ecured`. The more characters you add, the complexity of character obfuscation increases.

![Figure 1-1](Documentation/Options.png "Options")
### If you are using windows
- install git and download [this](https://www.mediafire.com/file/c7q8ei1rrf4uazp/executeJarWindows.ps1/file). Run it with Powershell
### Clone
- `git clone https://github.com/jaymar921/J-Crypt`
### go to directory
- `cd J-Crypt`
### Run the Jar
There are two options to run the Jar file
- run the `executeJar.sh` 
- run it in console with this command `java -jar JCrypt.jar`

![Figure 1-1](Documentation/Try_Decrypt_File.png "Decrypt File")

### Options
> - Encrypt and Decrypt text option, the String will be typed by the user. The encryption and decription will use the key that was generated/entered upon program start.
> - Encrypt and Decrypt file option, the user will provide an absolute path for the file to use. The encryption and decription will use the key that was generated/entered upon program start.
> - Try Decrypt text option, the String will be typed by the user, the user can provide a key dictionary or just use random for decryption process to start.
> - Try Decrypt file option, it will require an absolute path for the file to use, the user can use a key dictionary or random keys for decryption process, you can provide a word dictionary that is space separated for matching purpose. You can set the match percentage, 5% to 100%, I recommend at least (80%)
> ### NOTE:
> - use the 'Try Decrypt Text/File' option if you think that the encrypted file uses the program generated key.
### Use this program in your system?
[![](https://jitpack.io/v/jaymar921/J-Crypt.svg)](https://jitpack.io/#jaymar921/J-Crypt)
#### Maven Repository
```maven
<repositories>
	<repository>
		 <id>jitpack.io</id>
		 <url>https://jitpack.io</url>
	</repository>
</repositories>
  ```
#### Maven Dependency
```maven
<dependency>
	 <groupId>com.github.jaymar921</groupId>
	 <artifactId>J-Crypt</artifactId>
	 <version>-41f854c657-1</version>
</dependency>
```
#### Gradle, put this at the end of your repositories
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
#### Gradle, add the dependency
```gradle
dependencies {
	 implementation 'com.github.jaymar921:J-Crypt:-41f854c657-1'
}
```
## Using the Encryptor
```java
Encryptor encryptor; //create a variable encryptor from Encryptor class
encryptor = new Encryptor(8); // Generates a random key with 8 characters, it prompts when it generates a key
encryptor = new Encryptor(8, true); // Generates a random key with 8 characters, it will not prompt when it generates a key
encryptor = new Encryptor("Anonymous155"); // Provides a key 'Anonymous155', the program promts if it's a good key else it will generate a random
encryptor = new Encryptor("Anonymous155", true); // Provides a key 'Anonymous155', the program will not prompt if its a good key, it will not generate a random key
```
## Encrypt a string
```java
String message = "Hello World"; //'Hello World' will be the test message to encrypt
String encrypted_message = encryptor.getEncryption(message); // the getEncryption(str) returns the encrypted value from the string argument
System.out.println(encrypted_message); //print the encrypted message
```
Encrypted message of `Hello World` will be `58 29 36 36 119 § 73 119 42 36 28 ` using the key `Anonymous155`
## Decrypt a string
```java
String message = "58 29 36 36 119 § 73 119 42 36 28"; //'58 29 36 36 119 § 73 119 42 36 28' will be the test message to decrypt
String decrypted_message = encryptor.getDecryption(message); //the getDecryption(str) returns the decrypted value from the string argument
System.out.println(decrypted_message); //print the decrypted message
```
Decrypted message of `58 29 36 36 119 § 73 119 42 36 28` will be `Hello World` using the key `Anonymous155`
## Using HexConvert
you can use hex values as a key
```java
/*
  Using the hex value as a key, passing true on second parameter to turn off
  key checking
*/
String hex_key = HexConvert.toHex("Anonymous155"); //Convert String to hex values
encryptor = new Encryptor(hex_key, true);
/*
  Convert back the hex value to original String
*/
String original_string = HexConvert.fromHex("416e6f6e796d6f7573313535");
```
