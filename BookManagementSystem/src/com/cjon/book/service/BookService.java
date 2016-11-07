package com.cjon.book.service;

import com.cjon.book.dao.BookDAO;

public class BookService {

	// 리스트를 가져오는 일을 하는 method
	public String getList(String keyword) {
		// 일반적인 로직처리 나와요!!
		
		// 추가적으로 DB처리가 나올 수 있어요!
		// DB처리는 Service에서 처리는하는게 아니라..다른 객체를 이용해서
		// Database처리를 하게 되죠!!
		BookDAO dao = new BookDAO();
		String result = dao.select(keyword);	
		
		return result;
	}

	public boolean updateBook(String isbn, String title, String author, String price) {
		// TODO Auto-generated method stub
		BookDAO dao = new BookDAO();
		boolean result = dao.update(isbn,title, author, price);	
		return result;
	}

	public boolean insertBook(String img, String isbn, String title, String author, String price) {
		
		BookDAO dao= new BookDAO();
		boolean result =dao.insert(img, isbn,title, author, price);
		
		return result;
	}

	public String getDetailInfo(String isbn) {
		BookDAO dao = new BookDAO();
		String result = dao.detail(isbn);	
		
		return result;
	}

	public boolean getSignup(String mid, String memail, String mpassword) {
		
		BookDAO dao= new BookDAO();
		boolean result =dao.signup(mid, memail, mpassword);
		
		return result;
	}

	public boolean getLogin(String lid, String lpassword) {
		
		
		BookDAO dao= new BookDAO();
		boolean result =dao.login(lid,lpassword );
		
		return result;
	}

	public boolean getSaveReview(String reviewContent, String lid, String myIsbn) {
		
		BookDAO dao= new BookDAO();
		boolean result =dao.saveReview(reviewContent, lid ,myIsbn);
		
		return result;
	}

	public String getReviewList(String isbn) {
		BookDAO dao= new BookDAO();
		String result =dao.reviewList(isbn);
		
		return result;
	}

	public boolean getDeleteMyReview(String myReviewNo) {
		BookDAO dao= new BookDAO();
		boolean result =dao.deleteMyReview(myReviewNo);
		
		return result;
	}

	public String getReviewSearch(String reviewKeyword) {
		
		BookDAO dao = new BookDAO();
		String result = dao.reviewSearch(reviewKeyword);	
		
		return result;
	}

	public String getShareList(String shareKeyword) {
		BookDAO dao = new BookDAO();
		String result = dao.shareList(shareKeyword);	
		
		return result;
	}

	public boolean getShareStatus(String isbn, String lid) {
		BookDAO dao= new BookDAO();
		boolean result =dao.shareStatus(isbn, lid);
		
		return result;
	}

	public String getMyShare(String lid) {
		
		BookDAO dao = new BookDAO();
		String result = dao.myShare(lid);	
		
		return result;
	}

	public boolean getReturnMyBook(String isbn) {
		
		BookDAO dao= new BookDAO();
		boolean result =dao.returnMyBook(isbn);
		
		return result;
	}



}












