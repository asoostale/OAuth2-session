package study.session.oauth2.dto;

import java.util.Map;
import java.util.Objects;

public class NaverResponse implements OAuth2Response{

    //@23
    private final Map<String, Object> attribute;

    //@24 생성자를 @23의 Map을 받고 새롭게 태어난다.
    public NaverResponse(Map<String, Object> attribute) {
        /**
         * @25 네이버의 경우 json 안의 response가 key와 value의 형태이기 때문에
         * response 키에 대한 데이터를 가져오면 된다.
         * ex) 네이버 데이터
         *  {
         *   resultcode=60, message=success, response={id=12312313, name=asoostale}
         *  }
         */
        this.attribute = (Map<String, Object>)attribute.get("response");
    }

    @Override
    public String getProvider() {
        return "naver"; // @26 네이버이기에 naver로 작성
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString(); // @26 attribute에서 "id"키를 꺼내고 string으로 응답
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString(); // @26 attribute에서 "email"키를 꺼내고 string으로 응답
    }

    @Override
    public String getName() {
        return attribute.get("name").toString(); // @26 attribute에서 "name"키를 꺼내고 string으로 응답
    }

//    @27 dto패키지에 구글에 대한
//    클래스를 만들도록 하자.
}
