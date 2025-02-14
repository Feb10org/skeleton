package abc.skeleton.rest.controller;

import abc.skeleton.rest.model.Message;
import abc.skeleton.rest.service.SkeletonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController {

    private final SkeletonService skeletonService;

    public ProcessController(SkeletonService skeletonService) {
        this.skeletonService = skeletonService;
    }

    @GetMapping("/process")
    public ResponseEntity<Message> process() {
        String response = skeletonService.getHelloMessage("SpringBootUser");

        return ResponseEntity.ok(skeletonService.processMessage("Processing: " + response));
    }
}
