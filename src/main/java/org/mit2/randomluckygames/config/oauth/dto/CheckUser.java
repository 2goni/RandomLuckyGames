package org.mit2.randomluckygames.config.oauth.dto;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

// 사용자가 회원인지 확인하는 클래스
@NoArgsConstructor
@Component
public class CheckUser {
    //회원 여부 값을 가지는 개체
    private boolean isUser;

    // 해당 사용자의 회원여부를 리턴
    public boolean isUser() {
        return isUser;
    }

    // 해당사용자의 회원여부 받음
    public void userSet(boolean check){
        this.isUser = check;
    }
}
