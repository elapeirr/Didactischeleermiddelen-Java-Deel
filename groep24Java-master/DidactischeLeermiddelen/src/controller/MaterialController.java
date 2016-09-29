/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domein.Curricular;
import domein.Material;
import domein.PopulateDB;
import domein.TargetAudience;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityNotFoundException;
import repository.CurricularDaoJpa;
import repository.GenericDaoJpa;
import repository.MaterialDaoJpa;
import repository.TargetAudienceDaoJpa;

/**
 *
 * @author Elise Lapeirre
 */
public class MaterialController {

    private MaterialDaoJpa materialRepo;
    private CurricularDaoJpa curricularRepo;
    private TargetAudienceDaoJpa targetAudienceRepo;

    private Material currentMaterial;

    public MaterialController() {
        this(true);
    }

    public MaterialController(boolean withInit) {
//        if (withInit) {
//            new PopulateDB().run();
//        }
        materialRepo = new MaterialDaoJpa();
        curricularRepo = new CurricularDaoJpa();
        targetAudienceRepo = new TargetAudienceDaoJpa();
    }

    public void updateMaterial(Material material) {
        materialRepo.startTransaction();
        materialRepo.update(material);
        materialRepo.commitTransaction();
    }

    public void removeMaterial(Material material) {
        materialRepo.startTransaction();
        materialRepo.delete(material);
        materialRepo.commitTransaction();
    }

    public void addMaterial(Material material) {
        materialRepo.startTransaction();
        materialRepo.insert(material);
        materialRepo.commitTransaction();
    }

    public ObservableList<Material> getMaterials() {
        return FXCollections.observableArrayList(materialRepo.findAll());
    }

    public ObservableList<String> getFirms() {
        return FXCollections.observableArrayList(materialRepo.getFirms());
    }

    public List<Curricular> getCurriculars() {
        return curricularRepo.findAll();
    }

    public Curricular findCurricular(String curricular) {
        return curricularRepo.getCurricularByName(curricular);
    }

    public TargetAudience findTargetAudience(String targetAudience) {
        return targetAudienceRepo.getTargetAudienceByName(targetAudience);
    }

    public List<TargetAudience> getTargetAudiences() {
        return targetAudienceRepo.findAll();
    }

    public Material getCurrentMaterial() {
        return currentMaterial;
    }

    public void setCurrentMaterial(Material currentMaterial) {
        this.currentMaterial = currentMaterial;
    }

    public String findBy(String name) {
        String naam;
        try {
            naam = materialRepo.findBy(name);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException();
        }
        return naam;
    }

    public Material findByName(String name) {
        List<Material> materials = materialRepo.findAll();
        Material m = new Material();
        boolean bestaatmateriaal = false;
        for (int i = 0; i < materials.size(); i++) {
            if (materials.get(i).getName().equals(name)) {
                m = materials.get(i);
                bestaatmateriaal = true;
            }

        }
        if(!bestaatmateriaal){
            throw new EntityNotFoundException();
        }
        return m;

    }

    public ObservableList<Material> getFilteredMaterials(String s) {
        List<Material> materials = materialRepo.findAll();
        List<Material> hulp = new ArrayList<>();

        if (s.equals("")) {
            hulp = materials;

        } else {
            for (int i = 0; i < materials.size(); i++) {
                Material m = materials.get(i);
                Set<Curricular> cur = m.getCurriculars();
                Set<TargetAudience> targ = m.getTargetAudiences();
                if (m.getName().toLowerCase().contains(s.toLowerCase()) || m.getDescription().toLowerCase().contains(s.toLowerCase())) {
                    hulp.add(m);
                }else{
                    for(Curricular c: cur){
                        if(c.getName().toLowerCase().contains(s.toLowerCase())){
                            hulp.add(m);
                        }
                    }
                    for(TargetAudience t: targ){
                        if(t.getName().toLowerCase().contains(s.toLowerCase())){
                            hulp.add(m);
                        }
                    }
                }
                

            }

        }
        return FXCollections.observableArrayList(hulp);
    }
}
