/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petshouse.telas;

import java.sql.*;
import br.com.petshouse.dal.ModConexao;
import javax.swing.*;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author alessandro
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCliente() {
        initComponents();
        conexao = ModConexao.conector();
    }

    private void inserir() {
        String sql = "INSERT INTO cliente (Id_clien, nome, endereco, telefone, cpf) VALUES (?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliId.getText());
            pst.setString(2, txtCliNome.getText());
            pst.setString(3, txtCliEnder.getText());
            pst.setString(4, txtCliTel.getText());
            pst.setString(5, txtCliCpf.getText());

            // validação dos campos obrigatorios
            if ((txtCliId.getText().isEmpty() || (txtCliNome.getText().isEmpty() || (txtCliEnder.getText().isEmpty() || (txtCliTel.getText().isEmpty() || (txtCliCpf.getText().isEmpty())))))) {
                JOptionPane.showMessageDialog(null, "Prencha todo os campos obrigatorios");
            } else {

                //a linha baixo atualiza no bd os dados inseridos e
                // a linha abaixo executa a mensagem de "usuario adcionado com sucesso e limpa os campos"
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso");
                    txtCliId.setText(null); //limpa os campos
                    txtCliNome.setText(null);
                    txtCliEnder.setText(null);
                    txtCliTel.setText(null);
                    txtCliCpf.setText(null);

                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel cadastrar");
                }
            }
        } catch(com.mysql.jdbc.MysqlDataTruncation e){
            JOptionPane.showMessageDialog(null, "CPF não pode ter mais que 11 digitos" );
        
        }catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e2) {
            JOptionPane.showMessageDialog(null,"Ja existe um id com este numero");
        }catch(Exception e3){
            JOptionPane.showMessageDialog(null, e3);
        }
        
    }
    
        // metodo para altarar a tabela
    private void alterar() {
        String sql = "update cliente set nome=?, endereco=?, telefone=?, cpf=? where id_clien=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliEnder.getText());
            pst.setString(3, txtCliTel.getText());
            pst.setString(4, txtCliCpf.getText());
            pst.setString(5, txtCliId.getText());
           

            // validação dos campos obrigatorios
            if ((txtCliId.getText().isEmpty() || (txtCliNome.getText().isEmpty() || (txtCliEnder.getText().isEmpty() || (txtCliTel.getText().isEmpty() || (txtCliCpf.getText().isEmpty())))))) {
                JOptionPane.showMessageDialog(null, "Prencha todo os campos obrigatorios");
            } else {

                //a linha baixo atualiza no bd os dados inseridos e
                // a linha abaixo executa a mensagem de "usuario adcionado com sucesso e limpa os campos"
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso");
                    txtCliId.setText(null);
                    txtCliNome.setText(null);
                    txtCliEnder.setText(null);
                    txtCliTel.setText(null);
                    txtCliCpf.setText(null);

                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel alterar");
                }
            }
        }catch(com.mysql.jdbc.MysqlDataTruncation e){
            JOptionPane.showMessageDialog(null, "CPF não pode ter mais que 11 digitos" ); 
        
    }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    // metodo para pesquisar cliente

    private void pesquisar_cliente() {
        String sql = "select * from cliente where nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteudo da caixa de pesquisa para o ?
            //atencão ao "%" continuação da string sql
            pst.setString(1, txtCliPesq.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblCli.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
//metodo para setar campos da tabela cliente

    public void setar_campos() {
        int setar = tblCli.getSelectedRow();
        txtCliId.setText(tblCli.getModel().getValueAt(setar, 0).toString());
        txtCliNome.setText(tblCli.getModel().getValueAt(setar, 1).toString());
        txtCliEnder.setText(tblCli.getModel().getValueAt(setar, 2).toString());
        txtCliTel.setText(tblCli.getModel().getValueAt(setar, 3).toString());
        txtCliCpf.setText(tblCli.getModel().getValueAt(setar, 4).toString());
        txtCliIdPesq.setText(tblCli.getModel().getValueAt(setar, 0).toString());
    }

    //metodo para limpar campos
    public void limpa_campos() {
        txtCliId.setText(null);
        txtCliNome.setText(null);
        txtCliEnder.setText(null);
        txtCliTel.setText(null);
        txtCliCpf.setText(null);
    }
    
     // metod para exclusao de dados da tabela cliente
    private void excluir() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Atencão!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from cliente where id_clien=?";

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCliId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso!");
                    txtCliId.setText(null);
                    txtCliNome.setText(null);
                    txtCliEnder.setText(null);
                    txtCliTel.setText(null);
                    txtCliCpf.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtCliId = new javax.swing.JTextField();
        txtCliEnder = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCliTel = new javax.swing.JTextField();
        txtCliCpf = new javax.swing.JTextField();
        btnCliInser = new javax.swing.JButton();
        txtCliPesq = new javax.swing.JTextField();
        btnCliAtual = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnCliExclu = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCli = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCliNome = new javax.swing.JTextField();
        btnLimpa = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtCliIdPesq = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cliente");

        txtCliId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtCliEnder.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Id* :");

        txtCliTel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtCliCpf.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnCliInser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_insert-object_23421.png"))); // NOI18N
        btnCliInser.setToolTipText("Inserir");
        btnCliInser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliInserActionPerformed(evt);
            }
        });

        txtCliPesq.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCliPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesqKeyReleased(evt);
            }
        });

        btnCliAtual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_refresh_reload_update_2_2571204.png"))); // NOI18N
        btnCliAtual.setToolTipText("Atualizar");
        btnCliAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliAtualActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_sale_lineal_color_cnvrt-18_3827704.png"))); // NOI18N

        btnCliExclu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_1-04_511562.png"))); // NOI18N
        btnCliExclu.setToolTipText("Excluir");
        btnCliExclu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliExcluActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText(" (*) Campos Obrigatórios ");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Nome*:");

        tblCli.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblCli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nome", "Endereço", "Telefone", "CPF"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCliMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCli);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Endereço*:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Telefone*:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("CPF*:");

        txtCliNome.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnLimpa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLimpa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/vasoura.png.png"))); // NOI18N
        btnLimpa.setToolTipText("Limpar Campos");
        btnLimpa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpaActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("id");

        txtCliIdPesq.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCliPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCliIdPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel8))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel3)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCliEnder, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtCliCpf, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtCliTel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(btnCliInser)
                        .addGap(26, 26, 26)
                        .addComponent(btnCliAtual)
                        .addGap(28, 28, 28)
                        .addComponent(btnLimpa, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(btnCliExclu)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCliPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtCliIdPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCliEnder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCliCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnCliAtual, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnCliInser, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(btnLimpa)
                    .addComponent(btnCliExclu, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        setBounds(0, 0, 669, 536);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCliInserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliInserActionPerformed
        // chama o metodo inserir
        inserir();
    }//GEN-LAST:event_btnCliInserActionPerformed

    private void txtCliPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesqKeyReleased
        // este evento faz com que pesquise em tempo ral ao digitar
        //chamando o metodo pesquisar_cliente
        pesquisar_cliente();
    }//GEN-LAST:event_txtCliPesqKeyReleased

    private void tblCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCliMouseClicked
        // evento que sera utilizado para setar os campos, clicando com o mouse
        //chamando o metodo setar_campos
        setar_campos();
    }//GEN-LAST:event_tblCliMouseClicked

    private void btnLimpaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpaActionPerformed
        // chama o metodo limpa_campos
        limpa_campos();
    }//GEN-LAST:event_btnLimpaActionPerformed

    private void btnCliExcluActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliExcluActionPerformed
        // chama o metodo  excluir()
         excluir();
    }//GEN-LAST:event_btnCliExcluActionPerformed

    private void btnCliAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliAtualActionPerformed
        // chama o metodo alterar()
        alterar();
    }//GEN-LAST:event_btnCliAtualActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCliAtual;
    private javax.swing.JButton btnCliExclu;
    private javax.swing.JButton btnCliInser;
    private javax.swing.JButton btnLimpa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCli;
    private javax.swing.JTextField txtCliCpf;
    private javax.swing.JTextField txtCliEnder;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliIdPesq;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliPesq;
    private javax.swing.JTextField txtCliTel;
    // End of variables declaration//GEN-END:variables
}
