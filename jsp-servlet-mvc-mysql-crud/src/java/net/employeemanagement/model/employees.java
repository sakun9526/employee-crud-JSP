
package net.employeemanagement.model;

public class employees {
    
    private int id;
    private String name;
    private String email;
    private int nic;
    
    public employees (int id , String name, String email, int nic ){
        super();
        this.id = id;
        this.name = name;
        this.email= email;
        this.nic = nic;
    }
    
    public employees (String name, String email, int nic ){
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
    
    public int getNic(){
        return nic;
    }
    
    public void setNic(int nic){
        this.nic = nic; 
    }
    
}
