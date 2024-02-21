package study.session.oauth2.dto;

import java.util.Map;

public class GoogleResponse implements OAuth2Response {

   // @28 네이버와 동일
    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {
        // @29 네이버와 다른점은 response라는 키에서 꺼내서 초기화 해주었는
        // 구글은 내부 키에 담겨 있는게 아니라 데이터가 각자 담겨 있기 때문에 그대로 넣어주면 된다.
        /**
         * Json
         * {
         *      resultcode=60,       message=success,    id=12312313,          name=asoostale
         *   (string) = (Object)    (string) = (Object)  (string) = (Object)   (string) = (Object)
         * }
         */
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "google"; // @30
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString(); // @30  구글의 경우 "id"가 아니라 "sub"으로 원래 그렇게 되어 있다.
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString(); // @30
    }

    @Override
    public String getName() {
        return attribute.get("name").toString(); // @30
    }

    /**
     * @31 Dto를 다 만들었으니
     * CustomOAuth2UserService에서
     * "naver"에서 naver 바구니를 받고
     * "google"에서  google 바구니를 받아서
     *  로직을 처리해주자.
     */


}
