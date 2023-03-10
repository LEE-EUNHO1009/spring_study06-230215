package com.eunocompany.home4.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.eunocompany.home4.dao.BDao;
import com.eunocompany.home4.dto.BDto;

public class BContentCommand implements BCommand {

	@Override
	public void excute(Model model) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map =  model.asMap();
		//model 객체 안에 있는 request를 map 매핑
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bid = request.getParameter("bid");
		
		BDao dao = new BDao();
		BDto dto = dao.contentView(bid);
		
		model.addAttribute("contentDto", dto);
	}

}