/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salabatepapo.criptografia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import salabatepapo.interfaces.ICriptografia;

/**
 *
 * @author aluno
 */
public class AlgoritimoRSA implements ICriptografia {

    private String algoritimo;
    private String pathChavePrivada;
    private String pathChavePublica;

    public AlgoritimoRSA() {
        this.algoritimo = "RSA";
        this.pathChavePrivada = "../keys/private.key";
        this.pathChavePublica = "../keys/public.key";
    }

    public AlgoritimoRSA(String algoritimo, String pathChavePrivada, String pathChavePublica) {
        this.algoritimo = algoritimo;
        this.pathChavePrivada = pathChavePrivada;
        this.pathChavePublica = pathChavePublica;
    }

    /**
     * @return the algoritimo
     */
    public String getAlgoritimo() {
        return algoritimo;
    }

    /**
     * @param algoritimo the algoritimo to set
     */
    public void setAlgoritimo(String algoritimo) {
        this.algoritimo = algoritimo;
    }

    /**
     * @return the pathChavePrivada
     */
    public String getPathChavePrivada() {
        return pathChavePrivada;
    }

    /**
     * @param pathChavePrivada the pathChavePrivada to set
     */
    public void setPathChavePrivada(String pathChavePrivada) {
        this.pathChavePrivada = pathChavePrivada;
    }

    /**
     * @return the pathChavePublica
     */
    public String getPathChavePublica() {
        return pathChavePublica;
    }

    /**
     * @param pathChavePublica the pathChavePublica to set
     */
    public void setPathChavePublica(String pathChavePublica) {
        this.pathChavePublica = pathChavePublica;
    }

    public void gerarChaves() {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algoritimo);
            //Gera a chave que contém um par de chave Privada e Pública usando 1024 bytes.
            keyGen.initialize(1024);
            
            final KeyPair key = keyGen.genKeyPair();
            
            File chavePrivadaFile = new File(pathChavePrivada);
            File chavePublicaFile = new File(pathChavePublica);
            System.out.println(chavePublicaFile.getAbsolutePath());
            
            if(chavePrivadaFile.getParentFile() != null){
                chavePrivadaFile.getParentFile().mkdir();
            }
            chavePrivadaFile.createNewFile();
            
            if(chavePublicaFile.getParentFile() != null){
                chavePublicaFile.getParentFile().mkdir();
            }
            chavePublicaFile.createNewFile();
            
            //Salvar chaves
            
            try (ObjectOutputStream chavePublicaOS = new ObjectOutputStream(new FileOutputStream(chavePublicaFile.getCanonicalPath()))) {
                chavePublicaOS.writeObject(key.getPublic());
            }
            
            try (ObjectOutputStream chavePrivadaOS = new ObjectOutputStream(new FileOutputStream(chavePrivadaFile.getCanonicalPath()))) {
                chavePrivadaOS.writeObject(key.getPrivate());
            }
            
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    
    
    public boolean verificaSeExisteChavesNoSO(){
        File chavePublica = new File(pathChavePublica);
        File chavePrivada = new File(pathChavePrivada);
        
        return chavePrivada.exists() && chavePublica.exists();
    }
    

    @Override
    public Object criptografar(Object object, Object key) throws Exception {
       byte[] textoCifrado = null;
       String conteudo = (String) object;
        try {
            final Cipher cipher = Cipher.getInstance(algoritimo);
            cipher.init(Cipher.ENCRYPT_MODE, (PublicKey)key);
            textoCifrado = cipher.doFinal(conteudo.getBytes());
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return textoCifrado;
    }

    @Override
    public Object descriptografar(Object objetc, Object key) throws Exception {
        byte[] textoDecifrado = null;
        byte[] conteudoCriptografado = (byte[])objetc;
        try {
            final Cipher cipher = Cipher.getInstance(algoritimo);
            cipher.init(Cipher.DECRYPT_MODE,(PrivateKey)key);
            textoDecifrado = cipher.doFinal(conteudoCriptografado);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
      return new String(textoDecifrado);  
    }

}
