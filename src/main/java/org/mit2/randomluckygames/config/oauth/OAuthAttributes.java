package org.mit2.randomluckygames.config.oauth;

import lombok.Builder;
import lombok.Getter;
import org.mit2.randomluckygames.domain.userinfo.Platform;
import org.mit2.randomluckygames.domain.userinfo.UserInfo;

import java.util.Map;

// 로그인 API의 내용을 뽑아서 UserInfo에 필요한 값 매칭
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String nickName;
    private String email;
    private String profilePicture;
    private Platform platform;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String nickName, String email, String profilePicture, Platform platform) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.nickName = nickName;
        this.email = email;
        this.profilePicture = profilePicture;
        this.platform = platform;
    }
    // 플랫폼별 response 형태가 달라서 따로 골라내줌
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)){
            return ofNaver("id", attributes);
        }
        else if("kakao".equals(registrationId)){
            return ofKakao("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }
    //구글
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nickName((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profilePicture((String) attributes.get("picture"))
                .platform(Platform.Google)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    //네이버
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        System.out.println();

        return OAuthAttributes.builder()
                .nickName((String) response.get("name"))
                .email((String) response.get("email"))
                .profilePicture((String) response.get("profile_image"))
                .platform(Platform.Naver)
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    //카카오
    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");

        return OAuthAttributes.builder()
                .nickName((String) profile.get("nickname"))
                .profilePicture((String) profile.get("profile_image"))
                .email((String) kakao_account.get("email"))
                .platform(Platform.Kakao)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    public UserInfo toEntity(){
        return UserInfo.builder()
                .nickName(nickName)
                .email(email)
                .profilePicture(profilePicture)
                .platform(platform)
                .build();
    }
}
