package org.mit2.randomluckygames.domain.userinfo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    //email,platform 기준으로 조회
    Optional<UserInfo> findByEmailAndPlatform(String email, Platform platform);
}
