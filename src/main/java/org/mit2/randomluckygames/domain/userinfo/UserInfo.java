package org.mit2.randomluckygames.domain.userinfo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.mit2.randomluckygames.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UserInfo extends BaseTimeEntity {

    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserInfoId;

    //닉네임(별칭), OAuth2에서 가져올 정보
    @Column(nullable = false)
    private String nickName;

    //이메일, OAuth2에서 가져올 정보
    @Column(nullable = false)
    private String email;

    //로그인 플랫폼
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Platform platform;

    //사진(url 주소), OAuth2에서 가져올 정보, null 허용
    @Column(length = 500)
    private String profilePicture;

    //나이, null 허용
    private Integer age;

    //경험치, 기본값 0;
    @ColumnDefault("0")
    private Long experience = 0l;

    @Builder
    public UserInfo(String nickName, String email,Platform platform, String profilePicture, Integer age, Long experience){
        this.nickName = nickName;
        this.email = email;
        this.platform = platform;
        this.profilePicture = profilePicture;
        this.age = age;
        this.experience = experience;
    }
}