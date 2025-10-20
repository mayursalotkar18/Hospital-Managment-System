package Hospitalmanagmentsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
	 private Connection connection; //we will get these values from main class via constructor
	   private Scanner scanner;
	   
	   public Doctor(Connection connection,Scanner scanner ){
		   this.connection = connection;
		   this.scanner= scanner;   
	   }
	   
	   public void addDoctor() {
		   System.out.println("Enter Doctor name: ");
		   String name = scanner.next();
		   System.out.println("Enter Dr's specialization: ");
		   String specialization = scanner.next();
		   
		   try {
			    String query="INSERT INTO doctors(name,specialization)VALUES(?,?)";
			    PreparedStatement preparedstatement=connection.prepareStatement(query);
			    preparedstatement.setString(1,name);
			    preparedstatement.setString(2,specialization);
			    
			    int rowsaffected=preparedstatement.executeUpdate();
			    
			    if(rowsaffected>0) {
			    	System.out.println("Doctor Added Successfully !!");
			    }else {
			    	System.out.println("Doctor NOT Added !!!");
			    }
			   
		   }catch(SQLException e) {
			   System.out.println(e.getMessage());
		   }   
	   }
	   
	   public void viewDoctors() {
		   try {
		        String query="SELECT * FROM doctors";
		        PreparedStatement preparedstatement=connection.prepareStatement(query);
		        ResultSet resultset=preparedstatement.executeQuery();
		        System.out.println("#Doctors :");
		        System.out.println("+------------+------------------------+--------------------+");
		        System.out.println("| Doctor  ID | Name                   | Specialization     |");
		        System.out.println("+------------+------------------------+------------------- +");
		   
		        while(resultset.next()) {
			        int id= resultset.getInt("id");
			        String name= resultset.getString("name");
			        String specialization = resultset.getString("specialization");
			        System.out.printf("| %-11s| %-23s| %-19s|\n",id,name,specialization);
			        System.out.println("+------------+------------------------+--------------------+");
		        }
		   }catch(SQLException e) {
			   System.out.println(e.getMessage());
		   }   	   
	   }
	   
	   public boolean  getDoctorById(int id) {
		   try {
			   String query="Select * FROM doctors WHERE id=?";
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
