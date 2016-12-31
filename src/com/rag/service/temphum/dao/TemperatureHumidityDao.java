package com.rag.service.temphum.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rag.service.temphum.valueobject.TemperatureHumidityVO;

public class TemperatureHumidityDao {
	private static String JDBC_DRIVER ="com.mysql.jdbc.Driver";
	private static String DATABASE_URL = "jdbc:mysql://192.168.0.8/homeautomation";
	private static String USERNAME = "raghu";
	private static String PASSWORD="bharadwaj";
	private static String TEMPERATURE_INSERT_STATEMENT = "insert into temperature(date,time,temperature_value) values (curdate(), now(),?)";
	private static String HUMIDITY_INSERT_STATEMENT = "insert into humidity(date,time,humidity_value) values (curdate(), now(),?)";
	
	public TemperatureHumidityDao(){
		super();
	}
	
	public Boolean writeToDB(TemperatureHumidityVO vo){
		Boolean success=false;
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		try{
			Class.forName(JDBC_DRIVER);
			connection=DriverManager.getConnection(DATABASE_URL,USERNAME,PASSWORD);
			connection.setAutoCommit(false);
			preparedStatement=connection.prepareStatement(TEMPERATURE_INSERT_STATEMENT, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setDouble(1, vo.getTemperature());
			int temperatureRecordsUpdated =preparedStatement.executeUpdate();
			preparedStatement.close();
			preparedStatement=connection.prepareStatement(HUMIDITY_INSERT_STATEMENT, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setDouble(1, vo.getHumidity());
			int humidityRecordsUpdated = preparedStatement.executeUpdate();
			connection.commit();
			if(temperatureRecordsUpdated==1 && humidityRecordsUpdated==1){
				success=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
			preparedStatement.close();
			connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return success;
	}

}
