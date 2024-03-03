/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petshouse.dal;

import javax.swing.*;
import java.sql.*;

public class ModConexao {

    public static Connection conector() {
        java.sql.Connection conexao = null;
        //chama o driver conector
        String driver = "com.mysql.jdbc.Driver";
        //armazena as informação referente ao banco
        String url = "jdbc:mysql://localhost:3306/dbpet";
        String user = "root";
        String password = "";
        //Estabeleceendo conexao com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            //a linha abaixo serve para vericar possiveis erros de conexao
            //System.out.println(e);
            return null;
        }
    }

}
