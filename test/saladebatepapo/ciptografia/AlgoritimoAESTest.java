/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saladebatepapo.ciptografia;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author aluno
 */
public class AlgoritimoAESTest {
    
    public AlgoritimoAESTest() {
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
     * Test of criptografar method, of class AlgoritimoAES.
     */
    @Test
    public void testCriptografar() throws Exception {
        String mensagem = "Olá";
        String chave = "abacaxi123456789";
        AlgoritimoAES aes = new AlgoritimoAES();
        byte[] criptografado = (byte[]) aes.criptografar(mensagem, chave);
        System.out.println(new String(criptografado));
        System.out.println(aes.descriptografar(criptografado, chave));
    }

    /**
     * Test of descriptografar method, of class AlgoritimoAES.
     */
    @Test
    public void testDescriptografar() throws Exception {
        
    }
    
}
