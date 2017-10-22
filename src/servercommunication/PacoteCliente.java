/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servercommunication;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author Vitor
 */
public class PacoteCliente implements Serializable{

    private InetAddress enderecoClietne;
    private Object chavePublica;
    private int porta;

    public PacoteCliente() {
    }

    public PacoteCliente(InetAddress enderecoClietne, Object chavePublica, int porta) {
        this.enderecoClietne = enderecoClietne;
        this.chavePublica = chavePublica;
        this.porta = porta;
    }

    
    
    
    /**
     * @return the enderecoClietne
     */
    public InetAddress getEnderecoClietne() {
        return enderecoClietne;
    }

    /**
     * @param enderecoClietne the enderecoClietne to set
     */
    public void setEnderecoClietne(InetAddress enderecoClietne) {
        this.enderecoClietne = enderecoClietne;
    }

    /**
     * @return the chavePublica
     */
    public Object getChavePublica() {
        return chavePublica;
    }

    /**
     * @param chavePublica the chavePublica to set
     */
    public void setChavePublica(Object chavePublica) {
        this.chavePublica = chavePublica;
    }

    /**
     * @return the porta
     */
    public int getPorta() {
        return porta;
    }

    /**
     * @param porta the porta to set
     */
    public void setPorta(int porta) {
        this.porta = porta;
    }
}
