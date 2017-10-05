/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salabatepapo.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import salabatepapo.interfaces.ICriptografia;

/**
 *
 * @author aluno
 */
public class Multicast {

    private MulticastSocket socket;
    private InetAddress enderecoMulticast;
    private final ICriptografia criptografia;
    private ReceberMensagem receberMensagem;
    public Multicast(ICriptografia criptografia) {
        this.criptografia = criptografia;
    }

   
    
    

    public void run(String endereco) throws Exception {
        int porta = 50023;        
        enderecoMulticast = InetAddress.getByName(endereco);
        this.socket = new MulticastSocket(porta);
        this.socket.joinGroup(enderecoMulticast);
        System.out.println("Conectado ao endereço " + endereco);
        this.receberMensagem = new ReceberMensagem(socket, this.criptografia);
        receberMensagem.start();
        System.out.println("[IFSC Messenger] Iniciando recebimento de mensagens.");
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

    public InetAddress getEnderecoMulticast() {
        return enderecoMulticast;
    }
    
    public void sair() throws IOException{
        this.socket.leaveGroup(this.enderecoMulticast);
        this.receberMensagem.isInterrupted();
    }
    
    
}
