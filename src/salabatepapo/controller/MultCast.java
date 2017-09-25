/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salabatepapo.controller;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author aluno
 */
public class MultCast {
    private MulticastSocket soket;
    
    
    public void run(String endereco) throws Exception {
        int porta = 50023;
        InetAddress enderecoMulticast;
        enderecoMulticast = InetAddress.getByName(endereco);
        this.soket = new MulticastSocket(porta);
        this.soket.joinGroup(enderecoMulticast);
        ReceberMensagem receberMensagem = new ReceberMensagem(soket);
        receberMensagem.start();
    }

    /**
     * @return the soket
     */
    public MulticastSocket getSoket() {
        return soket;
    }

    /**
     * @param soket the soket to set
     */
    public void setSoket(MulticastSocket soket) {
        this.soket = soket;
    }
}
