package wf.garnier.sso.resourceserver;

import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ConferenceController {

    private final MultiValueMap<String, Conference> conferences;

    public ConferenceController() {
        this.conferences = new LinkedMultiValueMap<>();

        this.conferences.add("okta@garnier.wf", new Conference("Voxxed Days Zürich", "Zürich, Switzerland"));
        this.conferences.add("okta@garnier.wf", new Conference("Voxxed Days Luxembourg", "Mondorf-les-Bains, Luxembourg"));
        this.conferences.add("okta@garnier.wf", new Conference("RivieraDev", "Sophia Antipolis, France"));
        this.conferences.add("okta@garnier.wf", new Conference("SpringOne", "Las Vegas, Nevada, USA"));
        this.conferences.add("okta@garnier.wf", new Conference("Cloud Native Day", "Bern, Switzerland"));
        this.conferences.add("okta@garnier.wf", new Conference("Devoxx", "Antwerp, Belgium"));
        this.conferences.add("okta@garnier.wf", new Conference("J-Fall", "Ede, Netherlands"));

        this.conferences.add("-1", new Conference("Devoxx", "Antwerp, Belgium"));
        this.conferences.add("-1", new Conference("Fake Conferences", "Null Island, Somewhere"));
    }

    @GetMapping("/conferences")
    public List<Conference> conferences(String userId) {
        return this.conferences.get(userId);
    }

    record Conference(String name, String location) {
    }

}

