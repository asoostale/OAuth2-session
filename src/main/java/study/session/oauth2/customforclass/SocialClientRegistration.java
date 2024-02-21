package study.session.oauth2.customforclass;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;


//@60
@Component
public class SocialClientRegistration {
    public ClientRegistration naverClientRegistration() {

        return ClientRegistration.withRegistrationId("naver")
                .clientId("UU3bzFWzdlFr6dSgOORG")
                .clientSecret("HSv0n6vPhO")
                .redirectUri("http://localhost:8080/login/oauth2/code/naver")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("name", "email")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .build();
    }

    public ClientRegistration googleClientRegistration() {
        /**
         * @61 구글은 provider를 안해줘도 됐는데 이렇게 클래스로 커스텀 할 때는
         * authorizationUri , tokenUri ,jwkSetUri ,issuerUri, userInfoUri ,userNameAttributeName
         * 를 채워줘야 한다.
         */
        return ClientRegistration.withRegistrationId("google")
                .clientId("994016149640-chdf417lc2qruhv2admd755bg93o2q95.apps.googleusercontent.com")
                .clientSecret("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")
                .redirectUri("http://localhost:8080/login/oauth2/code/google")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .issuerUri("https://accounts.google.com")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .build();

        //@62 CustomClientRegistrationRepository 생성.
    }

}
