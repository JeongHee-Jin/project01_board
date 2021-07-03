package org.project01.tesk;

import java.sql.Connection;
import java.sql.DriverManager;

/*import org.project01.controller.BrdController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import org.springframework.stereotype.Component;

@Component
public class OracleTest {
	//private static final Logger log = LoggerFactory.getLogger(BrdController.class);

	public void testConnection() {
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; //localhost대신 ip주소가 들어갈수도
        String id = "aws";
        String pw = "aws";	
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
            System.out.println(conn.isClosed()?"접속종료":"접속중");
			conn = DriverManager.getConnection(url, id, pw);
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}	
//	(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@3.36.253.253:1521:xe",
//			"aws","aws"))
}
