package demo.template.common.utils;

import com.macasaet.fernet.Key;
import com.macasaet.fernet.Token;
import com.macasaet.fernet.Validator;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.TemporalAmount;

public class FernetUtil {

    public static String encrypt(String hashKey, String message) {
        Key key = new Key(hashKey);
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        return Token.generate(key, messageBytes).serialise();
    }

    public static String decrypt(String hashKey, String plainText) {
        Key key = new Key(hashKey);
        Token token = Token.fromString(plainText);

        Validator<String> noExpiryVerifier = new Validator() {

            @Override
            public TemporalAmount getTimeToLive() {
                return Duration.ofDays(36500); // 복호화 만료시간 무제한
            }

            @Override
            public java.util.function.Function<byte[], String> getTransformer() {
                return String::new;
            }

        };

        return token.validateAndDecrypt(key, noExpiryVerifier);

    }

}
