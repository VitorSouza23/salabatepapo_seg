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

    private MulticastSocket socket;

    public void run(String endereco) throws Exception {
        int porta = 50023;
        InetAddress enderecoMulticast;
        enderecoMulticast = InetAddress.getByName(endereco);
        this.socket = new MulticastSocket(porta);
        this.socket.joinGroup(enderecoMulticast);
        ReceberMensagem receberMensagem = new ReceberMensagem(socket);
        receberMensagem.start();
    }

    /**
     * @return the soket
     */
    public MulticastSocket getSocket() {
        return socket;
    }

    /**
     * @param socket the soket to set
     */
    public void setSocket(MulticastSocket socket) {
        this.socket = socket;
    }
}
