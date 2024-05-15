/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Clases.Ciudad;
import Clases.Provincia;
import Interfaz.Interfaz_Provincias_Ciudades;
import Modelos.Modelo_CiuProv;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author User
 */
public class Controlador_Provincias_Ciudades implements ActionListener, MouseListener, ListSelectionListener {

    private Interfaz_Provincias_Ciudades interfaz;
    private Modelo_CiuProv modelo;
    private Provincia provincia;
    private Ciudad ciudad;

    public Controlador_Provincias_Ciudades(Interfaz_Provincias_Ciudades interfaz, Modelo_CiuProv modelo, Provincia provincia, Ciudad ciudad) throws SQLException {
        this.interfaz = interfaz;
        this.modelo = modelo;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.interfaz.Btn_Agregar_Prov.addActionListener(this);
        this.interfaz.agrInt.BTN_Agregar1.addMouseListener(this);
        this.interfaz.Btn_Modificar_Prov.addActionListener(this);
        this.interfaz.Btn_Eliminar_Prov.addActionListener(this);
        this.interfaz.Btn_Agregar_Ciu.addActionListener(this);
        this.interfaz.Btn_Modificar_Ciu.addActionListener(this);
        this.interfaz.Btn_Eliminar_Ciu.addActionListener(this);
        this.interfaz.Btn_Buscar.addMouseListener(this);
        this.interfaz.Tabla_Provincias.getSelectionModel().addListSelectionListener(this);
        this.ActualizarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.interfaz.Btn_Agregar_Prov) {
            this.interfaz.agrInt.TXT_Nombre.setText("");
            this.interfaz.agrInt.TXT_Codigo.setText("");
            this.interfaz.agrInt.BTN_Agregar1.setText("AGREGAR");
            this.interfaz.agrInt.TXT_Codigo.setEditable(true);
            this.interfaz.agrInt.setVisible(true);
        } else if (ae.getSource() == this.interfaz.Btn_Eliminar_Prov && this.interfaz.Tabla_Provincias.getSelectedRow() >= 0) {
            this.provincia.setCodigoP(this.interfaz.tablaProv.getValueAt(this.interfaz.Tabla_Provincias.getSelectedRow(), 1).toString());
            this.provincia.setNombreP(this.interfaz.tablaProv.getValueAt(this.interfaz.Tabla_Provincias.getSelectedRow(), 0).toString());
            if (this.modelo.eliminar(provincia)) {
                JOptionPane.showMessageDialog(null, "ERROR");
            } else {
                JOptionPane.showMessageDialog(null, "Provincia Borrado");
                try {
                    this.ActualizarTabla();
                    this.ActualizarTablaCiu();
                } catch (SQLException ex) {
                }
            }

        } else if (ae.getSource() == this.interfaz.Btn_Modificar_Prov && this.interfaz.Tabla_Provincias.getSelectedRow() >= 0) {
            this.interfaz.agrInt.BTN_Agregar1.setText("MODIFICAR");
            this.interfaz.agrInt.TXT_Codigo.setText(this.interfaz.tablaProv.getValueAt(this.interfaz.Tabla_Provincias.getSelectedRow(), 1).toString());
            this.interfaz.agrInt.TXT_Codigo.setEditable(false);
            this.interfaz.agrInt.TXT_Nombre.setText(this.interfaz.tablaProv.getValueAt(this.interfaz.Tabla_Provincias.getSelectedRow(), 0).toString());
            this.interfaz.agrInt.setVisible(true);

        } else if (ae.getSource() == this.interfaz.Btn_Agregar_Ciu && this.interfaz.Tabla_Provincias.getSelectedRow() >= 0) {
            this.interfaz.agrInt.TXT_Nombre.setText("");
            this.interfaz.agrInt.TXT_Codigo.setText("");
            this.interfaz.agrInt.BTN_Agregar1.setText("AÑADIR");
            this.interfaz.agrInt.TXT_Codigo.setEditable(true);
            this.interfaz.agrInt.setVisible(true);

        } else if (ae.getSource() == this.interfaz.Btn_Eliminar_Ciu && this.interfaz.Tabla_Provincias.getSelectedRow() >= 0 && this.interfaz.Tabla_Ciudades.getSelectedRow() >= 0) {
            this.ciudad.setCodigoC(this.interfaz.tablaCiu.getValueAt(this.interfaz.Tabla_Ciudades.getSelectedRow(), 1).toString());
            this.ciudad.setNombreC(this.interfaz.tablaCiu.getValueAt(this.interfaz.Tabla_Ciudades.getSelectedRow(), 0).toString());
            this.ciudad.setCodigoP(this.interfaz.tablaProv.getValueAt(this.interfaz.Tabla_Provincias.getSelectedRow(), 1).toString());
            if (this.modelo.eliminar(ciudad)) {
                JOptionPane.showMessageDialog(null, "ERROR");
            } else {
                JOptionPane.showMessageDialog(null, "Ciudad Borrado");
                try {
                    this.ActualizarTablaCiu();
                } catch (SQLException ex) {
                }
            }

        } else if (ae.getSource() == this.interfaz.Btn_Modificar_Ciu && this.interfaz.Tabla_Provincias.getSelectedRow() >= 0 && this.interfaz.Tabla_Ciudades.getSelectedRow() >= 0) {
            this.interfaz.agrInt.BTN_Agregar1.setText("CAMBIAR");
            this.interfaz.agrInt.TXT_Codigo.setText(this.interfaz.tablaCiu.getValueAt(this.interfaz.Tabla_Ciudades.getSelectedRow(), 1).toString());
            this.interfaz.agrInt.TXT_Codigo.setEditable(false);
            this.interfaz.agrInt.TXT_Nombre.setText(this.interfaz.tablaCiu.getValueAt(this.interfaz.Tabla_Ciudades.getSelectedRow(), 0).toString());
            this.interfaz.agrInt.setVisible(true);
        }
    }

    public Interfaz_Provincias_Ciudades getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Interfaz_Provincias_Ciudades interfaz) {
        this.interfaz = interfaz;
    }

    public boolean comprobarTexto(String texto) {
        texto = texto.trim();
        String patron = "^[a-zA-ZáéíóúñÁÉÍÓÚÑ0-9\\-./!#\\[\\]&()$=?¿´+{}\\s]+$";
        return texto.matches(patron);
    }

    public void ActualizarTabla(String buscar, String qBusco) throws SQLException {
        try {
            ResultSet prod = this.modelo.seleccionar(buscar, qBusco);
            this.interfaz.tablaProv.setRowCount(0);
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.tablaProv.addRow(new Object[]{prod.getString("nombreP"), prod.getString("codigoP")});
                }
            }
        } catch (SQLException e) {
        }
    }

    public void ActualizarTabla() throws SQLException {
        try {
            this.interfaz.tablaProv.setRowCount(0);
            ResultSet prod = this.modelo.seleccionar();
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.tablaProv.addRow(new Object[]{prod.getString("nombreP"), prod.getString("codigoP")});
                }
            }
        } catch (SQLException e) {
        }
    }

    public void ActualizarTablaCiu() throws SQLException {
        try {
            this.interfaz.tablaCiu.setRowCount(0);
            if (this.interfaz.Tabla_Provincias.getSelectedRow() >= 0) {
                ResultSet prod = this.modelo.seleccionarCiudades(this.interfaz.tablaProv.getValueAt(this.interfaz.Tabla_Provincias.getSelectedRow(), 1).toString());
                if (prod != null) {
                    while (prod.next()) {
                        this.interfaz.tablaCiu.addRow(new Object[]{prod.getString("nombreC"), prod.getString("codigoC")});
                    }
                }
            }
        } catch (SQLException e) {
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == this.interfaz.agrInt.BTN_Agregar1) {
            if ("AGREGAR".equals(this.interfaz.agrInt.BTN_Agregar1.getText())) {
                if (this.comprobarTexto(this.interfaz.agrInt.TXT_Nombre.getText()) && this.comprobarTexto(this.interfaz.agrInt.TXT_Codigo.getText())) {
                    try {
                        this.provincia.setCodigoP(this.interfaz.agrInt.TXT_Codigo.getText());
                        this.provincia.setNombreP(this.interfaz.agrInt.TXT_Nombre.getText());
                        if (this.modelo.registrar(provincia)) {
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
            } else if ("MODIFICAR".equals(this.interfaz.agrInt.BTN_Agregar1.getText())) {
                if (this.comprobarTexto(this.interfaz.agrInt.TXT_Nombre.getText())) {
                    try {
                        this.provincia.setCodigoP(this.interfaz.agrInt.TXT_Codigo.getText());
                        this.provincia.setNombreP(this.interfaz.agrInt.TXT_Nombre.getText());
                        if (this.modelo.modificar(this.interfaz.agrInt.TXT_Codigo.getText(), provincia) > 0) {
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
            } else if ("AÑADIR".equals(this.interfaz.agrInt.BTN_Agregar1.getText())) {
                if (this.comprobarTexto(this.interfaz.agrInt.TXT_Nombre.getText()) && this.comprobarTexto(this.interfaz.agrInt.TXT_Codigo.getText())) {
                    try {
                        this.ciudad.setCodigoC(this.interfaz.agrInt.TXT_Codigo.getText());
                        this.ciudad.setNombreC(this.interfaz.agrInt.TXT_Nombre.getText());
                        this.ciudad.setCodigoP(this.interfaz.tablaProv.getValueAt(this.interfaz.Tabla_Provincias.getSelectedRow(), 1).toString());
                        if (this.modelo.registrar(ciudad)) {
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
            } else if ("CAMBIAR".equals(this.interfaz.agrInt.BTN_Agregar1.getText())) {
                if (this.comprobarTexto(this.interfaz.agrInt.TXT_Nombre.getText())) {
                    try {
                        this.ciudad.setCodigoC(this.interfaz.agrInt.TXT_Codigo.getText());
                        this.ciudad.setNombreC(this.interfaz.agrInt.TXT_Nombre.getText());
                        this.ciudad.setCodigoP(this.interfaz.tablaProv.getValueAt(this.interfaz.Tabla_Provincias.getSelectedRow(), 1).toString());
                        if (this.modelo.modificar(this.interfaz.agrInt.TXT_Codigo.getText(), ciudad) > 0) {
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

            } else if (me.getSource() == this.interfaz.Btn_Buscar) {
                if (this.comprobarTexto(this.interfaz.Texto_Buscar_Prov.getText())) {
                    try {
                        this.ActualizarTabla(this.interfaz.Texto_Buscar_Prov.getText(), this.interfaz.ComboBox_QueBusca.getSelectedItem().toString());
                    } catch (SQLException ex) {
                    }
                } else if (this.interfaz.Texto_Buscar_Prov.getText().trim().isEmpty()) {
                    try {
                        this.ActualizarTabla();
                    } catch (SQLException ex) {
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ingreso caracteres no permitidos", "ERROR", 0);
                }
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (lse.getSource() == this.interfaz.Tabla_Provincias.getSelectionModel()) {
            try {
                this.ActualizarTablaCiu();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error, No hay ciudades");
            }
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
