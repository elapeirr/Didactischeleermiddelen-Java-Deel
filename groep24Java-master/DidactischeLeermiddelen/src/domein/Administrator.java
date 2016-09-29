/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Axel
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Administrator.findByMail", query ="select a.email from Administrator a where a.email =:administratorMail"),
    @NamedQuery(name = "Administrator.getPassword", query ="select a.password from Administrator a where a.email =:administratorMail")
})
public class Administrator implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int administratorid;
    private String name;
    private String email;
    private String password;
    private boolean isHeadAdministrator;
    

    public Administrator(){
    }
     
    public Administrator(String name, String email, String password, boolean isHeadAdministrator) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isHeadAdministrator = isHeadAdministrator;
    }
    
    public Administrator(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isHeadAdministrator = false;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StringProperty getNameProperty() {
        return new SimpleStringProperty(name);
    }

    public StringProperty getEmailProperty() {
        return new SimpleStringProperty(email);
    }
    
    public StringProperty getHeadAdminProperty(){
        String headadmin ="Nee";
        if(isHeadAdministrator()) {
            headadmin="Ja";
        }

        return new SimpleStringProperty(headadmin);
    }

    public boolean isHeadAdministrator() {
        return isHeadAdministrator;
    }

    public void setIsHeadAdministrator(boolean isHeadAdministrator) {
        this.isHeadAdministrator = isHeadAdministrator;
    }
    
    
    
    

    

    

    
    
    

    
}
