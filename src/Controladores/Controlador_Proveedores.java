/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Clases.Proveedor;
import Interfaz.Interfaz_Proveedores;
import Modelos.Modelo_Proveedor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class Controlador_Proveedores implements MouseListener, ActionListener {

    private Modelo_Proveedor modelo;
    private Interfaz_Proveedores interfaz;
    private Proveedor proveedor;

    public Controlador_Proveedores(Modelo_Proveedor modelo, Interfaz_Proveedores interfaz, Proveedor proveedor) throws SQLException {
        this.modelo = modelo;
        this.interfaz = interfaz;
        this.proveedor = proveedor;
        this.interfaz.agrInt.BTN_Agregar1.addMouseListener(this);
        this.interfaz.Btn_Agregar_Pro.addActionListener(this);
        this.interfaz.Btn_Buscar_Pro.addMouseListener(this);
        this.interfaz.Btn_Eliminar_Pro.addActionListener(this);
        this.interfaz.Btn_Modificar_Pro.addActionListener(this);
        this.interfaz.agrInt.ComboBox_Provincia.addActionListener(this);
        this.ActualizarTabla();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == this.interfaz.agrInt.BTN_Agregar1) {
            if ("AGREGAR".equals(this.interfaz.agrInt.BTN_Agregar1.getText())) {
                if (this.comprobarNombre(this.interfaz.agrInt.TXT_Nombre.getText()) && this.ValidarRUC(this.interfaz.agrInt.TXT_RUC.getText()) && this.comprobarNumeros(this.interfaz.agrInt.TXT_Telefono.getText()) && this.comprobarDireccion(this.interfaz.agrInt.TXT_Direccion.getText()) && this.interfaz.agrInt.ComboBox_Ciudad.getSelectedIndex() > -1 && this.interfaz.agrInt.ComboBox_Provincia.getSelectedIndex() > -1) {
                    try {
                        this.proveedor.setRUC(Long.parseLong(this.interfaz.agrInt.TXT_RUC.getText().trim()));
                        this.proveedor.setNombre(this.interfaz.agrInt.TXT_Nombre.getText().trim());
                        this.proveedor.setDireccion(this.interfaz.agrInt.TXT_Direccion.getText().trim());
                        this.proveedor.setTelefono((this.interfaz.agrInt.TXT_Telefono.getText().trim()));
                        this.proveedor.setCodProvincia(this.interfaz.agrInt.ComboBox_Provincia.getSelectedItem().toString().split("->")[1]);
                        this.proveedor.setCodCiudad(this.interfaz.agrInt.ComboBox_Ciudad.getSelectedItem().toString().split("->")[1]);
                        if (this.modelo.registrar(proveedor)) {
                            JOptionPane.showMessageDialog(null, "ERROR");
                        } else {
                            try {
                                this.ActualizarTabla();
                            } catch (SQLException ex) {
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al registrar");
                    }
                    this.interfaz.agrInt.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar");
                }
            } else if ("MODIFICAR".equals(this.interfaz.agrInt.BTN_Agregar1.getText())) {
                if (this.comprobarNombre(this.interfaz.agrInt.TXT_Nombre.getText()) && this.ValidarRUC(this.interfaz.agrInt.TXT_RUC.getText()) && this.comprobarNumeros(this.interfaz.agrInt.TXT_Telefono.getText()) && this.comprobarDireccion(this.interfaz.agrInt.TXT_Direccion.getText()) && this.interfaz.agrInt.ComboBox_Ciudad.getSelectedIndex() > -1 && this.interfaz.agrInt.ComboBox_Provincia.getSelectedIndex() > -1) {
                    try {
                        this.proveedor.setRUC(Long.parseLong(this.interfaz.agrInt.TXT_RUC.getText().trim()));
                        this.proveedor.setNombre(this.interfaz.agrInt.TXT_Nombre.getText().trim());
                        this.proveedor.setDireccion(this.interfaz.agrInt.TXT_Direccion.getText().trim());
                        this.proveedor.setTelefono((this.interfaz.agrInt.TXT_Telefono.getText().trim()));
                        this.proveedor.setCodProvincia(this.interfaz.agrInt.ComboBox_Provincia.getSelectedItem().toString().split("->")[1]);
                        this.proveedor.setCodCiudad(this.interfaz.agrInt.ComboBox_Ciudad.getSelectedItem().toString().split("->")[1]);
                        if (this.modelo.modificar(Long.parseLong(this.interfaz.agrInt.TXT_RUC.getText()), proveedor) > 0) {
                            JOptionPane.showMessageDialog(null, "ERROR");
                        } else {
                            try {
                                this.ActualizarTabla();
                            } catch (SQLException ex) {
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al modificar");
                    }
                    this.interfaz.agrInt.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar");
                }
            }

        } else if (me.getSource() == this.interfaz.Btn_Buscar_Pro) {
            if (this.comprobarTexto(this.interfaz.Texto_Buscar_Pro.getText())) {
                try {
                    this.ActualizarTabla(this.interfaz.Texto_Buscar_Pro.getText(), this.interfaz.ComboBox_QueBusca.getSelectedItem().toString());
                } catch (SQLException ex) {
                }
            } else if (this.interfaz.Texto_Buscar_Pro.getText().trim().isEmpty()) {
                try {
                    this.ActualizarTabla();
                } catch (SQLException ex) {
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.interfaz.Btn_Agregar_Pro) {
            try {
                this.interfaz.agrInt.TXT_Nombre.setText("");
                this.interfaz.agrInt.TXT_RUC.setText("");
                this.interfaz.agrInt.TXT_Direccion.setText("");
                this.interfaz.agrInt.TXT_Telefono.setText("");
                this.interfaz.agrInt.BTN_Agregar1.setText("AGREGAR");
                this.interfaz.agrInt.TXT_RUC.setEditable(true);
                this.interfaz.agrInt.setVisible(true);
                this.ActualizarProvincias();
                this.ActualizarCiudades(this.interfaz.agrInt.ComboBox_Provincia.getSelectedItem().toString().split("->")[1]);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error");
            }
        } else if (ae.getSource() == this.interfaz.Btn_Eliminar_Pro && this.interfaz.Tabla_Proveedor.getSelectedRow() >= 0) {
            try {
                this.proveedor.setRUC(Long.parseLong(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Proveedor.getSelectedRow(), 0).toString().trim()));
                this.proveedor.setNombre(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Proveedor.getSelectedRow(), 1).toString().trim());
                this.proveedor.setDireccion(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Proveedor.getSelectedRow(), 2).toString().trim());
                this.proveedor.setTelefono((this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Proveedor.getSelectedRow(), 3).toString().trim()));
                this.proveedor.setCodProvincia(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Proveedor.getSelectedRow(), 4).toString().trim());
                this.proveedor.setCodCiudad(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Proveedor.getSelectedRow(), 5).toString().trim());
                if (this.modelo.eliminar(proveedor)) {
                    JOptionPane.showMessageDialog(null, "ERROR");
                } else {
                    JOptionPane.showMessageDialog(null, "Proveedor Borrado");
                    this.ActualizarTabla();
                }
            } catch (SQLException ex) {
            }

        } else if (ae.getSource() == this.interfaz.Btn_Modificar_Pro && this.interfaz.Tabla_Proveedor.getSelectedRow() >= 0) {
            try {
                this.interfaz.agrInt.BTN_Agregar1.setText("MODIFICAR");
                this.interfaz.agrInt.TXT_RUC.setText(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Proveedor.getSelectedRow(), 0).toString());
                this.interfaz.agrInt.TXT_RUC.setEditable(false);
                this.interfaz.agrInt.TXT_Nombre.setText(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Proveedor.getSelectedRow(), 1).toString());
                this.interfaz.agrInt.TXT_Direccion.setText(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Proveedor.getSelectedRow(), 2).toString());
                this.interfaz.agrInt.TXT_Telefono.setText(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Proveedor.getSelectedRow(), 3).toString());
                this.interfaz.agrInt.setVisible(true);
                this.ActualizarProvincias();
                this.ActualizarCiudades(this.interfaz.agrInt.ComboBox_Provincia.getSelectedItem().toString().split("->")[1]);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error");
            }
        } else if (ae.getSource() == this.interfaz.agrInt.ComboBox_Provincia) {
            try {
                if (this.interfaz.agrInt.ComboBox_Provincia.getSelectedItem() != null) {
                    this.ActualizarCiudades(this.interfaz.agrInt.ComboBox_Provincia.getSelectedItem().toString().split("->")[1]);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error");
            }
        }
    }

    public void ActualizarTabla() throws SQLException {
        try {
            this.interfaz.tabla.setRowCount(0);
            ResultSet prod = this.modelo.seleccionar();
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.tabla.addRow(new Object[]{String.valueOf(prod.getLong("RUC")), prod.getString("nombre"), prod.getString("direccion"), prod.getString("telefono"), prod.getString("codP"), prod.getString("codC")});
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
                    this.interfaz.tabla.addRow(new Object[]{String.valueOf(prod.getLong("RUC")), prod.getString("nombre"), prod.getString("direccion"), prod.getString("telefono"), prod.getString("codP"), prod.getString("codC")});
                }
            }
        } catch (SQLException e) {
        }
    }

    public void ActualizarProvincias() throws SQLException {
        try {
            ResultSet prod = this.modelo.seleccionarProvincias();
            this.interfaz.agrInt.ComboBox_Provincia.removeAllItems();
            if (prod == null) {
                JOptionPane.showMessageDialog(null, "No hay provincias");
                this.interfaz.agrInt.dispose();
            }
            if (prod == null) {
                JOptionPane.showMessageDialog(null, "No hay provincias, por favor ingrese provincias");
                this.interfaz.agrInt.dispose();
            }
            while (prod.next()) {
                this.interfaz.agrInt.ComboBox_Provincia.addItem(prod.getString("nombreP") + "->" + prod.getString("codigoP"));
            }
        } catch (SQLException e) {
        }

    }

    public void ActualizarCiudades(String s) throws SQLException {
        try {
            ResultSet prod = this.modelo.seleccionarCiudades(s);
            this.interfaz.agrInt.ComboBox_Ciudad.removeAllItems();
            if (prod == null) {
                JOptionPane.showMessageDialog(null, "No hay Ciudades");
                this.interfaz.agrInt.dispose();
            }
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.agrInt.ComboBox_Ciudad.addItem(prod.getString("nombreC") + "->" + prod.getString("codigoC"));
                }
            }
        } catch (SQLException e) {
        }

    }

    public Interfaz_Proveedores getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Interfaz_Proveedores interfaz) {
        this.interfaz = interfaz;
    }

    public boolean comprobarTexto(String texto) {
        texto = texto.trim();
        String patron = "^[a-zA-ZáéíóúñÁÉÍÓÚÑ0-9\\-./!#\\[\\]&()$=?¿´+{}\\s]+$";
        return texto.matches(patron);
    }

    public boolean comprobarNumeros(String texto) {
        texto = texto.trim();
        return texto.matches("\\d+");
    }

    public boolean comprobarNombre(String texto) {
        texto = texto.trim();
        if (this.comprobarTexto(texto)) {
            int cantidadPalabras = texto.split("\\s+").length;
            if (cantidadPalabras == 2) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese dos palabras en el nombre");
                return false;
            }
        }
        JOptionPane.showMessageDialog(null, "Ingrese solo letras en el nombre");
        return false;
    }

    public boolean ValidarRUC(String RUC1) {
        int val = 0;
        int comprobar;
        RUC1 = RUC1.trim();
        String RUC = "";
        if (this.comprobarNumeros(RUC1) && RUC1.length() == 13) {
            RUC = RUC1.substring(0, RUC1.length() - 3);
            String RUC2 = RUC1.substring(RUC1.length() - 3);
            for (int i = 0; RUC.length() - 1 > i; i++) {
                int a = Integer.parseInt(String.valueOf(RUC.charAt(i)));
                if (i % 2 == 0) {
                    a = (a * 2);
                }
                if (a >= 10) {
                    a = a - 9;
                }
                val = val + a;
            }
            if (val % 10 == 0) {
                comprobar = 0;
            } else {
                comprobar = val;
                while (comprobar % 10 != 0) {
                    comprobar++;
                }
                comprobar = comprobar - val;
            }
            if (!(comprobar == Integer.parseInt(String.valueOf(RUC.charAt(9))))) {
                JOptionPane.showMessageDialog(null, "RUC Invalido");
            }
            return (comprobar == Integer.parseInt(String.valueOf(RUC.charAt(9))) && RUC2.matches("001"));
        } else {
            JOptionPane.showMessageDialog(null, "RUC invalido");
            return false;
        }
    }

    public boolean comprobarDireccion(String texto) {
        texto = texto.trim();
        int cantidadPalabras = texto.split("\\s+").length;
        if (cantidadPalabras > 2) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese tres palabras en la direccion");
            return false;
        }
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
