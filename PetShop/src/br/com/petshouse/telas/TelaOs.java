/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petshouse.telas;

import java.sql.*;
import br.com.petshouse.dal.ModConexao;
import java.util.HashMap;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


  //@author POSITIVO
 

public class TelaOs extends javax.swing.JInternalFrame {
Connection conexao = null;
PreparedStatement pst = null;
ResultSet rs = null;
    
    public TelaOs() {
        initComponents();
        conexao = ModConexao.conector();
        
    }
    
    // variavel para armazenar texto para o radio button selecionado
    private String tipo_pagto;
    
    
//metodo para pesquisar tabela cliente
    private void pesquisar_cliente() {
        String sql = "select id_clien as id,nome,telefone,endereco from cliente where nome like?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtOsCliPesq.getText() + "%");
            rs = pst.executeQuery();
            tblCliente.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
     // metodo setar campos
    public void setar_tblCliente() {
        int setar = tblCliente.getSelectedRow();
        txtOsIdCli.setText(tblCliente.getModel().getValueAt(setar, 0).toString());
    }
    
    
    //metodo para pesquisar tabela animal
    private void pesquisar_animal() {
        String sql = "select id_ani as id,especie,nome_ani as nome,raca,genero from animal where nome_ani like?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtOsAniPesq.getText() + "%");
            rs = pst.executeQuery();
            tblAnimal.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
     // metodo setar campos
    public void setar_tblAnimal() {
        int setar = tblAnimal.getSelectedRow();
        txtOsIdAni.setText(tblAnimal.getModel().getValueAt(setar, 0).toString());
    }
    
     //metodo para pesquisar tabela usuario
    private void pesquisar_usuario() {
        String sql = "select id_user as id,nome_user as nome from usuarios where nome_user like?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtOsUsuPesq.getText() + "%");
            rs = pst.executeQuery();
            tblUsuario.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
     // metodo setar campos
    public void setar_tblUsuario() {
        int setar = tblUsuario.getSelectedRow();
        txtOsIdUsu.setText(tblUsuario.getModel().getValueAt(setar, 0).toString());
    }
    
     //metodo para insersão
    private void inserir() {
        String sql = "INSERT INTO os (servico, profissional, nome_animal, cor_animal, valor_total, tipo_pagto, id_ani, id_user, id_clien) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtOsTipo.getText());
            pst.setString(2, txtOsProfi.getText());
            pst.setString(3, txtOsNomeAni.getText());
            pst.setString(4,txtOsCor.getText());
            pst.setString(5, txtOsValor.getText());
            pst.setString(6, tipo_pagto);
            pst.setString(7,txtOsIdAni.getText());
            pst.setString(8,txtOsIdUsu.getText());
            pst.setString(9,txtOsIdCli.getText());        
              // validação dos campos obrigatorios
            if ((txtOsTipo.getText().isEmpty() || (txtOsProfi.getText().isEmpty() || (txtOsNomeAni.getText().isEmpty() || (txtOsCor.getText().isEmpty() || (txtOsValor.getText().isEmpty() || (txtOsIdAni.getText().isEmpty() || (txtOsIdUsu.getText().isEmpty() || (txtOsIdCli.getText().isEmpty()))))))))) {
                JOptionPane.showMessageDialog(null, "Preencha todo os campos obrigatorios");
            } else {

                //a linha baixo atualiza no bd os dados inseridos e
                // a linha abaixo executa a mensagem de "ordem de serviços adcionado com sucesso e limpa os campos"
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de serviços cadastrado com sucesso");
                    txtOsId.setText(null);
                    txtOsData.setText(null);
                    txtOsTipo.setText(null);
                    txtOsProfi.setText(null);
                    txtOsNomeAni.setText(null);
                    txtOsCor.setText(null);
                    txtOsValor.setText(null);
                    txtOsIdAni.setText(null);
                    txtOsIdUsu.setText(null);
                    txtOsIdCli.setText(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel cadastrar");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
      //metodo para pesquisar registros dentro da tabela
    private void consultar() {
        // essa variavel abre uma caixa de consulta de entrada de dados
        String nOs = JOptionPane.showInputDialog("Numero Ordem de serviços");
        String sql = "select * from os where id_os= " + nOs;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtOsId.setText(rs.getString(1));
                txtOsData.setText(rs.getString(2));
                txtOsTipo.setText(rs.getString(3));
                txtOsProfi.setText(rs.getString(4));
                txtOsNomeAni.setText(rs.getString(5));
                txtOsCor.setText(rs.getString(6));
                txtOsValor.setText(rs.getString(7));
                //setando o radio butoon
                String rbtOs = rs.getString(8);
                if (rbtOs.equals("dinheiro")) {
                    radioDinhe.setSelected(true);
                    tipo_pagto = "dinheiro";

                } else {
                    radioCartao.setSelected(true);
                    tipo_pagto = "Cartão";
                }
                txtOsIdAni.setText(rs.getString(9));
                txtOsIdUsu.setText(rs.getString(10));
                txtOsIdCli.setText(rs.getString(11));

            } else {
                JOptionPane.showMessageDialog(null, "Ordem de Serviços não cadastrada");
                //as linhas abaixo limpa os campos
                txtOsId.setText(null);
                    txtOsData.setText(null);
                    txtOsTipo.setText(null);
                    txtOsProfi.setText(null);
                    txtOsNomeAni.setText(null);
                    txtOsCor.setText(null);
                    txtOsValor.setText(null);
                    txtOsIdAni.setText(null);
                    txtOsIdUsu.setText(null);
                    txtOsIdCli.setText(null);
            }
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "Dados invalidos");
            //System.out.println(e);
        } catch (Exception e2) {

            JOptionPane.showMessageDialog(null, e2);
        }
    }
    
    
    
    // metodo para altarar a tabela
    private void alterar() {
        String sql = "update os set  data_hora=?, servico=?, profissional=?,nome_animal=?,cor_animal=?,valor_total=?, tipo_pagto=?,id_ani=?,id_user=?,id_clien=? where id_os=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtOsData.getText());
            pst.setString(2, txtOsTipo.getText());
            pst.setString(3, txtOsProfi.getText());
            pst.setString(4, txtOsNomeAni.getText());
            pst.setString(5, txtOsCor.getText());
            pst.setString(6, txtOsValor.getText());
            pst.setString(7, tipo_pagto);
            pst.setString(8,txtOsIdAni.getText());
            pst.setString(9,txtOsIdUsu.getText());
            pst.setString(10,txtOsIdCli.getText());
            pst.setString(11,txtOsId.getText());

            // validação dos campos obrigatorios
            if ((txtOsTipo.getText().isEmpty() || (txtOsProfi.getText().isEmpty() || (txtOsNomeAni.getText().isEmpty() || (txtOsCor.getText().isEmpty() || (txtOsValor.getText().isEmpty() || (txtOsIdAni.getText().isEmpty() || (txtOsIdUsu.getText().isEmpty() || (txtOsIdCli.getText().isEmpty()))))))))) {
                JOptionPane.showMessageDialog(null, "Prencha todo os campos obrigatorios");
            } else {

                //a linha baixo atualiza no bd os dados inseridos e
                // a linha abaixo executa a mensagem de "usuario adcionado com sucesso e limpa os campos"
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Compras alterado com sucesso");
                    txtOsId.setText(null);
                    txtOsData.setText(null);
                    txtOsTipo.setText(null);
                    txtOsProfi.setText(null);
                    txtOsNomeAni.setText(null);
                    txtOsCor.setText(null);
                    txtOsValor.setText(null);
                    txtOsIdAni.setText(null);
                    txtOsIdUsu.setText(null);
                    txtOsIdCli.setText(null);
                    

                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel alterar");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

      // metod para exclusao de dados da tabela Faz_compra
    private void excluir() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Atencão!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from os where id_os=?";

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtOsId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de Serviços excluida com sucesso!");
                    txtOsId.setText(null);
                    txtOsData.setText(null);
                    txtOsTipo.setText(null);
                    txtOsProfi.setText(null);
                    txtOsNomeAni.setText(null);
                    txtOsCor.setText(null);
                    txtOsValor.setText(null);
                    txtOsIdAni.setText(null);
                    txtOsIdUsu.setText(null);
                    txtOsIdCli.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    //metodo imprimir os
    private void imprimir_os(){
               // gera um grelatorio de compras
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão deste relatorio?","Atenção",JOptionPane.YES_NO_OPTION);
       if (confirma == JOptionPane.YES_OPTION){
        //imprimindo o relatorio com o framework jasperReport   
           try {
               //usando a classe HashMap para criar um fltro
               HashMap filtro = new HashMap(); 
               filtro.put("os",Integer.parseInt(txtOsId.getText()));
             // usando a classe jasper Print para preparar a impressao de um relatorio
          JasperPrint print = JasperFillManager.fillReport("C:/Reports/Relatorio_os2.jasper",filtro,conexao);
         // alinha abaixo exibe o relatorio atraves da calasse JasperViewer
         JasperViewer.viewReport(print, false);
           }catch (java.lang.NumberFormatException e){
             JOptionPane.showMessageDialog(null, "Click primeiro em pesquisar");
           } 
           
           catch (Exception e2) {
               JOptionPane.showMessageDialog(null, e2);
           }
       }
    }  
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAnimal = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuario = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtOsIdCli = new javax.swing.JTextField();
        txtOsIdAni = new javax.swing.JTextField();
        txtOsIdUsu = new javax.swing.JTextField();
        txtOsUsuPesq = new javax.swing.JTextField();
        txtOsAniPesq = new javax.swing.JTextField();
        txtOsCliPesq = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        radioDinhe = new javax.swing.JRadioButton();
        radioCartao = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        btnOsPesq = new javax.swing.JButton();
        btnOsInser = new javax.swing.JButton();
        btnOsAtual = new javax.swing.JButton();
        btnOSExcluir = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnOsImpre = new javax.swing.JButton();
        txtOsData = new javax.swing.JTextField();
        txtOsTipo = new javax.swing.JTextField();
        txtOsProfi = new javax.swing.JTextField();
        txtOsNomeAni = new javax.swing.JTextField();
        txtOsCor = new javax.swing.JTextField();
        txtOsValor = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtOsId = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ordem de Serviços");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nome", "Telefone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCliente.setToolTipText("Click na linha desejada");
        tblCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClienteMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblCliente);

        tblAnimal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Tipo", "Gênero", "Raça", "Porte"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAnimal.setToolTipText("Click na linha desejada");
        tblAnimal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAnimalMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblAnimal);

        tblUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Id", "Nome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsuario.setToolTipText("Click na linha desejada");
        tblUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuario);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_sale_lineal_color_cnvrt-18_3827704.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_sale_lineal_color_cnvrt-18_3827704.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_sale_lineal_color_cnvrt-18_3827704.png"))); // NOI18N

        txtOsIdCli.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtOsIdAni.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtOsIdUsu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtOsUsuPesq.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtOsUsuPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOsUsuPesqKeyReleased(evt);
            }
        });

        txtOsAniPesq.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtOsAniPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOsAniPesqKeyReleased(evt);
            }
        });

        txtOsCliPesq.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtOsCliPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOsCliPesqKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Id*");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Id*");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Id*");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Clinte:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Animal:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Usuário:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtOsAniPesq)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtOsIdAni, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtOsCliPesq)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtOsIdCli, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtOsUsuPesq)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addGap(12, 12, 12)
                        .addComponent(txtOsIdUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtOsIdCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtOsCliPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel8))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addComponent(txtOsAniPesq, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtOsIdAni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtOsUsuPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtOsIdUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opções de Pagamento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        buttonGroup1.add(radioDinhe);
        radioDinhe.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radioDinhe.setText("Dinheiro");
        radioDinhe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioDinheActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioCartao);
        radioCartao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radioCartao.setText("Cartão");
        radioCartao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioCartaoActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("OU");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioDinhe)
                    .addComponent(radioCartao))
                .addGap(0, 131, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radioDinhe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(radioCartao)
                .addGap(17, 17, 17))
        );

        btnOsPesq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_sale_lineal_color_cnvrt-18_3827704.png"))); // NOI18N
        btnOsPesq.setToolTipText("Pesquisar");
        btnOsPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsPesqActionPerformed(evt);
            }
        });

        btnOsInser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_insert-object_23421.png"))); // NOI18N
        btnOsInser.setToolTipText("Inserir");
        btnOsInser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsInserActionPerformed(evt);
            }
        });

        btnOsAtual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_refresh_reload_update_2_2571204.png"))); // NOI18N
        btnOsAtual.setToolTipText("Atualizar");
        btnOsAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAtualActionPerformed(evt);
            }
        });

        btnOSExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_1-04_511562.png"))); // NOI18N
        btnOSExcluir.setToolTipText("Excluir");
        btnOSExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSExcluirActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("(*) Campos Obrigatórios");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Data/hora:");
        jLabel12.setToolTipText("");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Tipo/serviços* :");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Profissional* :");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Nome/animal:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Cor/animal:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Valor total* :");

        btnOsImpre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_document-print_118913.png"))); // NOI18N
        btnOsImpre.setToolTipText("Imprimir");
        btnOsImpre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsImpreActionPerformed(evt);
            }
        });

        txtOsData.setEditable(false);
        txtOsData.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtOsTipo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtOsProfi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtOsNomeAni.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtOsCor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtOsValor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("Id.N°/OS:");

        txtOsId.setEditable(false);
        txtOsId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(30, 30, 30)
                                            .addComponent(txtOsId, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtOsData, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(90, 90, 90)
                                            .addComponent(txtOsTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(10, 10, 10)
                                            .addComponent(txtOsProfi, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(10, 10, 10)
                                            .addComponent(txtOsNomeAni, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel17)
                                            .addGap(30, 30, 30)
                                            .addComponent(txtOsCor, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(10, 10, 10)
                                            .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGap(10, 10, 10)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnOsPesq)
                            .addGap(15, 15, 15)
                            .addComponent(btnOsInser, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(20, 20, 20)
                            .addComponent(btnOsAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(btnOSExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(btnOsImpre, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 12, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addGap(25, 25, 25)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel19)
                                .addComponent(txtOsId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(19, 19, 19)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addComponent(txtOsData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(19, 19, 19)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtOsTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13))
                            .addGap(19, 19, 19)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15)
                                .addComponent(txtOsProfi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(19, 19, 19)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel16)
                                .addComponent(txtOsNomeAni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(19, 19, 19)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel17)
                                .addComponent(txtOsCor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(19, 19, 19)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel18)
                                .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(19, 19, 19)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnOsPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnOsInser, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnOsAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnOSExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnOsImpre, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 12, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtOsCliPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOsCliPesqKeyReleased
        // chama o metodo pesquisar_cliente();
        pesquisar_cliente();
        
    }//GEN-LAST:event_txtOsCliPesqKeyReleased

    private void tblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClienteMouseClicked
        // chama o metodo  setar_tblCliente();
  setar_tblCliente();
    }//GEN-LAST:event_tblClienteMouseClicked

    private void txtOsAniPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOsAniPesqKeyReleased
        // chama o metodo pesquisar_animal()
  pesquisar_animal();        
    }//GEN-LAST:event_txtOsAniPesqKeyReleased

    private void tblAnimalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAnimalMouseClicked
        // chama o metodo setar_tblAnimal()
        setar_tblAnimal();
    }//GEN-LAST:event_tblAnimalMouseClicked

    private void txtOsUsuPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOsUsuPesqKeyReleased
        // chama o metodo pesquisar_usuario()
        pesquisar_usuario();
    }//GEN-LAST:event_txtOsUsuPesqKeyReleased

    private void tblUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuarioMouseClicked
        // chama o medodo setar_tblUsuario()
        setar_tblUsuario();
    }//GEN-LAST:event_tblUsuarioMouseClicked

    private void radioDinheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioDinheActionPerformed
        // chama a variavel tipo_pagto
        tipo_pagto = "dinheiro";
    }//GEN-LAST:event_radioDinheActionPerformed

    private void radioCartaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioCartaoActionPerformed
        // chama o metodo tipo_pagto
        tipo_pagto = "cartão";
    }//GEN-LAST:event_radioCartaoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // evento para marcar executar uma acao dentro do formilario
        radioDinhe.setSelected(true);
        tipo_pagto = "dinheiro";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOsInserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsInserActionPerformed
        // chama o metodo inserir();
        inserir();
    }//GEN-LAST:event_btnOsInserActionPerformed

    private void btnOsPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsPesqActionPerformed
        // chama o metodo consultar()
        consultar();
    }//GEN-LAST:event_btnOsPesqActionPerformed

    private void btnOsAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAtualActionPerformed
        // chama o metodo alterar()
        alterar();
    }//GEN-LAST:event_btnOsAtualActionPerformed

    private void btnOSExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSExcluirActionPerformed
        // chama o metodo excluir()
        excluir();
    }//GEN-LAST:event_btnOSExcluirActionPerformed

    private void btnOsImpreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsImpreActionPerformed
        // metodo para impressa
        imprimir_os();
    }//GEN-LAST:event_btnOsImpreActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOSExcluir;
    private javax.swing.JButton btnOsAtual;
    private javax.swing.JButton btnOsImpre;
    private javax.swing.JButton btnOsInser;
    private javax.swing.JButton btnOsPesq;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JRadioButton radioCartao;
    private javax.swing.JRadioButton radioDinhe;
    private javax.swing.JTable tblAnimal;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTable tblUsuario;
    private javax.swing.JTextField txtOsAniPesq;
    private javax.swing.JTextField txtOsCliPesq;
    private javax.swing.JTextField txtOsCor;
    private javax.swing.JTextField txtOsData;
    private javax.swing.JTextField txtOsId;
    private javax.swing.JTextField txtOsIdAni;
    private javax.swing.JTextField txtOsIdCli;
    private javax.swing.JTextField txtOsIdUsu;
    private javax.swing.JTextField txtOsNomeAni;
    private javax.swing.JTextField txtOsProfi;
    private javax.swing.JTextField txtOsTipo;
    private javax.swing.JTextField txtOsUsuPesq;
    private javax.swing.JTextField txtOsValor;
    // End of variables declaration//GEN-END:variables
}
