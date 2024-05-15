/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Clases.Factura;
import Clases.Productos;
import Interfaz.Interfaz_Compra;
import Interfaz.Interfaz_Productos;
import Interfaz.Interfaz_agregarProd;
import Modelos.Modelo_Compra;
import Modelos.Modelo_Productos;
import QR.QR;
import com.google.zxing.WriterException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author User
 */
public class Controlador_Compra implements MouseListener, ActionListener, ListSelectionListener, TableModelListener {

    private Modelo_Compra modelo;
    private Interfaz_Compra interfaz;
    private Factura factura;
    private Productos productos = new Productos();
    private Modelo_Productos modProd = new Modelo_Productos();

    public Controlador_Compra(Modelo_Compra modelo, Interfaz_Compra interfaz, Factura factura) throws SQLException {
        this.modelo = modelo;
        this.interfaz = interfaz;
        this.factura = factura;
        this.interfaz.agrInt.BTN_Agregar1.addMouseListener(this);
        this.interfaz.Btn_Agregar_Comp.addActionListener(this);
        this.interfaz.Btn_Buscar_Comp.addMouseListener(this);
        this.interfaz.Btn_Eliminar_Comp.addActionListener(this);
        this.interfaz.Tabla_Facturas.getSelectionModel().addListSelectionListener(this);
        this.interfaz.agrInt.Combo_codCliente.addActionListener(this);
        this.interfaz.agrInt.Btn_Buscar_Prod.addMouseListener(this);
        this.interfaz.agrInt.Btn_AgrProd.addMouseListener(this);
        this.interfaz.agrInt.Btn_ElimProd.addMouseListener(this);
        this.interfaz.agrInt.tabla.addTableModelListener(this);
        this.ActualizarTabla();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == this.interfaz.agrInt.BTN_Agregar1 && this.interfaz.agrInt.tabla.getRowCount() > 0 && this.interfaz.agrInt.Combo_codCliente.getSelectedIndex() >= 0) {
            if ("AGREGAR".equals(this.interfaz.agrInt.BTN_Agregar1.getText())) {
                if (this.comprobarTexto(this.interfaz.agrInt.Txt_CodFactura.getText())) {
                    this.factura.setCodF(this.interfaz.agrInt.Txt_CodFactura.getText());
                    this.factura.setCodCl(this.interfaz.agrInt.Combo_codCliente.getSelectedItem().toString());
                    this.factura.setFecha(new Date(this.interfaz.agrInt.Sel_Fecha.getDate().getTime()));
                    this.factura.setTipoFactura("compra");
                    boolean a=true;
                    for (int i = 0; i < this.interfaz.agrInt.tabla.getRowCount(); i++) {
                        this.factura.setCantidad(Integer.parseInt(this.interfaz.agrInt.tabla.getValueAt(i, 2).toString()));
                        Productos prod = new Productos();
                        prod.setCodigo(this.interfaz.agrInt.tabla.getValueAt(i, 1).toString());
                        prod.setNombre(this.interfaz.agrInt.tabla.getValueAt(i, 0).toString());
                        prod.setPrecioU(Double.parseDouble(this.interfaz.agrInt.tabla.getValueAt(i, 3).toString()));
                        this.factura.setProducto(prod);
                        a = this.modelo.registrar(factura);
                        if (a) {
                            JOptionPane.showMessageDialog(null, "ERROR");
                            break;
                        }
                    }
                    if (!a) {
                        try {
                            this.ActualizarTabla();
                        } catch (SQLException ex) {
                        }
                    }
                    this.interfaz.agrInt.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede agregar la factura", "ERROR", 0);
                }
            }
        } else if (me.getSource() == this.interfaz.Btn_Buscar_Comp) {
            if (this.comprobarTexto(this.interfaz.Texto_Buscar_Comp.getText())) {
                try {
                    this.ActualizarTabla(this.interfaz.Texto_Buscar_Comp.getText(), this.interfaz.ComboBox_QueBusca.getSelectedItem().toString());
                } catch (SQLException ex) {
                }
            } else if (this.interfaz.Texto_Buscar_Comp.getText().trim().isEmpty()) {
                try {
                    this.ActualizarTabla();
                } catch (SQLException ex) {
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingreso caracteres no permitidos", "ERROR", 0);
            }
        } else if (me.getSource() == this.interfaz.agrInt.Btn_Buscar_Prod) {
            if (this.comprobarTexto(this.interfaz.agrInt.Texto_Buscar_Prod.getText())) {
                this.ActualizarProductosA(this.interfaz.agrInt.Texto_Buscar_Prod.getText(), this.interfaz.agrInt.ComboBox_QueBusca.getSelectedItem().toString(), this.interfaz.agrInt.Combo_codCliente.getSelectedItem().toString());
            } else if (this.interfaz.agrInt.Texto_Buscar_Prod.getText().trim().isEmpty()) {
                this.ActualizarProductosA(this.interfaz.agrInt.Combo_codCliente.getSelectedItem().toString());
            } else {
                JOptionPane.showMessageDialog(null, "Ingreso caracteres no permitidos", "ERROR", 0);
            }
        } else if (me.getSource() == this.interfaz.agrInt.Btn_AgrProd) {
            String[] codigo = this.interfaz.agrInt.Combo_CodProducto.getSelectedItem().toString().split("->");
            try {
                this.interfaz.agrInt.tabla.addRow(new Object[]{codigo[0], codigo[1], this.interfaz.agrInt.Spi_Cantidad.getValue(), this.interfaz.agrInt.Txt_Precio.getText(), Integer.valueOf(this.interfaz.agrInt.Spi_Cantidad.getValue().toString()) * Double.valueOf(this.interfaz.agrInt.Txt_Precio.getText())});
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error");
            }
        } else if (me.getSource() == this.interfaz.agrInt.Btn_ElimProd && this.interfaz.agrInt.Tabla_Producto.getSelectedRow() >= 0) {
            this.interfaz.agrInt.tabla.removeRow(this.interfaz.agrInt.Tabla_Producto.getSelectedRow());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.interfaz.Btn_Agregar_Comp) {
            this.interfaz.agrInt.Txt_CodFactura.setText("");
            this.interfaz.agrInt.Txt_DirCliente.setText("");
            this.interfaz.agrInt.Txt_NomCliente.setText("");
            this.interfaz.agrInt.Txt_Precio.setText("");
            this.interfaz.agrInt.TXT_Total.setText("0");
            this.interfaz.agrInt.Lbl_codCliente.setText("RUC:");
            this.interfaz.agrInt.BTN_Agregar1.setText("AGREGAR");
            this.interfaz.agrInt.Txt_CodFactura.setEditable(true);
            this.ActualizarProveedor();
            this.interfaz.tabla.setRowCount(0);
            this.interfaz.agrInt.setVisible(true);
        } else if (ae.getSource() == this.interfaz.Btn_Eliminar_Comp && this.interfaz.Tabla_Facturas.getSelectedRow() >= 0) {
            this.factura.setCodF(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Facturas.getSelectedRow(), 1).toString());
            this.factura.setCodCl(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Facturas.getSelectedRow(), 2).toString());
            Date d = new Date();
            this.factura.setFecha(d);
            boolean a = true;
            for (int i = 0; i < this.interfaz.tabla.getRowCount(); i++) {
                if (!this.interfaz.tablaProd.getValueAt(i, 2).toString().matches("Total=")) {
                    this.factura.setCantidad(Integer.parseInt(this.interfaz.tablaProd.getValueAt(i, 2).toString()));
                    Productos p = new Productos();
                    p.setCodigo(this.interfaz.tablaProd.getValueAt(i, 0).toString());
                    this.factura.setProducto(p);
                    a = this.modelo.eliminar(factura);
                }
            }
            if (a) {
                JOptionPane.showMessageDialog(null, "ERROR");
            } else {
                JOptionPane.showMessageDialog(null, "Factura Borrada");
                try {
                    this.ActualizarTabla();
                    this.ActualizarProductos("", "");
                } catch (SQLException ex) {
                }
            }

        } else if (ae.getSource() == this.interfaz.agrInt.Combo_codCliente && this.interfaz.agrInt.Combo_codCliente.getSelectedIndex() != -1) {
            this.ActualizarProveedor(this.interfaz.agrInt.Combo_codCliente.getSelectedItem().toString());
            this.ActualizarProductosA(this.interfaz.agrInt.Combo_codCliente.getSelectedItem().toString());
            this.interfaz.agrInt.tabla.setRowCount(0);
        }
    }

    public void ActualizarTabla() throws SQLException {
        try {
            this.interfaz.tabla.setRowCount(0);
            ResultSet prod = this.modelo.seleccionar();
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.tabla.addRow(new Object[]{prod.getDate("fecha").toString(), prod.getString("codF"), prod.getString("codCl")});
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
                    this.interfaz.tabla.addRow(new Object[]{prod.getDate("fecha").toString(), prod.getString("codF"), prod.getString("codCl")});
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (lse.getSource() == this.interfaz.Tabla_Facturas.getSelectionModel()) {
            try {
                this.ActualizarProductos(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Facturas.getSelectedRow(), 1).toString(), this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Facturas.getSelectedRow(), 2).toString());
            } catch (Exception e) {
            }
        }
    }

    public void ActualizarProductos(String codigoF, String codCl) {
        try {
            ResultSet prod = this.modelo.seleccionarProductos(codigoF, codCl);
            this.interfaz.tablaProd.setRowCount(0);
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.tablaProd.addRow(new Object[]{prod.getString("codproducto"), prod.getDouble("precioProd"), prod.getInt("cantidad"), prod.getDouble("precioProd") * prod.getInt("cantidad")});
                }
                double total = 0;
                for (int i = 0; i < this.interfaz.tablaProd.getRowCount(); i++) {
                    total = total + Double.parseDouble(this.interfaz.tablaProd.getValueAt(i, 3).toString());
                }
                this.interfaz.tablaProd.addRow(new Object[]{"", "", "Total=", total});
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void ActualizarProveedor() {
        try {
            ResultSet prod = this.modelo.seleccionarRUCProveedores();
            this.interfaz.agrInt.Combo_codCliente.removeAllItems();
            if (prod == null) {
                JOptionPane.showMessageDialog(null, "No hay proveedores");
                this.interfaz.agrInt.dispose();
            }
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.agrInt.Combo_codCliente.addItem(String.valueOf(prod.getLong("RUC")));
                }
            }
        } catch (SQLException e) {
        }
    }

    public void ActualizarProveedor(String codigo) {
        try {
            ResultSet prod = this.modelo.seleccionarProveedores(Long.parseLong(codigo));
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.agrInt.Txt_NomCliente.setText(prod.getString("nombre"));
                    this.interfaz.agrInt.Txt_DirCliente.setText(prod.getString("direccion"));
                }
            }
        } catch (SQLException e) {
        }
    }

    public void ActualizarProductosA(String proveedor) {
        try {
            ResultSet prod = this.modProd.seleccionar(proveedor);
            this.interfaz.agrInt.Combo_CodProducto.removeAllItems();
            if (prod == null) {
                JOptionPane.showMessageDialog(null, "No hay productos");
                this.interfaz.agrInt.dispose();
            }
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.agrInt.Combo_CodProducto.addItem(prod.getString("nombreP") + "->" + prod.getString("codProd"));
                }
            }
        } catch (SQLException e) {
        }
    }

    public void ActualizarProductosA(String busca, String queBusca, String proveedor) {
        try {
            ResultSet prod = this.modProd.seleccionar(busca, queBusca, proveedor);
            this.interfaz.agrInt.Combo_CodProducto.removeAllItems();
            if (prod == null) {
                JOptionPane.showMessageDialog(null, "No hay productos");
                this.interfaz.agrInt.dispose();
            }
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.agrInt.Combo_CodProducto.addItem(prod.getString("nombreP") + "->" + prod.getString("codProd"));
                }
            }
        } catch (SQLException e) {
        }
    }

    public Interfaz_Compra getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Interfaz_Compra interfaz) {
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

    @Override
    public void tableChanged(TableModelEvent tme) {
        if (tme.getSource() == this.interfaz.agrInt.tabla) {
            this.interfaz.agrInt.TXT_Total.setText("0");
            if (this.interfaz.agrInt.tabla.getRowCount() > 0) {
                for (int i = 0; i < this.interfaz.agrInt.tabla.getRowCount(); i++) {
                    this.interfaz.agrInt.TXT_Total.setText(String.valueOf(Double.valueOf(this.interfaz.agrInt.TXT_Total.getText()) + Double.valueOf(this.interfaz.agrInt.tabla.getValueAt(i, 4).toString())));
                }
            }
        }
    }

}
