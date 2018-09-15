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


 @ManagedBean(name="login")
 @SessionScoped
 public class loginBean {
 private String userName;
 private String password;
 private String label1;
 private String dbpwd;
 private String dbusername;

 private static int numOfAttempts = 0;
/** Creates a new instance of loginBean */
public loginBean() {
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
            SQL_Str="Select * from loginbean where admin_username =('" + UName +"')";
            rs=ps.executeQuery(SQL_Str);
            rs.next();
            dbusername=rs.getString("admin_username");
            dbpwd=rs.getString("admin_password");
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
        return "/index.xhtml?faces-redirect=true"; 
    }
    else
    {
        numOfAttempts++;
        if (numOfAttempts >= 3)
        {
        this.setLabel1("Account Locked");
        return "/adminLog.xhtml?faces-redirect=true"; 
        }
        else
        {
            this.setLabel1("Login Failure" );
             return "/adminLog.xhtml?faces-redirect=true"; 
        }
    }
 }






}