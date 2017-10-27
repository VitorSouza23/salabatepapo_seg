/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salabatepapo.controller;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.security.PrivateKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import salabatepapo.criptografia.AlgoritimoRSA;
import salabatepapo.utils.ChaveSimetrica;

/**
 *
 * @author aluno
 */
public class ThreadGetChaveSimetrica extends Thread {

    private final AlgoritimoRSA criptografiaAssimetrica;
    private final DatagramSocket soket;
    private DatagramPacket pacote;

    public ThreadGetChaveSimetrica(AlgoritimoRSA criptografiaAssimetrica, DatagramSocket soket) {
        this.criptografiaAssimetrica = criptografiaAssimetrica;
        this.soket = soket;
    }

    @Override
    public void run() {

        synchronized (this) {
            ObjectInputStream ois = null;
            PrivateKey chavePrivada;
            try {
                ois = new ObjectInputStream(new FileInputStream(this.criptografiaAssimetrica.getPathChavePrivada()));
                chavePrivada = (PrivateKey) ois.readObject();
                byte[] dados = new byte[1024];
                this.pacote = new DatagramPacket(dados, dados.length);
                this.soket.receive(pacote);
                dados = pacote.getData();
                ByteArrayInputStream bais = new ByteArrayInputStream(dados);
                ois = new ObjectInputStream(bais);
                String chaveSimetrica = (String) this.criptografiaAssimetrica.descriptografar((byte[]) ois.readObject(), chavePrivada);
                ChaveSimetrica.CHAVE_SIMERICA = new String(chaveSimetrica);
                System.out.println("Está é chave simétrica: " + ChaveSimetrica.CHAVE_SIMERICA);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ThreadGetChaveSimetrica.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ThreadGetChaveSimetrica.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThreadGetChaveSimetrica.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ThreadGetChaveSimetrica.class.getName()).log(Level.SEVERE, null, ex);
            }
            notify();
        }

    }
}
