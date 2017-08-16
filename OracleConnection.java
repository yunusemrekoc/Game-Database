import java.sql.*;
import javax.swing.*;
public class OracleConnection {
Connection con;
public static Connection dbConnector(){
	try{
    	Class.forName("oracle.jdbc.driver.OracleDriver");
    	Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","EMRE","Ahmet2005.");
	return con;
	}catch(Exception e){
		JOptionPane.showMessageDialog(null, "Connected!");
		return null;
	}

}public static void main(String[] args){
	dbConnector();
}
}
