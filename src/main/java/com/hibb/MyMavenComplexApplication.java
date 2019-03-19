package com.hibb;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.hibb.dm.Cat;
import com.hibb.dm.CatRepository;
import com.hibb.dm.CollegeComputer;
import com.hibb.dm.CollegeComputerRepository;
import com.hibb.dm.Course;
import com.hibb.dm.CourseRepository;
import com.hibb.dm.Gender;
import com.hibb.dm.Student;
import com.hibb.dm.StudentRepository;

@SpringBootApplication

@EnableJpaAuditing
//@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)

public class MyMavenComplexApplication implements CommandLineRunner {

	// implementing CommandLineRunneris for executing the repository methods on application start up (see below)
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StudentRepository stuRepository;
	
	@Autowired
	private CollegeComputerRepository ccRepository;
	
	@Autowired
	private CatRepository catRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(MyMavenComplexApplication.class, args);
	}

	@Override  // this method is executed as soon as application is launched 
	public void run(String... args) throws Exception {
		logger.info("Student id 10001 -> {}", stuRepository.findById(10001L));
		
		logger.info("All users 1 -> {}", stuRepository.findAll());
		
		//Insert
		logger.info("Inserting -> {}", stuRepository.save(new Student("John", "A1234657")));

		
		Student newStudent = new Student(10001L, "Name-Updated", "New-Passport");
		
		//Update
		logger.info("Update 10001 -> {}", stuRepository.save(newStudent));

		// TODO test stuRepository.deleteById(10002L);
		
		logger.info("All users 2 -> {}", stuRepository.findAll());
		
		//--------------------------------------------------
		// MANY v MANY mapping: Now add the full use of college computers to all students
		List<CollegeComputer> ccList = Arrays.asList(new CollegeComputer("CompA"), 
				new CollegeComputer("CompB"), new CollegeComputer("CompC")); 
		ccRepository.saveAll(ccList);
		
		List<Student> studentList = Arrays.asList(new Student("NameXXX", "PassportXXXX", ccList), 
				new Student("NameYYY", "PassportYYY", ccList), new Student("NameZZZ", "PassportZZZ", ccList));
		
		// do NvN relationships have to be done both ways????
		stuRepository.saveAll(studentList);
		//--------------------------------------------------
		// ONE v MANY (PRIMITIVE) mapping: update a record and set a list of string values
		newStudent.setTelNumbers(Arrays.asList("009087", "234546", "213123123"));
		stuRepository.save(newStudent);
		stuRepository.flush();
		//--------------------------------------------------
		// ONE v MANY (OBJECT) mapping: update a record with a one to many relationship
		Set<Cat> catSet = Arrays.stream(new Cat[] {new Cat("Old Cat", 19, true), new Cat("Older Cat", 20, true)}).collect(Collectors.toSet());
		catRepository.saveAll(catSet);
		newStudent.setCatSet(catSet);
		stuRepository.save(newStudent);
		//--------------------------------------------------
		// update a record with a one-to-one mapping
		Course course = new Course("Geography", "Humanities", 9);
		courseRepository.save(course);
		newStudent.setCourse(course);
		stuRepository.save(newStudent);
		//--------------------------------------------------
		// To access the value, EAGER INSTANTIALIZATION is necessary
		// ie fetch = FetchType.EAGER on the relationship annotation
		// TODO NOTE:  THIS CANN ONLY WORK IF fetch = FetchType.EAGER IS ADDED TO BOTH RELATIONSHIP ANNOTATIONS
//		List<Student> myStudentList = (List<Student>)stuRepository.findAll();
//		String computerName = myStudentList.get(3).getCollegeComputers().get(0).getCompName();
//		logger.info("all working? computer name = " + computerName);
		//--------------------------------------------------
		// THIS CUSTOM GET WILL WORK WITH LAZY INITIALIZATION BECAUSES OF THE BESPOKE QUERY AND THE FETCH COMMAND
		List<Student> stList = (List<Student>)stuRepository.findAllWithCollegeComputerInfo();
		String computerName = stList.get(3).getCollegeComputers().get(0).getCompName();
		logger.info("all working? computer name = " + computerName);
		//--------------------------------------------------
		
		// List<T> findAllWithCollegeComputerInfo(@Param("compName") String compName);
		// TODO if this works, it will bring back the lot for each student as it stands
		List<Student> stList2 = (List<Student>)stuRepository.findAllWithCollegeComputerInfo("CompB");
		String computerName2 = stList2.get(2).getCollegeComputers().get(0).getCompName();
		logger.info("all working WITH CUSTOM GET METHOD WITH PARAMS:    ? computer name = " + computerName2 + "  --> student list size = " + stList2.size());
		//--------------------------------------------------
		// ATTEMPT DELETION - note that this has deleted the student and its collecge computers without CASCADE_ALL, so CASCADE_ALL is set by default  
		stuRepository.deleteById(stList2.get(2).getId());
		logger.info("1 student has been deleted - check this has removed the relationships");
		stList2 = (List<Student>)stuRepository.findAllWithCollegeComputerInfo("CompB");
		logger.info("computer name = " + computerName2 + "  --> student list size ius now:   " + stList2.size());
		//--------------------------------------------------
		// save enum to database via the converter [String enums only for now]
		Student changer = stList2.get(0);
		changer.setGender(Gender.FEMALE);
		stuRepository.save(changer);
		Optional<Student> femaleStudent = stuRepository.findById(changer.getId());
		if (femaleStudent.isPresent()) {
			String ans = "Name = " + femaleStudent.get().getName() + "   --> gender (despite the name should be female) = " + femaleStudent.get().getGender();
			logger.info(ans);
		}
		//--------------------------------------------------
		// set LocalDate and LocalDateTime objects
		changer.setBirthday(LocalDate.of(1993, 04, 25));
		changer.setMagicMoment(LocalDateTime.of(LocalDate.of(1987, 8, 21), LocalTime.of(18, 04, 25)));
		stuRepository.save(changer);
		//--------------------------------------------------
		stuRepository.flush();
	}
	
}

