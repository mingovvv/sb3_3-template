package demo.template.common.utils;

import com.macasaet.fernet.Key;
import com.macasaet.fernet.Token;
import com.macasaet.fernet.Validator;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.Base64;

public class FernetTest {

    @Test
    void test() {
        String hashKey = "1sLRLaO99pROxf1EL6dz2PDCdMNSDT0RxjQyod6Mppc=";
        // Base64로 디코딩하여 32바이트 배열로 변환
        byte[] decodedKey = Base64.getDecoder().decode(hashKey);
        // URL-safe Base64 인코딩된 Fernet Key 생성
//        Key key = new Key(Base64.getUrlEncoder().encodeToString(decodedKey));
        Key key = new Key(hashKey);
        Token token = Token.fromString("gAAAAABnNa34lobDM3_I9URzEvcIsuN1xKN0aQkpPugQ8ffGygJwgIZjqNxqXnGNcDgcb2YIfIgCFyTZvtU_WlQlV39sWeY7qA==");
//        Token token = Token.fromString("gAAAAABnNf8G3jSKYJQQKaM4aIDsTlB6G2zy-_C2L4JnwH-UbEWzDhL9v1EQkX0wudSuFankrT26lk8DpxV-EL_SntXdJcvKh9ZoUHDPOtXkycrTJNDIjZLrJgX4ycL3MJLm7mYmrRVo");

        Validator<String> noExpiryVerifier = new Validator() {

            @Override
            public TemporalAmount getTimeToLive() {
                return Duration.ofDays(36500);
            }

            @Override
            public java.util.function.Function<byte[], String> getTransformer() {
                return bytes -> new String(bytes);
            }
        };

        String decryptedMessage = token.validateAndDecrypt(key, noExpiryVerifier);
        System.out.println("복호화된 메시지: " + decryptedMessage);
    }

    @Test
    void test2() {
        // 주어진 hashKey 값 (표준 Base64로 인코딩된 키)
        String hashKey = "1sLRLaO99pROxf1EL6dz2PDCdMNSDT0RxjQyod6Mppc=";

        // URL-safe Base64 인코딩된 Fernet Key 생성
        Key key = new Key(hashKey);

        // 암호화할 메시지
        String message = "Hello, this is a secret message!";

        // 메시지를 바이트 배열로 변환
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

        // Fernet 암호화
        Token token = Token.generate(key, messageBytes);

        // 암호화된 토큰 출력
        System.out.println("암호화된 토큰: " + token.serialise());
    }

    @Test
    void test3() {
        try {
            String base64Key = "1sLRLaO99pROxf1EL6dz2PDCdMNSDT0RxjQyod6Mppc=";
            String tokenString = "gAAAAABnNfwOp1jc_RqtcUYBbS99H8GsTRIdWQzNVfgpm6iUsvzmwacBD4RyaOlNHF20bi1LuzfCTD-BZFfdpwnIgEJrEkzh1yX9geEE8iWmBC5D8OBPXr6hOsXrWTgk7Wx9sDziWAdj"; // Python에서 암호화된 Fernet 토큰

            String decryptedMessage = decrypt(tokenString, base64Key);
            System.out.println("복호화된 메시지: " + decryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("복호화에 실패했습니다: " + e.getMessage());
        }
    }

    public static String decrypt(String tokenString, String base64Key) throws Exception {
        // Decode the token and key
        byte[] token = Base64.getUrlDecoder().decode(tokenString);
        byte[] key = Base64.getDecoder().decode(base64Key);

        // Split the key into encryption key and signing key
        byte[] encryptionKey = new byte[16];
        byte[] signingKey = new byte[16];
        System.arraycopy(key, 0, encryptionKey, 0, 16);
        System.arraycopy(key, 16, signingKey, 0, 16);

        // Parse the token (version, timestamp, IV, ciphertext, HMAC)
        ByteBuffer buffer = ByteBuffer.wrap(token);
        byte version = buffer.get(); // Version
        if (version != (byte) 0x80) {
            throw new IllegalArgumentException("Invalid Fernet token version.");
        }

        byte[] timestamp = new byte[8];
        buffer.get(timestamp); // Timestamp (ignored)

        byte[] iv = new byte[16];
        buffer.get(iv); // IV

        byte[] ciphertext = new byte[buffer.remaining() - 32];
        buffer.get(ciphertext); // Ciphertext

        byte[] hmac = new byte[32];
        buffer.get(hmac); // HMAC

        // Verify HMAC
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(signingKey, "HmacSHA256"));
        mac.update(token, 0, token.length - 32);
        byte[] computedHmac = mac.doFinal();
        if (!MessageDigest.isEqual(hmac, computedHmac)) {
            throw new IllegalArgumentException("HMAC verification failed.");
        }

        // Decrypt the ciphertext
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encryptionKey, "AES"), new IvParameterSpec(iv));
        byte[] plaintext = cipher.doFinal(ciphertext);

        return new String(plaintext);
    }

    @Test
    void test4() {
        String enc = FernetUtil.encrypt("1sLRLaO99pROxf1EL6dz2PDCdMNSDT0RxjQyod6Mppc=", "안녕");
        System.out.println(enc);

        String decrypt = FernetUtil.decrypt("1sLRLaO99pROxf1EL6dz2PDCdMNSDT0RxjQyod6Mppc=", enc);
        System.out.println(decrypt);
    }

}
