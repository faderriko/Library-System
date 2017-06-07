
package databaseHolder;

import java.sql.*;
import javax.swing.JOptionPane;

public final class DatabaseClass {
    
  
private static final String CONNECTION = "jdbc:mysql://localhost:3306/dero";
private static final String USER = "root";
private static final String PASSWORD = "root";
Connection conn = null;
Statement stmt = null;
private static DatabaseClass databaseClass = null; 
    
    private DatabaseClass(){
        createConnection();
        setupBookTable();
        setupMemberTable();
        setupIssueTable();
}
    
    public static DatabaseClass getInstance()
    {
        if(databaseClass == null)
        {
            databaseClass = new DatabaseClass();
            
        }
        return databaseClass;
    }
    
    
    
    
    void createConnection()
    {
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            conn =  DriverManager.getConnection(CONNECTION, USER, PASSWORD);
            System.out.println("Connection Successful");
        }
        catch(Exception e)
        {
           e.printStackTrace();
           System.exit(1);
        }
    }
    
    void setupBookTable()
    {
        String TABLE_NAME = "BOOK";
        try{
            stmt =  conn.createStatement();
            
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null,TABLE_NAME.toUpperCase(),null);
            
            
            if(tables.next())
            {
                System.out.println("Table " + TABLE_NAME + " Already Exist. Ready for go!");
                
            }
            else{
             String table= "CREATE TABLE " + TABLE_NAME + ""+
                   "(id INTEGER not NULL, " +
                   " title VARCHAR(200), " + 
                   " author VARCHAR(200), " + 
                   " publisher VARCHAR(200), " + 
                   " isAvail boolean default true, " + 
                   " PRIMARY KEY ( id ))"; 
                stmt.execute(table);
            }
            
            
        } catch (SQLException e) {
            System.err.println(e.getMessage()+ "--- setupDatabase");
        } finally {
            
        }
    }
    
    public ResultSet execQuery(String query){
        ResultSet result;
        try{
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
        System.out.println("Exception at execQuery:datahandler"+ ex.getLocalizedMessage());
        return null;
    }
        finally {
            
        }
        return result;
    }
    
    public boolean execAction(String qu){
        try{
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error:" + ex.getMessage(),"Error Occured",JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
            
        } finally{
            
        } 
    }

    private void setupMemberTable() {
        String TABLE_NAME = "MEMBER";
        try{
            stmt =  conn.createStatement();
            
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null,TABLE_NAME.toUpperCase(),null);
            
            
            if(tables.next())
            {
                System.out.println("Table " + TABLE_NAME + " Already Exist. Ready for go!");
                
            }
            else{
                String table= "CREATE TABLE " + TABLE_NAME + "" +
                   "(id INTEGER not NULL, " +
                   " name VARCHAR(200), " + 
                   " mobile VARCHAR(200), " + 
                   " email VARCHAR(200), " + 
                   " PRIMARY KEY ( id ))"; 
                stmt.execute(table);
            }
            
            
        } catch (SQLException e) {
            System.err.println(e.getMessage()+ "--- setupDatabase");
        } finally {
            
        }
    }
    
    private void setupIssueTable()
    {
        String TABLE_NAME = "ISSUE";
        try{
            stmt =  conn.createStatement();
            
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null,TABLE_NAME.toUpperCase(),null);
            
            
            if(tables.next())
            {
                System.out.println("Table " + TABLE_NAME + " Already Exist. Ready for go!");
                
            }
            else{
                String table= "CREATE TABLE " + TABLE_NAME + "" +
                   "(bookID INTEGER not NULL, " +
                   " memberID VARCHAR(200), " + 
                   " issueTime timestamp default CURRENT_TIMESTAMP, " + 
                   " renew_count integer default 0, " + 
                   " FOREIGN KEY(bookID) REFERENCES book(id), " +
                   " PRIMARY KEY ( bookID ))"; 
                stmt.execute(table);
            }
            
            
        } catch (SQLException e) {
            System.err.println(e.getMessage()+ "--- setupDatabase");
        } finally {
            
        }
    }
        
    
        
    }
 
    
   
    
