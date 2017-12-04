/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salabatepapo.controller;

import certificados.controller.GerenciadorCertificados;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.PrivateKey;
import rmi.configs.RMIHelper;
import rmi.entities.Certificado;
import rmi.interfaces.ITranfereciaDeChaves;
import salabatepapo.criptografia.AlgoritimoRSA;
import salabatepapo.utils.ChaveSimetrica;

/**
 *
 * @author Vitor
 */
public class ConexaoServidorDeChaves {
    
private AlgoritimoRSA criptografiaAssimetrica;
    /*
    private DatagramSocket soket;
    private DatagramPacket pacote;
    private final int porta;

    public ConexaoServidorDeChaves(int porta) {
        this.porta = porta;
    }
    */
    public void requestCheveSimetrica() {
        this.criptografiaAssimetrica = new AlgoritimoRSA();
        /*
        if (this.criptografiaAssimetrica.verificaSeExisteChavesNoSO() == false) {
            this.criptografiaAssimetrica.gerarChaves();
        }
        
        try {
            ObjectInputStream ois = null;
            ois = new ObjectInputStream(new FileInputStream(this.criptografiaAssimetrica.getPathChavePublica()));
            PublicKey chavePublica = (PublicKey) ois.readObject();
            
            
            PacoteCliente pacoteCliente = new PacoteCliente(InetAddress.getLocalHost(), chavePublica, this.porta);
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
            this.soket.close();*/
        try{
            System.out.println("Conectando com servidor de chaves...");
            Registry registro = LocateRegistry.getRegistry(RMIHelper.RMI_SERVER_ADDRESS, RMIHelper.TRANSFERENCIA_DE_CHAVES_PORTA);
            ITranfereciaDeChaves transferenciaDeChaves = (ITranfereciaDeChaves) registro.lookup(RMIHelper.TRANSFERENCIA_DE_CHAVES_NAME);
            Certificado certificado = GerenciadorCertificados.getCertificado(RMIHelper.CERTIFICADOS_USUARIO_PATH + "vitor.crt");
            byte[] chaveSimetricaCriptografada = transferenciaDeChaves.getChaveSimetricaAtravesDeCertificado(certificado);
            if(chaveSimetricaCriptografada != null){
                ChaveSimetrica.CHAVE_SIMERICA = (String) this.criptografiaAssimetrica.descriptografar(chaveSimetricaCriptografada, getPrivateKey(RMIHelper.CERTIFICADOS_USUARIO_PATH + "vitor.key"));
            }else{
                throw new Exception("Não foi possível obeter a cheve simétrica (Retorno nulo)");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private PrivateKey getPrivateKey(String path) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fin = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fin);
        PrivateKey privateKey = (PrivateKey) ois.readObject();
        return privateKey;
    }

}
