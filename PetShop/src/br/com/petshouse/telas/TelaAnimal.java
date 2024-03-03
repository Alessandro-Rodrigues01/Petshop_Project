/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petshouse.telas;

import javax.swing.*;
import java.sql.*;
import br.com.petshouse.dal.ModConexao;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Alessandro
 */
public class TelaAnimal extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    
    public TelaAnimal() {
        initComponents();
        conexao = ModConexao.conector();
    }
    //metodo pesqusar cliente e animais,utlilando o comando iner join
    private void pesquisar_cliente_animal(){
     String sql = "select C.id_clien,nome,telefone, A.ID_Ani,raca,Especie,nome_ani,genero from  animal A inner join Cliente as C on (A.Id_Clien = C.id_Clien) where nome like?";  
        try {
         pst = conexao.prepareStatement(sql);
         pst.setString(1, txtPesqCli.getText()+ "%");
         rs = pst.executeQuery();
         tblAniCli.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    // metodo setar campos da tabela cliente/animal 'uniao'
    public void setar_campos_animal_cliente() {
        int setar = tblAniCli.getSelectedRow();
        txtPesqCliId.setText(tblAniCli.getModel().getValueAt(setar, 0).toString());
    }
    
    //metodo para pesquisar registros dentro da tabela
    private void consultar() {
        String sql = "select * from animal where id_ani=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtAniId.getText());
          //  pst.setString(1,txtUsuNome.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                //txtAniId.setText(rs.getString(1));
                txtAniEspe.setText(rs.getString(3));
                txtAniNomeAni.setText(rs.getString(4));
                txtAniRaca.setText(rs.getString(5));
                txtAniGen.setText(rs.getString(6));
                txtAniPorte.setText(rs.getString(7));
              
            } else {
                JOptionPane.showMessageDialog(null, "Animal não cadastrado");
                //as linhas abaixo limpa os campos
                txtAniEspe.setText(null);
                txtAniNomeAni.setText(null);
                txtAniRaca.setText(null);
                txtAniGen.setText(null);
                txtAniPorte.setText(null);
               

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //metodo para insersão
      private void inserir() {
        String sql = "INSERT INTO animal (Id_ani, Especie, Nome_ani, raca, genero, porte,Id_clien) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtAniId.getText());
            pst.setString(2, txtAniEspe.getText());
            pst.setString(3, txtAniNomeAni.getText());
            pst.setString(4, txtAniRaca.getText());
            pst.setString(5, txtAniGen.getText());
            pst.setString(6, txtAniPorte.getText());
            pst.setString(7, txtIdClient.getText());

            // validação dos campos obrigatorios
            if ((txtAniId.getText().isEmpty() || (txtAniEspe.getText().isEmpty() || (txtAniRaca.getText().isEmpty() || (txtAniGen.getText().isEmpty() || (txtAniPorte.getText().isEmpty() || (txtIdClient.getText().isEmpty()))))))) {
                JOptionPane.showMessageDialog(null, "Preencha todo os campos obrigatorios");
            } else {

                //a linha baixo atualiza no bd os dados inseridos e
                // a linha abaixo executa a mensagem de "usuario adcionado com sucesso e limpa os campos"
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Animal cadastrado com sucesso");
                    txtAniId.setText(null);
                    txtAniEspe.setText(null);
                    txtAniNomeAni.setText(null);
                    txtAniRaca.setText(null);
                    txtAniGen.setText(null);
                    txtAniPorte.setText(null);
                    txtIdClient.setText(null);

                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel cadastrar");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
      //metodo para pesquisar tabela cliente
      private void pesquisar_cliente(){
     String sql = "select id_clien as id,nome,telefone,endereco from cliente where nome like?";  
        try {
         pst = conexao.prepareStatement(sql);
         pst.setString(1, txtClientPesq.getText()+ "%");
         rs = pst.executeQuery();
         tblClient.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    // metodo setar campos
    public void setar_tblCliente() {
        int setar = tblClient.getSelectedRow();
        txtIdClient.setText(tblClient.getModel().getValueAt(setar, 0).toString());
    }
    
        // metodo para altarar a tabela
    private void alterar() {
        String sql = "update animal set Especie=?, Nome_ani=?, raca=?, genero=?,porte=?,id_clien=? where id_ani=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtAniEspe.getText());
            pst.setString(2, txtAniNomeAni.getText());
            pst.setString(3, txtAniRaca.getText());
            pst.setString(4, txtAniGen.getText());
            pst.setString(5, txtAniPorte.getText());
            pst.setString(6, txtIdClient.getText());
            pst.setString(7, txtAniId.getText());

            // validação dos campos obrigatorios
            if ((txtAniId.getText().isEmpty() || (txtAniEspe.getText().isEmpty() || (txtAniRaca.getText().isEmpty() || (txtAniGen.getText().isEmpty() || (txtIdClient.getText().isEmpty () || (txtAniPorte.getText().isEmpty()))))))) {
                JOptionPane.showMessageDialog(null, "Prencha todo os campos obrigatorios");
            } else {

                //a linha baixo atualiza no bd os dados inseridos e
                // a linha abaixo executa a mensagem de "usuario adcionado com sucesso e limpa os campos"
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Animal alterado com sucesso");
                    txtAniId.setText(null);
                    txtAniEspe.setText(null);
                    txtAniNomeAni.setText(null);
                    txtAniRaca.setText(null);
                    txtAniGen.setText(null);
                    txtAniPorte.setText(null);
                    txtIdClient.setText(null);

                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel alterar");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
      
     // metod para exclusao de dados da tabela animal
    private void excluir() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Atencão!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from animal where id_ani=?";

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtAniId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Animal excluido com sucesso!");
                    txtAniId.setText(null);
                    txtAniEspe.setText(null);
                    txtAniNomeAni.setText(null);
                    txtAniRaca.setText(null);
                    txtAniGen.setText(null);
                    txtAniPorte.setText(null);
                    txtIdClient.setText(null);
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

        txtAniId = new javax.swing.JTextField();
        btnAniAtual = new javax.swing.JButton();
        txtAniEspe = new javax.swing.JTextField();
        btnAniExcluir = new javax.swing.JButton();
        txtAniGen = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtAniRaca = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        txtPesqCli = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPesqCliId = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAniCli = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClient = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtClientPesq = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtIdClient = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtAniPorte = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnAniPesq = new javax.swing.JButton();
        btnAniInserir = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtAniNomeAni = new javax.swing.JTextField();

        setBackground(new java.awt.Color(204, 204, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Animal");
        getContentPane().setLayout(null);

        txtAniId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtAniId);
        txtAniId.setBounds(90, 51, 85, 30);

        btnAniAtual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_refresh_reload_update_2_2571204.png"))); // NOI18N
        btnAniAtual.setToolTipText("Atualizar");
        btnAniAtual.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAniAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAniAtualActionPerformed(evt);
            }
        });
        getContentPane().add(btnAniAtual);
        btnAniAtual.setBounds(260, 380, 81, 57);

        txtAniEspe.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtAniEspe);
        txtAniEspe.setBounds(90, 91, 144, 30);

        btnAniExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_1-04_511562.png"))); // NOI18N
        btnAniExcluir.setToolTipText("Excluir");
        btnAniExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAniExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAniExcluirActionPerformed(evt);
            }
        });
        getContentPane().add(btnAniExcluir);
        btnAniExcluir.setBounds(380, 380, 97, 55);
        btnAniExcluir.getAccessibleContext().setAccessibleDescription("Excluir");

        txtAniGen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtAniGen);
        txtAniGen.setBounds(90, 181, 142, 30);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Id* :");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(50, 60, 52, 15);

        txtAniRaca.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtAniRaca);
        txtAniRaca.setBounds(90, 221, 142, 30);

        jPanel1.setBackground(new java.awt.Color(204, 255, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        txtPesqCli.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPesqCli.setToolTipText("Digite o nome");
        txtPesqCli.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtPesqCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesqCliKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_sale_lineal_color_cnvrt-18_3827704.png"))); // NOI18N

        txtPesqCliId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Id* :");

        tblAniCli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Título 1", "Título 2", "Título 3", "Título 4", "Título 5"
            }
        ));
        tblAniCli.setToolTipText("Click na linha pesquisada.");
        tblAniCli.setGridColor(new java.awt.Color(153, 0, 102));
        tblAniCli.setSelectionBackground(new java.awt.Color(255, 102, 102));
        tblAniCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAniCliMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAniCli);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Cliente/Animais");

        tblClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblClient.setToolTipText("Click na linha pesquisada.");
        tblClient.setGridColor(new java.awt.Color(153, 0, 102));
        tblClient.setSelectionBackground(new java.awt.Color(255, 102, 102));
        tblClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblClient);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Clientes:");

        txtClientPesq.setToolTipText("Digite o nome");
        txtClientPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtClientPesqKeyReleased(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_sale_lineal_color_cnvrt-18_3827704.png"))); // NOI18N

        jLabel13.setText("Id:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel14.setText("Click na linha da tabela!!");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel15.setText("Click na linha da tabela!!");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPesqCli, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPesqCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtClientPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIdClient, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPesqCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(txtPesqCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)))
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtClientPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(txtIdClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3)
                .addComponent(jLabel14)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(250, 20, 580, 350);

        txtAniPorte.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtAniPorte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAniPorteActionPerformed(evt);
            }
        });
        getContentPane().add(txtAniPorte);
        txtAniPorte.setBounds(90, 261, 145, 30);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Especie* :");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 100, 60, 15);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Gênero* :");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(28, 190, 60, 15);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Raça* :");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(40, 230, 40, 15);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("(*) Campos Obrigatórios");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(35, 11, 200, 30);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Porte* :");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(30, 270, 45, 15);

        btnAniPesq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_sale_lineal_color_cnvrt-18_3827704.png"))); // NOI18N
        btnAniPesq.setToolTipText("Pesquisar");
        btnAniPesq.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAniPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAniPesqActionPerformed(evt);
            }
        });
        getContentPane().add(btnAniPesq);
        btnAniPesq.setBounds(30, 380, 65, 57);

        btnAniInserir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_insert-object_23421.png"))); // NOI18N
        btnAniInserir.setToolTipText("Inserir");
        btnAniInserir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAniInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAniInserirActionPerformed(evt);
            }
        });
        getContentPane().add(btnAniInserir);
        btnAniInserir.setBounds(130, 380, 81, 57);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Nome/Ani:");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(20, 150, 70, 15);

        txtAniNomeAni.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtAniNomeAni);
        txtAniNomeAni.setBounds(90, 140, 140, 30);

        setBounds(0, 0, 867, 474);
    }// </editor-fold>//GEN-END:initComponents

    private void txtAniPorteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAniPorteActionPerformed
        //evento nulo
    }//GEN-LAST:event_txtAniPorteActionPerformed

    private void txtPesqCliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesqCliKeyReleased
        // chama o metodo pesquisar_cliente_animal()
        pesquisar_cliente_animal();
    }//GEN-LAST:event_txtPesqCliKeyReleased

    private void tblAniCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAniCliMouseClicked
        //quando clicar no mause vai setar a tabela cliente
        setar_campos_animal_cliente();
    }//GEN-LAST:event_tblAniCliMouseClicked

    private void btnAniPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAniPesqActionPerformed
        // chama o metodo consultar
        consultar();
    }//GEN-LAST:event_btnAniPesqActionPerformed

    private void btnAniInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAniInserirActionPerformed
       //chama o metodo inserir
       inserir();
    }//GEN-LAST:event_btnAniInserirActionPerformed

    private void txtClientPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientPesqKeyReleased
        // chama o metodo pesquisa_cliente
        pesquisar_cliente();
    }//GEN-LAST:event_txtClientPesqKeyReleased

    private void tblClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientMouseClicked
        // chama o metodo setar_tblCliente() quando a tabela for clicada
        setar_tblCliente();
    }//GEN-LAST:event_tblClientMouseClicked

    private void btnAniAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAniAtualActionPerformed
        // chama o metodo alterar
        alterar();
    }//GEN-LAST:event_btnAniAtualActionPerformed

    private void btnAniExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAniExcluirActionPerformed
        // chama o metodo excluir
        excluir();
    }//GEN-LAST:event_btnAniExcluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAniAtual;
    private javax.swing.JButton btnAniExcluir;
    private javax.swing.JButton btnAniInserir;
    private javax.swing.JButton btnAniPesq;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblAniCli;
    private javax.swing.JTable tblClient;
    private javax.swing.JTextField txtAniEspe;
    private javax.swing.JTextField txtAniGen;
    private javax.swing.JTextField txtAniId;
    private javax.swing.JTextField txtAniNomeAni;
    private javax.swing.JTextField txtAniPorte;
    private javax.swing.JTextField txtAniRaca;
    private javax.swing.JTextField txtClientPesq;
    private javax.swing.JTextField txtIdClient;
    private javax.swing.JTextField txtPesqCli;
    private javax.swing.JTextField txtPesqCliId;
    // End of variables declaration//GEN-END:variables
}
