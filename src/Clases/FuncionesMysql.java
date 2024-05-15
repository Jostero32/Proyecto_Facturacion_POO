/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.ResultSet;

/**
 *
 * @author User
 */
public interface FuncionesMysql {
    public ResultSet seleccionar();
    public boolean eliminar();
    public boolean modificar();
    public boolean registrar();
}
