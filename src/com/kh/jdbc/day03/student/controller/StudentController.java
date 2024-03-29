package com.kh.jdbc.day03.student.controller;

import java.util.List;

import com.kh.jdbc.day03.student.model.dao.StudentDAO;
import com.kh.jdbc.day03.student.model.vo.Student;

public class StudentController {
	private StudentDAO sDao;
	
	public StudentController() {
		sDao = new StudentDAO();
	}
	//쿼리문을 생각해 보고 매개변수, 반환값 예측
	//SELECT * FROM STUDENT_TBL
	public List<Student> selecAllStudent() {
		List<Student> sList = sDao.selectAll();
		return sList;
	}
	public Student selectOneById(String studentId) {
		Student student = sDao.selectOneById(studentId);
		return student;
	}
	public List<Student> selectAllByName(String studentName) {
		List<Student> sList = sDao.selectAllByName(studentName);
		return sList;
	}
	public int insertStudent(Student student) {
		int result = sDao.insertStudent(student);
		return result;
	}
	

	public int deleteStudent(String studentId) {
		int result = sDao.deleteStudent(studentId);
		return result;
	}
	public int updateStudent(Student student) {
		int result = sDao.updateStudent(student);
		return result;
	}
	public Student studentLogin(Student student) {
		Student result = sDao.selectLoginInfo(student);
		return result;
	}


}
