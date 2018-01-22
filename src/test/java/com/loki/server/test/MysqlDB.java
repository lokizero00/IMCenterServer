package com.loki.server.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.loki.server.entity.User;




public class MysqlDB {

	public static void main(String[] args) {
//		String driver="com.mysql.jdbc.Driver";
//		String url="jdbc:mysql://123.57.81.90:3306/redis_db";
//		String user="admin";
//		String password="FenqiShow20159986P";
//		
//		try {
//			Class.forName(driver);
//			Connection conn=DriverManager.getConnection(url, user, password);
//			
//			if(!conn.isClosed()) {
//				System.out.println("Succeeded connecting to the DataBase!");
//			}
//			
//			
//			Statement statement=conn.createStatement();
//			String sql="select * from user";
//			
//			ResultSet rs=statement.executeQuery(sql);
//			
//			String name=null;
//			String pwd=null;
//			while(rs.next()) {
//				name=rs.getString("user_name");
//				pwd=rs.getString("password");
//				
//				System.out.println("用户名："+name+",密码:"+pwd);
//			}
//			rs.close();
//			conn.close();
//		}catch(ClassNotFoundException e) {
//			System.out.println("Sorry,can't find the Driver!"); 
//            e.printStackTrace();
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		
		
//		DozerBeanMapper mapper=new DozerBeanMapper();  
//        User user1 = new User();  
//        User user2=new User();
//        user1.setId(3);
//        user2.setId(4);
//        List<User> userList=new ArrayList<User>();
//        userList.add(user1);
//        userList.add(user2);
//        List<UserVO> userVOList=new ArrayList<UserVO>();
//        UserVO user3=mapper.map(user2, UserVO.class);
//        userVOList=(List<UserVO>) mapper.map(userList, UserVO.class);
//       System.out.println(userVOList.size());
	}

}
