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
import java.util.List;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import repository.GenericDaoJpa;


/**
 *
 * @author Elise Lapeirre
 */
public class MaterialControllerTest {
    
    public MaterialControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findCurricular method, of class MaterialController.
     */
    @Test
    public void testFindCurricular() {
        System.out.println("findCurricular");
        String curricular = "Rekenen";
        MaterialController instance = new MaterialController();
        String expResult = "Rekenen";
        Curricular result = instance.findCurricular(curricular);
        assertEquals(expResult, result.getName());
        fail("Het leergebied wordt niet gevonden");
    }

    /**
     * Test of findTargetAudience method, of class MaterialController.
     */
    @Test
    public void testFindTargetAudience() {
        System.out.println("findTargetAudience");
        String targetAudience = "Kleuteronderwijs";
        MaterialController instance = new MaterialController();
        String expResult = "Kleuteronderwijs";
        TargetAudience result = instance.findTargetAudience(targetAudience);
        assertEquals(expResult, result.getName());
        fail("De doelgroep wordt niet gevonden");
    }

    /**
     * Test of findBy method, of class MaterialController.
     */
    @Test
    public void testFindBy() {
        System.out.println("findBy");
        String name = "Wereldbol";
        MaterialController instance = new MaterialController();
        String expResult = "Wereldbol";
        String result = instance.findBy(name);
        assertEquals(expResult, result);
        fail("Het materiaal wordt niet gevonden");
    }

    /**
     * Test of findByName method, of class MaterialController.
     */
    @Test
    public void testFindByName() {
        System.out.println("findByName");
        String name = "Wereldbol";
        MaterialController instance = new MaterialController();
        String expResult = "Wereldbol";
        Material result = instance.findByName(name);
        assertEquals(expResult, result.getName());
        fail("Het materiaal wordt niet gevonden");
    }
    
}
