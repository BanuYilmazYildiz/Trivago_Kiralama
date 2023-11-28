package com.banu.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {
    /**
     * Secret KEy -> şifreleme için özel bir anahtar
     * Issuer -> jwt'yi oluşturan, sahiplik
     * IssureAt -> jwt'nin oluşturulma zamanı
     * ExpiresAt -> jwt'nin geçerlilik son zamanı
     * Sign -> jwt'nin imzalanması yani bir sifreleme algoritması ile şifrelenmesi
     */

    // şifre online password generator ile üretilebilir
    private final String SECRETKEY = "Yoklr8?8h8biDrOtiS_iT_opheQlKE@l3WEVL_!a4E6e#r*Seb+phEwO$7arURaZ";

    private final String ISSUER = "Java11BoostAuth";

    private final Long EXDATE = 1000L * 30; // 30 saniye

    /**
     * Kişiyi benzersiz kılan kişinin auth id'si ya da username'idir.
     * Kullanıcıdan authId'si alınarak yeni bir jwt token üretilir.
     *
     * @param authId
     * @return
     */
    public Optional<String> createToken(Long authId) {
        String token;
        try {
            token = JWT.create()
                    .withAudience()
                    .withClaim("authId", authId) // DİKKAT!! -> buralara(Claim) eklediğiniz datalar şifrelenmez
                    .withClaim("howtopage", "AuthMicroService") //Ex: bankacılıkta güvenliği aarttırmak, jwt nereden gelip nereye gidiyor anlayabilmek için
                    .withClaim("key yazıp", "istediğimiz value buraya girilebilir")
                    .withIssuer(ISSUER) //jwt'yi üreten, sahiplik
                    .withIssuedAt(new Date()) //jwt üretilme zamanı
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXDATE)) //jwt'nin sona erme tarihi
                    .sign(Algorithm.HMAC512(SECRETKEY));
            return Optional.of(token);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Token geçerli mi değil mi bakıcaz
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            //Şifrelediğimiz token için şifreyi çözme ve doğrulama işlemi için algoritma tanımlıyoruz
            Algorithm algorithm = Algorithm.HMAC512(SECRETKEY);
            // Token'ı doğrulayabilmek için algoritmayı kullanarak token sahipliğini giriyoruz
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER).build();
            // verifier ile token'ı çzöüyoruz
            DecodedJWT decodedJWT = verifier.verify(token);
            /* Eğer ilgili token çözülmemiş ise false dönülür.
                1- Token yanlış gelmiş olabilir
                2- Token süresi dolmuş olabilir
                3- Farklı bir sahiplik gönderilmiş olabilir
             */
            if (decodedJWT == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * token verip kime ait olduğunu dönüyoruz
     *
     * @param token
     * @return
     */
    public Optional<Long> getIdByToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(SECRETKEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null){
                return Optional.empty();
            }
            Long authId = decodedJWT.getClaim("authId").asLong();
            return Optional.of(authId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
