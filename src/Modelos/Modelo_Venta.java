/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Clases.Factura;
import Clases.Productos;
import MySql.Mysql;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Modelo_Venta {

    public ResultSet seleccionar(String busca, String queBusca) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String as = "nombreP";
        busca = busca + "%";
        if (busca.isEmpty()) {
            busca = "*";
        }
        switch (queBusca) {
            case "Codigo":
                as = "codF";
                break;

            case "Cedula":
                as = "codCl";
                break;

        }
        try {
            String orden = "select distinct codF,fecha,codCl from facturas where " + as + " like ? and tipoFactura =? and eliminado=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, busca);
            ps.setString(2, "venta");
            ps.setBoolean(3, false);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public ResultSet seleccionar() {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String orden = "select distinct codF,fecha,codCl from facturas where tipoFactura=? and eliminado=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, "venta");
            ps.setBoolean(2, false);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public boolean eliminar(Factura f) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = true;
        try {
            String orden = "update facturas set  eliminado=?, fechaE=? where codF=? and codCl=? and tipoFactura=? ";
            ps = sql.conectar().prepareStatement(orden);
            ps.setBoolean(1, true);
            ps.setDate(2, new java.sql.Date(f.getFecha().getTime()));
            ps.setString(3, f.getCodF());
            ps.setString(4, f.getCodCl());
            ps.setString(5, "venta");
            ejecuto = ps.execute();
            orden = "update productos set  cantidad=cantidad+? where codProd=? ";
            ps = sql.conectar().prepareStatement(orden);
            ps.setInt(1, f.getCantidad());
            ps.setString(2, f.getProducto().getCodigo());
            ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public boolean registrar(Factura fNuevo) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = true;
        try {
            String orden = "insert into facturas (precioProd,codCl,tipoFactura,codF,codproducto,cantidad,fecha) values (?,?,?,?,?,?,?);";
            ps = sql.conectar().prepareStatement(orden);
            ps.setDouble(1, fNuevo.getProducto().getPrecioU());
            ps.setString(2, fNuevo.getCodCl());
            ps.setString(3, fNuevo.getTipoFactura());
            ps.setString(4, fNuevo.getCodF());
            ps.setString(5, fNuevo.getProducto().getCodigo());
            ps.setInt(6, fNuevo.getCantidad());
            ps.setDate(7, new java.sql.Date(fNuevo.getFecha().getTime()));
            ejecuto = ps.execute();
            orden = "update productos set  cantidad=cantidad-? where codProd=? ";
            ps = sql.conectar().prepareStatement(orden);
            ps.setInt(1, fNuevo.getCantidad());
            ps.setString(2, fNuevo.getProducto().getCodigo());
            System.out.println(ps.toString());
            ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public ResultSet seleccionarProductos(String codigoF, String codCl) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String orden = "select * from facturas where codF = ? and codCl=? and tipoFactura=? and eliminado=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, codigoF);
            ps.setString(2, codCl);
            ps.setString(3, "venta");
            ps.setBoolean(4, false);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public ResultSet seleccionarCliente(int codigo) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String orden = "select * from cliente where cedula = ? ";
            ps = sql.conectar().prepareStatement(orden);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public ResultSet seleccionarCedulaCliente() {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String orden = "select cedula from cliente";
            ps = sql.conectar().prepareStatement(orden);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public ResultSet seleccionarProdNombre(String codProd) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String orden = "select nombreP from productos where codProd=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, codProd);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }
}
