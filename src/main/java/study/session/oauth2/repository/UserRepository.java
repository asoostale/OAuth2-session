package study.session.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.session.oauth2.entity.UserEntity;
// @50
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    //@51 @52는 CustomOAuth2UserService로 이동
    UserEntity findByUsername(String username);
}
