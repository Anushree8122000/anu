package sdbms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import customException.StudentNotFoundException;
import customsortiing.SortStudentByAge;
import customsortiing.SortStudentById;
import customsortiing.SortStudentByMarks;
import customsortiing.SortStudentByName;
import customException.InvalidChoiceException;

// Implementation class
public class StudentManagementSystemImpl implements StudentManagementSystem {
	Scanner scan= new Scanner(System.in);
	//key-->Student id & value-->Student Object
	Map<String,Student>db=new LinkedHashMap<String,Student>();


	@Override
	public void addStudent() {
		//Accept age
		System.out.println("Enter the age");
		int age=scan.nextInt();
		//Accept Name
		System.out.println("Enter the Name");
		String name=scan.next();
		//Accept Marks
		System.out.println("Enter the Marks");
		int marks=scan.nextInt();

		//creating a Student instance
		Student std =new Student(age,name,marks);

		//adding entry inside db(Map)
		db.put(std.getId(),std);
		System.out.println("Student record inserted succesfuly");
		System.out.println("your student Id is"+std.getId());
		countStudent();
	}
	@Override
	public void displayStudent() {
		//Accepting student ID
		System.out.println("Enter Student ID");
		//Accepting Student Id-->//JSP101 JSP101 JSP101
		String id=scan.next(); //String id=scan.next().toUpperCase();
		id=id.toUpperCase();

		//checking if the is present or not-->(id==key)
		if(db.containsKey(id)) {
			System.out.println("Student record FOund");
			System.out.println("Student details are as Follows:");
			System.out.println("====================");
			Student std = db.get(id); //getting student object based on id
			System.out.println("Id: "+std.getId());
			System.out.println("Age: "+std.getAge());
			System.out.println("Name: "+std.getName());
			System.out.println("Marks "+std.getMarks());
			//printing reference variable as toString() is Overridden
			//System.out.println(std);
		}
		else
		{
			try {
				String message="Student with id "+id+" is not Found";
				throw new StudentNotFoundException(message);
			}
			catch(StudentNotFoundException e){
				System.out.println(e.getMessage());
			}
		}
	}
	@Override
	public void displayAllStudents() {
		if(!db.isEmpty()) {  // checking if DB is Not Empty
			System.out.println("Student Records are as Folows");
			System.out.println("----------------------------");
			Set<String>keys=db.keySet(); //JSP101 JSP102 JSP103
			for(String key:keys) {
				Student std=db.get(key); //getting Student Object
				System.out.println(std); //toString() is Overridden
				//System.out.println(db.get(key));
			}
		}
		else {
			try {
				String message="No student records to Display!";
				throw new StudentNotFoundException(message);
			}

			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	@Override
	public void removeStudent() {
		System.out.println("Enter the Student id");
		String id=scan.next();
		id=id.toUpperCase();
		if(db.containsKey(id)) {
			System.out.println("Student Record Found");
			System.out.println(db.get(id));
			System.out.println("Student record deleted Succesfully");

		}
		else
		{
			try {
				String message="Student with id "+id+" is not Found!";
				throw new StudentNotFoundException(message);
			}
			catch(StudentNotFoundException e){
				System.out.println(e.getMessage());
			}
		}
	}
	@Override
	public void removeAllStudents() {
		if(!db.isEmpty()) {
			System.out.println("No of Student Record "+db.size());
			db.clear();
			System.out.println("All Student Record Deleted Successfully");
		}
		else {
			try {
				String message = "No Student Record to Deleted!";
				throw new StudentNotFoundException(message);
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	@Override
	public void updateStudent() {
		System.out.println("Enter student Id: ");
		String id = scan.next();

		if(db.containsKey(id)) {
			System.out.println("Stident Record Found");
			Student std=db.get(id); //getting value(Student object)
			System.out.println("1.update Age\n2.Update Name");
			System.out.println("UpdatevMarks\n4.Enter choice");
			int choice=scan.nextInt();

			switch(choice) {
			case 1:
				System.out.println("Enter Age");
				int age=scan.nextInt();
				std.setAge(age); //std.setAge(scan.nextInt());
				System.out.println("Age Updated Sucessfully");
				break;

			case 2:
				System.out.println("Enter Name");
				String Name=scan.next();
				std.setName(Name);  //std.setName(scan.next());
				System.out.println("Name updated Succesfully");
				break;

			case 3:
				System.out.println("Enter marks");
				int marks=scan.nextInt();
				std.setMarks(marks); //std.setMarks(scan.nextInt());
				System.out.println("Marks Updated Succesfully");
				break;

			default:
				try {
					String message="InvalidChoice,Enter viod choice";
					throw new InvalidChoiceException(message);
				}
				catch(Exception e){
					System.out.println(e.getMessage());

				}
			}
		}
		else {
			try {
				String message="Student with Id "+id+"is not Found! ";
				throw new StudentNotFoundException(message);
			}
			catch(StudentNotFoundException e){
				System.out.println(e.getMessage());
			}
		}
	}
	@Override
	public void countStudent() {
		System.out.println("No of Student records Records: "+db.size());
	}
	@Override
	public void sortStudent() {
		/** This is documentation comment
		 * we cannot sort Map based on values,therefore we are getting
		 * the values from Map & sorting it inside list so that we can sort
		 * list using--->Collections.sort(list,sorting logic object);
		 */

		//Reference ofList & object of ArrayList Storing Student Object
		List<Student> list =new ArrayList<Student>();

		//converting Map into Set,which Store keys(id)
		Set<String> keys=db.keySet();

		//Traversing keys(Id)
		for(String key:keys) {
			//getting value (Student object)&adding it into list
			list.add(db.get(key));
		}
		System.out.println("1: Sort Student By Id\n2: Sort Student By Age");
		System.out.println("3:Sort Student By Name\n4. Sort Student By Marks");
		System.out.println("Enter choice");
		int choice=scan.nextInt();

		switch(choice) {
		case 1:
			Collections.sort(list,new SortStudentById());
			for(Student s:list) {
				System.out.println(s);
			}
			break;

		case 2:
			Collections.sort(list,new SortStudentByAge());
			for(Student s:list) {
				System.out.println(s);
			}
			break;

		case 3:
			Collections.sort(list,new SortStudentByName());
			for(Student s:list) {
				System.out.println(s);
			}
			break;

		case 4:
			Collections.sort(list,new SortStudentByMarks());
			for(Student s:list) {
				System.out.println(s);
			}
			break;
		default:
			try {
				String message="Invalid choice,Enter choice";
				throw new InvalidChoiceException(message);
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	@Override
	public void findStudentWithHighestMarks() {
		List<Student>list=new ArrayList<Student>();
		Set<String>keys=db.keySet();
		for(String key: keys) {
			list.add(db.get(key)); //adding student objects from map into list
		}
		Collections.sort(list,new SortStudentByMarks());
		System.out.println("Student with highest Marks: ");
		System.out.println(list.get(list.size()-1));

	}
	@Override
	public void findStudentWithLowestMarks() {
		List<Student>list=new ArrayList<Student>();
		Set<String>keys=db.keySet();
		for(String key: keys) {
			list.add(db.get(key));
		}
		Collections.sort(list,new SortStudentByMarks());
		System.out.println("Student with Lowest Marks: ");
		System.out.println(list.get(0));
	}

}
