package ecl_dummy_plugin.handlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;




public class Connect {
	
	
	public Connection connect(String filepath) {
		Connection conn = null;
		
		try {
			//Hard coded path, make sure to change to local file destination
			conn = DriverManager.getConnection("jdbc:sqlite:" + filepath);
			System.out.println("Connection has been established");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return conn;
		
	}
	
	public ResultSet getRecords(Connection conn, String query) {
		ResultSet records = null;
		try {
			Statement st = conn.createStatement();
			records = st.executeQuery(query);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return records;
	}
	
	
	
	
	
	
	public ArrayList<Clone> placeClones(ResultSet rs){
		ArrayList<Clone> clones = new ArrayList<Clone>();
		
		try {
			while (rs.next()) {
				Clone newClone = new Clone(rs.getString("file"),
										   rs.getInt("startline"),
										   rs.getInt("endline"),
										   rs.getInt("pcid"),
										   rs.getInt("class_id"));
				
				clones.add(newClone);
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return clones;
		}
	

	public ArrayList<CloneFamily> placeCloneFamily(ResultSet rs){
		ArrayList<CloneFamily> cloneFamily = new ArrayList<CloneFamily>();
		
		try {
			while (rs.next()) {
				CloneFamily newCloneFamily = new CloneFamily(rs.getInt("id"),
										   rs.getInt("pop"),
										   rs.getInt("length"),
										   rs.getFloat("simil"));
				
				cloneFamily.add(newCloneFamily);
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return cloneFamily;
		}
	
	
	public void printCloneList(ArrayList<Clone> arr) {
		for (Clone c : arr) {
			System.out.println(c.toString());
		}
	}
	
	public void printCloneFamilyList(ArrayList<CloneFamily> arr) {
		for (CloneFamily c : arr) {
			System.out.println(c.toString());
		}
	}
	
	

}
