package com.cjon.book.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cjon.book.service.BookService;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/login")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {// 1. 입력받고
		String lid = request.getParameter("lid"); 
		String lpassword = request.getParameter("lpassword"); 
		String callback = request.getParameter("callback"); 
		
		System.out.println("서블릿: "+ lid+ lpassword);
	
		// 2. 로직처리하고(DB처리포함)
		// Servlet은 입력을 받고 출력에대한 지정을 담당. 로직처리는 하지 않아요!!
		// 로직처리하는 객체를 우리가 일반적으로 Service객체라고 불러요! 이놈을 만들어서 일을 시켜서
		// 결과를 받아오는 구조로 만들어 보아요!
		// 로직처리를 하기 위해서 일단 Service객체를 하나 생성합니다.
		
		BookService service = new BookService();		
		boolean result = service.getLogin(lid,lpassword);
		
		//로그인 됐으면 로그인 정보 세션에 저장. 
		if(result==true){
			//세션 생성
			HttpSession session=request.getSession(true); //세션 true = 세션 가능
			session.setAttribute("sessionID", lid);
			session.setAttribute("sessionLogin", "ok");
		}
		
		// 결과로 가져올건..DB 처리한 후 나온 책에 대한 JSON data		
		// 3. 출력처리
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out = response.getWriter();
		out.println(callback + "(" + result + ")");
		out.flush();
		out.close();
	}

}
