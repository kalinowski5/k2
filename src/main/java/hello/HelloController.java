package hello;

import org.joda.time.LocalTime;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        LocalTime currentTime = new LocalTime();
        return "<h1>Greetings from Spring Boot!</h1>"  + currentTime;
    }

}