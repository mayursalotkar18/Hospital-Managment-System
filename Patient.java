package Hospitalmanagmentsystem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Patient {
   private Connection connection; //we will get these values from main class via constructor
   private Scanner scanner;
   
   public Patient(Connection connection,Scanner scanner ){
	   this.connection = connection;
	   this.scanner= scanner;   
   }
   
   public void addPatient() {
	   System.out.print("Enter patient name: ");
	   String name = scanner.nextLine();
	   System.out.print("Enter patient age: ");
	   int age = Integer.parseInt(scanner.nextLine());
	   System.out.print("Enter patient gender: ");
	   String gender = scanner.nextLine();
	   
	   try {
		    String query="INSERT INTO patients(name,age,gender)VALUES(?,?,?)";
		    PreparedStatement preparedstatement=connection.prepareStatement(query);
		    preparedstatement.setString(1,name);
		    preparedstatement.setInt(2,age);
		    preparedstatement.setString(3,gender);
		    
		    int rowsaffected=preparedstatement.executeUpdate();
		    
		    if(rowsaffected>0) {
		    	System.out.println("Patient Added Successfully !!");
		    }else {
		    	System.out.println("Patient NOT Added !!!");
		    }
		   
	   }catch(SQLException e) {
		   System.out.println(e.getMessage());
	   }   
   }
   
   public void viewPatients() {
	   try {
	        String query="SELECT * FROM patients";
	        PreparedStatement preparedstatement=connection.prepareStatement(query);
	        ResultSet resultset=preparedstatement.executeQuery();
	        System.out.println("#Patients :");
	        System.out.println("+------------+------------------------+--------+------------+");
	        System.out.println("| Patient ID | Name                   | Age    | Gender     |");
	        System.out.println("+------------+------------------------+--------+----------- +");
	   
	        while(resultset.next()) {
		        int id= resultset.getInt("id");
		        String name= resultset.getString("name");
		        int age= resultset.getInt("age");
		        String gender= resultset.getString("gender");
		        System.out.printf("| %-11s| %-23s| %-7s| %-11s|\n",id,name,age,gender);
		        System.out.println("+------------+------------------------+--------+----------- +");
	        }
	   }catch(SQLException e) {
		   System.out.println(e.getMessage());
	   }   	   
   }
   
   public boolean  getPatientById(int id) {
	   try {
		   String query="Select * FROM patients WHERE id=?";
		   PreparedStatement preparedstatement=connection.prepareStatement(query);
		   preparedstatement.setInt(1, id);
		   ResultSet resultset=preparedstatement.executeQuery();
		   if(resultset.next()) {
			   return true;
		   }else {
			   return false;
		   }
		   
	   }catch(SQLException e){
		   System.out.println(e.getMessage());
	   }
	   return false; 
   }
   
}
