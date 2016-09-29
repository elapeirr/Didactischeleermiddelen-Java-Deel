/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sofie
 */
public class ReservationTest {
    
    public ReservationTest() {
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
     * Test of checkConflicts method, of class Reservation.
     */
    @Test
    public void testCheckConflicts() {
        System.out.println("checkConflicts");
        Material material = new Material("rekenmachine", 3);
        Reservation r;
        r = new Reservation(material, 2, new GregorianCalendar(2016,4,19), new GregorianCalendar(2016,4,21), "test@test.be");
        Reservation res = new Reservation(material, 2, new GregorianCalendar(2016,4,19), new GregorianCalendar(2016,4,21), "test@test.be");
        res.checkConflicts();
        assertTrue(res.isConflict());
        fail("De conflicten worden niet gevonden");
    }
    
}
