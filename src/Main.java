package studentProject;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
	public static final int INPUT = 1, UPDATE = 2, DELETE = 3, SEARCH = 4;
	public static final int OUTPUT = 5, SORT = 6, STATS = 7, EXIT = 8;
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] agrs) {
		List<Student> list = new ArrayList<Student>();

		boolean loopflag = false;

		while (!loopflag) {
			int num = displayMenu();
			switch (num) {
			case INPUT:
				studentInputData();
				break;
			case UPDATE:
				studentUpDate();
				break;
			case DELETE:
				studentDelete();
				break;
			case SEARCH:
				studentSearch();
				break;
			case OUTPUT:
				studentOutput();
				break;
			case SORT:
				studentSort();
				break;
			case STATS:
				studentStats();
				break;
			case EXIT:
				System.out.println("프로그램 종료");
				return;
			default:
				System.out.println("1~8번 중에 입력해주세요.");
				return;
			}
		}
	}

	private static void studentStats() {
		List<Student> list = new ArrayList<Student>();
		try {
			DBConnection dbConnection = new DBConnection();
			dbConnection.connect();

			System.out.print("통계방식선택 (1.최고점수 2.최저점수) >>");
			int type = sc.nextInt();
			//번호 패턴
			boolean value = checkInputPattern(String.valueOf(type), 6);
			if(!value) {
				return;
			}
			
			list = dbConnection.selectMaxMin(type);
			
			if (list.size() <= 0) {
				System.out.println("보여줄 리스트가 없습니다.");
				return;
			}
			
			System.out.println("학번\t이름\t국어점수\t영어점수\t수학점수\t총점\t평균\t등급\t순위");
			for (Student student : list) {
				System.out.println(student);
			}
			dbConnection.close();
		}catch (InputMismatchException e) {
			System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요. " + e.getMessage());
			return;
		}catch (Exception e) {
			System.out.println("데이터베이스 통계 에러. " + e.getMessage());
		}
	}
	
	private static void studentSort() {
		List<Student> list = new ArrayList<Student>();
		try {
			DBConnection dbConnection = new DBConnection();
			dbConnection.connect();

			System.out.print("정렬방식선택 (1.학번순 2.이름순 3.총점순) >>");
			int type = sc.nextInt();
			//번호 패턴
			boolean value = checkInputPattern(String.valueOf(type), 5);
			if(!value) {
				return;
			}
			
			list = dbConnection.selectOrderBy(type);
			
			if (list.size() <= 0) {
				System.out.println("보여줄 리스트가 없습니다.");
				return;
			}

			System.out.println("학번\t이름\t국어점수\t영어점수\t수학점수\t총점\t평균\t등급\t순위");
			for (Student student : list) {
				System.out.println(student);
			}
			dbConnection.close();
		}catch (Exception e) {
			System.out.println("데이터베이스 정렬 에러. " + e.getMessage());
		}
		return;

	}
	
	private static void studentDelete() {
		try {
			System.out.print("삭제할 학생번호 입력요망 >>");
			String no = sc.nextLine();

			boolean value = checkInputPattern(no, 2);
			if (!value) {
				return;
			}
			
			DBConnection dbConnection = new DBConnection();
			dbConnection.connect();

			int deleteReturnValue = dbConnection.delete(no);
			if (deleteReturnValue == -1) {
				System.out.println("삭제실패입니다. " + deleteReturnValue);
			} else if (deleteReturnValue == 0) {
				System.out.println("삭제할 번호가 존재하지 않습니다. " + deleteReturnValue);
			} else {
				System.out.println("삭제성공입니다.");
			}
			dbConnection.close();
		} catch (InputMismatchException e) {
			System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요");
			return;
		} catch (Exception e) {
			System.out.println("데이터베이스 삭제 에러. " + e.getStackTrace());
		} 
	}
	
	private static void studentSearch() {
		List<Student> list = new ArrayList<Student>();
		try {
			System.out.print("검색할 학생이름 입력 >>");
			String name = sc.nextLine();
			boolean value = checkInputPattern(name, 3);
			if (!value) {
				return;
			}
			
			DBConnection dbConnection = new DBConnection();
			dbConnection.connect();

			list = dbConnection.selectSearch(name, 2);
			if (list.size() <= 0) {
				System.out.println("검색할 리스트가 없습니다");
			}

			System.out.println("학번\t이름\t국어점수\t영어점수\t수학점수\t총점\t평균\t등급\t순위");
			for (Student student : list) {
				System.out.println(student);
			}
			dbConnection.close();
		} catch (InputMismatchException e) {
			System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요");
			return;
		} catch (Exception e) {
			System.out.println("데이터베이스 검색 에러. " + e.getMessage());
		}
	}
	
	private static void studentUpDate() {
		List<Student> list = new ArrayList<Student>();
		System.out.print("수정할 학생번호 입력 >>");
		String no = sc.nextLine();
		// 패턴분석
		boolean value = checkInputPattern(no, 2);
		if (!value) {
			return;
		}

		DBConnection dbConnection = new DBConnection();
		dbConnection.connect();

		list = dbConnection.selectSearch(no, 1);
		if (list.size() <= 0) {
			System.out.println("수정할 학생정보가 없습니다" + list.size());
			return;
		}
		System.out.println("학번\t이름\t국어점수\t영어점수\t수학점수\t총점\t평균\t등급\t순위");
		for (Student student : list) {
			System.out.println(student);
		}

		Student savedStudent = list.get(0);
		System.out.println("국어" + savedStudent.getKor() + ">>");
		int kor = sc.nextInt();
		value = checkInputPattern(String.valueOf(kor), 4);
		if (!value) {
			return;
		}
		savedStudent.setKor(kor);

		System.out.println("영어" + savedStudent.getEng() + ">>");
		int eng = sc.nextInt();
		value = checkInputPattern(String.valueOf(eng), 4);
		if (!value) {
			return;
		}
		savedStudent.setEng(eng);

		System.out.println("수학" + savedStudent.getMath() + ">>");
		int math = sc.nextInt();
		value = checkInputPattern(String.valueOf(math), 4);
		if (!value) {
			return;
		}
		savedStudent.setMath(math);
		
		int returnUpdateValue = dbConnection.update(savedStudent);
		if (returnUpdateValue == -1) {
			System.out.println("학생정보 수정실패");
			return;
		}
		System.out.println("학생수정 완료");

		dbConnection.close();
	}
	
	private static void studentOutput() {
		List<Student> list = new ArrayList<Student>();

		try {
			DBConnection dbConnection = new DBConnection();
			dbConnection.connect();

			list = dbConnection.select();
			if (list.size() <= 0) {
				System.out.println("보여줄 리스트가 없습니다.");
			}

			System.out.println("학번\t이름\t국어점수\t영어점수\t수학점수\t총점\t평균\t등급\t순위");
			for (Student student : list) {
				System.out.println(student);
			}
			dbConnection.close();
		} catch (Exception e) {
			System.out.println("데이터베이스 출력 에러. " + e.getMessage());
		}
	}

	public static void studentInputData() {
		String pattern = null;
		boolean regex = false;

		try {
			System.out.print("학년(01~03)반(01~09)번호(01~60) >>");
			String no = sc.nextLine();
			boolean value = checkInputPattern(no, 2);
			if (!value) {
				return;
			}

			System.out.print("학생이름 입력요망 >>");
			String name = sc.nextLine();
			value = checkInputPattern(name, 3);
			if (!value) {
				return;
			}

			System.out.print("국어점수 입력요망 >>");
			int kor = sc.nextInt();
			value = checkInputPattern(String.valueOf(kor), 4);
			if (!value) {
				return;
			}

			System.out.print("영어점수 입력요망 >>");
			int eng = sc.nextInt();
			value = checkInputPattern(String.valueOf(eng), 4);
			if (!value) {
				return;
			}

			System.out.print("수학점수 입력요망 >>");
			int math = sc.nextInt();
			value = checkInputPattern(String.valueOf(math), 4);
			if (!value) {
				return;
			}

			Student student = new Student(no, name, kor, eng, math);
			DBConnection dbConnection = new DBConnection();
			dbConnection.connect();

			int insertReturnValue = dbConnection.insert(student);
			if (insertReturnValue == -1) {
				System.out.println("삽입 실패입니다.");
			} else {
				System.out.println("삽입 성공입니다.");
			}
			dbConnection.close();
		} catch (InputMismatchException e) {
			System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요.");
		} finally {
			sc.nextLine();
		}
	}

	private static int displayMenu() {
		int num = -1;
		try {
			System.out.print("1.입력 2.수정 3.삭제 4.검색 5.출력 6.정렬 7.통계 8.종료\n>>");
			num = sc.nextInt();
			// 정수패턴 검색
			boolean value = checkInputPattern(String.valueOf(num), 1);
			if (!value) {
				return num - 1;
			}
		} catch (Exception e) {
			System.out.println("displayMenu error" + e.getMessage());
		} finally {
			sc.nextLine();
		}
		return num;
	}

	private static boolean checkInputPattern(String data, int patternType) {

		String pattern = null;
		boolean regex = false;
		String message = null;
		switch (patternType) {
		case 1:
			pattern = "^[1-9]$";
			message = "번호 재입력(1~9)";
			break;
		case 2:
			pattern = "^0[1-3]0[1-9][0-6][0-9]$";
			message = "학번 재입력(ex010101)";
			break;
		case 3:
			pattern = "^[가-힣]{3,5}$";
			message = "이름 재입력(5자 이내)";
			break;
		case 4:
			pattern = "^[0-9]{1,2}|100$";
			message = "점수 재입력(0~100)";
			break;
		case 5:
			pattern = "^[1-3]$";
			message = "정렬타입 재입력(1~9)";
			break;
		case 6:
			pattern = "^[1-2]$";
			message = "통계타입 재입력(1~9)";
			break;
		}

		regex = Pattern.matches(pattern, data);
		if (!regex) {
			System.out.println(message);
			return false;
		}
		return regex;
	}
}
