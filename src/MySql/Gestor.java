/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MySql;

import java.sql.Connection;

/**
 *
 * @author User
 */
public interface Gestor {
    public Connection conectar();
    public void desconectarMySql();
}
