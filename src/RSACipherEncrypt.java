import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.io.FileOutputStream;

public class RSACipherEncrypt {
    public static void main(String[] args) throws Exception {

        // 1. Генерируем ключи
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512);
        KeyPair keyPair = keyGen.generateKeyPair();

        // 2. Сохраняем ключи в файлы в виде Base64 строк
        String publicKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        try (FileOutputStream fos = new FileOutputStream("public.key")) {
            fos.write(publicKeyBase64.getBytes());
        }

        try (FileOutputStream fos = new FileOutputStream("private.key")) {
            fos.write(privateKeyBase64.getBytes());
        }

        System.out.println("Ключи сохранены в public.key и private.key");

        // 3. Шифруем текст
        String text = "hello world";
        System.out.println("Исходный текст: " + text);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] encrypted = cipher.doFinal(text.getBytes());

        // 4. Сохраняем зашифрованное сообщение
        String encryptedBase64 = Base64.getEncoder().encodeToString(encrypted);
        try (FileOutputStream fos = new FileOutputStream("encrypted.txt")) {
            fos.write(encryptedBase64.getBytes());
        }

        System.out.println("Зашифрованное сообщение: " + encryptedBase64);
        System.out.println("Сохранено в encrypted.txt");
    }
}