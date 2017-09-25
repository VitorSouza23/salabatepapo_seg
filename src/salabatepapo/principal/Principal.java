/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salabatepapo.principal;

import javax.swing.JOptionPane;
import salabatepapo.model.Usuario;
import salabatepapo.view.FrameMensagens;

/**
 *
 * @author aluno
 */
public class Principal {
    public static void main(String[] args) {
        String endereco;
        endereco = JOptionPane.showInputDialog(null, "Entre com o endereço Multicast:", "Endereço Multicast", JOptionPane.INFORMATION_MESSAGE);
        String nome = JOptionPane.showInputDialog(null, "Entre com o Nome de usuário:", "Nome de Usuário", JOptionPane.INFORMATION_MESSAGE);
        Usuario user = new Usuario(nome);
        FrameMensagens fm = new FrameMensagens(user, endereco);
        fm.setVisible(true);
        
    }
}
