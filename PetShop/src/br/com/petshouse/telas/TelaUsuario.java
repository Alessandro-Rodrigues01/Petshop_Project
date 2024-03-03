/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petshouse.telas;

/**
 *
 * @author alessandro rodrigues
 *
 */
import java.sql.*;
import br.com.petshouse.dal.ModConexao;
import javax.swing.*;

public class TelaUsuario extends javax.swing.JInternalFrame {

    //usando a variavel conexão do dal
    //PreparedStatement e ResultSet sao framewoks do pacote java.sql
    //e seve para preparar e executar as intruções sql
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaUsuario() { //metodo para verificar conexao com bd
        initComponents();
        conexao = ModConexao.conector();
    }
//metodo para pesquisar registros dentro da tabela

    private void consultar() {
        String sql = "select * from usuarios where nome_user = ?";
        
        try {
            pst = conexao.prepareStatement(sql);
           // pst.setString(1, txtUsuId.getText());
            pst.setString(1,txtUsuNome.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUsuId.setText(rs.getString(1));
                txtUsuNome.setText(rs.getString(2));
                txtUsuTele.setText(rs.getString(3));
                txtUsuLogin.setText(rs.getString(4));
                txtUsuSenha.setText(rs.getString(5));
                // aliha abaixo se refere ao combobox onde tera uma de duas ou mais opçao para escolher
                txtComboUsuPerfil.setSelectedItem(rs.getString(6));
            } else {
                JOptionPane.showMessageDialog(null, "Usuario não cadastrado");
                //as linhas abaixo limpa os campos
                txtUsuNome.setText(null);
                txtUsuTele.setText(null);
                txtUsuLogin.setText(null);
                txtUsuSenha.setText(null);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //metodo para adicionar usuarios

    private void inserir() {
        String sql = "INSERT INTO usuarios (Id_User, nome_user, Telefone, Login, Senha, Perfil) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuId.getText());
            pst.setString(2, txtUsuNome.getText());
            pst.setString(3, txtUsuTele.getText());
            pst.setString(4, txtUsuLogin.getText());
            pst.setString(5, txtUsuSenha.getText());
            pst.setString(6, txtComboUsuPerfil.getSelectedItem().toString());
            // validação dos campos obrigatorios
            if ((txtUsuId.getText().isEmpty() || (txtUsuNome.getText().isEmpty() || (txtUsuLogin.getText().isEmpty() || (txtUsuSenha.getText().isEmpty()))))) {
                JOptionPane.showMessageDialog(null, "Prencha todo os campos obrigatorios");
            } else {

                //a linha baixo atualiza no bd os dados inseridos e
                // a linha abaixo executa a mensagem de "usuario adcionado com sucesso e limpa os campos"
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuTele.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);

                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel cadastrar");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para altarar a tabeal
    private void alterar() {
        String sql = "update usuarios set nome_user=?, telefone=?, login=?, senha=?, perfil=? where id_user=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText());
            pst.setString(2, txtUsuTele.getText());
            pst.setString(3, txtUsuLogin.getText());
            pst.setString(4, txtUsuSenha.getText());
            pst.setString(5, txtComboUsuPerfil.getSelectedItem().toString());
            pst.setString(6, txtUsuId.getText());

            // validação dos campos obrigatorios
            if ((txtUsuId.getText().isEmpty() || (txtUsuNome.getText().isEmpty() || (txtUsuLogin.getText().isEmpty() || (txtUsuSenha.getText().isEmpty()))))) {
                JOptionPane.showMessageDialog(null, "Prencha todo os campos obrigatorios");
            } else {

                //a linha baixo atualiza no bd os dados inseridos e
                // a linha abaixo executa a mensagem de "usuario adcionado com sucesso e limpa os campos"
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario alterado com sucesso");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuTele.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);

                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel alterar");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
 //metodo para limpar campos
    public void limpa_campos() {
        txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuTele.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);
                    
    }
    // metod para exclusao de dados da tabela usuario
    private void excluir() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Atencão", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from usuarios where id_user=?";

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario excluido com sucesso!");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuTele.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /*
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        btnUsuPesq = new javax.swing.JButton();
        txtUsuId = new javax.swing.JTextField();
        txtUsuNome = new javax.swing.JTextField();
        txtUsuTele = new javax.swing.JTextField();
        txtUsuLogin = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtUsuSenha = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnUsuAdd = new javax.swing.JButton();
        btnUsuAtual = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnUsuExcuir = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtComboUsuPerfil = new javax.swing.JComboBox<String>();
        bntLimpar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuarios");
        setName("desktop"); // NOI18N
        setPreferredSize(new java.awt.Dimension(710, 450));
        setRequestFocusEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Perfil:");

        btnUsuPesq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_sale_lineal_color_cnvrt-18_3827704.png"))); // NOI18N
        btnUsuPesq.setToolTipText("Pesquisar");
        btnUsuPesq.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuPesqActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Id*:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nome*:");

        btnUsuAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_insert-object_23421.png"))); // NOI18N
        btnUsuAdd.setToolTipText("Adicionar");
        btnUsuAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuAddActionPerformed(evt);
            }
        });

        btnUsuAtual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_refresh_reload_update_2_2571204.png"))); // NOI18N
        btnUsuAtual.setToolTipText("Atualizar");
        btnUsuAtual.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuAtualActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Telefone:");

        btnUsuExcuir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_1-04_511562.png"))); // NOI18N
        btnUsuExcuir.setToolTipText("excluir");
        btnUsuExcuir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuExcuir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuExcuirActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Login*:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("(*) Campos Obrigatórios");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Senha*:");

        txtComboUsuPerfil.setEditable(true);
        txtComboUsuPerfil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtComboUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "admin", "usuario" }));

        bntLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/vasoura.png.png"))); // NOI18N
        bntLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(19, 19, 19))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(btnUsuAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btnUsuAtual))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtUsuTele, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtComboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(bntLimpar)
                                            .addGap(31, 31, 31)))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(btnUsuPesq)
                        .addGap(241, 241, 241)
                        .addComponent(btnUsuExcuir, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel8)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtUsuTele, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bntLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUsuExcuir, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnUsuAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUsuPesq, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnUsuAtual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(62, 62, 62))
        );

        setBounds(0, 0, 717, 497);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUsuPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuPesqActionPerformed
        //chama o metodo consultar
        consultar();
    }//GEN-LAST:event_btnUsuPesqActionPerformed

    private void btnUsuAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuAddActionPerformed
        // chama o metodo inserir
        inserir();
    }//GEN-LAST:event_btnUsuAddActionPerformed

    private void btnUsuAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuAtualActionPerformed
        // chama o metodo alteral
        alterar();
    }//GEN-LAST:event_btnUsuAtualActionPerformed

    private void btnUsuExcuirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuExcuirActionPerformed
        // chama o metodo excluir
        excluir();
    }//GEN-LAST:event_btnUsuExcuirActionPerformed

    private void bntLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntLimparActionPerformed
        // limpa os camps
        limpa_campos();
    }//GEN-LAST:event_bntLimparActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntLimpar;
    private javax.swing.JButton btnUsuAdd;
    private javax.swing.JButton btnUsuAtual;
    private javax.swing.JButton btnUsuExcuir;
    private javax.swing.JButton btnUsuPesq;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JComboBox<String> txtComboUsuPerfil;
    private javax.swing.JTextField txtUsuId;
    private javax.swing.JTextField txtUsuLogin;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JTextField txtUsuSenha;
    private javax.swing.JTextField txtUsuTele;
    // End of variables declaration//GEN-END:variables
}
