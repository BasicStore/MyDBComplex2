package com.hibb.dm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long>{

	// IE> Extending JpaRepository:  Cat is the entity, and long is the primary key
	
}

