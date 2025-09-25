package crmfitness.project.controller;

import crmfitness.project.service.SupportService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/support")
public class SupportController {

    private final SupportService supportService;

    @GetMapping("/message")
    public String getMessage() {
        return supportService.getMessage();
    }
}
