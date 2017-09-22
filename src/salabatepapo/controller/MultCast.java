/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salabatepapo.controller;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import salabatepapo.model.Usuario;

/**
 *
 * @author aluno
 */
public class MultCast {

    public void run(String endereco, Usuario usuario) throws Exception {
        int porta = 50023;
        InetAddress enderecoMulticast;
        MulticastSocket soket;
        byte[] dados = new byte[1024];
        DatagramPacket datagrama;

        enderecoMulticast = InetAddress.getByName(endereco);
        soket = new MulticastSocket(porta);
        soket.joinGroup(enderecoMulticast);
    }
}
