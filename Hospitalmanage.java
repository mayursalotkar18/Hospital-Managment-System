package Hospitalmanagmentsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Hospitalmanage {
	private static final String url="jdbc:mysql://127.0.0.1:3306/hospital";
	private static final String username="root";
	private static final String password="975318642";
	
	public static void main(String args[]) {
		   try {
			   Class.forName("com.mysql.cj.jdbc.Driver");
		   }catch(ClassNotFoundException e) {
			   System.out.println(e.getMessage());
		   }
		   
		   try {
			   Scanner scanner=new Scanner(System.in);
			   Connection connection=DriverManager.getConnection(url, username, password);
			   Patient patient=new Patient(connection,scanner);
			   Doctor doctor=new Doctor(connection,scanner);
			   
			   while(true) {
				   System.out.println("@ HOSPITAL MANAGMENT SYSTEM @");
				   System.out.println("1. Add Patient");
				   System.out.println("2. View Patient");
				   System.out.println("3. Add Doctor");
				   System.out.println("4. View Doctor");
				   System.out.println("5. Book Appointment");
				   System.out.println("6. Exit");
				   System.out.println("Enter your choice: ");
				   int choice=scanner.nextInt();
				        scanner.nextLine();
				   
				   switch(choice) {
				       case 1: //Add Patient
				    	   patient.addPatient();
				    	   System.out.println();
				    	   break;
				       case 2: //View Patient
				    	   patient.viewPatients();
				    	   System.out.println();
				    	   break;
				       case 3: //Add Doctor
				    	   doctor.addDoctor();
				    	   System.out.println();
				    	   break;
				       case 4: //view Doctor
				    	   doctor.viewDoctors();
				    	   System.out.println();
				    	   break;
				       case 5: //book appointment
				    	   bookAppointment(doctor,patient,connection,scanner);
				    	   System.out.println();
				    	   break;
				       case 6: //exit
				    	   System.out.println("THANK YOU @");
				    	   return;
				       default:
				    	   System.out.println("Enter valid choice");
				    	   
				   }
			   }
			   
		   }catch(SQLException e) {
			   System.out.println(e.getMessage());
		   }   
	}
	
	public static void bookAppointment(Doctor doctor,Patient patient,Connection connection,Scanner scanner) {
		System.out.println("Enter Patient id: ");
		int patient_id=scanner.nextInt();
		System.out.println("Enter Doctor id: ");
		int doctor_id=scanner.nextInt();
		System.out.println("Enter appointment date (YYYY-MM-DD): ");
		String appointment_date=scanner.next();
		if(patient.getPatientById( patient_id) && doctor.getDoctorById(doctor_id)) {
			   if(checkDoctorAvailable(doctor_id,appointment_date,connection)) {
				   String query="INSERT INTO appointments( patient_id,doctor_id,appointment_date)VALUES(?,?,?)";
				   try {
				       PreparedStatement preparedstatement=connection.prepareStatement(query);
				       preparedstatement.setInt(1, patient_id);
				       preparedstatement.setInt(2, doctor_id);
				       preparedstatement.setString(3, appointment_date);
				       int rowsaffected=preparedstatement.executeUpdate();
				       if(rowsaffected>0) {
					       System.out.println("Appointment Booked");
				       }else {
				    	   System.out.println("somethig wrong");
				       }
				   }catch(SQLException e) {
					   System.out.println(e.getMessage());
				   }
			   }else {
				   System.out.println("Doctor is not available on this date");
			   }
		}else {
			System.out.println("Either patient or doctor dosent exists");
		}
	}
	
	public static boolean checkDoctorAvailable(int doctor_id, String appointment_date,Connection connection) {
		try {
			String query="SELECT appointment_date FROM appointments WHERE doctor_id=?";
			PreparedStatement preparedstatement=connection.prepareStatement(query);
		    preparedstatement.setInt(1, doctor_id);
		    ResultSet resultset=preparedstatement.executeQuery();
		    if(resultset.next()) {
		    	String appointedDate=resultset.getString("appointment_date");
		    	
		    	if(appointment_date.equals(appointedDate)) {
		    		return false;
		    	}else {
		    		return true;
		    	}
		    }
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return true;
		
	}
}
