package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PendudukController {

	@RequestMapping("/penduduk")
    public String index (Model model, 
    		@RequestParam(value = "nik", required = false) String nik)
    {
        return "get-penduduk-by-npm";
    }
}
