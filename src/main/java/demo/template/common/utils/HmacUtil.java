package demo.template.common.utils;

import org.springframework.security.authentication.AuthenticationServiceException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HmacUtil {

    private HmacUtil() {}

    public static String generateHmac(long unixTime, String secret) {

        try {
            return createHmac(secret, Long.toString(unixTime), "HmacSHA256");
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }

    }

    private static String createHmac(String key, String msg, String algorithm) throws Exception {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm));
        byte[] hash = mac.doFinal(msg.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

}
