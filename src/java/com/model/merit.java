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
public class merit {
    
    private int student_id;
    private String event_name;
    private String merit_id;

    public String getMerit_id() {
        return merit_id;
    }

    public void setMerit_id(String merit_id) {
        this.merit_id = merit_id;
    }
    

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }
    
      //add data into database
    public String add_merit(){
        int no= 1;
    for(int i=0; i<num; i++){
        try {
            Connection connection=null;
            DB_connection obj_DB_connection=new DB_connection();
            connection=obj_DB_connection.get_connection();
            PreparedStatement ps=connection.prepareStatement("insert into merit(student_id,event_name) VALUES('"+student_id*no+"','"+event_name+"')");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        no++;
        
    }
    return "/viewmerit.xhtml?faces-redirect=true";  
    }
    
    //view data from database
        public ArrayList getGet_all_merit() throws Exception{
        ArrayList list_of_merit=new ArrayList();
             Connection connection=null;
        try {
            DB_connection obj_DB_connection=new DB_connection();
            connection=obj_DB_connection.get_connection();
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery("select * from merit");
            while(rs.next()){
                merit obj_merit=new merit();
                obj_merit.setStudent_id(rs.getInt("student_id"));
                obj_merit.setEvent_name(rs.getString("event_name"));
                obj_merit.setMerit_id(rs.getString("merit_id"));
                list_of_merit.add(obj_merit);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            if(connection!=null){
                connection.close();
            }
        }
        return list_of_merit;
}
        
        //delete data from database
        
      public String delete_merit(){
      FacesContext fc = FacesContext.getCurrentInstance();
      Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
      String field_merit= params.get("action");
      try {
         DB_connection obj_DB_connection=new DB_connection();
         Connection connection=obj_DB_connection.get_connection();
       PreparedStatement ps=connection.prepareStatement("delete from merit where merit_id=?");
         ps.setString(1, field_merit);
         System.out.println(ps);
         ps.executeUpdate();
        } catch (Exception e) {
         System.out.println(e);
        }
       return "/viewmerit.xhtml?faces-redirect=true";   
}
      
      //edit data from database
         private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 

public String edit_merit(){
     FacesContext fc = FacesContext.getCurrentInstance();
     Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
     String field_merit_id= params.get("action");
     try {
          DB_connection obj_DB_connection=new DB_connection();
          Connection connection=obj_DB_connection.get_connection();
          Statement st=connection.createStatement();
          ResultSet rs=st.executeQuery("select * from merit where merit_id="+field_merit_id);
          merit obj_merit=new merit();
          rs.next();
          obj_merit.setStudent_id(rs.getInt("student_id"));
          obj_merit.setEvent_name(rs.getString("event_name"));
          obj_merit.setMerit_id(rs.getString("merit_id"));
          sessionMap.put("editcategory", obj_merit);  
      } catch (Exception e) {
            System.out.println(e);
      }
     return "/editmerit.xhtml?faces-redirect=true";   
}

private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


public String update_merit(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	String	update_merit_id= params.get("update_merit_id");
        try {
            DB_connection obj_DB_connection=new DB_connection();
            Connection connection=obj_DB_connection.get_connection();
      PreparedStatement ps=connection.prepareStatement("update merit set student_id=?,event_name=? where merit_id=?");
            ps.setInt(1, student_id);
            ps.setString(2, event_name);
            ps.setString(3, update_merit_id);
            
            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
       return "/viewmerit.xhtml?faces-redirect=true";   
}
    
}
