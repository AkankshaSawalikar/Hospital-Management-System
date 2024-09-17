package HMS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.PrintStream.*;

public class Doctors {
	private Connection con;
	
	public Doctors(Connection con)
	{
		this.con=con;

	}
	
	
	public void viewDoctors()
	{
		String q="SELECT * FROM Doctors";
		try {
			PreparedStatement prep=con.prepareStatement(q);
			 ResultSet resultSet = prep.executeQuery();
	            System.out.println("Doctors: ");
	            System.out.println("+------------+--------------------+------------------+");
	            System.out.println("| Doctor Id  | Name               | Specialization   |");
	            System.out.println("+------------+--------------------+------------------+");
	            while(resultSet.next()){
	                int id = resultSet.getInt("id");
	                String name = resultSet.getString("name");
	                String specialization = resultSet.getString("specialization");
	                System.out.printf("| %-10s | %-18s | %-16s |\n", id, name, specialization);
	                System.out.println("+------------+--------------------+------------------+");
	            }

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public boolean getDoctorById(int id)
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
