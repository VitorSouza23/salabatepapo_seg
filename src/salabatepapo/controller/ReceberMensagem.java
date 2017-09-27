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
import salabatepapo.interfaces.ICriptografia;
import salabatepapo.view.FrameMensagens;

/**
 *
 * @author aluno
 */
public class ReceberMensagem extends Thread {

    private final MulticastSocket socket;
    private final ICriptografia cropitografia;

    public ReceberMensagem(MulticastSocket socket, ICriptografia cropitografia) {
        this.socket = socket;
        this.cropitografia = cropitografia;
    }
    
    

    @Override
    public void run() {
        byte[] buffer = new byte[1024];

        try {
            while (true) {
                System.out.println("[IFSC Messenger] Recebendo mensagem");
                DatagramPacket msgIn = new DatagramPacket(buffer, buffer.length);
                socket.receive(msgIn);
                byte[] mensagemCriptografada = msgIn.getData();
                
                int tamanhoDaMensagem = 0;
                for(int x = 0; x < mensagemCriptografada.length; x++){
                    if(mensagemCriptografada[x] == 0){
                        break;
                    }
                    tamanhoDaMensagem++;
                }
                
                byte[] mensagemCriptografadaSemPaddin = new byte[tamanhoDaMensagem];
                for(int x = 0; x < mensagemCriptografadaSemPaddin.length; x++){
                    mensagemCriptografadaSemPaddin[x] = mensagemCriptografada[x];
                }
                //Dando problema aqui
                String mensagemConvertida  = (String) cropitografia.descriptografar(mensagemCriptografadaSemPaddin, "abacaxi123456789");
                System.out.println("[IFSC Messenger]" + mensagemConvertida + "\n");
                Mensagem.MENSAGEM += mensagemConvertida;
                FrameMensagens.taMensagens.setText(Mensagem.MENSAGEM);
//                byte[] mensagem = (byte[]) aes.descriptografar(mensagemCriptografada, "issoEUmaCheveAES");
//                System.out.println(mensagem);
            }

        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            Logger.getLogger(ReceberMensagem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
