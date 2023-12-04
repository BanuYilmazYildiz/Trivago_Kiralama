package com.banu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ElasticSecurityConfig {
    @Bean //jwt token filtewrdan neste yaratmak lazım, bean olarak yaratıyorum, nesne yaratacak
    JwtTokenFilter getJwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        /**
         * Spring Security gelen istekleri filtrelemek icin belli metotları kullanır bunlar;
         * - requestMatcher -> filrelenecek isteklerin URL bilgilerini işlemek için kullanılır.
         * - permitAll -> bütün istekler için izin verç Oturum zorunluluğu yok.
         * - authenticated -> belirlemiş olduğumuz isteklere/endpointlere oturuma tabi tutulması gerektiğini belirtiyor
         * - anyRequest -> tüm istekelr/endpointler için bunu yap
         * - anyMatcher -> tüm eşleşmeler için
         * -
         */
        // SpringBoot 3.0 oncesindeki kullanim ;
//        httpSecurity.authorizeRequests()
//                .requestMatchers("/elastic-user-profile/find-all")
//                .permitAll() //bunlara izin ver
//                .anyRequest().authenticated();//bunun haricindekilere oturum açmayı zorunlu tut
//        httpSecurity.formLogin();
        /*
            3.0 sonrası gelenler
         */
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers("/elastic-user-profile/find-all", // izin verilecek end pointler yazılacak
                                "/elastic-user-profile/get-message",
                                "/swagger-ui/**", // /** diğer tüm uzantilar dfemektir, swagger ile ilgili, görsel arayüzüne izin veriliyor
                                "/v3/api-docs/**") //swaggerın api dokumentasyonuna ulastığı yer
                        .permitAll() //bunlara izin ver kalana izin verme
                        .requestMatchers("/elastic-user-profile/get-secret-message").hasAnyRole("Super_Admin") //sadece super_adminle buna erişsin
                        .requestMatchers("/admin/**").hasAnyRole("Admin")
                        .anyRequest().authenticated());
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        /*
          csrf -> restApi kullanılırken kapatılır. Bir güvenlik önlemi türüdür.
         */
        //spring 3.0 oncesi icin kullanılır
//        httpSecurity.csrf().disable();

        /*
            Filtreleme yaparken Roller, Yetkiler gibi kullanıcı bazlı işlemlerin kontrol edilmesi için filter içinde Springin yöneteceği
            bir user tanımlaması ve bu işlemin türü belirlenmelidir.
         */
        httpSecurity.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
