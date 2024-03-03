/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petshouse.telas;

import javax.swing.*;
import java.sql.*;
import br.com.petshouse.dal.ModConexao;
import java.util.HashMap;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author POSITIVO
 */
public class TelaCompra extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCompra() {
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
            pst.setString(1, txtPesqClient.getText() + "%");
            rs = pst.executeQuery();
            tblClient.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo setar campos
    public void setar_tblCliente() {
        int setar = tblClient.getSelectedRow();
        txtCompIdCli.setText(tblClient.getModel().getValueAt(setar, 0).toString());
    }

    //metodo para pesquisar tabela Produtos
    private void pesquisar_Produto() {
        String sql = "select id_Prod as id,tipo,nome_prod as nome,valor,validade,quantidade from produtos where tipo like?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesqProd.getText() + "%");
            rs = pst.executeQuery();
            tblProdutos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo setar campos
    public void setar_tblProdutos() {
        int setar = tblProdutos.getSelectedRow();
        txtIdProd.setText(tblProdutos.getModel().getValueAt(setar, 0).toString());
    }

    //metodo para insersão
    private void inserir() {
        String sql = "INSERT INTO Faz_compra (quant_item, tipo_pagto, valor_total, id_clien, id_prod) VALUES (?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCompQuant.getText());
            pst.setString(2, tipo_pagto);
            pst.setString(3, txtCompValor.getText());
            pst.setString(4, txtCompIdCli.getText());
            pst.setString(5, txtIdProd.getText());

            // validação dos campos obrigatorios
            if ((txtCompQuant.getText().isEmpty() || (txtCompValor.getText().isEmpty() || (tipo_pagto.isEmpty() || (txtCompIdCli.getText().isEmpty() || (txtIdProd.getText().isEmpty())))))) {
                JOptionPane.showMessageDialog(null, "Preencha todo os campos obrigatorios");
            } else {

                //a linha baixo atualiza no bd os dados inseridos e
                // a linha abaixo executa a mensagem de "usuario adcionado com sucesso e limpa os campos"
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "compra cadastrado com sucesso");
                    txtCompId.setText(null);
                    txtCompData.setText(null);
                    txtCompQuant.setText(null);
                    txtCompValor.setText(null);
                    txtCompIdCli.setText(null);
                    txtIdProd.setText(null);

                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel cadastrar");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para altarar a tabela
    private void alterar() {
        String sql = "update Faz_compra set  data_hora=?, quant_item=?, tipo_pagto=?,valor_total=?,id_clien=?,id_prod=? where id_comp=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtCompData.getText());
            pst.setString(2, txtCompQuant.getText());
            pst.setString(3, tipo_pagto);
            pst.setString(4, txtCompValor.getText());
            pst.setString(5, txtCompIdCli.getText());
            pst.setString(6, txtIdProd.getText());
            pst.setString(7, txtCompId.getText());

            // validação dos campos obrigatorios
            if ((txtCompQuant.getText().isEmpty() || (tipo_pagto).isEmpty() || (txtCompValor.getText().isEmpty() || (txtCompIdCli.getText().isEmpty() || (txtIdProd.getText().isEmpty()))))) {
                JOptionPane.showMessageDialog(null, "Prencha todo os campos obrigatorios");
            } else {

                //a linha baixo atualiza no bd os dados inseridos e
                // a linha abaixo executa a mensagem de "usuario adcionado com sucesso e limpa os campos"
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Compras alterado com sucesso");
                    txtCompId.setText(null);
                    txtCompData.setText(null);
                    txtCompValor.setText(null);
                    txtCompIdCli.setText(null);
                    txtIdProd.setText(null);
                    txtCompQuant.setText(null);

                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel alterar");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metodo para pesquisar registros dentro da tabela
    private void consultar() {
        // essa variavel abre uma caixa de consulta de entrada de dados
        String nCompra = JOptionPane.showInputDialog("Numero da compra");
        String sql = "select * from Faz_compra where id_comp= " + nCompra;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtCompId.setText(rs.getString(1));
                txtCompData.setText(rs.getString(2));
                txtCompQuant.setText(rs.getString(3));
                //setando o radio butoon
                String rbtComp = rs.getString(4);
                if (rbtComp.equals("dinheiro")) {
                    jRadioButton1.setSelected(true);
                    tipo_pagto = "dinheiro";

                } else {
                    rbCompCartao.setSelected(true);
                    tipo_pagto = "Cartão";
                }
                txtCompValor.setText(rs.getString(5));
                txtCompIdCli.setText(rs.getString(6));
                txtIdProd.setText(rs.getString(7));

            } else {
                JOptionPane.showMessageDialog(null, "Compra não cadastrado");
                //as linhas abaixo limpa os campos
                txtCompId.setText(null);
                txtCompData.setText(null);
                txtCompQuant.setText(null);
                txtCompValor.setText(null);
                txtCompIdCli.setText(null);
                txtIdProd.setText(null);

            }
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "Dados invalidos");
            //System.out.println(e);
        } catch (Exception e2) {

            JOptionPane.showMessageDialog(null, e2);
        }
    }

    //metodo para pesquisar tabela Produtos
    private void pesquisar_Clientes_produto() {
        String sql = "select C.nome,telefone, P.tipo,nome_Prod,valor,validade,F.id_comp,data_hora,quant_item,tipo_pagto,valor_total \n"
                + //este "+" significa continuacao na proxima linha
                "from  Faz_compra F \n"
                + "inner join Cliente as C on (F.Id_Clien = C.id_Clien)          \n"
                + "inner join Produtos as P on (F.id_prod = P.id_prod) where id_comp like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesqComp.getText() + "%");
            rs = pst.executeQuery();
            TblCompra.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo setar campos
    public void setar_tblProdutos_tblClientes() {
        int setar = TblCompra.getSelectedRow();
        txtIdComp.setText(TblCompra.getModel().getValueAt(setar, 6).toString());
    }

    // metod para exclusao de dados da tabela Faz_compra
    private void excluir() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Atencão!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from Faz_compra where id_comp=?";

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCompId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Compra excluida com sucesso!");
                    txtIdComp.setText(null);
                    txtCompData.setText(null);
                    txtCompQuant.setText(null);
                    txtCompValor.setText(null);
                    txtCompIdCli.setText(null);
                    txtIdProd.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
      //metodo imprimir comprovante
    private void imprimir_comprovante(){
               // gera um grelatorio de compras
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão deste relatorio?","Atenção",JOptionPane.YES_NO_OPTION);
       if (confirma == JOptionPane.YES_OPTION){
        //imprimindo o relatorio com o framework jasperReport   
           try {
               //usando a classe HashMap para criar um fltro
               HashMap filtro = new HashMap(); 
               filtro.put("comp",Integer.parseInt(txtCompId.getText()));
             // usando a classe jasper Print para preparar a impressao de um relatorio
          JasperPrint print = JasperFillManager.fillReport("C:/Reports/Comprovante_de_compras.jasper",filtro,conexao);
         // alinha abaixo exibe o relatorio atraves da calasse JasperViewer
         JasperViewer.viewReport(print, false);
           } catch (java.lang.NumberFormatException e){
             JOptionPane.showMessageDialog(null, "Primeiro Click em Pesquisar!");
           }
           
           catch (Exception e2) {
               JOptionPane.showMessageDialog(null, e2);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtCompId = new javax.swing.JTextField();
        txtCompData = new javax.swing.JTextField();
        txtCompQuant = new javax.swing.JTextField();
        txtCompValor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnCompPesq = new javax.swing.JButton();
        btnCompInser = new javax.swing.JButton();
        btnCompAtual = new javax.swing.JButton();
        btnCompExcluir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtPesqClient = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCompIdCli = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClient = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();
        txtPesqProd = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtIdProd = new javax.swing.JTextField();
        btnCompImprim = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        rbCompCartao = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TblCompra = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        txtPesqComp = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtIdComp = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Compras");
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
        getContentPane().setLayout(null);

        txtCompId.setEditable(false);
        txtCompId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtCompId);
        txtCompId.setBounds(104, 57, 82, 21);

        txtCompData.setEditable(false);
        txtCompData.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtCompData);
        txtCompData.setBounds(104, 97, 190, 21);

        txtCompQuant.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtCompQuant);
        txtCompQuant.setBounds(104, 137, 211, 21);

        txtCompValor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtCompValor);
        txtCompValor.setBounds(104, 177, 166, 21);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Id.N°/Compra:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(14, 57, 79, 15);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Data/hora* :");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(14, 97, 69, 15);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Quant/item* :");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(14, 137, 78, 15);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Valor total* :");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(16, 184, 71, 15);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("(*) Campos Obrigatórios");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(34, 18, 145, 15);

        btnCompPesq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_sale_lineal_color_cnvrt-18_3827704.png"))); // NOI18N
        btnCompPesq.setToolTipText("Pesquisar");
        btnCompPesq.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCompPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompPesqActionPerformed(evt);
            }
        });
        getContentPane().add(btnCompPesq);
        btnCompPesq.setBounds(30, 480, 65, 54);

        btnCompInser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_insert-object_23421.png"))); // NOI18N
        btnCompInser.setToolTipText("Atualizar");
        btnCompInser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCompInser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompInserActionPerformed(evt);
            }
        });
        getContentPane().add(btnCompInser);
        btnCompInser.setBounds(120, 480, 81, 57);

        btnCompAtual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_refresh_reload_update_2_2571204.png"))); // NOI18N
        btnCompAtual.setToolTipText("Atualizar");
        btnCompAtual.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCompAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompAtualActionPerformed(evt);
            }
        });
        getContentPane().add(btnCompAtual);
        btnCompAtual.setBounds(210, 480, 81, 57);

        btnCompExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_1-04_511562.png"))); // NOI18N
        btnCompExcluir.setToolTipText("Excluir");
        btnCompExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCompExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompExcluirActionPerformed(evt);
            }
        });
        getContentPane().add(btnCompExcluir);
        btnCompExcluir.setBounds(320, 480, 77, 59);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesqisas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Id* :");

        txtPesqClient.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPesqClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesqClientKeyReleased(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_sale_lineal_color_cnvrt-18_3827704.png"))); // NOI18N

        tblClient.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Título 1", "Título 2", "Título 3", "Título 4"
            }
        ));
        tblClient.setToolTipText("Click na linha desejada");
        tblClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClient);

        tblProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Título 1", "Título 2", "Título 3", "Título 4"
            }
        ));
        tblProdutos.setToolTipText("Click na linha desejada");
        tblProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblProdutos);

        txtPesqProd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPesqProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesqProdKeyReleased(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_sale_lineal_color_cnvrt-18_3827704.png"))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Produtos:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Clientes:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Id* :");

        txtIdProd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 128, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txtPesqClient, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCompIdCli, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesqProd, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdProd, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(txtPesqClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9)
                                .addComponent(txtCompIdCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(txtPesqProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel13)
                                .addComponent(txtIdProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(310, 10, 660, 300);

        btnCompImprim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_document-print_118913.png"))); // NOI18N
        btnCompImprim.setToolTipText("Imprimir");
        btnCompImprim.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCompImprim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompImprimActionPerformed(evt);
            }
        });
        getContentPane().add(btnCompImprim);
        btnCompImprim.setBounds(430, 480, 81, 57);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opções de pagamentos.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRadioButton1.setText("Dinheiro");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbCompCartao);
        rbCompCartao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbCompCartao.setText("Cartão");
        rbCompCartao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCompCartaoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("OU");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1)
                    .addComponent(rbCompCartao)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel4)))
                .addGap(0, 181, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbCompCartao)
                .addGap(19, 19, 19))
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 210, 270, 100);

        TblCompra.setModel(new javax.swing.table.DefaultTableModel(
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
        TblCompra.setToolTipText("Click na linha desejada");
        TblCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblCompraMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TblCompra);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(20, 380, 910, 90);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Compras:");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(20, 350, 50, 15);

        txtPesqComp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPesqComp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesqCompKeyReleased(evt);
            }
        });
        getContentPane().add(txtPesqComp);
        txtPesqComp.setBounds(80, 350, 248, 21);

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petshouse/iconi/iconfinder_sale_lineal_color_cnvrt-18_3827704.png"))); // NOI18N
        getContentPane().add(jLabel17);
        jLabel17.setBounds(340, 340, 32, 32);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Id:");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(390, 350, 15, 15);

        txtIdComp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtIdComp);
        txtIdComp.setBounds(410, 350, 87, 21);

        setBounds(0, 0, 955, 580);
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // chama a variavel tipo para selecionar aopção dinheiro
        tipo_pagto = "dinheiro";
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void rbCompCartaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCompCartaoActionPerformed
        // chama a variavel tipo para selecionar aopção cartao
        tipo_pagto = "cartão";
    }//GEN-LAST:event_rbCompCartaoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // evento para marcar executar uma acao dentro do formilario
        jRadioButton1.setSelected(true);
        tipo_pagto = "dinheiro";
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtPesqClientKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesqClientKeyReleased
        // chama o metodo pesquisar_cliente
        pesquisar_cliente();
    }//GEN-LAST:event_txtPesqClientKeyReleased

    private void tblClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientMouseClicked
        // quando clicado na tabela, aciona o metodo setar_tblCliente()
        setar_tblCliente();
    }//GEN-LAST:event_tblClientMouseClicked

    private void txtPesqProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesqProdKeyReleased
        //Chama o metodo pesquisar_Produto() 'pesquisa por digitos'
        pesquisar_Produto();
    }//GEN-LAST:event_txtPesqProdKeyReleased

    private void tblProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutosMouseClicked
        // chama o metodo  setar_tblProdutos() quando a tabela produto for clicda, setando o campo id prod
        setar_tblProdutos();
    }//GEN-LAST:event_tblProdutosMouseClicked

    private void btnCompInserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompInserActionPerformed
        // cahama o metodo inserir
        inserir();
    }//GEN-LAST:event_btnCompInserActionPerformed

    private void btnCompAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompAtualActionPerformed
        // chama o metodo alterar()
        alterar();
    }//GEN-LAST:event_btnCompAtualActionPerformed

    private void btnCompPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompPesqActionPerformed
        // chama o metodo consultar()
        consultar();
    }//GEN-LAST:event_btnCompPesqActionPerformed

    private void txtPesqCompKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesqCompKeyReleased
        // chama o metodo pesquisar_Clientes_produto() do inener join tabela cliente,produtos,faz_compras
        pesquisar_Clientes_produto();
    }//GEN-LAST:event_txtPesqCompKeyReleased

    private void TblCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblCompraMouseClicked
        // chama o medodo setar_tblProdutos_tblClientes() quando a tabela for clicada
        setar_tblProdutos_tblClientes();
    }//GEN-LAST:event_TblCompraMouseClicked

    private void btnCompExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompExcluirActionPerformed
        // chama ometodo   
        excluir();
    }//GEN-LAST:event_btnCompExcluirActionPerformed

    private void btnCompImprimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompImprimActionPerformed
        // chama o metodo imprimir_comprovante()
        imprimir_comprovante();
    }//GEN-LAST:event_btnCompImprimActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TblCompra;
    private javax.swing.JButton btnCompAtual;
    private javax.swing.JButton btnCompExcluir;
    private javax.swing.JButton btnCompImprim;
    private javax.swing.JButton btnCompInser;
    private javax.swing.JButton btnCompPesq;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JRadioButton rbCompCartao;
    private javax.swing.JTable tblClient;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JTextField txtCompData;
    private javax.swing.JTextField txtCompId;
    private javax.swing.JTextField txtCompIdCli;
    private javax.swing.JTextField txtCompQuant;
    private javax.swing.JTextField txtCompValor;
    private javax.swing.JTextField txtIdComp;
    private javax.swing.JTextField txtIdProd;
    private javax.swing.JTextField txtPesqClient;
    private javax.swing.JTextField txtPesqComp;
    private javax.swing.JTextField txtPesqProd;
    // End of variables declaration//GEN-END:variables
}
