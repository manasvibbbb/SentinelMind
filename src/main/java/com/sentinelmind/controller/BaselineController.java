package com.sentinelmind.controller;

import com.sentinelmind.repository.BaselineProfileRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaselineController {

    private final BaselineProfileRepository baselineProfileRepository;

    public BaselineController(BaselineProfileRepository baselineProfileRepository) {
        this.baselineProfileRepository = baselineProfileRepository;
    }

    @GetMapping("/baselines")
    public String baselines(Model model) {

        model.addAttribute(
                "baselines",
                baselineProfileRepository.findAll()
        );

        return "baselines";
    }
}
