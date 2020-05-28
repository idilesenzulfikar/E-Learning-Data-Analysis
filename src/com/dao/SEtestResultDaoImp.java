package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.entities.StudentEtestResult;
import com.util.ConnectionConfiguration;

public class SEtestResultDaoImp implements StudentEtestResultDao{

	@Override
	public void createStudentEtestResultTable(String tableName) {
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = ConnectionConfiguration.getConnection();
			statement = connection.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS " + tableName + " (id int primary key unique auto_increment," +
					"mailAdresse varchar(256),"+
					"e1 double,e2 double,e3 double,e4 double,e5 double,e6 double,e7 double,e8 double," + 
					"e9 double,e10 double,e11 double,e12 double,e13 double,e14 double,e15 double,e16 double," + 
					"e17 double,e18 double,e19 double,e20 double,e21 double,e22 double,e23 double,e24 double," +
					"e25 double,e26 double,e27 double,e28 double,e29 double,e30 double,e31 double,e32 double)");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void insertStudentEtestResult(StudentEtestResult ser,String tableName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = ConnectionConfiguration.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO " + tableName +
								" (mailAdresse,e1,e2,e3,e4,e5,e6,e7,e8," + 
								"e9,e10,e11,e12,e13,e14,e15,e16,"+ 
								"e17,e18,e19,e20,e21,e22,e23,e24," + 
								"e25,e26,e27,e28,e29,e30,e31,e32)" +
					"VALUES (?,?,?,?,?,?,?,?,?," + 
							"?,?,?,?,?,?,?,?," + 
							"?,?,?,?,?,?,?,?," + 
							"?,?,?,?,?,?,?,?)");
			preparedStatement.setString(1, ser.getMailAdresse());
			int i = 2;
			for(Double e:ser.geteTestResults()){
				preparedStatement.setDouble(i++,e);
			}
			preparedStatement.executeUpdate();
			System.out.println("INSERT INTO " + tableName +
								" (mailAdresse,e1,e2,e3,e4,e5,e6,e7,e8," + 
								"e9,e10,e11,e12,e13,e14,e15,e16,"+ 
								"e17,e18,e19,e20,e21,e22,e23,e24," + 
								"e25,e26,e27,e28,e29,e30,e31,e32)" +
					"VALUES (?,?,?,?,?,?,?,?,?," + 
							"?,?,?,?,?,?,?,?," + 
							"?,?,?,?,?,?,?,?," + 
							"?,?,?,?,?,?,?,?)");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public StudentEtestResult selectStudentEtestResult(int id) {

		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public List<StudentEtestResult> selectAllStudentEtestResult() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<StudentEtestResult> selectAllEtestResult(String tableName){
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		
		List<String> student = new ArrayList<String>();
		List<StudentEtestResult> ser = new ArrayList<StudentEtestResult>();
		int i = 0;
		try {
			connection = ConnectionConfiguration.getConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery("SELECT distinct `E-Mail-Adresse` FROM " + tableName);
			
			while(resultset.next()) {
			    student.add(resultset.getString("E-Mail-Adresse"));
			}

			for(String s:student){
				
				resultset = statement.executeQuery("SELECT * FROM " + tableName + " WHERE `E-Mail-Adresse` = '" + s + "'");
				
				Double[] seResult = new Double[32];
				Arrays.fill( seResult, 0.0 );
				
				while(resultset.next()){
					int etest = resultset.getInt("etestnr");
					double eresult	= resultset.getDouble("Bewertung");
					seResult[etest-1-32] = eresult;
				}
				
				StudentEtestResult studentER = new StudentEtestResult();
				studentER.setId(i+1);
				studentER.setMailAdresse(s);
				studentER.seteTestResults(seResult);
				ser.add(i,studentER);	
				i++;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return ser;
	}

	@Override
	public void deleteStudentEtestResult(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateStudentEtestResult(StudentEtestResult ser, int id) {
		// TODO Auto-generated method stub
		
	}

}
