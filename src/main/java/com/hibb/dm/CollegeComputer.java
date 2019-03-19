package com.hibb.dm;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class CollegeComputer {

	@Id 
	@GeneratedValue 
	private Long id;
		
	private String compName;
	
	@ManyToMany (mappedBy = "collegeComputers") 
	private List<Student> students;
	
	
	public CollegeComputer() {
		
	}

	
	public CollegeComputer(String compName) {
		this.compName = compName;
	}
	
	
	public CollegeComputer(String compName, List<Student> students) {
		super();
		this.compName = compName;
		this.students = students;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCompName() {
		return compName;
	}


	public void setCompName(String compName) {
		this.compName = compName;
	}


	public List<Student> getStudents() {
		return students;
	}


	public void setStudents(List<Student> students) {
		this.students = students;
	}

	
}
