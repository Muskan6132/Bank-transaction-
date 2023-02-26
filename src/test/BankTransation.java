package test;
import java.util.*;
import java.sql.*;
public class BankTransation {
	public static void main(String[] args) {
	try
	{
		Scanner s= new Scanner(System.in);
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection
				("jdbc:oracle:thin:@localhost:1521:xe","AZAD","Azad");
		con.setAutoCommit(false);
		System.out.println("Commit Status:"+con.getAutoCommit());
		PreparedStatement ps1=con.prepareStatement("select * from bank44 where AccNo=?");
		
		PreparedStatement ps2=con.prepareStatement
				("update bank44 set balence=balence+? where AccNo=?");
		Savepoint sp=con.setSavepoint();
		System.out.println("Enter the HomeAccNo:()");
		long haccno=s.nextLong();
		ps1.setLong(1, haccno);
		ResultSet rs1=ps1.executeQuery();  // execution
		
		if(rs1.next())
		{
	    	float bal=rs1.getFloat(3);  // Retrieving balance
			System.out.println("Enter the bAccNo:");
			long baccno=s.nextLong();
			ps1.setLong(1, baccno);
			ResultSet rs2=ps1.executeQuery();
			
			if(rs2.next())
			{
				System.out.println("Enter the amt to be Transfered:");
				int amt=s.nextInt();
				if(amt<=bal)
				{
					ps2.setInt(1,-amt);
					ps2.setLong(2,haccno);
					int p=ps2.executeUpdate();  // execution
					
					ps2.setInt(1,amt);
					ps2.setLong(2,baccno);
					int q=ps2.executeUpdate();  // execution
					
					if(p==1 && q==1)
					{
						con.commit();
						System.out.println("Transation Successfull....");
					} // end of if
					else
					{
						con.rollback();
						System.out.println("Transaction Failed...");
					}
				}// end of if
				else {
					System.out.println("Insufficient Balance...");
				}
			}  // end of if
			else
			{
				System.out.println("Invalid bAccNo...");
			}
		}  // end of if
		else 
		{
			System.out.println("Invalid Home AccNo...");
		}
		
		s.close();
	}  // end if try
	   catch(Exception e)
	  {
		e.printStackTrace();
	  }
	}
}
