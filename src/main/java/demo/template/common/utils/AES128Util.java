package demo.template.common.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AES128Util {

    private static final Charset ENCODING_TYPE = StandardCharsets.UTF_8;
    private static final String INSTANCE_TYPE = "AES/ECB/PKCS5Padding";

    @Value("${aes.secret-key}")
    private String secretKey;

    private static SecretKeySpec secretKeySpec;
    private static Cipher cipher;

    @PostConstruct
    private void init() {
        try {
            secretKeySpec = new SecretKeySpec(secretKey.getBytes(ENCODING_TYPE), "AES");
            cipher = Cipher.getInstance(INSTANCE_TYPE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // AES 암호화
    public static String encryptAes(String plaintext) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encoded = cipher.doFinal(plaintext.getBytes(ENCODING_TYPE));
            return new String(Base64.getEncoder().encode(encoded), ENCODING_TYPE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // AES 복호화
    public static String decryptAes(String plaintext) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decoded = Base64.getDecoder().decode(plaintext.getBytes(ENCODING_TYPE));
            return new String(cipher.doFinal(decoded), ENCODING_TYPE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
