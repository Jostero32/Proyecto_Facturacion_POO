/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Clases.Cliente;
import Clases.Productos;
import Interfaz.Interfaz_Clientes;
import Interfaz.Interfaz_Kardex;
import Modelos.Modelo_Clientes;
import Modelos.Modelo_Kardex;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author User
 */
public class Controlador_Kardex implements MouseListener, ActionListener {

    private Modelo_Kardex modelo;
    private Interfaz_Kardex interfaz;
    private Productos producto;

    public Controlador_Kardex(Modelo_Kardex modelo, Interfaz_Kardex interfaz, Productos producto) throws SQLException {
        this.modelo = modelo;
        this.interfaz = interfaz;
        this.producto = producto;
        this.interfaz.Btn_Buscar_Cl.addMouseListener(this);
        this.interfaz.Combo_Productos.addActionListener(this);
        this.interfaz.Combo_Productos.addMouseListener(this);
        this.ActualizarProductos();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == this.interfaz.Btn_Buscar_Cl) {
            if (this.comprobarTexto(this.interfaz.Texto_Buscar_Cl.getText())) {
                try {
                    this.ActualizarProductos(this.interfaz.Texto_Buscar_Cl.getText(), this.interfaz.ComboBox_QueBusca.getSelectedItem().toString());
                } catch (SQLException ex) {
                }
            } else if (this.interfaz.Texto_Buscar_Cl.getText().trim().isEmpty()) {
                try {
                    this.ActualizarProductos();
                } catch (SQLException ex) {
                }
            }
        } else if (me.getSource() == this.interfaz.Combo_Productos) {
            try {
                this.ActualizarProductos();
            } catch (SQLException ex) {
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.interfaz.Combo_Productos) {
            if (this.interfaz.Combo_Productos.getSelectedItem() != null) {
                try {
                    String[] pro = this.interfaz.Combo_Productos.getSelectedItem().toString().split("->");
                    this.ActualizarProveedor(pro[1]);
                    this.ActualizarTabla(pro[1]);
                } catch (SQLException ex) {
                }
            }
        }
    }

    public void ActualizarTabla(String cod) throws SQLException {
        try {
            this.interfaz.tabla.setRowCount(0);
            ResultSet prod = this.modelo.seleccionar(cod);
            int total = 0;
            if (prod != null) {
                while (prod.next()) {
                    String t = "Venta";
                    int entrada = 0, salida = 0;
                    if (prod.getString("tipoFactura").matches("compra")) {
                        t = "Compra";
                        entrada = prod.getInt("cantidad");
                    } else {
                        salida = prod.getInt("cantidad");
                    }
                    total = total + entrada - salida;
                    this.interfaz.tabla.addRow(new Object[]{String.valueOf(prod.getDate("fecha")), t + "->" + prod.getString("codF"), entrada, salida, total});
                    if (prod.getBoolean("eliminado")) {
                        total = total + salida - entrada;
                        this.interfaz.tabla.addRow(new Object[]{String.valueOf(prod.getDate("fechaE")), "Eliminado->" + t + "->" + prod.getString("codF"), salida, entrada, total});
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void ActualizarProductos(String buscar, String qBusco) throws SQLException {
        try {
            ResultSet prod = this.modelo.seleccionarProducto(buscar, qBusco);
            this.interfaz.Combo_Productos.removeAllItems();
            this.interfaz.Combo_Productos.addItem("----------------> ");
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.Combo_Productos.addItem(prod.getString("nombreP") + "->" + prod.getString("codProd"));
                }
            }
        } catch (SQLException e) {
        }
    }

    public void ActualizarProductos() throws SQLException {
        try {
            ResultSet prod = this.modelo.seleccionarProducto();
            this.interfaz.Combo_Productos.removeAllItems();
            this.interfaz.Combo_Productos.addItem("----------------> ");
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.Combo_Productos.addItem(prod.getString("nombreP") + "->" + prod.getString("codProd"));
                }
            }
        } catch (SQLException e) {
        }
    }

    public void ActualizarProveedor(String cod) throws SQLException {
        try {
            ResultSet prod = this.modelo.seleccionarProducto(cod, "Codigo");
            long RUC = 0;
            if (prod != null) {
                while (prod.next()) {
                    RUC = Long.parseLong(prod.getString("codigoP"));
                }
            }
            prod = this.modelo.seleccionarProveedor(RUC);
            this.interfaz.Txt_Nombre_Proveedor.setText("");
            this.interfaz.Txt_RUC_Proveedor.setText("");
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.Txt_Nombre_Proveedor.setText(prod.getString("nombre"));
                    this.interfaz.Txt_RUC_Proveedor.setText(String.valueOf(prod.getLong("RUC")));
                }
            }
        } catch (SQLException e) {
        }
    }

    public Interfaz_Kardex getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Interfaz_Kardex interfaz) {
        this.interfaz = interfaz;
    }

    public boolean comprobarTexto(String texto) {
        texto = texto.trim();
        String patron = "^[a-zA-ZáéíóúñÁÉÍÓÚÑ0-9\\-./!#\\[\\]&()$=?¿´+{}\\s]+$";
        return texto.matches(patron);
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

}
