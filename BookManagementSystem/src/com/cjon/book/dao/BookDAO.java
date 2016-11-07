package com.cjon.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.common.DBTemplate;

public class BookDAO {

	public String select(String keyword) {
		// Database처리가 나와요!
		// 일반적으로 Database처리를 쉽게 하기 위해서
		// Tomcat같은 경우는 DBCP라는걸 제공해 줘요!
		// 추가적으로 간단한 라이브러리를 이용해서 DB처리를 해 볼꺼예요!!
		// 1. Driver Loading ( 설정에 있네.. )
		// 2. Connection 생성
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		//System.out.println("들어옴");
		try {
			String sql = "select bisbn, bimgbase64, btitle, bauthor, bprice "
					   + "from book where btitle like ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			System.out.println(keyword);
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("img", rs.getString("bimgbase64"));
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("price", rs.getString("bprice"));
				arr.add(obj);
				//System.out.println(arr.size());
			}
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean update(String isbn, String title, String author, String price) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
			System.out.println(isbn);
			System.out.println(title);
			System.out.println(author);
			System.out.println(price);
			String sql = "update book set btitle=?, bauthor=?, bprice=? where bisbn=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setInt(3, Integer.parseInt(price));
			pstmt.setString(4, isbn);
			
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean insert(String img, String isbn, String title, String author, String price) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try{
			System.out.println(img);
			System.out.println(isbn);
			System.out.println(title);
			System.out.println(author);
			System.out.println(price);
			
			String sql="insert into book(bisbn, bimgbase64, btitle, bauthor, bprice) values(?,?,?,?,?)";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setString(2, img);
			pstmt.setString(3, title);
			pstmt.setString(4, author);
			pstmt.setInt(5, Integer.parseInt(price));
			
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		}catch(Exception e){
			System.out.println(e);
		}finally{
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}
		return result;
	}

	public String detail(String isbn) {

		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		System.out.println("dddd 들어옴");
		
		try {
			String sql = "select bdate, bpage, btranslator, bpublisher from book where bisbn=?";
			
			pstmt= con.prepareStatement(sql);
			
			pstmt.setString(1, isbn);
			
			rs = pstmt.executeQuery();
			
			System.out.println(isbn);
			
			if(rs.next()){
			
				JSONObject obj = new JSONObject();
				obj.put("date", rs.getString("bdate"));
				obj.put("page", rs.getString("bpage"));
				obj.put("translator", rs.getString("btranslator"));
				obj.put("publisher", rs.getString("bpublisher"));
				result = obj.toJSONString();
			
			}
			
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean signup(String mid, String memail, String mpassword) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
			System.out.println(mid);
			System.out.println(memail);
			System.out.println(mpassword);
			String sql = "insert into member values(?,?,?)";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, mid);
			pstmt.setString(2, memail);
			pstmt.setString(3, mpassword);
			
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean login(String lid, String lpassword) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;		
		boolean result = false;
		String passCheck=null;
		
		try {
			System.out.println("dao:" +lid +lpassword);
			String sql = "select mpassword from member where mid=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, lid);
			
			rs=pstmt.executeQuery(); 
			
			while(rs.next()){
				passCheck=rs.getString("mpassword");
			}
			
			if(lpassword.equals(passCheck)==true){
				System.out.println("pw일치");
				result=true;
			}else{
				System.out.println("pw불일치");
				result=false;
			}
			System.out.println(result);			
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean saveReview(String reviewContent, String lid, String myIsbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;		
		boolean result = false;
		String passCheck=null;
		
		try {
			System.out.println("dao:" +reviewContent + "로그인ID: " +lid + "isbn: " + myIsbn);
			String sql = "insert into review (rcontent,mid,bisbn) values (?,?,?)";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, reviewContent);
			pstmt.setString(2, lid);
			pstmt.setString(3, myIsbn);
						
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}	
			System.out.println(result);	
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public String reviewList(String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;		
		String result = null;
		
		try {
			System.out.println("dao:"+ isbn);
			String sql = "select seq, rcontent, mid, bisbn from review where bisbn=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			
			//System.out.println(sql + isbn);
			
			rs = pstmt.executeQuery();
			
			JSONArray arr = new JSONArray();
			
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("no", rs.getString("seq"));
				obj.put("rcontent", rs.getString("rcontent"));
				obj.put("mid", rs.getString("mid"));
				obj.put("isbn", rs.getString("bisbn"));
				arr.add(obj);
				//System.out.println(arr.size());
			}
			result = arr.toJSONString();		
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
		}

	public boolean deleteMyReview(String myReviewNo) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;		
		boolean result = false;
		String passCheck=null;
		
		try {
			System.out.println("dao:" +myReviewNo);
			String sql = "delete from review where seq=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, myReviewNo);
						
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}	
			System.out.println(result);	
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
		}

	public String reviewSearch(String reviewKeyword) {
		// Database처리가 나와요!
		// 일반적으로 Database처리를 쉽게 하기 위해서
		// Tomcat같은 경우는 DBCP라는걸 제공해 줘요!
		// 추가적으로 간단한 라이브러리를 이용해서 DB처리를 해 볼꺼예요!!
		// 1. Driver Loading ( 설정에 있네.. )
		// 2. Connection 생성
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		//System.out.println("들어옴");
		try {
			String sql = "select b.btitle, r.mid, r.rcontent from review r join book b on b.bisbn=r.bisbn where r.rcontent like ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, "%" + reviewKeyword + "%");
			
			rs = pstmt.executeQuery();
			System.out.println(reviewKeyword);
			
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("title", rs.getString("btitle"));
				obj.put("mid", rs.getString("mid"));
				obj.put("rcontent", rs.getString("rcontent"));
				arr.add(obj);
				//System.out.println(arr.size());
			}
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public String shareList(String shareKeyword) {
		// Database처리가 나와요!
		// 일반적으로 Database처리를 쉽게 하기 위해서
		// Tomcat같은 경우는 DBCP라는걸 제공해 줘요!
		// 추가적으로 간단한 라이브러리를 이용해서 DB처리를 해 볼꺼예요!!
		// 1. Driver Loading ( 설정에 있네.. )
		// 2. Connection 생성
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		//System.out.println("들어옴");
		try {
			String sql = "select bimgbase64, btitle, bauthor, rstatus, rental.mid, book.bisbn from book " +
						"left outer join rental on book.bisbn= rental.bisbn where btitle like ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, "%" + shareKeyword + "%");
			rs = pstmt.executeQuery();
			System.out.println(shareKeyword);
			
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("img", rs.getString("bimgbase64"));
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("rstatus", rs.getString("rstatus"));
				obj.put("mid", rs.getString("mid"));
				obj.put("isbn", rs.getString("bisbn"));
				arr.add(obj);
				//System.out.println(arr.size());
			}
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean shareStatus(String isbn, String lid) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;		
		boolean result = false;
		
		try {
			System.out.println("dao isbn:" +isbn + "로그인ID: " +lid );
			String sql = "insert into rental (rstatus, mid, bisbn) values ('대여중',?,?)";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, lid);
			pstmt.setString(2, isbn);
						
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}	
			System.out.println(result);	
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
		}

	public String myShare(String lid) {
		// Database처리가 나와요!
		// 일반적으로 Database처리를 쉽게 하기 위해서
		// Tomcat같은 경우는 DBCP라는걸 제공해 줘요!
		// 추가적으로 간단한 라이브러리를 이용해서 DB처리를 해 볼꺼예요!!
		// 1. Driver Loading ( 설정에 있네.. )
		// 2. Connection 생성
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		//System.out.println("들어옴");
		try {
			String sql = "select btitle, bauthor, rstatus, rental.mid, book.bisbn from book " +
						"left outer join rental on book.bisbn= rental.bisbn where rental.mid=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, lid);
			rs = pstmt.executeQuery();
			System.out.println(lid);
			
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("rstatus", rs.getString("rstatus"));
				obj.put("mid", rs.getString("mid"));
				obj.put("isbn", rs.getString("bisbn"));
				arr.add(obj);
				//System.out.println(arr.size());
			}
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean returnMyBook(String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;		
		boolean result = false;
		
		try {
			System.out.println("dao isbn:" +isbn );
			String sql = "delete from rental where bisbn=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
						
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}	
			System.out.println(result);	
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	
}
















