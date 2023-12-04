package com.banu.config;

import com.banu.utility.JwtTokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Autowired
    private JwtUserDetails jwtUserDetails;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
            Uygulamanız içinde gelen her bir istek buradan gecmek zorundadır. Bizde burada gelen isteklerin kontrolünü
            yaoarak bir tokenlerının olup olmadığına bakarız. Böylece oturum kontrolü sağlanmış olur.
            Burada ilk olarak gelen isteğin Header'ı içince Bearer Token bilgisi var mı kontrol ederiz.
            Sonra bu token'ı ayrıştıracak geçerliliği kontrol ederiz
            Geçerli bir token ise bu token'a sahip kullanıcını yetkilerini kontrol ederiz.
            Tüm bu işlemlerden sonra Spring için yetki kontrolünde kullanabilceği bir UserDetails nesnesini
            oluşturarak Filtrenin arasına yerleştiririz

         */
        String bearer_token = request.getHeader("Authorization");
        if (bearer_token.startsWith("Bearer ")){ //token var mı yok mu kontrol ediyoruz
            //Token okunur
            String token = bearer_token.substring(7);
            // Token içinden kullanıcı id'si çekilir
            Optional<Long> authId = jwtTokenManager.getIdByToken(token);
            // Eğer token içinden geçerli bir id donmez ise hata fırlatıyoruz
            if (authId.isEmpty()){
                throw new RuntimeException("Geçersiz Token");
            }
            /*
                Spring Security'nin yönetebileceği bir auth nesnesi tanımlıyoruz.
             */
            UserDetails userDetails = jwtUserDetails.findByAuthId(authId.get());
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            //Auth nesnesinin güvenlik kabının içine entegre ediyoruz. Böylece otutum olusturacak ve bunun yetkileri üzerinden sayfalara erişimi acacaktır
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
