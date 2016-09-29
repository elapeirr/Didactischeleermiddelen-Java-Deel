/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

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
public class CurricularTest {
    
    public CurricularTest() {
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
     * Test of getName method, of class Curricular.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Curricular instance = new Curricular();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Curricular.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Curricular instance = new Curricular();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Curricular.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Curricular instance = new Curricular();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurricularId method, of class Curricular.
     */
    @Test
    public void testGetCurricularId() {
        System.out.println("getCurricularId");
        Curricular instance = new Curricular();
        int expResult = 0;
        int result = instance.getCurricularId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCurricularId method, of class Curricular.
     */
    @Test
    public void testSetCurricularId() {
        System.out.println("setCurricularId");
        int curricularId = 0;
        Curricular instance = new Curricular();
        instance.setCurricularId(curricularId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
