package HMS;
import java.sql.*;
import java.util.*;
public class Patient {
	private Connection con;
	private Scanner scan;
	public Patient(Connection con,Scanner scan)
	{
		this.con=con;
		this.scan=scan;
	}
	public void addpatient()
	{
		System.out.print( "Enter Patient name :");
		String name=scan.next();
		System.out.print( "Enter Patient age :");
		int age=scan.nextInt();
		System.out.print( "Enter Patient gender :");
		String gender=scan.next();
		try {
			String query ="INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
			PreparedStatement prep=con.prepareStatement(query);
			prep.setString(1, name);
			 prep.setInt(2, age);
			prep.setString(3, gender);
			int rows=prep.executeUpdate(); //executeUpdate() : this method returns integer value
			if(rows>0)
			{
				System.out.println("Patient added successfully");
			}
			else
			{
				System.out.println("Failed to add patient");
				 
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	public void viewPatients()
	{
		String q="SELECT * FROM Patients";
		try {
			PreparedStatement prep=con.prepareStatement(q);
			ResultSet resultSet=prep.executeQuery();
			System.out.println("Patients: ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
                System.out.println("+------------+--------------------+----------+------------+");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public boolean getPatientById(int id)
	{
		String q="SELECT * FROM Patients WHERE id=?";
		try
		{
			PreparedStatement prep=con.prepareStatement(q);
			prep.setInt(1, id);
			ResultSet res=prep.executeQuery();
			if(res.next())
			{
				return true;
			}
			
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
