package wf.garnier.sso.demo;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String unauthenticated() {
        return "anonymous";
    }

    @GetMapping("/authenticated")
    public String authenticated(@AuthenticationPrincipal OidcUser user, Model model) {
        model.addAttribute("username", user.getEmail());
        model.addAttribute("attributes", user.getClaims());
        return "authenticated";
    }

}
