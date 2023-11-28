package com.banu.controller;

import com.banu.dto.request.LoginRequestDto;
import com.banu.dto.request.RegisterRequestDto;
import com.banu.dto.response.BaseResponseDto;
import com.banu.dto.response.LoginResponseDto;
import com.banu.dto.response.RegisterResponseDto;
import com.banu.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.banu.constants.RestApi.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService authService;
//    @Value("${buraya-canımın-istedigini-yazarim.okuyabilirim}")
//    private String ifade;

    /**
     ** Login.html:1 Access to fetch at 'http://34.163.201.190:9090/api/v1/auth/register' from origin
     * 'http://localhost:63342' has been blocked by CORS policy: Response to preflight request doesn't pass access
     * control check: No 'Access-Control-Allow-Origin' header is present on the requested resource.
     * If an opaque response serves your needs, set the request's mode to 'no-cors' to fetch the resource with
     * CORS disabled.
     * Dikkat!!!!
     * Yukarıda bulunan hata, sunucuya gelen isteği sunucunun dışından bir yerden gelmesi sunucuda oluşan bir hatadır.
     * bunu aşmak için sınıf, method ya da security ayarlarında istek atabilecek end-pointleri tanımlamak gerekir.
     *
     */

    @PostMapping(REGISTER)
//    @CrossOrigin("*BILGISAYAR_IP_ADRES")
//    @CrossOrigin("*.cludflare.com")
    @CrossOrigin("*") // tum her yerden atılan isteklere izin ver
    public ResponseEntity<BaseResponseDto<RegisterResponseDto>> register(@RequestBody @Valid RegisterRequestDto dto) {
        authService.register(dto);
        return ResponseEntity.ok(BaseResponseDto.<RegisterResponseDto> builder()
                        .responseCode(200)
                        .data(RegisterResponseDto.builder()
                                .isRegister(true)
                                .message("Üyelik Başarı ile Gerçekleşti")
                                .build())
                .build());
    }

    @CrossOrigin("*")
    @PostMapping(LOGIN)
    public ResponseEntity<BaseResponseDto<LoginResponseDto>> login(@RequestBody @Valid LoginRequestDto dto) {
        String token = authService.login(dto);
        return ResponseEntity.ok(BaseResponseDto.<LoginResponseDto>builder()
                        .responseCode(200)
                        .data(LoginResponseDto.builder()
                                .isLogin(true)
                                .token(token)
                                .build())
                .build());

    }
    @GetMapping("/getmassage")
    public String getMessage(){
        return "Bu Auth Servistir";
    }
}
