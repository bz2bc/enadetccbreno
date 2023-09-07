package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
	public static Connection conn = null;
	
	//conectar com o banco
	public static Connection getConnection() {
		if(conn==null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
			
		}
		return conn;
	}
	
	//fecha conex�o com o banco
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	//carrega as propriedades do banco(como senha, usuario, etc)
	private static Properties loadProperties() {
		FileInputStream fs = null;
		try {
			fs = new FileInputStream("db.properties");
			Properties props = new Properties();
			props.load(fs);
			return props;
		}catch(IOException e) {
			throw new DbException(e.getMessage());
		}finally {
			if(fs!=null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
