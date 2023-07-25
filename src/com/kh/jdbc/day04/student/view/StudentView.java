package com.kh.jdbc.day04.student.view;

import java.util.List;
import java.util.Scanner;

import com.kh.jdbc.day04.student.model.vo.Student;
import com.kh.jdbc.day04.student.controller.StudentController;

public class StudentView {
	
	private StudentController controller;
	
	public StudentView() {
		controller = new StudentController();
		
	}
	public void startProgram() {
		List<Student> sList = null;
		String studentId = "";
		int result = 0;
		while (true) {
			int choice = printMenu();
			switch (choice) {
			case 1:
				//SELECT * FROM STUDENT_TBL
				sList = controller.selectAll();
				if(!sList.isEmpty()) {
					printAllStudents(sList);					
				} else {
					displayError("데이터 조회에 실패하였습니다.");
				}
				break;
			case 2:
				//SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
				studentId = inputStdId("검색");
				Student student = controller.selectOneById(studentId);
				if(student != null) {
					printOneStudent(student);					
				} else {
					displayError("데이터 조회에 실패하였습니다.");
				}
				break;
			case 3:
				//SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '일용자';
				
				String studentName = inputStdName();
				sList = controller.selectAllByName(studentName);
//				if(sList.size()>0)
				if(!sList.isEmpty()) {
					printAllStudents(sList);					
				} else {
					displayError("데이터 조회에 실패하였습니다.");
				}
				
				break;
			case 4:
				student = inputStudent();
				result = controller.insertStudent(student);
				if(result>0) {
					displaySuccess("데이터 등록 완료");
				} else {
					displayError("데이터 등록 실패");
				}
				break;
			case 5:
				studentId = inputStdId("수정");
				student = controller.selectOneById(studentId);
				if(student != null) {
					//수정 정보 입력받기
					student = modifyStudent(student);
					result = controller.updateStudent(student);
					if (result>0) {
						displaySuccess("데이터 수정 성공");
					} else {
						displayError("데이터 수정 실패");
					}
				} else {
					displayError("데이터 조회 실패");
				}
				break;
			case 6:
				//DELETE FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01';
				studentId = inputStdId("삭제");
				result = controller.deleteStudent(studentId);
				if(result>0) {
					displaySuccess("데이터 삭제 완료");
				} else {
					displayError("데이터 삭제 실패");
				}
				break;
			case 0:
				break;
			}
		}
	}

	private Student modifyStudent(Student student) {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 정보 수정 =====");
//		System.out.print("정보를 수정할 아이디 : ");
//		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine(); // 공백 제거, 엔터제거
		String address = sc.nextLine();
		System.out.print("취미(,로 구분) : ");
		String hobby = sc.nextLine();
		student.setStudentPwd(studentPw);
		student.setEmail(email);
		student.setPhone(phone);
		student.setAddress(address);
		student.setHobby(hobby);
		return student;
		
	}
	private Student inputStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 정보 등록 =====");
		System.out.print("아이디 : ");
		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		System.out.print("이름 : ");
		String studentName = sc.next();
		System.out.print("성별 : ");
		char gender = sc.next().charAt(0);
		System.out.print("나이 : ");
		int age = sc.nextInt();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine(); // 공백 제거, 엔터제거
		String address = sc.nextLine();
		System.out.print("취미(,로 구분) : ");
		String hobby = sc.nextLine();
		Student student = new Student(studentId, studentPw, studentName, gender, age, email, phone, address, hobby);
		return student;
	}
	
	private String inputStdName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 이름으로 조회 ===== ");
		System.out.print("검색할 이름 입력 : ");
		String studentName = sc.next();
		return studentName;
	}
	
	private void printOneStudent(Student student) {
			System.out.println("===== 학생 아이디로 조회 =====");
			System.out.printf("이름 : %s, 나이 : %d, 아이디 : %s, 성멸 : %s, 이메일 : %s, 전화번호 : %s, 주소 : %s, 취미 : %s, 등록일 : %s\n",
					student.getStudentName(), student.getAge(), student.getStudentId(), student.getGender(),
					student.getEmail(), student.getPhone(), student.getAddress(), student.getHobby(),
					student.getEnrollDate());

	}
	private int printMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생관리 프로그램 =====");
		System.out.println("9. 학생 로그인");
		System.out.println("1. 학생 전체 조회");
		System.out.println("2. 학생 아이디로 조회");
		System.out.println("3. 학생 이름으로 조회");
		System.out.println("4. 학생 정보 등록");
		System.out.println("5. 학생 정보 수정");
		System.out.println("6. 학생 정보 삭제");
		System.out.println("0. 프로그램 종료");
		System.out.print("메뉴 선택 : ");
		int input = sc.nextInt();
		return input;
	}
	private void displaySuccess(String message) {
		System.out.println("[서비스 성공] : " + message);
	}

	private void displayError(String message) {
		System.out.println("[서비스 실패] : " + message);

	}
	private void printAllStudents(List<Student> sList) {
		System.out.println("===== 학생 전체 조회 =====");
		// foreach문을 쓰면 인덱스를 신경안쓰고 출력가능
		for (Student student : sList) {
			System.out.printf("이름 : %s, 나이 : %d, 아이디 : %s, 성별 : %s, 이메일 : %s, 전화번호 : %s, 주소 : %s, 취미 : %s, 등록일 : %s\n",
					student.getStudentName(), student.getAge(), student.getStudentId(), student.getGender(),
					student.getEmail(), student.getPhone(), student.getAddress(), student.getHobby(),
					student.getEnrollDate());
		}		
	}
	
	private String inputStdId(String category) {
		Scanner sc = new Scanner(System.in);
		System.out.print(category + "할 아이디 입력 : ");
		String studentId = sc.next();
		return studentId;
	}
}
