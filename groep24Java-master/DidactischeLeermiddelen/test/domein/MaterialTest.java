/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import javafx.beans.property.StringProperty;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Elise Lapeirre
 */
public class MaterialTest {
    
    public MaterialTest() {
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
     * Test of getAmountAvailable method, of class Material.
     */
    @Test
    public void testGetAmountAvailable() {
        System.out.println("getAmountAvailable");
        Date day = new Date(2016,5,20);
        Material instance = new Material("rekenmachine", 3);
        Reservation r;
        r = new Reservation(instance, 2, new GregorianCalendar(2016,4,19), new GregorianCalendar(2016,4,21), "test@test.be");
        int expResult = 1;
        int result = instance.getAmountAvailable(day);
        assertEquals(expResult, result);
        fail("Het correcte aantal wordt niet terug gegeven");
    }   
    
}
