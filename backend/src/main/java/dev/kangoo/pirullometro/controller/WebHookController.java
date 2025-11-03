package dev.kangoo.pirullometro.controller;

import dev.kangoo.pirullometro.service.WebHookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/v1/api/webhook")
public class WebHookController {

    private final WebHookService webHookService;

    private static final Logger log = LoggerFactory.getLogger(WebHookController.class);

    public WebHookController(WebHookService webHookService) {
        this.webHookService = webHookService;
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> verifySubscription(
            @RequestParam(name = "hub.mode", required = false) String mode,
            @RequestParam(name = "hub.topic", required = false) String topic,
            @RequestParam(name = "hub.challenge", required = false) String challenge,
            @RequestParam(name = "hub.lease_seconds", required = false) String leaseSeconds) {
        if (challenge != null)
            return ResponseEntity.ok(challenge);
        return ResponseEntity.badRequest().body("Missing challenge");
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE, MediaType.ALL_VALUE})
    public ResponseEntity<String> receiveNotification(InputStream bodyStream) {
        log.info("Received notification");
        try (bodyStream) {
            String requestBody = new String(bodyStream.readAllBytes(), StandardCharsets.UTF_8);
            this.webHookService.processNotification(requestBody);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error reading notification");
        }

        return ResponseEntity.ok().build();
    }
}