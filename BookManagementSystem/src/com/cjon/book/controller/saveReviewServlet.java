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
 * Servlet implementation class saveReviewServlet
 */
@WebServlet("/saveReview")
public class saveReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public saveReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {// 1. 입력받고
		String reviewContent = request.getParameter("reviewContent"); // 책에 대한 keyword를 받는부분
		
		//session 불러오기. 
		HttpSession session=request.getSession(true);
		String lid=(String) session.getAttribute("sessionID");
		
		String callback = request.getParameter("callback"); // JSONP처리를 위해서 사용
		
		String myIsbn=request.getParameter("myIsbn");
		
		
		// 2. 로직처리하고(DB처리포함)
		// Servlet은 입력을 받고 출력에대한 지정을 담당. 로직처리는 하지 않아요!!
		// 로직처리하는 객체를 우리가 일반적으로 Service객체라고 불러요! 이놈을 만들어서 일을 시켜서
		// 결과를 받아오는 구조로 만들어 보아요!
		// 로직처리를 하기 위해서 일단 Service객체를 하나 생성합니다.
		BookService service = new BookService();		
		boolean result = service.getSaveReview(reviewContent, lid,myIsbn );
		
		// 결과로 가져올건..DB 처리한 후 나온 책에 대한 JSON data		
		// 3. 출력처리
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out = response.getWriter();
		out.println(callback + "(" + result + ")");
		out.flush();
		out.close();
	}

}
