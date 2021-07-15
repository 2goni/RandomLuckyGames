package org.mit2.randomluckygames.config.oauth;

import lombok.RequiredArgsConstructor;
import org.mit2.randomluckygames.config.oauth.dto.CheckUser;
import org.mit2.randomluckygames.config.oauth.dto.SessionUser;
import org.mit2.randomluckygames.domain.userinfo.UserInfo;
import org.mit2.randomluckygames.domain.userinfo.UserInfoRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

//로그인 API를 사용하고 UserInfo에 담긴 값을 DB에 저장함
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserInfoRepository userInfoRepository;
    private final HttpSession httpSession;
    private final CheckUser checkUser;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        checkUser(attributes);
        UserInfo user = getUser(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getNickName())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }
    // attribute에 담긴 값 UserIfo에 매칭시키는 메소드
    private UserInfo getUser(OAuthAttributes attributes){
        UserInfo user = userInfoRepository.findByEmailAndPlatform(attributes.getEmail(), attributes.getPlatform())
                .orElse(attributes.toEntity());
        return user;
    }
    // 사용자가 회원인지 확인하는 메소드
    private void checkUser(OAuthAttributes attributes){
        Optional<UserInfo> user = userInfoRepository.findByEmailAndPlatform(attributes.getEmail(), attributes.getPlatform());
        checkUser.userSet(user.isPresent());

    }
}
