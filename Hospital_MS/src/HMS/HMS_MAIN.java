package HMS;
import java.sql.DriverManager;
import java.util.*;
import java.sql.*;
public class HMS_MAIN {
	private static final String url="jdbc:mysql://localhost:3306/hospital_management_system";
	private static final String username="root";
	private static final String password="Akanksha1234#";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		Scanner sc=new Scanner(System.in);
		try {
			Connection con=DriverManager.getConnection( url,username,password);
			Patient patient=new Patient(con,sc);
			Doctors doctor=new Doctors(con);
			while(true)
			{
				System.out.println("HOSPITAL MANGAMENT SYSTEM");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter your choice :");
				int choice=sc.nextInt();
				switch(choice)
				{
				case 1:
					patient.addpatient();
					break;
				case 2:
					patient.viewPatients();
					break;
				case 3:
					doctor.viewDoctors();
					break;
				case 4:
					bookappoinment(patient,doctor,con,sc);
					System.out.println();
					break;
				case 5:
					System.out.println("Thank you");
					return;
				default:
					System.out.println("Enter valid choice!!");
					break;
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public static void bookappoinment(Patient patient, Doctors doctor,Connection con,Scanner sc)
	{
		System.out.print("Enter patient id :");
		int patientid=sc.nextInt();
		System.out.print("Enter doctor id :");
		int doctorid=sc.nextInt();
		System.out.print("Enter the appoinment date(yyyy-mm-dd):");
		String date=sc.next();
		//NOW WE HAVE TO CHECK THAT AT A GIVEN ID OF PATIENT AND DOCTOR DOES EXIST OR NOT.
		if(patient.getPatientById(patientid) && doctor.getDoctorById(doctorid)) {
			if(checkDoctorAvailable(doctorid,date, con)) {
				String appoinment="INSERT INTO  appoinments(Patient_id,Doctors_id, Appointment_date)VALUES(?,?,?)";
				try {
					PreparedStatement prep=con.prepareStatement(appoinment);
					prep.setInt(1, patientid);
					prep.setInt(2, doctorid);
					prep.setString(3, date);
					int rows=prep.executeUpdate();
					if(rows>0)
					{
						System.out.println("Appoinment Booked!");
					}
					else
					{
						System.out.println("Failed to book appoinment!");
					}
					
				}catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			else
			System.out.println("Doctor not available on this date");
		}
		else
		{
			System.out.println("Either doctor and patient not exist!");
		}
	}
 
	public static boolean checkDoctorAvailable(int doctorid, String date, Connection con) {
	    String query = "SELECT COUNT(*) FROM appoinments WHERE doctors_id=? AND Appointment_date=?";
	    try {
	        PreparedStatement preparedStatement = con.prepareStatement(query);
	        preparedStatement.setInt(1, doctorid);
	        preparedStatement.setString(2, date);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            int count = resultSet.getInt(1);
	            return count == 0; // Doctor is available if count is 0
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	}

