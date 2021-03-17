package XBeeProject.XBeeProject;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

class CoSQL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static void Enregistrer(String source, int val) {
		String url = "jdbc:mysql://localhost/IOT";
		String login = "root";
		String pwd = "";
		Connection cn = null;
		Statement st = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn=(Connection) DriverManager.getConnection(url,login,pwd);
			st=(Statement) cn.createStatement();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();
			String sql = "INSERT INTO `Mesures` VALUES ('"+dtf.format(now)+"','"+source+"', "+val+");";
			st.executeUpdate(sql);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
