package com.kh.jdbc.day02.student.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day01.student.model.vo.Student;

public class StudentDAO {
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USER = "STUDENT";
	private final String PASSWORD = "STUDENT";

	public List<Student> selectAll() {
		/*
		 * 1.드라이브 등록 2. DB 연결 생성 3. 쿼리문 실행 준비 4. 쿼리문 실행 및 5. 결과 받기 6. 자원해제(close())
		 */
		String query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = null;
		Student student = null;
		try {
			// 1. 드라이버 등록
			Class.forName(DRIVER_NAME);
			// 2. DB연결 생성(DriverManager)
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			sList = new ArrayList<Student>();
			// 후처리
			while (rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
			}
			// 6. 자원해제
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sList;
	}

	public Student selectOneByID(String studentId) {
		// SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID ='" + studentId + "'";
		Student student = null;
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			// 결과가 한개일땐 if문으로 후처리, 여러개일땐 while(rset.next()){}
			if (rset.next()) {
				student = rsetToStudent(rset);
			}
			rset.close();
			stmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return student;
	}

	public List<Student> selectAllByName(String studentName) {

		// SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '일용자' ;
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME ='" + studentName + "'";
		List<Student> sList = new ArrayList<Student>();
		Student student = null;

		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sList;
	}
	
	public int insertStudent(Student student) {
			/*
			 * 1. 드라이버 등록 2. DB 연결 생성 3. 쿼리문 실행 준비 4. 쿼리문 실행 및 5. 결과 받기 6. 자원 해제
			 */
			// INSERT INTO STUDENT_TBL VALUES('user11', 'pas11', '홍길동', 'M',
			// 23,'hong77@kh.org', '01077778888', '경기도 수원시 팔달구 팔달동 77', '운동,등산,기타',
			// '17/09/21');
			String query = "INSERT INTO STUDENT_TBL VALUES(" + "'" + student.getStudentId() + "', " + "'"
					+ student.getStudentPwd() + "', " + "'" + student.getStudentName() + "', " + "'" + student.getGender()
					+ "', " + student.getAge() + ", " + "'" + student.getEmail() + "', " + "'" + student.getPhone() + "', "
					+ "'" + student.getAddress() + "', " + "'" + student.getHobby() + "', " + "SYSDATE)";
			int result = -1;
			try {
				// 1. 드라이버 등록
				Class.forName(DRIVER_NAME);
				// 2. DB 연결 생성
				Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				// 3. 쿼리 실행 준비
				Statement stmt = conn.createStatement();
				// 4. 실행하고 결과 받기
	//			stmt.executeQuery(query);   //SELECT용 
				result = stmt.executeUpdate(query); // DML(INSERT, UDDATE, DELETE)\
				stmt.close();
				conn.close();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
	
			return result;
		}

	public int deleteStudent(String studentId) {
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID ='"+ studentId +"'";
		int result = -1;
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateStudent(Student student) {
//		UPDATE STUDENT_TBL
//		SET STUDENT_PWD = 'pass11', EMAIL = 'khuser01@iei.or.kr', PHONE = '01092952033', ADDRESS = '서울시 강남구', HOBBY = '코딩,수영'
//		WHERE STUDENT_ID = 'khuser01';
		String query = "UPDATE STUDENT_TBL SET "
				+ "STUDENT_PWD = '"+ student.getStudentPwd() + "', "
						+ "EMAIL = '"+ student.getEmail() +"', "
								+ "PHONE = '"+student.getPhone()+"', "
										+ "ADDRESS = '"+student.getAddress()+"', "
												+ "HOBBY = '"+student.getHobby()+"' "
														+ "WHERE STUDENT_ID = '" + student.getStudentId() + "'" ; 
	
		int result = -1;
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	private Student rsetToStudent(ResultSet rset) throws SQLException {
		Student student = new Student();
		student.setStudentId(rset.getString("STUDENT_ID"));
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
