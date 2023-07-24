package com.kh.jdbc.day03.student.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day03.student.model.vo.Student;

public class StudentDAO {
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private final String USER = "STUDENT";
	private final String PASSWORD = "STUDENT";
	
	public List<Student> selectAll() {
		String query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = null;
		Student student = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
				
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			sList = new ArrayList<Student>();
			while(rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {			
			try {
				conn.close();
				stmt.close();
				rset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return sList;
	}


	public List<Student> selectAllByName(String studentName) {
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME =  ?";
		Student student = null;
		List<Student> sList = null;
		Connection conn  = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			sList = new ArrayList<Student>();
			while(rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
				rset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return sList;
	}


	public Student selectOneById(String studentId) {
//		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = '"+studentId+"'";
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ?";
		Student student = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			Statement stmt = conn.createStatement();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				student = rsetToStudent(rset);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return student;
	}

	public Student selectLoginInfo(Student student) {
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ? AND STUDENT_PWD = ?";
		Student result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			//sql인젝션 공격 방어 방법
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentId());
			pstmt.setString(2, student.getStudentPwd()); //시작은 1로해서 마지막 숫자는 물음표 갯수와 같아야함.(물음표 = 위치홀더)
			rset = pstmt.executeQuery();
			while(rset.next()) {
				result = rsetToStudent(rset);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}


	public int insertStudent(Student student) {
		// INSERT INTO STUDENT_TBL VALUES('user11', 'pas11', '홍길동', 'M',
		// 23,'hong77@kh.org', '01077778888', '경기도 수원시 팔달구 팔달동 77', '운동,등산,기타',
		// '17/09/21');
//		String query = "INSERT INTO STUDENT_TBL VALUES("
//				+ "'"+student.getStudentId()+"',"
//						+ " '"+student.getStudentPwd()+"',"
//								+ " '"+student.getStudentName()+"',"
//										+ " '"+student.getGender()+"',"
//												+ " "+student.getAge()+","
//														+ " '"+student.getEmail()+"',"
//																+ " '"+student.getPhone()+"',"
//																		+ " '"+student.getAddress()+"',"
//																				+ " '"+student.getHobby()+"',"
//																						+ " + SYSDATE)";
		String query = "INSERT INTO STUDENT_TBL VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		int result = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			Statement stmt = conn.createStatement();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentId());
			pstmt.setString(2, student.getStudentPwd());
			pstmt.setString(3, student.getStudentName());
//			pstmt.setString(4, student.getGender()+"");
			pstmt.setString(4, String.valueOf(student.getGender()));
			pstmt.setInt(5, student.getAge());
			pstmt.setString(6, student.getEmail());
			pstmt.setString(7, student.getPhone());
			pstmt.setString(8, student.getAddress());
			pstmt.setString(9, student.getHobby());
			result = pstmt.executeUpdate(); // 쿼리문 실행 빼먹지 않기
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}


	public int deleteStudent(String studentId) {
		// 1. 위치 홀더 셋팅
		// 2. PreparedStatement 객체 생성 with query
		// 3. 입력값 세팅
		// 4. 쿼리문 실행 및 결과 받기(feat. mehthod())
//		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = '"+studentId+"'";
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = ?";
		int result = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			Statement stmt = conn.createStatement();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			result = pstmt.executeUpdate();
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();			
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}


	public int updateStudent(Student student) {
//		String query = "UPDATE STUDENT_TBL SET "
//				+ "STUDENT_PWD = '"+student.getStudentPwd()+"', "
//						+ "EMAIL = '"+student.getEmail()+"', "
//								+ "PHONE = '"+student.getPhone()+"', "
//										+ "ADDRESS = '"+student.getAddress()+"', "
//												+ "HOBBY= '"+student.getHobby()+"' "
//														+ "WHERE STUDENT_ID = '"+student.getStudentId()+"'";
		String query = "UPDATE STUDENT_TBL SET STUDENT_PWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?,  HOBBY= ?  WHERE STUDENT_ID = ?";
		int result = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			Statement stmt = conn.createStatement();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentPwd());
			pstmt.setString(2, student.getEmail());
			pstmt.setString(3, student.getPhone());
			pstmt.setString(4, student.getAddress());
			pstmt.setString(5, student.getHobby());
			pstmt.setString(6, student.getStudentId());
			result = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {			
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		return result;
	}


	private Student rsetToStudent(ResultSet rset) throws SQLException {
		Student student = new Student();
		//rset.getString(1) 컬럼의 순번으로 가져올 수 있다.
		student.setStudentId(rset.getString(1));
		student.setStudentPwd(rset.getString("STUDENT_PWD"));
		student.setStudentName(rset.getString("STUDENT_NAME"));
		student.setAge(rset.getInt("AGE"));
		student.setEmail(rset.getString("EMAIL"));
		student.setPhone(rset.getString("PHONE"));
		// 문자는 문자열에 문자로 잘라서 사용, charAt() 메소드 사용
		student.setGender(rset.getString("GENDER").charAt(0));
		student.setAddress(rset.getString("ADDRESS"));
		student.setHobby(rset.getString("HOBBY"));
		student.setEnrollDate(rset.getDate("ENROLL_DATE"));
		return student;
	}
}
