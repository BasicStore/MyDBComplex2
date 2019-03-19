package com.hibb.dm;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//import org.hibernate.envers.Audited;
//import org.hibernate.envers.NotAudited;
//import org.hibernate.envers.RevisionNumber;
//import org.hibernate.envers.RevisionTimestamp;



@Entity
//@Audited
public class Cat {

	@Id // id will be the primary key of this entity
	@GeneratedValue   // this value will be generated by JPA (ie hibernate will create a sequence on the table and use it to generate this record) 
	private Long id;
	
	private String catName;
	
	//@NotAudited  // keep quiet about age
	private int age;
	
	private boolean gender;
	
	public Cat() {
		
	}
	
	public Cat(String catName, int age, boolean gender) {
		super();
		this.catName = catName;
		this.age = age;
		this.gender = gender;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}

	 @Override
	 public boolean equals(Object o) {
		 if (o == null) {
			 return false;
		 }
		 return ((Cat)o).getId() == id ? true : false;
	 }
	 
	 @Override
	    public int hashCode() {
	        return id != null ? id.hashCode() : 0;
	}
	
}
