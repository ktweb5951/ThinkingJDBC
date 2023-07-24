package com.kh.jdbc.day03.student.view;

import java.util.List;
import java.util.Scanner;

import com.kh.jdbc.day03.student.controller.StudentController;
import com.kh.jdbc.day03.student.model.vo.Student;

public class StudentView {
	private StudentController controller;
	
	public StudentView() {
		controller = new StudentController();
	}

	public void studentProgram() {
		List<Student> sList = null;
		Student student = null;
		int result = 0;
		String studentId = null;
		
		theEnd: while (true) {
			int input = printMenu();
			switch (input) {
			case 1:
				sList = controller.selecAllStudent();
				printAllStudent(sList);
				break;
			case 2:
				studentId = inputStdId("검색");
				student = controller.selectOneById(studentId);
				printOneStudent(student);
				break;
			case 3:
				String studentName = inputStdName();
				sList = controller.selectAllByName(studentName);
				printAllStudent(sList);
				break;
			case 4:
				student = inputStdInfo();
				result =  controller.insertStudent(student);
				if(result>0) {
					displaySuccess("학생 정보 등록 성공");
				} else {
					displayError("학생 정보 등록 실패");
				}
				break;
			case 5:
				//UPDATE STUDENT_TBL SET STUDENT_PWD = 'pass05', EMAIL= '', PHONE='', ADDRESS='', HOBBY=''
				// WHERE STuDENT_ID = '';
//				student = modifyStdInfo();
//				result = controller.updateStudent(student);
//				if(result>0) {
//					displaySuccess("학생 정보 수정 성공");
//				} else {
//					displayError("학생 정보 수정 실패");
//				}
				
				//다른 방법 이방법의 경우 아이디 입력을 지워야함
				studentId = inputStdId("수정");
				student = controller.selectOneById(studentId);
				if(student != null) {
					// 있는거
					student = modifyStdInfo();
					student.setStudentId(studentId);
					result = controller.updateStudent(student);
					displaySuccess("학생 정보 수정 성공");
				}else {
					displayError("학생 정보 수정 실패");
				}
				break;
			case 6:
				studentId = inputStdId("삭제");
				result = controller.deleteStudent(studentId);
				if(result>0) {
					displaySuccess("학생 정보 삭제 성공");
				} else {
					displayError("학생 정보 삭제 실패");
				}
				
				break;
			case 9:
				//SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01' AND STDUENT_PWD= '1234'
				student = inputLoginInfo();
				student = controller.studentLogin(student);
				if(student != null) {
					displaySuccess("로그인 성공");
				} else {
					displayError("해당 정보가 존재하지 않습니다.");
				}
				break;
			case 0:
				System.out.println("프로그램을 종료합니다.");
				break theEnd;
			}
		}
	}



	private Student inputLoginInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 로그인 =====");
		System.out.print("아이디 : ");
		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		Student student = new Student(studentId, studentPw);
		return student;
	}

	private String inputStdId(String category) {
		Scanner sc = new Scanner(System.in);
		System.out.print(category +"할 아이디 입력 : ");
		String studentId = sc.next();
		return studentId;
	}

	private Student inputStdInfo() {
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
		String email =  sc.next();
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
		System.out.print("검색할 이름 입력 : ");
		String name = sc.next();
		return name;
	}

	private Student modifyStdInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 정보 수정 =====");
//		System.out.print("정보를 수정할 아이디 : ");
//		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		System.out.print("이메일 : ");
		String email =  sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine(); // 공백 제거, 엔터제거
		String address = sc.nextLine();
		System.out.print("취미(,로 구분) : ");
		String hobby = sc.nextLine();
		Student student = new Student(studentPw, email, phone, address, hobby);
		return student;
	}

	private void printOneStudent(Student student) {
		System.out.println("===== 학생 아이디로 조회 =====");
		System.out.printf("이름 : %s, 나이 : %d, 아이디 : %s, 성멸 : %s, 이메일 : %s, 전화번호 : %s, 주소 : %s, 취미 : %s, 가입날짜 : %s\n",
					student.getStudentName(), student.getAge(), student.getStudentId(), student.getGender(),
					student.getEmail(), student.getPhone(), student.getAddress(), student.getHobby(),
					student.getEnrollDate());
		
	}

	private void printAllStudent(List<Student> sList) {
		System.out.println("===== 학생 전체 조회 =====");
		// foreach문을 쓰면 인덱스를 신경안쓰고 출력가능
		for (Student student : sList) {
			System.out.printf("이름 : %s, 나이 : %d, 아이디 : %s, 성별 : %s, 이메일 : %s, 전화번호 : %s, 주소 : %s, 취미 : %s, 가입날짜 : %s\n",
					student.getStudentName(), student.getAge(), student.getStudentId(), student.getGender(),
					student.getEmail(), student.getPhone(), student.getAddress(), student.getHobby(),
					student.getEnrollDate());
		}
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
}
