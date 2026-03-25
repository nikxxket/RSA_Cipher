import javax.crypto.Cipher;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class RSACipherDecrypt {
    public static void main(String[] args) throws Exception {

        // 1. Читаем приватный ключ из файла
        String privateKeyBase64 = new String(Files.readAllBytes(Paths.get("private.key")));
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        System.out.println("Приватный ключ загружен из private.key");

        // 2. Читаем зашифрованное сообщение
        String encryptedBase64 = new String(Files.readAllBytes(Paths.get("encrypted.txt")));
        System.out.println("Зашифрованное сообщение: " + encryptedBase64);

        // 3. Расшифровываем
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] encrypted = Base64.getDecoder().decode(encryptedBase64);
        byte[] decrypted = cipher.doFinal(encrypted);

        // 4. Выводим результат
        String decryptedText = new String(decrypted);
        System.out.println("Расшифрованный текст: " + decryptedText);
    }
}