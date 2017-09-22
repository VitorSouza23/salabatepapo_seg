/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salabatepapo.interfaces;

/**
 *
 * @author aluno
 */
public interface ICriptografia {
    public Object criptografar(Object object, Object key) throws Exception;
    public Object descriptografar(Object objetc, Object key) throws Exception;
}
