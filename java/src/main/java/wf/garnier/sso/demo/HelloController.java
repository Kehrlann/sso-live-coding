package wf.garnier.sso.demo;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
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


    @Value("${sso.client_id}")
    private String clientId;

    @Value("${sso.client_secret}")
    private String clientSecret;

    private static final String authorizationUri = "https://dev-51438889.okta.com/oauth2/default/v1/authorize";

    private static final String tokenUri = "https://dev-51438889.okta.com/oauth2/default/v1/token";

    public HelloController() {
        this.restClient = RestClient.create();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
                    .queryParam("scope", "openid profile email test.scope")
                    .queryParam("response_type", "code")
                    .queryParam("state", UUID.randomUUID().toString());
            model.addAttribute("loginUri", loginUri.toUriString());
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
        var session = request.getSession();
        if (session != null && session.getAttribute("access_token") != null) {
            try {
                var conferences = this.restClient.get()
                        .uri("http://localhost:8081/conferences")
                        .header("authorization", "Bearer " + session.getAttribute("access_token"))
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .body(List.class);
                model.addAttribute("conferences", conferences);
            } catch (Exception e) {
                model.addAttribute("error", "Error while getting conferences: " + e.getMessage());
            }

            return "conferences";
        }

        return "redirect:/";
    }

    @GetMapping(AUTHORIZATION_SUCCESS_PATH)
    public String authorized(@RequestParam("code") String code, HttpServletRequest request) throws IOException {
        System.out.println("Got a code: " + code);
        var payload = new LinkedMultiValueMap<>();
        payload.add("redirect_uri", REDIRECT_URI);
        payload.add("grant_type", "authorization_code");
        payload.add("code", code);

        var response = this.restClient
                .post()
                .uri(tokenUri)
                .header("Authorization", "Basic " + getCredentials())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(payload)
                .retrieve()
                .body(String.class);
        var body = this.objectMapper.readValue(response, TokenReponse.class);
        System.out.println(body.idToken());

        var idToken = decodeIdToken(body.idToken());
        System.out.println(idToken);

        var session = request.getSession(true);
        session.setAttribute("username", idToken.get("email"));
        session.setAttribute("attributes", idToken);
        session.setAttribute("access_token", body.accessToken());

        return "redirect:/";
    }

    private Map<String, Object> decodeIdToken(String token) throws IOException {
        var parts = token.split("\\.");
        var body = Base64.getUrlDecoder().decode(parts[1]);
        return objectMapper.readValue(body, Map.class);
    }

    private String getCredentials() {
        var credsString = "%s:%s".formatted(clientId, clientSecret);
        return Base64.getUrlEncoder().encodeToString(credsString.getBytes());
    }

    record TokenReponse(
            @JsonProperty("id_token") String idToken,
            @JsonProperty("access_token") String accessToken
    ) {
    }

}
