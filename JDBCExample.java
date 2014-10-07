package Demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCExample {
  String url;
  String user;
  String password;
  Connection connection;
  Statement statement;
  public boolean initialize(byte dbmsType) {
    Properties p = new Properties();
    try {
		p.load(new FileInputStream("database.properties"));
		url = p.getProperty("url");
		user = p.getProperty("user");
		password = p.getProperty("password");
		switch (dbmsType) {
		case 0:
		  Class.forName("com.mysql.jdbc.Driver");
		  connection = DriverManager.getConnection(url + "?user=" +user+ "&password=" +password);
		  break;
		case 1:
		  Class.forName("oracle.jdbc.OracleDriver");
		  break;
		default:
		  throw new IllegalArgumentException("Invalid DBMS Type");
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    if (connection == null) {
      throw new NullPointerException("Connection is null");
    }
    return true;
  }
  public void createStatement() {
    try {
		statement = connection.createStatement();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  public ResultSet retrieveData (String sqlCommand) {
	  try {
		  createStatement();
		  ResultSet resultSet = statement.executeQuery(sqlCommand);
		  return resultSet;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
  }
  public int executeUpdate(String sqlCommand, int[] index, String[] values) {
	if (sqlCommand == null) {
		throw new NullPointerException("SQL Commmand is null");
	}
	int rowNu = 0;
    try {
		PreparedStatement ps = connection.prepareStatement(sqlCommand);
		if (index != null && values != null) {
			if (index.length != values.length) {
			  throw new IllegalArgumentException();
			}
			for (int i = 0; i < values.length; i++) {
				ps.setString(index[i], values[i]);
			}
		}	
		rowNu = ps.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return rowNu;
  }
  public int insertData(String sqlCommand, int[] index, String[] values) {
	  return executeUpdate(sqlCommand, index, values);
  }
  public int deleteData(String sqlCommand, int[] index, String[] values) {
	  return executeUpdate(sqlCommand, index, values);
  }
  public int updateData(String sqlCommand, int[] index, String[] values) {
	  return executeUpdate(sqlCommand, index, values);
  }
//  public static void main(String [] args) {
//    JDBCExample jb = new JDBCExample();
//    if (jb.initialize((byte) 0)) {
//      System.out.println("Connection is initialized");
//    }
//    String sqlCommand = "select name from hocsinh";
//    ResultSet rs = jb.retrieveData(sqlCommand);
//    try {
//		while (rs.next()) {
//		  System.out.println(rs.getString(1));
//			
//		}
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//    
//    
//   
//  }
}


