/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Clases.Productos;
import Interfaz.Interfaz_Productos;
import Interfaz.Interfaz_agregarProd;
import Modelos.Modelo_Productos;
import QR.QR;
import com.google.zxing.WriterException;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author User
 */
public class Controlador_Productos implements MouseListener, ActionListener, ListSelectionListener {

    private Modelo_Productos modelo;
    private Interfaz_Productos interfaz;
    private Productos producto;

    public Controlador_Productos(Modelo_Productos modelo, Interfaz_Productos interfaz, Productos producto) throws SQLException {
        this.modelo = modelo;
        this.interfaz = interfaz;
        this.producto = producto;
        this.interfaz.agrInt.BTN_Agregar1.addMouseListener(this);
        this.interfaz.Btn_Agregar_Prod.addActionListener(this);
        this.interfaz.Btn_Buscar_Prod.addMouseListener(this);
        this.interfaz.Btn_Eliminar_Prod.addActionListener(this);
        this.interfaz.Btn_Modificar_Prod.addActionListener(this);
        this.interfaz.Tabla_Producto.getSelectionModel().addListSelectionListener(this);
        this.ActualizarTabla();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == this.interfaz.agrInt.BTN_Agregar1) {
            if ("AGREGAR".equals(this.interfaz.agrInt.BTN_Agregar1.getText())) {
                if (this.comprobarTexto(this.interfaz.agrInt.TXT_NombreProd.getText()) && this.comprobarTexto(this.interfaz.agrInt.TXT_CodigoProd.getText())) {
                    try {
                        this.producto.setCodigo(this.interfaz.agrInt.TXT_CodigoProd.getText());
                        this.producto.setNombre(this.interfaz.agrInt.TXT_NombreProd.getText());
                        this.producto.setCodigoProveedor(this.interfaz.agrInt.Proveedor.getSelectedItem().toString().split("->")[1]);
                        if (this.modelo.registrar(producto)) {
                            JOptionPane.showMessageDialog(null, "ERROR");
                        } else {
                            try {
                                new QR().generarQR(this.interfaz.agrInt.TXT_CodigoProd.getText());
                                this.ActualizarTabla();
                            } catch (SQLException ex) {
                            }
                        }
                    } catch (Exception ex) {
                    }

                    this.interfaz.agrInt.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Ingreso caracteres no permitidos", "ERROR", 0);
                }
            } else if ("MODIFICAR".equals(this.interfaz.agrInt.BTN_Agregar1.getText())) {
                if (this.comprobarTexto(this.interfaz.agrInt.TXT_NombreProd.getText())) {
                    try {
                        this.producto.setCodigo(this.interfaz.agrInt.TXT_CodigoProd.getText());
                        this.producto.setNombre(this.interfaz.agrInt.TXT_NombreProd.getText());
                        this.producto.setCodigoProveedor(this.interfaz.agrInt.Proveedor.getSelectedItem().toString().split("->")[1]);
                        if (this.modelo.modificar(this.interfaz.agrInt.TXT_CodigoProd.getText(), producto) > 0) {
                            JOptionPane.showMessageDialog(null, "ERROR");
                        } else {
                            try {
                                this.ActualizarTabla();
                            } catch (SQLException ex) {
                            }
                        }
                    } catch (Exception ex) {
                    }
                    this.interfaz.agrInt.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Ingreso caracteres no permitidos", "ERROR", 0);
                }
            }

        } else if (me.getSource() == this.interfaz.Btn_Buscar_Prod) {
            if (this.comprobarTexto(this.interfaz.Texto_Buscar_Prod.getText())) {
                try {
                    this.ActualizarTabla(this.interfaz.Texto_Buscar_Prod.getText(), this.interfaz.ComboBox_QueBusca.getSelectedItem().toString());
                } catch (SQLException ex) {
                }
            } else if (this.interfaz.Texto_Buscar_Prod.getText().trim().isEmpty()) {
                try {
                    this.ActualizarTabla();
                } catch (SQLException ex) {
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingreso caracteres no permitidos", "ERROR", 0);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.interfaz.Btn_Agregar_Prod) {
            this.interfaz.agrInt.TXT_NombreProd.setText("");
            this.interfaz.agrInt.TXT_CodigoProd.setText("");
            this.interfaz.agrInt.BTN_Agregar1.setText("AGREGAR");
            this.interfaz.agrInt.TXT_CodigoProd.setEditable(true);
            this.interfaz.agrInt.setVisible(true);
            this.ActualizarProveedores();
        } else if (ae.getSource() == this.interfaz.Btn_Eliminar_Prod && this.interfaz.Tabla_Producto.getSelectedRow() >= 0) {
            this.producto.setCodigo(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Producto.getSelectedRow(), 1).toString());
            this.producto.setNombre(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Producto.getSelectedRow(), 0).toString());
            this.producto.setCodigoProveedor(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Producto.getSelectedRow(), 2).toString());
            try {
                if (this.modelo.eliminar(producto)) {
                    JOptionPane.showMessageDialog(null, "ERROR");
                } else {
                    try {
                        this.ActualizarTabla();
                        JOptionPane.showMessageDialog(null, "Producto Borrado");
                    } catch (SQLException ex) {
                    }
                }
            } catch (HeadlessException ex) {
                System.out.println(ex);
            }

        } else if (ae.getSource() == this.interfaz.Btn_Modificar_Prod && this.interfaz.Tabla_Producto.getSelectedRow() >= 0) {
            this.interfaz.agrInt.BTN_Agregar1.setText("MODIFICAR");
            this.interfaz.agrInt.TXT_CodigoProd.setText(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Producto.getSelectedRow(), 1).toString());
            this.interfaz.agrInt.TXT_CodigoProd.setEditable(false);
            this.interfaz.agrInt.TXT_NombreProd.setText(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Producto.getSelectedRow(), 0).toString());
            this.interfaz.agrInt.setVisible(true);
            this.ActualizarProveedores();
        }
    }

    public void ActualizarTabla() throws SQLException {
        try {
            this.interfaz.tabla.setRowCount(0);
            ResultSet prod = this.modelo.seleccionar();
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.tabla.addRow(new Object[]{prod.getString("nombreP"), prod.getString("codProd"), prod.getString("codigoP"), prod.getInt("cantidad")});
                }
            }
        } catch (SQLException e) {
        }
    }

    public void ActualizarTabla(String buscar, String qBusco) throws SQLException {
        try {
            ResultSet prod = this.modelo.seleccionar(buscar, qBusco);
            this.interfaz.tabla.setRowCount(0);
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.tabla.addRow(new Object[]{prod.getString("nombreP"), prod.getString("codProd"), prod.getString("codigoP"), prod.getInt("cantidad")});
                }
            }
        } catch (SQLException e) {
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (lse.getSource() == this.interfaz.Tabla_Producto.getSelectionModel()) {
            try {
                this.interfaz.Img_QR_Prod.setIcon(new ImageIcon(getClass().getResource("/QR/" + this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Producto.getSelectedRow(), 1).toString() + ".png")));
            } catch (Exception e) {
            }
        }
    }

    public void ActualizarProveedores() {
        try {
            ResultSet prod = this.modelo.seleccionarProveedor();
            this.interfaz.agrInt.Proveedor.removeAllItems();
            if (prod == null) {
                JOptionPane.showMessageDialog(null, "No hay proveedores");
                this.interfaz.agrInt.dispose();
            }
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.agrInt.Proveedor.addItem(prod.getString("nombre") + "->" + prod.getString("RUC"));
                }
            }
        } catch (SQLException e) {
        }
    }

    public Interfaz_Productos getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Interfaz_Productos interfaz) {
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
