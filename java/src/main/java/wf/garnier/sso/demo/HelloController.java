package wf.garnier.sso.demo;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClient;

@Controller
public class HelloController {

    private final RestClient restClient;

    public HelloController(RestClient.Builder builder) {
        this.restClient = builder.build();
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
        request.getSession().setAttribute(
                "attributes",
                Map.of(
                        "firstName", "Daniel",
                        "lastName", "Garnier-Moiroux",
                        "company", "VMware",
                        "userType", "hardcoded"
                )
        );
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        var session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/conferences")
    public String conferences(Model model, HttpServletRequest request) {
        try {
            var conferences = this.restClient.get()
                    .uri("http://localhost:8081/conferences?userId={id}", "-1")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(List.class);
            model.addAttribute("conferences", conferences);
        } catch (Exception e) {
            model.addAttribute("error", "Error while getting conferences: " + e.getMessage());
        }

        return "conferences";
    }
}
