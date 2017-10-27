/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salabatepapo.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.PrivateKey;
import java.security.PublicKey;
import salabatepapo.utils.ChaveSimetrica;
import servercommunication.PacoteCliente;
import salabatepapo.criptografia.AlgoritimoRSA;

/**
 *
 * @author Vitor
 */
public class ConexaoServidorDeChaves {

    private AlgoritimoRSA criptografiaAssimetrica;
    private DatagramSocket soket;
    private DatagramPacket pacote;
    private final int porta;

    public ConexaoServidorDeChaves(int porta) {
        this.porta = porta;
    }

    public void requestCheveSimetrica(InetAddress enderecoServidorDeNomes,int portaServidorDeNomes) {
        this.criptografiaAssimetrica = new AlgoritimoRSA();
        if (this.criptografiaAssimetrica.verificaSeExisteChavesNoSO() == false) {
            this.criptografiaAssimetrica.gerarChaves();
        }
        
        try {
            ObjectInputStream ois = null;
            ois = new ObjectInputStream(new FileInputStream(this.criptografiaAssimetrica.getPathChavePublica()));
            PublicKey chavePublica = (PublicKey) ois.readObject();
            
            
            PacoteCliente pacoteCliente = new PacoteCliente(InetAddress.getByName("10.151.34.121"), chavePublica, this.porta);
            this.soket = new DatagramSocket(this.porta);
            ThreadGetChaveSimetrica tChaveSimetrica = new ThreadGetChaveSimetrica(this.criptografiaAssimetrica, this.soket);
            tChaveSimetrica.start();
            
            byte[] dados;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(pacoteCliente);
            dados = baos.toByteArray();
            
            this.pacote = new DatagramPacket(dados, dados.length, enderecoServidorDeNomes, portaServidorDeNomes);
            this.soket.send(pacote);
            synchronized(tChaveSimetrica){
                try{
                    tChaveSimetrica.wait();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            this.soket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
