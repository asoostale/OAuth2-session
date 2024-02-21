package study.session.oauth2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import study.session.oauth2.dto.CustomOAuth2User;
import study.session.oauth2.dto.GoogleResponse;
import study.session.oauth2.dto.NaverResponse;
import study.session.oauth2.dto.OAuth2Response;
import study.session.oauth2.entity.UserEntity;
import study.session.oauth2.repository.UserRepository;

//@15 구글, 네이버로 부터 받은 정보를 처리하기 위해 DefaultOAuth2UserService 상속

/**
 * DefaultOAuth2UserService는 OAuth2UserService(인터페이스)의 구현체
 * 즉 OAuth2UserService를 사용하여도 된다.
 */

@Service
@RequiredArgsConstructor // @53
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    // @53 UserRepository 선언
    private final UserRepository userRepository;


    // @16 함수 "loadUser"는 구글이나 네이버에서 사용자 정보 데이터를(OAuth2UserRequest) 내부 인자로 받아오게 된다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // @17 부모클래스(DefaultOAuth2UserService)의 loadUser로부터 해당 인증 데이터를 가져오기 위함.
        System.out.println("oAuth2User.getAttribute = " + oAuth2User.getAttributes());

        //@18 구글인지 네이버인지, 어떤 인증 "provider"인지 확인하는 것.
        String registrationId = userRequest.getClientRegistration().getClientId();

        /**
         *@20 해당 Dto바구니 (인터페이스로 선언)
         * dto 패키지를 만들고 네이버, 구글에서 보내주는 것을 받을 인터페스를 만든다.
         * 나중에(@32에서 없어짐) 즉 틀만 일단 선언(개념용)
         */
        //  OAuth2Response oAuth2Response = null;

        /**
         * @19 네이버의 인증 규격과 구글의 인증 규격이 다르기 때문에 서로 다른 Dto로 담아야 한다.
         * 네이버, 구글 아니면 null 반환 @20으로 이어 설명
         */

        //@32 기본적으로 바구니 틀인 OAuth2Response 선언해놓는다
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {

            // @33 네이버 바구니를 가져오는데 매개값은 @17의 해당 인증값을 Map 형태로 넣어준다.
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

        } else if (registrationId.equals("google")) {

            // @34 구글 바구니를 가져오는데 매개값은 @17의 해당 인증값을 Map 형태로 넣어준다.
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

        } else {
            return null;
        }
        // @52 DB정보 구현(저장이라던가 업데이트)
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
                                        //네이버 or 구글인지  //구글 or 네이버에서 보내준 유저에 대한 번호데이터
        UserEntity existData = userRepository.findByUsername(username); // 해당 유저 DB조회

        String role = "ROLE_USER";
        if (existData == null) { //처음 로그인 진행

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setEmail(oAuth2Response.getEmail());
            userEntity.setRole(role); //강제로 ROLE_USER

            userRepository.save(userEntity); //회원가입
        }
        else { // 존재하게 되면 업데이트 구문 작성
            //처음 로그인하는 경우가 아닌 케이스는 조회를 해야함. 이경우는 업데이트 방식/

            existData.setUsername(username);
            existData.setEmail(oAuth2Response.getEmail()); // 새로 갱신된 이메일이나 새로 업데이트 된 이메일을 업데이트
             // @53
            role = existData.getRole();

            userRepository.save(existData);
        }
        //@35 이 서비스를 config에 등록해주자.
        //@36 꺼낸 바구니에서 최종적으로 데이터를 뽑아서 여기에 로직 구현

        //@39 권한을 부여할 role 값도 넣어줌
        //String role = "ROLE_USER"; //일단 강제로 하드코딩 방식
        return new CustomOAuth2User(oAuth2Response, role);
    }
}
