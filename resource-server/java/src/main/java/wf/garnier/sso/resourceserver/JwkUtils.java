package wf.garnier.sso.resourceserver;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.web.client.RestClient;

class JwkUtils {

    private static final RestClient restClient = RestClient.builder().build();

    static List<Jwk> getKeys(String jwksUri) {
        Map<String, List<Map<String, String>>> body = restClient.get()
                .uri(jwksUri)
                .retrieve()
                .body(Map.class);

        return Objects.requireNonNull(body)
                .get("keys")
                .stream()
                .map(r -> {
                    var modulus = r.get("n");
                    var exponent = r.get("e");
                    byte[] exponentB = Base64.getUrlDecoder().decode(exponent);
                    byte[] modulusB = Base64.getUrlDecoder().decode(modulus);
                    BigInteger bigExponent = new BigInteger(1, exponentB);
                    BigInteger bigModulus = new BigInteger(1, modulusB);
                    var key = toKey(new RSAPublicKeySpec(bigModulus, bigExponent));
                    return new Jwk(r.get("kid"), key);
                })
                .toList();
    }

    private static PublicKey toKey(RSAPublicKeySpec spec) {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static boolean verifySignature(PublicKey publicKey, byte[] signedData, byte[] signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        var sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(signedData);
        return sig.verify(signature);
    }

    public record Jwk(String kid, PublicKey publicKey) {
    }

}
