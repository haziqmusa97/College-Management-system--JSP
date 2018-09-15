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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
@ManagedBean
@RequestScoped

public class student {
    
    private String student_id;
    private String student_name;
    private String student_kolej;
    private String student_course;
    private String student_no;
    private String student_ic;

    public String getStudent_ic() {
        return student_ic;
    }

    public void setStudent_ic(String student_ic) {
        this.student_ic = student_ic;
    }
    

    public String getStudent_kolej() {
        return student_kolej;
    }

    public void setStudent_kolej(String student_kolej) {
        this.student_kolej = student_kolej;
    }

    public String getStudent_course() {
        return student_course;
    }

    public void setStudent_course(String student_course) {
        this.student_course = student_course;
    }

    public String getStudent_no() {
        return student_no;
    }

    public void setStudent_no(String student_no) {
        this.student_no = student_no;
    }
    
    

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }
    
     //add data into database
    public String add_student(){
        try {
            Connection connection=null;
            DB_connection obj_DB_connection=new DB_connection();
            connection=obj_DB_connection.get_connection();
            PreparedStatement ps=connection.prepareStatement("insert into student(student_id,student_name,student_kolej,student_course,student_ic) VALUES('"+student_id+"','"+student_name+"','"+student_kolej+"','"+student_course+"','"+student_ic+"')");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/viewstudent.xhtml?faces-redirect=true";  
    }
    
       //view data from database
        public ArrayList getGet_all_student() throws Exception{
        ArrayList list_of_student=new ArrayList();
             Connection connection=null;
        try {
            DB_connection obj_DB_connection=new DB_connection();
            connection=obj_DB_connection.get_connection();
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery("select * from student");
            while(rs.next()){
                student obj_student=new student();
                obj_student.setStudent_id(rs.getString("student_id"));
                obj_student.setStudent_name(rs.getString("student_name"));
                obj_student.setStudent_kolej(rs.getString("student_kolej"));
                obj_student.setStudent_course(rs.getString("student_course"));
                obj_student.setStudent_no(rs.getString("student_no"));
                obj_student.setStudent_ic(rs.getString("student_ic"));
                list_of_student.add(obj_student);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            if(connection!=null){
                connection.close();
            }
        }
        return list_of_student;
}
        
        //delete data event
      public String delete_student(){
      FacesContext fc = FacesContext.getCurrentInstance();
      Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
      String field_student= params.get("action");
      try {
         DB_connection obj_DB_connection=new DB_connection();
         Connection connection=obj_DB_connection.get_connection();
       PreparedStatement ps=connection.prepareStatement("delete from student where student_no=?");
         ps.setString(1, field_student);
         System.out.println(ps);
         ps.executeUpdate();
        } catch (Exception e) {
         System.out.println(e);
        }
       return "/viewstudent.xhtml?faces-redirect=true";   
}
      
      //edit data from database
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 

    public String edit_student(){
     FacesContext fc = FacesContext.getCurrentInstance();
     Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
     String field_student_no= params.get("action");
     try {
          DB_connection obj_DB_connection=new DB_connection();
          Connection connection=obj_DB_connection.get_connection();
          Statement st=connection.createStatement();
          ResultSet rs=st.executeQuery("select * from student where student_no="+field_student_no);
          student obj_student=new student();
          rs.next();
          obj_student.setStudent_id(rs.getString("student_id"));
                obj_student.setStudent_name(rs.getString("student_name"));
                obj_student.setStudent_kolej(rs.getString("student_kolej"));
                obj_student.setStudent_course(rs.getString("student_course"));
                obj_student.setStudent_no(rs.getString("student_no"));
                obj_student.setStudent_ic(rs.getString("student_ic"));
          sessionMap.put("editcategory", obj_student);  
      } catch (Exception e) {
            System.out.println(e);
      }
     return "/editstudent.xhtml?faces-redirect=true";   
}

        public String update_student(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	String	update_student_no= params.get("update_student_no");
        try {
            DB_connection obj_DB_connection=new DB_connection();
            Connection connection=obj_DB_connection.get_connection();
            PreparedStatement ps=connection.prepareStatement("update student set student_id=?,student_name=?,student_kolej=?,student_course=?,student_ic=? where student_no=?");
            ps.setString(1, student_id);
            ps.setString(2, student_name);
            ps.setString(3, student_kolej);
            ps.setString(4, student_course);
            ps.setString(5, student_ic);
            ps.setString(6, update_student_no);
            
            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
       return "/viewstudent.xhtml?faces-redirect=true";   
}
    
}
