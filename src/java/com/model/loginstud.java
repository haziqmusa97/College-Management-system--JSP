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
import java.util.Map;
import javax.faces.context.FacesContext;


 @ManagedBean(name="loginstud")
 @SessionScoped
 public class loginstud {
 private String userName;
 private String password;
 private String label1;
 private String dbpwd;
 private String dbusername;
 private String kolej;
 private String student_name;
 private String student_course;

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_course() {
        return student_course;
    }

    public void setStudent_course(String student_course) {
        this.student_course = student_course;
    }
 
 
 

    public String getKolej() {
        return kolej;
    }

    public void setKolej(String kolej) {
        this.kolej = kolej;
    }

 private static int numOfAttempts = 0;
/** Creates a new instance of loginBean */
public loginstud() {
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
            SQL_Str="Select * from student where student_id =('" + UName +"')";
            rs=ps.executeQuery(SQL_Str);
            rs.next();
            dbusername=rs.getString("student_id");
            dbpwd=rs.getString("student_ic");
            kolej=rs.getString("student_kolej");
            student_name=rs.getString("student_name");
            student_course=rs.getString("student_course");
            
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
     if (userName.equals(dbusername) && password.equals(dbpwd))
    {
        this.setLabel1("");
        return "/studentindex.xhtml?faces-redirect=true"; 
    }
    else
    {
        numOfAttempts++;
        if (numOfAttempts >= 3)
        {
        this.setLabel1("Account Locked");
        return "/studentLog.xhtml?faces-redirect=true"; 
        }
        else
        {
            this.setLabel1("Login Failure" );
             return "/studentLog.xhtml?faces-redirect=true"; 
        }
    }
 }
 







}