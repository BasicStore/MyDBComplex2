package com.hibb.dm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
	// IE> Extending JpaRepository:  Cat is the entity, and long is the primary key
	
}