package org.mit2.randomluckygames.config.oauth.dto;

import lombok.Getter;
import org.mit2.randomluckygames.domain.userinfo.Platform;
import org.mit2.randomluckygames.domain.userinfo.UserInfo;

import java.io.Serializable;

// session에 저장될 사용자 정보
@Getter
public class SessionUser implements Serializable {
    private String nickName;
    private String email;
    private String profilepicture;
    private Platform platform;

    public SessionUser(UserInfo user) {
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.profilepicture = user.getProfilePicture();
        this.platform =user.getPlatform();
    }

}