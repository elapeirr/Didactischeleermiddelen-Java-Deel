/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.List;

/**
 *
 * @author Elise Lapeirre
 */
public class Catalogus {
    private List<Material> materials;

    public Catalogus(List<Material> mat) {
        this.materials = mat;
    }

    public List<Material> getMat() {
        return materials;
    }

    public void setMat(List<Material> mat) {
        this.materials = mat;
    }
    
    
}
