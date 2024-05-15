/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Clases.Cliente;
import Interfaz.Interfaz_Agregar_Cliente;
import Interfaz.Interfaz_Clientes;
import Interfaz.Interfaz_agregarProd;
import Modelos.Modelo_Clientes;
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

/**
 *
 * @author User
 */
public class Controlador_Clientes implements MouseListener, ActionListener {

    private Modelo_Clientes modelo;
    private Interfaz_Clientes interfaz;
    private Cliente cliente;

    public Controlador_Clientes(Modelo_Clientes modelo, Interfaz_Clientes interfaz, Cliente cliente) throws SQLException {
        this.modelo = modelo;
        this.interfaz = interfaz;
        this.cliente = cliente;
        this.interfaz.agrInt.BTN_Agregar1.addMouseListener(this);
        this.interfaz.Btn_Agregar_Cl.addActionListener(this);
        this.interfaz.Btn_Buscar_Cl.addMouseListener(this);
        this.interfaz.Btn_Eliminar_Cl.addActionListener(this);
        this.interfaz.Btn_Modificar_Cl.addActionListener(this);
        this.interfaz.agrInt.ComboBox_Provincia.addActionListener(this);
        this.ActualizarTabla();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == this.interfaz.agrInt.BTN_Agregar1) {
            if ("AGREGAR".equals(this.interfaz.agrInt.BTN_Agregar1.getText())) {
                if (this.comprobarNombre(this.interfaz.agrInt.TXT_Nombre.getText()) && this.ValidarCedula(this.interfaz.agrInt.TXT_Cedula.getText()) && this.comprobarDireccion(this.interfaz.agrInt.TXT_Direccion.getText()) && this.interfaz.agrInt.ComboBox_Ciudad.getSelectedIndex() > -1 && this.interfaz.agrInt.ComboBox_Provincia.getSelectedIndex() > -1) {
                    try {
                        this.cliente.setCedula(Integer.parseInt(this.interfaz.agrInt.TXT_Cedula.getText().trim()));
                        this.cliente.setNombre(this.interfaz.agrInt.TXT_Nombre.getText().trim());
                        this.cliente.setDireccion(this.interfaz.agrInt.TXT_Direccion.getText().trim());
                        this.cliente.setCodProvincia(this.interfaz.agrInt.ComboBox_Provincia.getSelectedItem().toString().split("->")[1]);
                        this.cliente.setCodCiudad(this.interfaz.agrInt.ComboBox_Ciudad.getSelectedItem().toString().split("->")[1]);
                        this.modelo.registrar(cliente);
                        this.ActualizarTabla();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al registrar");
                    }
                    this.interfaz.agrInt.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar");
                }
            } else if ("MODIFICAR".equals(this.interfaz.agrInt.BTN_Agregar1.getText())) {
                if (this.comprobarNombre(this.interfaz.agrInt.TXT_Nombre.getText()) && this.ValidarCedula(this.interfaz.agrInt.TXT_Cedula.getText()) && this.comprobarDireccion(this.interfaz.agrInt.TXT_Direccion.getText()) && this.interfaz.agrInt.ComboBox_Ciudad.getSelectedIndex() > -1 && this.interfaz.agrInt.ComboBox_Provincia.getSelectedIndex() > -1) {
                    try {
                        this.cliente.setCedula(Integer.parseInt(this.interfaz.agrInt.TXT_Cedula.getText().trim()));
                        this.cliente.setNombre(this.interfaz.agrInt.TXT_Nombre.getText().trim());
                        this.cliente.setDireccion(this.interfaz.agrInt.TXT_Direccion.getText().trim());
                        this.cliente.setCodProvincia(this.interfaz.agrInt.ComboBox_Provincia.getSelectedItem().toString().split("->")[1]);
                        this.cliente.setCodCiudad(this.interfaz.agrInt.ComboBox_Ciudad.getSelectedItem().toString().split("->")[1]);
                        this.modelo.modificar(Integer.valueOf(this.interfaz.agrInt.TXT_Cedula.getText()), cliente);
                        this.ActualizarTabla();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al modificar");
                    }
                    this.interfaz.agrInt.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar");
                }
            }

        } else if (me.getSource() == this.interfaz.Btn_Buscar_Cl) {
            if (this.comprobarTexto(this.interfaz.Texto_Buscar_Cl.getText())) {
                try {
                    this.ActualizarTabla(this.interfaz.Texto_Buscar_Cl.getText(), this.interfaz.ComboBox_QueBusca.getSelectedItem().toString());
                } catch (SQLException ex) {
                }
            } else if (this.interfaz.Texto_Buscar_Cl.getText().trim().isEmpty()) {
                try {
                    this.ActualizarTabla();
                } catch (SQLException ex) {
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.interfaz.Btn_Agregar_Cl) {
            try {
                this.interfaz.agrInt.TXT_Nombre.setText("");
                this.interfaz.agrInt.TXT_Cedula.setText("");
                this.interfaz.agrInt.TXT_Direccion.setText("");
                this.interfaz.agrInt.BTN_Agregar1.setText("AGREGAR");
                this.interfaz.agrInt.TXT_Cedula.setEditable(true);
                this.interfaz.agrInt.setVisible(true);
                this.ActualizarProvincias();
                this.ActualizarCiudades(this.interfaz.agrInt.ComboBox_Provincia.getSelectedItem().toString().split("->")[1]);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error");
            }
        } else if (ae.getSource() == this.interfaz.Btn_Eliminar_Cl && this.interfaz.Tabla_Clientes.getSelectedRow() >= 0) {
            this.cliente.setCedula(Integer.parseInt(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Clientes.getSelectedRow(), 0).toString().trim()));
            this.cliente.setNombre(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Clientes.getSelectedRow(), 1).toString());
            this.cliente.setDireccion(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Clientes.getSelectedRow(), 2).toString());
            this.cliente.setCodProvincia(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Clientes.getSelectedRow(), 3).toString());
            this.cliente.setCodCiudad(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Clientes.getSelectedRow(), 4).toString());
            if (this.modelo.eliminar(cliente)) {
                JOptionPane.showMessageDialog(null, "ERROR");
            } else {
                JOptionPane.showMessageDialog(null, "Cliente Borrado");
                try {
                    this.ActualizarTabla();
                } catch (SQLException ex) {
                }
            }

        } else if (ae.getSource() == this.interfaz.Btn_Modificar_Cl && this.interfaz.Tabla_Clientes.getSelectedRow() >= 0) {

            try {
                this.interfaz.agrInt.BTN_Agregar1.setText("MODIFICAR");
                this.interfaz.agrInt.TXT_Cedula.setText(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Clientes.getSelectedRow(), 0).toString());
                this.interfaz.agrInt.TXT_Cedula.setEditable(false);
                this.interfaz.agrInt.TXT_Nombre.setText(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Clientes.getSelectedRow(), 1).toString());
                this.interfaz.agrInt.TXT_Direccion.setText(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Clientes.getSelectedRow(), 2).toString());
                this.interfaz.agrInt.setVisible(true);
                this.ActualizarProvincias();
                this.ActualizarCiudades(this.interfaz.agrInt.ComboBox_Provincia.getSelectedItem().toString().split("->")[1]);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error");
            }
        } else if (ae.getSource() == this.interfaz.agrInt.ComboBox_Provincia) {
            try {
                if (this.interfaz.agrInt.isShowing() && this.interfaz.agrInt.ComboBox_Provincia.getSelectedItem() != null) {
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
                    this.interfaz.tabla.addRow(new Object[]{String.valueOf(prod.getInt("cedula")), prod.getString("nombre"), prod.getString("direccion"), prod.getString("codProvincia"), prod.getString("codCiudad")});
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
                    this.interfaz.tabla.addRow(new Object[]{String.valueOf(prod.getInt("cedula")), prod.getString("nombre"), prod.getString("direccion"), prod.getString("codProvincia"), prod.getString("codCiudad")});
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
                JOptionPane.showMessageDialog(null, "No hay provincias, por favor ingrese provincias");
                this.interfaz.agrInt.dispose();
            } else {

                while (prod.next()) {
                    this.interfaz.agrInt.ComboBox_Provincia.addItem(prod.getString("nombreP") + "->" + prod.getString("codigoP"));
                }
            }
        } catch (SQLException e) {
        }

    }

    public void ActualizarCiudades(String s) throws SQLException {
        try {
            ResultSet prod = this.modelo.seleccionarCiudades(s);
            this.interfaz.agrInt.ComboBox_Ciudad.removeAllItems();
            if (prod == null) {
                JOptionPane.showMessageDialog(null, "No hay ciudades");
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

    public Interfaz_Clientes getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Interfaz_Clientes interfaz) {
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

    public boolean ValidarCedula(String Ced) {
        int val = 0;
        int comprobar;
        Ced = Ced.trim();
        if (this.comprobarNumeros(Ced) && Ced.length() == 10) {
            for (int i = 0; Ced.length() - 1 > i; i++) {
                int a = Integer.parseInt(String.valueOf(Ced.charAt(i)));
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
            if (!(comprobar == Integer.parseInt(String.valueOf(Ced.charAt(9))))) {
                JOptionPane.showMessageDialog(null, "Cedula Invalida");
            }
            return (comprobar == Integer.parseInt(String.valueOf(Ced.charAt(9))));
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese cedula diez numeros");
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
