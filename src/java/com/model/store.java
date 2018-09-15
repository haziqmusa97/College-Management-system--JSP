/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author haziq
 */
import javax.faces.bean.ManagedBean;
 import javax.faces.bean.SessionScoped;
 import java.sql.*;
import java.util.ArrayList;


 @ManagedBean(name="store")
 @SessionScoped
 public class store {
 private String userName;
 private String password;
 private String label1;
 private String dbpwd;
 private String dbusername;
 private String kolej;

    

    

 private static int numOfAttempts = 0;
/** Creates a new instance of loginBean */
public store() {
}

/**
 * @return the userName
 */
public String getUserName() {
    return userName;
}

/**
 * @param userName the userName to set
 */
public void setUserName(String userName) {
    this.userName = userName;
}

/**
 * @return the password
 */
public String getPassword() {
    return password;
}

/**
 * @param password the password to set
 */
public void setPassword(String password) {
    this.password = password;
}

/**
 * @return the label1
 */
public String getLabel1() {
    return label1;
}

/**
 * @param label1 the label1 to set
 */
public void setLabel1(String label1) {
    this.label1 = label1;
}

        Connection con;
    Statement ps;
    ResultSet rs;
    String SQL_Str;

    public void dbData(String UName)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartkolej","root","");
            ps = con.createStatement();
            SQL_Str="Select * from merit where student_id =('" + UName +"')";
            rs=ps.executeQuery(SQL_Str);
            rs.next();
            dbusername=rs.getString("student_id");
            dbpwd=rs.getString("merit_id");
            //kolej=rs.getString("student_kolej");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
           // System.out.println("Exception Occur :" + ex);
        }
    }


 public String checkLogin()
 {
      dbData(userName);
     if (userName.equals(dbusername) )
    {
        
        
        try {
            Connection connection=null;
            DB_connection obj_DB_connection=new DB_connection();
            connection=obj_DB_connection.get_connection();
            PreparedStatement ps=connection.prepareStatement("insert into store(student_id,merit_id) VALUES('"+password+"','"+userName+"')");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        this.setLabel1("Success");
        return "/Studkeyinmerit.xhtml?faces-redirect=true"; 
    }
    else
    {
        numOfAttempts++;
        if (numOfAttempts >= 3)
        {
        this.setLabel1("Incorrect ID");
        return "/Studkeyinmerit.xhtml?faces-redirect=true"; 
        }
        else
        {
            this.setLabel1("Incorrect ID" );
             return "/Studkeyinmerit.xhtml?faces-redirect=true"; 
        }
    }
 }
 
         public ArrayList getGet_all_merit() throws Exception{
        ArrayList list_of_store=new ArrayList();
             Connection connection=null;
        try {
            DB_connection obj_DB_connection=new DB_connection();
            connection=obj_DB_connection.get_connection();
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery("select * from store");
            while(rs.next()){
                store obj_store=new store();
                obj_store.setUserName(rs.getString("student_id"));
                obj_store.setPassword(rs.getString("merit_id"));
                
                list_of_store.add(obj_store);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            if(connection!=null){
                connection.close();
            }
        }
        return list_of_store;
}





}