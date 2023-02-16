package com.eunocompany.home4.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.eunocompany.home4.dao.BDao;

public class BWriteCommand implements BCommand {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		//model 객체 안에 있는 request를 map으로 매핑
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bname = request.getParameter("bname");
		String btitle = request.getParameter("btitle");
		String bcontent = request.getParameter("bcontent");
		
		BDao dao=new BDao();
		dao.write(bname, btitle, bcontent);
		
	}

}
