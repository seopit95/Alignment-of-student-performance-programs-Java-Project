package studentProject;

import java.io.Serializable;

public class Student implements Comparable<Student>, Serializable{
	private static final int SUBJECT_NO = 3;
	private String no;
	private String name;
	private int kor;
	private int eng;
	private int math;
	private int total;
	private double avr;
	private String grade;
	private int rate;
	
	public Student(String no, String name, int kor, int eng, int math){
		this(no,name,kor,eng,math,0,0.0,null,0);
	}
	
	public Student(String no, String name, int kor, int eng, int math, int total, double avr, String grade, int rate) {
		super();
		this.no = no;
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.total = total;
		this.avr = avr;
		this.grade = grade;
		this.rate = rate;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public double getAvr() {
		return avr;
	}

	public void setAvr(double avr) {
		this.avr = avr;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	@Override
	public int hashCode() {
		return this.no.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Student)) return false;
		return this.no.equalsIgnoreCase(((Student)obj).no);
	}
	
	@Override
	public int compareTo(Student student) {
		return this.no.compareToIgnoreCase(student.no);
	}
	
	@Override
	public String toString() {
		return no + "\t" + name + "\t" + kor + "\t" + eng + "\t" + math + "\t"
				+ total + "\t" + avr + "\t" + grade + "\t" + rate;
	}
}
