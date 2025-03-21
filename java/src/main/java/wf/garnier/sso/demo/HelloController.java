package wf.garnier.sso.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class HelloController {

    public static final String AUTHORIZATION_SUCCESS_PATH = "/oauth2/callback";

    static final String REDIRECT_URI = "http://localhost:8080" + AUTHORIZATION_SUCCESS_PATH;

    private final RestClient restClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${SSO_CLIENT_ID}")
    private String clientId;

    @Value("${SSO_CLIENT_SECRET}")
    private String clientSecret;

    private static final String authorizationUri = "https://accounts.google.com/o/oauth2/v2/auth";

    private static final String tokenUri = "https://oauth2.googleapis.com/token";

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
            var loginUri = UriComponentsBuilder.fromHttpUrl(authorizationUri)
                    .queryParam("client_id", clientId)
                    .queryParam("redirect_uri", REDIRECT_URI)
                    .queryParam("scope", "openid profile email")
                    .queryParam("response_type", "code");
            model.addAttribute("loginUri", loginUri.toUriString());
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

    @GetMapping(AUTHORIZATION_SUCCESS_PATH)
    public String authorized(@RequestParam("code") String code, HttpServletRequest request) throws IOException {
        System.out.println("Got a code: " + code);
        var response = this.restClient
                .post()
                .uri(tokenUri)
                .headers(h -> h.setBasicAuth(clientId, clientSecret))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("code=%s&redirect_uri=%s&grant_type=authorization_code".formatted(code, REDIRECT_URI))
                .retrieve()
                .body(String.class);
        var body = this.objectMapper.readValue(response, Map.class);
        System.out.println(body.get("id_token"));

        var idToken = decodeIdToken((String) body.get("id_token"));
        System.out.println(idToken);

        var session = request.getSession(true);
        session.setAttribute("username", idToken.get("email"));
        request.getSession().setAttribute("attributes", idToken);

        return "redirect:/";
    }

    private Map<String, Object> decodeIdToken(String idToken) throws IOException {
        var parts = idToken.split("\\.");
        var body = Base64.getUrlDecoder().decode(parts[1]);
        return objectMapper.readValue(body, Map.class);
    }

    private String getCredentials() {
        var credsString = "%s:%s".formatted(clientId, clientSecret);
        return Base64.getUrlEncoder().encodeToString(credsString.getBytes());
    }
}
