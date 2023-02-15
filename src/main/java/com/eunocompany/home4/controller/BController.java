package com.eunocompany.home4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eunocompany.home4.command.BCommand;
import com.eunocompany.home4.command.BListCommand;

@Controller
public class BController {

	BCommand command= null;
	
	@RequestMapping(value ="/")
	public String index() {
		return "index";
	}
	@RequestMapping("/list")
	public String list(Model model) {
		
		command = new BListCommand();
		command.execute(model);
		
		return "list";
	}
}
