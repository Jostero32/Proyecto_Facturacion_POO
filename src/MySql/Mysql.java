/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MySql;

import java.sql.DriverManager;

import java.sql.*;
import java.sql.SQLException;


/**
 *
 * @author Usuario
 */
public class Mysql extends GestorBD implements Gestor {//en lugar de local hosta es la ip de donde se encuentra la base de datos

    private static final String url = "jdbc:mysql://localhost/proyecto";
    private static final String driver = "com.mysql.jdbc.Driver";
    public static Connection con;



    @Override
    public Connection conectar() {
        con = null;
        super.setUser("root");
        super.setPassword("1234");
        try {
            Class.forName(driver);
            con = (Connection) DriverManager.getConnection(url, super.getUser(), super.getPassword());

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return con;
    }


    @Override
    public void desconectarMySql() {
        try {
            con.close();
        } catch (SQLException e) {

        }
    }
}
