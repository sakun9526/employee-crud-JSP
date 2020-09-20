/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.employeemanagement.model;

/**
 *
 * @author Sakun
 */
public class Employees {
    
    protected int id;
    protected String name;
    protected String email;
    protected String nic;
    
    public Employees() {}
    
    public Employees (int id ,String name, String email, String nic ){
        super();
        this.id = id;
        this.name = name;
        this.email= email;
        this.nic = nic;
    }
    
    public Employees (String name, String email, String nic ){
        super();
        this.name = name;
        this.email= email;
        this.nic = nic;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id; 
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name; 
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email; 
    }
    
    public String getNic(){
        return nic;
    }
    
    public void setNic(String nic){
        this.nic = nic; 
    }
    
}




