package study.session.oauth2.dto;

public interface OAuth2Response {
    /**
     * @21
     *  provider(구글,네이버)와 그 아이디, email과 이름을 구현할 메소드 정의
     */
    String getProvider(); // ex) naver, google

    String getProviderId(); // 제공자에서 발급해주는 아이디(번호)

    String getEmail(); // 이메일

    String getName(); //사용자 실명(설정한 이름)

    /**
     * @22
     * 네이버 구현부터 만들자.
     * NaverResponse클래스를 dto패키지에 생성한다.
     */

}
