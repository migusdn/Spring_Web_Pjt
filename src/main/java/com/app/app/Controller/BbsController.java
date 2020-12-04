package com.app.app.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.app.Dao.BDao;
import com.app.app.Dao.BbsDao;
import com.app.app.Dto.BbsDto;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;




@Controller
public class BbsController {
	@Autowired
	private SqlSession sqlSession;
	
	private static final Logger logger = LoggerFactory.getLogger(AppController.class);
	
	
	@RequestMapping("/write")
	public String Write(Model model, HttpSession session, HttpServletResponse response) throws IOException {
		if(session.getAttribute("user_id") == null) {
			System.out.println("로그인세션 없음");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('잘못된 접근'); history.back();</script>");
			out.flush();
		}
			
	return "write";	
	}
	@RequestMapping("/WriteAct")
	public String WriteAct(HttpSession session, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		BbsDto BDto = new BbsDto(request.getParameter("bbs_title"), request.getParameter("bbs_content"), request.getParameter("user_id"));
		Map map = new HashMap();
		BDao dao = sqlSession.getMapper(BDao.class);
		dao.Write(BDto);
		return "index";
	}
	
	@RequestMapping("/bbs")
	public String bbs(Model model) {
		
		BDao dao = sqlSession.getMapper(BDao.class);
		ArrayList<BbsDto> list = dao.list();
		model.addAttribute("list",list);
		return "bbs";
	}
	@RequestMapping("/bbs/{bbs_id}")
	public String bbs_detail(@PathVariable String bbs_id, Model model) {
		BDao dao = sqlSession.getMapper(BDao.class);
		BbsDto detail = dao.detail(bbs_id);
		model.addAttribute("detail", detail);
		return "bbs_detail";
	}
}
