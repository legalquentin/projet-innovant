package app;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private static final String template = "This is the, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api")
    public Api api(@RequestParam(value="name", defaultValue="API") String name) {
        return new Api(counter.incrementAndGet(), String.format(template, name));
    }
}
