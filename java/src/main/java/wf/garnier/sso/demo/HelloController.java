package wf.garnier.sso.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClient;

@Controller
public class HelloController {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public HelloController(RestClient.Builder restClientBuilder, Jackson2ObjectMapperBuilder objectMapperBuilder) {
        this.restClient = restClientBuilder.build();
        this.objectMapper = objectMapperBuilder.build();
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        var session = request.getSession();
        if (session != null && session.getAttribute("username") != null) {
            model.addAttribute("username", session.getAttribute("username"));
            model.addAttribute("attributes", session.getAttribute("attributes"));
            return "authenticated";
        } else {
            return "anonymous";
        }
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        var session = request.getSession(true);
        session.setAttribute("username", "Daniel");
        session.setAttribute(
                "attributes",
                Map.of(
                        "firstName", "Daniel",
                        "lastName", "Garnier-Moiroux",
                        "team", "Spring",
                        "userType", "hardcoded"
                )
        );
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        var session = request.getSession();
        if (session != null) {
            session.removeAttribute("username");
            session.removeAttribute("attributes");
        }
        return "redirect:/";
    }

}
