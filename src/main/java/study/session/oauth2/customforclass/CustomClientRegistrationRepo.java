package study.session.oauth2.customforclass;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration //@63 Configuration 으로 등록
@RequiredArgsConstructor //@64 SocialClientRegistration 생성자 빈등록
public class CustomClientRegistrationRepo {

    private final SocialClientRegistration socialClientRegistration;

    // @65 clientRegistrationRepository를 응답하는 메소드 생성
    public ClientRegistrationRepository clientRegistrationRepository() {


        // @66 간단히 InMemory 저장 방식 사용.
        return new InMemoryClientRegistrationRepository(
                socialClientRegistration.naverClientRegistration(), socialClientRegistration.googleClientRegistration());
        // @67 네이버레지스트레이션과 구글레지스트레이션 넣어서 등록

        /**
         * @68 SecurityConfig에 등록해준다.
         */
    }

}
