package edu.puj.toolmaster.tools.controller;

import edu.puj.toolmaster.tools.services.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tools")
public class ToolsController {

    @Autowired
    ToolService service;
}
