/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
public class event {
    private String event_id;
    private String event_name;
    private String event_org;
    private String event_date;
    private String event_venue;

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_org() {
        return event_org;
    }

    public void setEvent_org(String event_org) {
        this.event_org = event_org;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_venue() {
        return event_venue;
    }

    public void setEvent_venue(String event_venue) {
        this.event_venue = event_venue;
    }
    
    
      //add data into database
    public String add_event(){
        try {
            Connection connection=null;
            DB_connection obj_DB_connection=new DB_connection();
            connection=obj_DB_connection.get_connection();
            PreparedStatement ps=connection.prepareStatement("insert into event(event_name,event_org,event_date,event_venue) VALUES('"+event_name+"','"+event_org+"','"+event_date+"','"+event_venue+"')");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/viewevent.xhtml?faces-redirect=true";  
    }
    
       //view data from database
        public ArrayList getGet_all_event() throws Exception{
        ArrayList list_of_event=new ArrayList();
             Connection connection=null;
        try {
            DB_connection obj_DB_connection=new DB_connection();
            connection=obj_DB_connection.get_connection();
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery("select * from event");
            while(rs.next()){
                event obj_event=new event();
                obj_event.setEvent_id(rs.getString("event_id"));
                obj_event.setEvent_name(rs.getString("event_name"));
                obj_event.setEvent_org(rs.getString("event_org"));
                obj_event.setEvent_date(rs.getString("event_date"));
                obj_event.setEvent_venue(rs.getString("event_venue"));
                list_of_event.add(obj_event);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            if(connection!=null){
                connection.close();
            }
        }
        return list_of_event;
}
        
        //delete data event
      public String delete_event(){
      FacesContext fc = FacesContext.getCurrentInstance();
      Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
      String field_event= params.get("action");
      try {
         DB_connection obj_DB_connection=new DB_connection();
         Connection connection=obj_DB_connection.get_connection();
       PreparedStatement ps=connection.prepareStatement("delete from event where event_id=?");
         ps.setString(1, field_event);
         System.out.println(ps);
         ps.executeUpdate();
        } catch (Exception e) {
         System.out.println(e);
        }
       return "/viewevent.xhtml?faces-redirect=true";   
}
      
      //edit data from database
         private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 

    public String edit_event(){
     FacesContext fc = FacesContext.getCurrentInstance();
     Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
     String field_event_id= params.get("action");
     try {
          DB_connection obj_DB_connection=new DB_connection();
          Connection connection=obj_DB_connection.get_connection();
          Statement st=connection.createStatement();
          ResultSet rs=st.executeQuery("select * from event where event_id="+field_event_id);
          event obj_event=new event();
          rs.next();
          obj_event.setEvent_id(rs.getString("event_id"));
                obj_event.setEvent_name(rs.getString("event_name"));
                obj_event.setEvent_org(rs.getString("event_org"));
                obj_event.setEvent_date(rs.getString("event_date"));
                obj_event.setEvent_venue(rs.getString("event_venue"));
          sessionMap.put("editcategory", obj_event);  
      } catch (Exception e) {
            System.out.println(e);
      }
     return "/editevent.xhtml?faces-redirect=true";   
}

        public String update_event(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	String	update_event_id= params.get("update_event_id");
        try {
            DB_connection obj_DB_connection=new DB_connection();
            Connection connection=obj_DB_connection.get_connection();
            PreparedStatement ps=connection.prepareStatement("update event set event_name=?,event_org=?,event_date=?,event_venue=? where event_id=?");
            ps.setString(1, event_name);
            ps.setString(2, event_org);
            ps.setString(3, event_date);
            ps.setString(4, event_venue);
            ps.setString(5, update_event_id);
            
            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
       return "/viewevent.xhtml?faces-redirect=true";   
}
    
}
