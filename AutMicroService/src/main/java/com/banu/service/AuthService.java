package com.banu.service;

import com.banu.dto.request.LoginRequestDto;
import com.banu.dto.request.RegisterRequestDto;
import com.banu.exception.AuthException;
import com.banu.exception.ErrorType;
import com.banu.manager.UserProfileManager;
import com.banu.mapper.AuthMapper;
import com.banu.rabbitmq.model.CreateUser;
import com.banu.rabbitmq.producer.CreateUserProducer;
import com.banu.repository.AuthRepository;
import com.banu.repository.entity.Auth;
import com.banu.utility.JwtTokenManager;
import com.banu.utility.enums.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository repository;
    private final UserProfileManager userProfileManager;
    private final JwtTokenManager jwtTokenManager;
    private final CreateUserProducer createUserProducer;


    public void register(RegisterRequestDto dto) {
        /*
         * Yeni üye olmak için gelen username veritabanında kayıtlı olup olmadığını
         * kontol ediyoruz. Eğer kullanıcı kayıtlı ise hata fırlatıyoruz.
         */
        repository.findOptionalByUserName(dto.getUserName())
                .ifPresent(auth -> {
                    throw new AuthException(ErrorType.KAYITLI_KULLANICI_ADI);
                });

        Auth auth = AuthMapper.INSTANCE.fromDto(dto);
        auth.setCreatAt(System.currentTimeMillis());
        auth.setUpdateAt(System.currentTimeMillis());
        auth.setState(State.ACTIF);
        repository.save(auth);
        /*
         * Kullanıxı yeni bir hesap açtığında auth bilgileri kayıt oluyor. Kendini doğrulamayal alakalı.
         * Ancak kullanıcı uygulama içinde UserProfile bilgisi ile hareket edecek. Bu nedenle register işleminde
         * kullanıcını profil bilgilerinin de oluşturulması gerekli.
         */
//        userProfileManager.save(UserProfileSaveRequestDto.builder()
//                        .authId(auth.getId())
//                        .userName(auth.getUserName())
//                .build());
        //userProfileManager.save(AuthMapper.INSTANCE.fromAuth(auth));
        createUserProducer.convertAndSendMessage(CreateUser.builder()
                        .authId(auth.getId())
                        .userName(auth.getUserName())
                .build());
    }

    public String login(LoginRequestDto dto) {
        Optional<Auth> auth = repository.findOptionalByUserNameAndPassword(dto.getUserName(), dto.getPassword());
        if (auth.isEmpty()) throw new AuthException(ErrorType.USERNAME_PASSWORD_ERROR);
        /*
            Kullanıcının authId bilgisi ile token üretioruz. Bu token bilgisini döneceğiz
         */
        Optional<String> jwtToken = jwtTokenManager.createToken(auth.get().getId());
        if (jwtToken.isEmpty()) {
            throw new AuthException(ErrorType.TOKEN_ERROR);
        }
        return jwtToken.get();
    }
}
