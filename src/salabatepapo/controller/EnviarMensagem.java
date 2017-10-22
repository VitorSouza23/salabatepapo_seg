/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salabatepapo.controller;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import salabatepapo.interfaces.ICriptografia;
import salabatepapo.model.Usuario;
import salabatepapo.utils.ChaveSimetrica;

/**
 *
 * @author aluno
 */
public class EnviarMensagem {

    private final MulticastSocket socket;
    private final int porta;
    private final InetAddress enderecoGrupo;
    private final ICriptografia criptografia;

    public EnviarMensagem(MulticastSocket socket, int porta, InetAddress enderecoGrupo, ICriptografia criptografia) {
        this.socket = socket;
        this.porta = porta;
        this.enderecoGrupo = enderecoGrupo;
        this.criptografia = criptografia;
    }

    

    public void enviarMensagemCriptografada(Usuario usuario, String mensagem) throws Exception {
        System.out.println("[IFSC Messenger] Enviando mensagem");
        String mensagemFormatada = "<" + usuario.getNome() + ">: " + mensagem;
        byte[] buffer = (byte[]) criptografia.criptografar(mensagemFormatada, ChaveSimetrica.CHAVE_SIMERICA);
        //byte[] buffer = (byte[]) aes.criptografar(mensagemFormatada, "issoEUmaCheveAES");
        DatagramPacket msgOut = new DatagramPacket(buffer, buffer.length, this.enderecoGrupo, this.porta);
        socket.send(msgOut);
        System.out.println("[IFSC Messenger] Mensagem enviada");
    }
}
