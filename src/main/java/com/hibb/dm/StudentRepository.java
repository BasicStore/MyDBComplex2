package com.hibb.dm;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository<T> extends JpaRepository<Student, Long>{

	@Query("SELECT s FROM Student s inner join fetch s.collegeComputers")
	List<T> findAllWithCollegeComputerInfo();
	
	
	@Query("SELECT s FROM Student s inner join fetch s.collegeComputers cc WHERE cc.compName = :compName")
	List<T> findAllWithCollegeComputerInfo(@Param("compName") String compName);
	
}