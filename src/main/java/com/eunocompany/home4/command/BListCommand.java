package com.eunocompany.home4.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.eunocompany.home4.dao.BDao;
import com.eunocompany.home4.dto.BDto;

public class BListCommand implements BCommand {

	@Override
	public void excute(Model model) {
		// TODO Auto-generated method stub
		
		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.list();
		model.addAttribute("boardCount", dao.boardCount());
		
		model.addAttribute("list", dtos);
	}

}