package com.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ROG
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
public class room {
    
    private String student_id;
    private String collage;
    private String numroom;
    private String status;

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public String getNumroom() {
        return numroom;
    }

    public void setNumroom(String numroom) {
        this.numroom = numroom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
     //add data into database
    public String add_room(){
        try {
            Connection connection=null;
            DB_connection obj_DB_connection=new DB_connection();
            connection=obj_DB_connection.get_connection();
            PreparedStatement ps=connection.prepareStatement("insert into room(student_id,collage,numroom,status) VALUES('"+student_id+"','"+collage+"','"+numroom+"','"+status+"')");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/Studentbidroom.xhtml?faces-redirect=true";  
    }
    
    //view data from database
        public ArrayList getGet_all_room() throws Exception{
        ArrayList list_of_room=new ArrayList();
             Connection connection=null;
        try {
            DB_connection obj_DB_connection=new DB_connection();
            connection=obj_DB_connection.get_connection();
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery("select * from room");
            while(rs.next()){
                room obj_room=new room();
                obj_room.setStudent_id(rs.getString("student_id"));
                obj_room.setCollage(rs.getString("collage"));
                obj_room.setNumroom(rs.getString("numroom"));
                obj_room.setStatus(rs.getString("status"));
                
                list_of_room.add(obj_room);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            if(connection!=null){
                connection.close();
            }
        }
        return list_of_room;
}
    
          //edit data from database
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 

    public String edit_student(){
     FacesContext fc = FacesContext.getCurrentInstance();
     Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
     String field_student_id= params.get("action");
     try {
          DB_connection obj_DB_connection=new DB_connection();
          Connection connection=obj_DB_connection.get_connection();
          Statement st=connection.createStatement();
          ResultSet rs=st.executeQuery("select * from room where student_id="+field_student_id);
          room obj_room=new room();
          rs.next();
          obj_room.setStudent_id(rs.getString("student_id"));
                obj_room.setCollage(rs.getString("collage"));
                obj_room.setNumroom(rs.getString("numroom"));
                obj_room.setStatus(rs.getString("status"));
                
          sessionMap.put("editcategory", obj_room);  
      } catch (Exception e) {
            System.out.println(e);
      }
     return "/editroom.xhtml?faces-redirect=true";   
}

        public String update_room(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	String	update_student_id= params.get("update_student_id");
        try {
            DB_connection obj_DB_connection=new DB_connection();
            Connection connection=obj_DB_connection.get_connection();
            PreparedStatement ps=connection.prepareStatement("update room set collage=?,numroom=?,status=? where student_id=?");
            
            ps.setString(1, collage);
            ps.setString(2, numroom);
            ps.setString(3, status);
            ps.setString(4, update_student_id);
            
            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
       return "/viewstudentroom.xhtml?faces-redirect=true";   
}
        
        
}
