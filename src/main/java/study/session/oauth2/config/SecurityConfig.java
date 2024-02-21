package study.session.oauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import study.session.oauth2.customforclass.CustomClientRegistrationRepo;
import study.session.oauth2.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // @37 구현한 service를 생성자 방식으로 등록
public class SecurityConfig {


    // @37 구현한 service를 생성자 방식으로 등록
    private final CustomOAuth2UserService customOAuth2UserService;

    // @69 커스텀한 repo를 등록해주자.
    private final CustomClientRegistrationRepo customClientRegistrationRepo;


    //@1
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf -> csrf.disable()); //@3 csrf설정 잠깐 끔.
        httpSecurity.formLogin(formlogin -> formlogin.disable()); //@4 formlogin방식 사용 X
        httpSecurity.httpBasic(httpBasic -> httpBasic.disable()); //@5 httpBasic 사용 X
       // httpSecurity.oauth2Login(Customizer.withDefaults()); //@6 oauth2Client 방식 X(일일이 다 커스텀 해야한다.) 반드시 Login 방식
        //@7 Customizer.withDefaults() <== 잠시동안만 이렇게 디폴트로 설정. 나중에 직접 구현

       /**
        *  @38 유저 엔드포인트 설정
        *  유저 엔드포인트 :
        *  우리가 데이터를 받을 수 있는 UserDetailsService를 등록해주는 엔드포인트 라는 뜻
        *  내부의 userInfoEndpointConfig 를 다시 람다식으로 구현
        *  이 userInfoEndpointConfig에도 userService를 만들 떄 우리가 만든 CustomOAuth2UserService 를 넣어줌
        *
        * @39는 CustomOAuth2UserService
        */
        httpSecurity.oauth2Login(oauth2 ->
                oauth2
                        .loginPage("/login")// @마지막! 해당 /login 요청 받는 url 추가
                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository()) //@70 @71의 등록한 값을 넣어준다.
                        .userInfoEndpoint(userInfoEndpointConfig ->
                        userInfoEndpointConfig.userService(customOAuth2UserService)));

        //@8 각각의 경로에 대해 인가작업
        httpSecurity.authorizeHttpRequests(auth ->
                auth.requestMatchers("/", "oauth2/**", "/login").permitAll()
                 /*  ==> "/", "oauth2/**", "/login" 경로에 대해서 아무나 접속할 수 있도록 설정 **그렇다면 메인페이지나 남들 다 볼 수
                        있는 경로는 여기에 해야겠죠?** */
                        .anyRequest().authenticated()); //그 외 나머지 경로는 로그인을 한 사람만 접속할 수 있도록 **로그인 페이지나 보안필요한 페이지는 여기에 해야겠죠?**




        //@2
        return httpSecurity.build();
    }

}
