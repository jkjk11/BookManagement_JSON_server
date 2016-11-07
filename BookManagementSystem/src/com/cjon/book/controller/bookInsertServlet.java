package com.cjon.book.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjon.book.service.BookService;

/**
 * Servlet implementation class bookInsertServlet
 */
@WebServlet("/bookInsert")
public class bookInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public bookInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 입력받고
				String img=request.getParameter("img");
				String isbn = request.getParameter("isbn");
				String title = request.getParameter("title");
				String author = request.getParameter("author");
				String price = request.getParameter("price");
				String callback = request.getParameter("callback");
				// 2. 로직처리
				BookService service = new BookService();
				boolean result = service.insertBook(img,isbn, title, author, price);
				// 3. 출력처리
				response.setContentType("text/plain; charset=utf8");
				PrintWriter out = response.getWriter();
				out.println(callback + "(" + result + ")");
				out.flush();
				out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
