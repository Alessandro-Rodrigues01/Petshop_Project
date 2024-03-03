/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petshouse.telas;

/**
 *
 * @author Alessandro
 */
import java.sql.*;
import javax.swing.*;
import br.com.petshouse.dal.ModConexao;
import net.proteanit.sql.DbUtils;

public class TelaProduto extends javax.swing.JInternalFrame {
    //usando a variavel conexão do dal
    //PreparedStatement e ResultSet sao framewoks do pacote java.sql
    //e seve para preparar e executar as intruções sql

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaProduto() {   //metodo chama a conexao com bd
        initComponents();
        conexao = ModConexao.conector();
    }

    // metodo para pesquisar Produtos
    private void pesquisar_produto() {
        String sql = "select * from produtos where tipo like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteudo da caixa de pesquisa para o ?
            //atencão ao "%" continuação da string sql
            pst.setString(1, txtProdPesq.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblProd.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
//metodo para setar campos da tabela Produtos

    public void setar_campos() {
        int setar = tblProd.getSelectedRow();
        txtProdId.setText(tblProd.getModel().getValueAt(setar, 0).toString());
        txtProdTipo.setText(tblProd.getModel().getValueAt(setar, 1).toString());
        txtProdNome.setText(tblProd.getModel().getValueAt(setar, 2).toString());
        txtProdValor.setText(tblProd.getModel().getValueAt(setar, 3).toString());
        txtProdValid.setText(tblProd.getModel().getValueAt(setar, 4).toString());
        txtProdQuant.setText(tblProd.getModel().getValueAt(setar, 5).toString());
        txtProdIdPesq.setText(tblProd.getModel().getValueAt(setar, 0).toString());
    }

    //metodo para adicionar usuarios
    private void inserir() {
        String sql = "INSERT INTO Produtos (Id_Prod, tipo, nome_prod, valor, validade, quantidade) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtProdId.getText());
            pst.setString(2, txtProdTipo.getText());
            pst.setString(3, txtProdNome.getText());
            pst.setString(4, txtProdValor.getText());
            pst.setString(5, txtProdValid.getText());
            pst.setString(6, txtProdQuant.getText());
            // validação dos campos obrigatorios
            if (txtProdId.getText().isEmpty() || (txtProdTipo.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Prencha todo os campos obrigatorios");
            } else {

                //a linha baixo atualiza no bd os dados inseridos e
                // a linha abaixo executa a mensagem de "usuario adcionado com sucesso e limpa os campos"
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
                    // limpa campo
                    txtProdId.setText(null);
                    txtProdTipo.setText(null);
                    txtProdNome.setText(null);
                    txtProdValor.setText(null);
                    txtProdValid.setText(null);
                    txtProdQuant.setText(null);

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
        String sql = "update produtos set tipo=?, nome=?, valor=?, validade=?, quantidade=? where id_prod=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtProdTipo.getText());
            pst.setString(2, txtProdNome.getText());
            pst.setString(3, txtProdValor.getText());
            pst.setString(4, txtProdValid.getText());
            pst.setString(5, txtProdQuant.getText());
            pst.setString(6, txtProdId.getText());

            // validação dos campos obrigatorios
            if ((txtProdTipo.getText().isEmpty() || (txtProdNome.getText().isEmpty() || (txtProdValor.getText().isEmpty() || (txtProdValid.getText().isEmpty() || (txtProdQuant.getText().isEmpty() || (txtProdId.getText().isEmpty()))))))) {
                JOptionPane.showMessageDialog(null, "Preencha todo os campos obrigatorios");
            } else {

                //a linha baixo atualiza no bd os dados inseridos e
                // a linha abaixo executa a mensagem de "usuario adcionado com sucesso e limpa os campos"
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Produto alterado com sucesso");
                    txtProdId.setText(null);
                    txtProdTipo.setText(null);
                    txtProdNome.setText(null);
                    txtProdValor.setText(null);
                    txtProdValid.setText(null);
                    txtProdQuant.setText(null);

                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel alterar");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metodo limpa campos
    public void limpa_campos() {
        txtProdId.setText(null);
        txtProdTipo.setText(null);
        txtProdNome.setText(null);
        txtProdValor.setText(null);
        txtProdValid.setText(null);
        txtProdQuant.setText(null);
    }
        // metod para exclusao de dados da tabela Produtos
    private void excluir() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Atencão!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from produtos where id_prod=?";

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtProdId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
                    txtProdId.setText(null);
                    txtProdTipo.setText(null);
                    txtProdNome.setText(null);
                    txtProdValor.setText(null);
                    txtProdValid.setText(null);
                    txtProdQuant.setText(null);
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

        jLabel1 = new javax.swing.JLabel();
        btnProdAtual = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnProdExclu = new javax.swing.JButton();
        txtProdIdPesq = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProd = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtProdId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtProdNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtProdValor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtProdValid = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtProdQuant = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtProdTipo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtProdPesq = new javax.swing.JTextField();
        btnProdInser = new javax.swing.JButton();
        btnProdLimp = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Produto");
        setName(""); // NOI18N
        setRequestFocusEnabled(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_sale_lineal_color_cnvrt-18_3827704.png"))); // NOI18N

        btnProdAtual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_refresh_reload_update_2_2571204.png"))); // NOI18N
        btnProdAtual.setToolTipText("Atualizar");
        btnProdAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdAtualActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Id* :");

        btnProdExclu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_1-04_511562.png"))); // NOI18N
        btnProdExclu.setToolTipText("Excluir");
        btnProdExclu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdExcluActionPerformed(evt);
            }
        });

        txtProdIdPesq.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        tblProd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Típo", "Nome", "Valor", "Validade", "Quantidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProd);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Id* :");

        txtProdId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Tipo* :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Nome :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Valor* :");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Validade* :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Quantidade* :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("(*) Campos Obrigatórios");

        txtProdPesq.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtProdPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProdPesqKeyReleased(evt);
            }
        });

        btnProdInser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_insert-object_23421.png"))); // NOI18N
        btnProdInser.setToolTipText("Inserir");
        btnProdInser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdInserActionPerformed(evt);
            }
        });

        btnProdLimp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/vasoura.png.png"))); // NOI18N
        btnProdLimp.setToolTipText("Limpa campo");
        btnProdLimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdLimpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btnProdInser)
                        .addGap(22, 22, 22)
                        .addComponent(btnProdAtual)
                        .addGap(35, 35, 35)
                        .addComponent(btnProdLimp, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(btnProdExclu, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(txtProdPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtProdIdPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtProdValid, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProdQuant, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel2)))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtProdValor, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProdNome, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProdTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProdId, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtProdPesq))
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtProdIdPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtProdId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtProdTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtProdNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(txtProdValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtProdValid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtProdQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnProdLimp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnProdExclu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnProdInser)
                            .addComponent(btnProdAtual))))
                .addGap(48, 48, 48))
        );

        setBounds(0, 0, 689, 574);
    }// </editor-fold>//GEN-END:initComponents

    private void btnProdInserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdInserActionPerformed
        // chama o metodo inserir
        inserir();
    }//GEN-LAST:event_btnProdInserActionPerformed

    private void tblProdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdMouseClicked
        // chama o metodo setar_campos() quando clicar na tabela
        setar_campos();
    }//GEN-LAST:event_tblProdMouseClicked

    private void btnProdAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdAtualActionPerformed
        // chama o metodo alterar
        alterar();
    }//GEN-LAST:event_btnProdAtualActionPerformed

    private void txtProdPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProdPesqKeyReleased
        // chama o metodo pesquisar_produto() que pesquisa pelo nome
        pesquisar_produto();
    }//GEN-LAST:event_txtProdPesqKeyReleased

    private void btnProdLimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdLimpActionPerformed
        // chama o metodo limpa_campos()
        limpa_campos();
    }//GEN-LAST:event_btnProdLimpActionPerformed

    private void btnProdExcluActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdExcluActionPerformed
        // chama ometodo excluir
        excluir();
    }//GEN-LAST:event_btnProdExcluActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProdAtual;
    private javax.swing.JButton btnProdExclu;
    private javax.swing.JButton btnProdInser;
    private javax.swing.JButton btnProdLimp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProd;
    private javax.swing.JTextField txtProdId;
    private javax.swing.JTextField txtProdIdPesq;
    private javax.swing.JTextField txtProdNome;
    private javax.swing.JTextField txtProdPesq;
    private javax.swing.JTextField txtProdQuant;
    private javax.swing.JTextField txtProdTipo;
    private javax.swing.JTextField txtProdValid;
    private javax.swing.JTextField txtProdValor;
    // End of variables declaration//GEN-END:variables
}
