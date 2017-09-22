/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saladebatepapo.ciptografia;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import salabatepapo.interfaces.ICriptografia;

/**
 *
 * @author aluno
 */
public class AlgoritimoAES implements ICriptografia{
    private String IV;

    public AlgoritimoAES() {
        this.IV = "AAAAAAAAAAAAAAAA";
    }

    public AlgoritimoAES(String IV) {
        this.IV = IV;
    }
    
    
    @Override
    public Object criptografar(Object object, Object key) throws Exception{
        String texto = (String) object;
        String chave = (String) key;
        Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec chaveSecreta = new SecretKeySpec(chave.getBytes("UTF-8"), "AES");
        encripta.init(Cipher.ENCRYPT_MODE, chaveSecreta,new IvParameterSpec(this.IV.getBytes("UTF-8")));
        return encripta.doFinal(texto.getBytes("UTF-8"));
    }

    @Override
    public Object descriptografar(Object objetc, Object key) throws Exception{
        String chave = (String) key;
        byte[] mensagemCriptografada = (byte[]) objetc;
        Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec chaveSecreta = new SecretKeySpec(chave.getBytes("UTF-8"), "AES");
        decripta.init(Cipher.DECRYPT_MODE, chaveSecreta,new IvParameterSpec(this.IV.getBytes("UTF-8")));
        return new String(decripta.doFinal(mensagemCriptografada),"UTF-8");
    }
    
}
