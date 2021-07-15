package org.mit2.randomluckygames.controller;

import lombok.RequiredArgsConstructor;
import org.mit2.randomluckygames.config.oauth.LoginUser;
import org.mit2.randomluckygames.config.oauth.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class MainController {

    @RequestMapping("/")
    public String main(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("userName", user.getNickName());
            return "main";
        }
        return "index";
    }
}
