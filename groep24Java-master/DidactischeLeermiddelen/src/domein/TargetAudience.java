/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
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
    @NamedQuery(name = "TargetAudience.findByName", query = "select b from TargetAudience b where b.name = :targetAudienceName")            
})
public class TargetAudience implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int targetAudienceId;
    private String name;

    public TargetAudience() {
    }
    
    public TargetAudience(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
