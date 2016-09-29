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
    @NamedQuery(name = "Curricular.findByName", query = "select b from Curricular b where b.name = :curricularName")            
})
public class Curricular implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int curricularId;
    private String name;

    public Curricular() {
    }
    
    public Curricular(String name) {
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

    public int getCurricularId() {
        return curricularId;
    }

    public void setCurricularId(int curricularId) {
        this.curricularId = curricularId;
    }
}
