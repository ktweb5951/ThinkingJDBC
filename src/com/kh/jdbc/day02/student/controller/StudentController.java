package com.kh.jdbc.day02.student.controller;

import java.util.List;

import com.kh.jdbc.day01.student.model.dao.StudentDAO;
import com.kh.jdbc.day01.student.model.vo.Student;

public class StudentController {
	private StudentDAO studentDao;
	
	public StudentController() {
		 studentDao = new StudentDAO();
	}
	public List<Student> printStduentList() {
		List<Student> sList = studentDao.selectAll();
		return sList;
	}
	public Student printStudentByID(String studentId) {
		Student student = studentDao.selectOneByID(studentId);
		return student;
	}
	public List<Student> printStudentsByName(String studentName) {
		List<Student> sList = studentDao.selectAllByName(studentName);
		return sList;
	}
	public int insertStudent(Student student) {
		int result =  studentDao.insertStudent(student);
		return result;
	}
	public int deleteStudent(String studentId) {
		int result = studentDao.deleteStudent(studentId);
		return result;
	}
	public int modifyStudent(Student student) {
		int result = studentDao.updateStudent(student);
		return result;
	}

}
