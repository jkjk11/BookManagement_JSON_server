package com.cjon.book.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.service.BookService;

/**
 * Servlet implementation class sessionServlet
 */
@WebServlet("/session")
public class sessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sessionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String callback = request.getParameter("callback"); 
		
		//session객체 부르기
		HttpSession session=request.getSession(true);
		
		JSONArray arr=new JSONArray();
		JSONObject obj=new JSONObject();
		
		obj.put("sessionID", session.getAttribute("sessionID"));
		obj.put("sessionLogin", session.getAttribute("sessionLogin"));
		arr.add(obj);
		
		String result=arr.toJSONString();
		
		// 결과로 가져올건..DB 처리한 후 나온 책에 대한 JSON data		
		// 3. 출력처리
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out = response.getWriter();
		out.println(callback + "(" + result + ")");
		out.flush();
		out.close();
	}

}
