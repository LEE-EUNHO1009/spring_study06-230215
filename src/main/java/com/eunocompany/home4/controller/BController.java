package com.eunocompany.home4.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eunocompany.home4.command.BCommand;
import com.eunocompany.home4.command.BListCommand;
import com.eunocompany.home4.command.BWriteCommand;

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
	@RequestMapping(value="writeForm")
	public String writeForm() {
		return "write_form";
	}
	@RequestMapping(value="write", method=RequestMethod.POST)
	public String write(HttpServletRequest request, Model model) {
		
		model.addAttribute("request", request);  //리퀘스트 객체를 모델에 실어서 보내준다.
		
		command = new BWriteCommand();
		command.execute(model);
		
		return "redirect:list";
	}
}
