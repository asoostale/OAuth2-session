package study.session.oauth2.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// @40 인터페이스 OAuth2User 상속
@RequiredArgsConstructor //@41
public class CustomOAuth2User implements OAuth2User {


    //@41 @39에서 넘겨준 값을 가지고 생성자 방식으로 생성
    private final OAuth2Response oAuth2Response;
    private final String role;



    @Override
    public Map<String, Object> getAttributes() { //로그인을 진행하면 리소스 서버로부터 넘어오는 모든 데이터

        return null; //일단 넣어주지 않는 것으로 한다. 네이버와 구글이 해당 데이터의 키가 다르기 때문에 안넣어주는게 낫다.
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //롤값에 해당한다.

        //@42
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                // @43 입력 받은 role 값을 리턴해준다.
                return role;
            }
        });
            // @44
        return collection;
    }

    @Override
    public String getName() { //사용자의 별명이나 이름 값
         //@45 여기에 해당 데이터가 있기 때문에
        return oAuth2Response.getName();
    }

    //@46 오버라이딩 하지 않고 강제로 하나 만들어줄건데
    public String getUsername() {
        //@47 띄어쓰기 한번 해주면 됨. 이 데이터를 Id값으로 씀.
        return oAuth2Response.getProvider()+" "+oAuth2Response.getProvider();
    }
}
