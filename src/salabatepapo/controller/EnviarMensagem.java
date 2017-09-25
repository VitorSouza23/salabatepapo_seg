/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salabatepapo.controller;

import java.net.DatagramPacket;
import java.net.MulticastSocket;
import salabatepapo.model.Usuario;
import saladebatepapo.ciptografia.AlgoritimoAES;

/**
 *
 * @author aluno
 */
public class EnviarMensagem{
    private final MulticastSocket soket;
    private final int porta;

    public EnviarMensagem(MulticastSocket soket, int porta) {
        this.soket = soket;
        this.porta = porta;
    }

    public void enviar(Usuario usuario, String mensagem) throws Exception{
        AlgoritimoAES aes = new AlgoritimoAES();
        String mensagemFormatada = "<" + usuario.getNome() + ">: " + mensagem;
        byte[] buffer = (byte[])aes.criptografar(mensagemFormatada, "issoEUmaCheveAES");
        DatagramPacket msgOut = new DatagramPacket(buffer, buffer.length, this.porta);
        soket.send(msgOut);
    }
}
