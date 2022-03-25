package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
public class InfoController {

    private final ServerProperties serverProperties;
    Logger logger = LoggerFactory.getLogger(InfoController.class);

    public InfoController(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @GetMapping("/getPort")
    public ResponseEntity<Integer> getPortNumber() {
        int port = serverProperties.getPort();
        return ResponseEntity.ok(port);
    }
    @GetMapping("/getSum")
    public ResponseEntity<Integer> getSum(int n) {
        long time = System.currentTimeMillis();
        logger.debug("Start calculating sum");
        int sum = IntStream
                .rangeClosed(1,n)
                .parallel()
                .reduce(0, Integer::sum);
        time = System.currentTimeMillis() - time;
        logger.debug("Start calculating sum, time={}",time);
        return ResponseEntity.ok(sum);
    }
}
