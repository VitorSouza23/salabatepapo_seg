/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salabatepapo.controller;

import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import saladebatepapo.ciptografia.AlgoritimoAES;

/**
 *
 * @author aluno
 */
public class ReceberMensagem extends Thread {

    private final MulticastSocket socket;

    public ReceberMensagem(MulticastSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        AlgoritimoAES aes = new AlgoritimoAES();
        byte[] buffer = new byte[1024];

        try {
            while (true) {
                DatagramPacket msgIn = new DatagramPacket(buffer, buffer.length);
                socket.receive(msgIn);
                String mensagemCriptografada = new String(msgIn.getData());
                //Mensagem.MENSAGEM += new String((String) aes.descriptografar(mensagemCriptografada, "issoEUmaCheveAES"));
                System.out.println(aes.descriptografar(mensagemCriptografada, "issoEUmaCheveAES"));
            }

        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            Logger.getLogger(ReceberMensagem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
