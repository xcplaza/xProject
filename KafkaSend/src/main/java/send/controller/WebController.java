package send.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import send.dto.Message;
import send.service.KafkaSenderExample;

@RestController
public class WebController {

    @Autowired
    KafkaSenderExample kafkaSenderExample;

    @GetMapping("/hello")
    private Mono<Message> hello() {

        var msg = new Message();
        msg.setMyMessage("hello, omega!");

        kafkaSenderExample.sendMessage(msg, "demo_topic");

        return Mono.just(msg);
    }

}
