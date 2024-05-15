/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Clases.Cliente;
import Clases.Encriptar;
import Clases.Usuario;
import Interfaz.Interfaz_Agregar_Cliente;
import Interfaz.Interfaz_Clientes;
import Interfaz.Interfaz_Usuarios;
import Interfaz.Interfaz_agregarProd;
import Modelos.Modelo_Clientes;
import Modelos.Modelo_Usuarios;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author User
 */
public class Controlador_Usuarios implements MouseListener, ActionListener, ListSelectionListener {

    private Modelo_Usuarios modelo;
    private Interfaz_Usuarios interfaz;
    private Usuario usuario;
    private Encriptar encriptar = new Encriptar();

    public Controlador_Usuarios(Modelo_Usuarios modelo, Interfaz_Usuarios interfaz, Usuario usuario) throws SQLException {
        this.modelo = modelo;
        this.interfaz = interfaz;
        this.usuario = usuario;
        this.interfaz.agrInt.BTN_Agregar1.addMouseListener(this);
        this.interfaz.Btn_Agregar_U.addActionListener(this);
        this.interfaz.Btn_Buscar.addMouseListener(this);
        this.interfaz.Btn_Eliminar_U.addActionListener(this);
        this.interfaz.Btn_Modificar_U.addActionListener(this);
        this.interfaz.Tabla_Usuarios.getSelectionModel().addListSelectionListener(this);
        this.ActualizarTabla();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == this.interfaz.agrInt.BTN_Agregar1) {
            if ("AGREGAR".equals(this.interfaz.agrInt.BTN_Agregar1.getText())) {
                if (this.comprobarTexto(this.interfaz.agrInt.Txt_Usuario.getText()) && this.comprobarTexto(String.valueOf(this.interfaz.agrInt.Txt_Clave.getPassword()))) {
                    try {
                        this.usuario.setClave(this.encriptar.encriptar(String.valueOf(this.interfaz.agrInt.Txt_Clave.getPassword()).trim()));
                        this.usuario.setUsuario(this.encriptar.encriptar(this.interfaz.agrInt.Txt_Usuario.getText().trim()));
                        this.usuario.setP_Clientes(this.interfaz.agrInt.Clientes.isSelected());
                        this.usuario.setP_Factura(this.interfaz.agrInt.Compra.isSelected());
                        this.usuario.setP_Kardex(this.interfaz.agrInt.Kardex.isSelected());
                        this.usuario.setP_Productos(this.interfaz.agrInt.Productos.isSelected());
                        this.usuario.setP_ProvCiu(this.interfaz.agrInt.Provincias.isSelected());
                        this.usuario.setP_Proveedores(this.interfaz.agrInt.Proveedores.isSelected());
                        this.usuario.setP_Usuarios(this.interfaz.agrInt.Usuario.isSelected());
                        this.usuario.setP_Venta(this.interfaz.agrInt.Venta.isSelected());
                        if (this.modelo.registrar(usuario)) {
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
                if (this.comprobarTexto(this.interfaz.agrInt.Txt_Usuario.getText()) && this.comprobarTexto(String.valueOf(this.interfaz.agrInt.Txt_Clave.getPassword()))) {
                    try {
                        this.usuario.setClave(this.encriptar.encriptar(String.valueOf(this.interfaz.agrInt.Txt_Clave.getPassword()).trim()));
                        this.usuario.setUsuario(this.encriptar.encriptar(this.interfaz.agrInt.Txt_Usuario.getText().trim()));
                        this.usuario.setP_Clientes(this.interfaz.agrInt.Clientes.isSelected());
                        this.usuario.setP_Factura(this.interfaz.agrInt.Compra.isSelected());
                        this.usuario.setP_Kardex(this.interfaz.agrInt.Kardex.isSelected());
                        this.usuario.setP_Productos(this.interfaz.agrInt.Productos.isSelected());
                        this.usuario.setP_ProvCiu(this.interfaz.agrInt.Provincias.isSelected());
                        this.usuario.setP_Proveedores(this.interfaz.agrInt.Proveedores.isSelected());
                        this.usuario.setP_Usuarios(this.interfaz.agrInt.Usuario.isSelected());
                        this.usuario.setP_Venta(this.interfaz.agrInt.Venta.isSelected());
                        this.modelo.modificar(this.encriptar.encriptar(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Usuarios.getSelectedRow(), 0).toString()), this.encriptar.encriptar(String.valueOf(this.interfaz.Txt_Clave.getPassword())), usuario);
                        if (this.modelo.modificar(this.encriptar.encriptar(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Usuarios.getSelectedRow(), 0).toString()), this.encriptar.encriptar(String.valueOf(this.interfaz.Txt_Clave.getPassword())), usuario) > 0) {
                            JOptionPane.showMessageDialog(null, "ERROR");
                        } else {
                            try {
                                this.ActualizarTabla();
                            } catch (SQLException ex) {
                            }
                        }
                    } catch (HeadlessException | UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
                        JOptionPane.showMessageDialog(null, "Error al modificar");
                    }
                    this.interfaz.agrInt.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar");
                }
            }

        } else if (me.getSource() == this.interfaz.Btn_Buscar) {
            if (this.comprobarTexto(this.interfaz.Texto_Buscar_U.getText())) {
                try {
                    this.ActualizarTabla(this.interfaz.Texto_Buscar_U.getText());
                } catch (SQLException ex) {
                }
            } else if (this.interfaz.Texto_Buscar_U.getText().trim().isEmpty()) {
                try {
                    this.ActualizarTabla();
                } catch (SQLException ex) {
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.interfaz.Btn_Agregar_U) {
            this.interfaz.agrInt.Txt_Usuario.setText("");
            this.interfaz.agrInt.Txt_Clave.setText("");
            this.interfaz.agrInt.Clientes.setSelected(false);
            this.interfaz.agrInt.Compra.setSelected(false);
            this.interfaz.agrInt.Kardex.setSelected(false);
            this.interfaz.agrInt.Productos.setSelected(false);
            this.interfaz.agrInt.Proveedores.setSelected(false);
            this.interfaz.agrInt.Provincias.setSelected(false);
            this.interfaz.agrInt.Usuario.setSelected(false);
            this.interfaz.agrInt.Venta.setSelected(false);
            this.interfaz.agrInt.BTN_Agregar1.setText("AGREGAR");
            this.interfaz.agrInt.setVisible(true);
        } else if (ae.getSource() == this.interfaz.Btn_Eliminar_U && this.interfaz.Tabla_Usuarios.getSelectedRow() >= 0) {
            this.usuario.setClave(String.valueOf(this.interfaz.Txt_Clave.getPassword()));
            this.usuario.setUsuario(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Usuarios.getSelectedRow(), 0).toString());
            this.usuario.setP_Clientes(this.interfaz.Ch_Clientes.isSelected());
            this.usuario.setP_Factura(this.interfaz.Ch_Compra.isSelected());
            this.usuario.setP_Kardex(this.interfaz.Ch_Kardex.isSelected());
            this.usuario.setP_Productos(this.interfaz.Ch_Productos.isSelected());
            this.usuario.setP_ProvCiu(this.interfaz.Ch_Provincias.isSelected());
            this.usuario.setP_Proveedores(this.interfaz.Ch_Proveedores.isSelected());
            this.usuario.setP_Usuarios(this.interfaz.Ch_Usuarios.isSelected());
            this.usuario.setP_Venta(this.interfaz.Ch_Venta.isSelected());
            if (this.modelo.eliminar(usuario)) {
                JOptionPane.showMessageDialog(null, "ERROR");
            } else {
                JOptionPane.showMessageDialog(null, "Usuario Borrado");
                try {
                    this.ActualizarTabla();
                } catch (SQLException ex) {
                }
            }

        } else if (ae.getSource() == this.interfaz.Btn_Modificar_U && this.interfaz.Tabla_Usuarios.getSelectedRow() >= 0) {
            this.interfaz.agrInt.Txt_Usuario.setText(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Usuarios.getSelectedRow(), 0).toString());
            this.interfaz.agrInt.Txt_Clave.setText(String.valueOf(this.interfaz.Txt_Clave.getPassword()));
            this.interfaz.agrInt.Clientes.setSelected(this.interfaz.Ch_Clientes.isSelected());
            this.interfaz.agrInt.Compra.setSelected(this.interfaz.Ch_Compra.isSelected());
            this.interfaz.agrInt.Kardex.setSelected(this.interfaz.Ch_Kardex.isSelected());
            this.interfaz.agrInt.Productos.setSelected(this.interfaz.Ch_Productos.isSelected());
            this.interfaz.agrInt.Proveedores.setSelected(this.interfaz.Ch_Proveedores.isSelected());
            this.interfaz.agrInt.Provincias.setSelected(this.interfaz.Ch_Provincias.isSelected());
            this.interfaz.agrInt.Usuario.setSelected(this.interfaz.Ch_Usuarios.isSelected());
            this.interfaz.agrInt.Venta.setSelected(this.interfaz.Ch_Venta.isSelected());
            this.interfaz.agrInt.setVisible(true);
            this.interfaz.agrInt.BTN_Agregar1.setText("MODIFICAR");
        }
    }

    public void ActualizarTabla() throws SQLException {
        try {
            this.interfaz.tabla.setRowCount(0);
            ResultSet prod = this.modelo.seleccionar();
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.tabla.addRow(new Object[]{this.encriptar.desencriptar(prod.getString("usuario"))});
                }
            }
        } catch (SQLException e) {
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ActualizarTabla(String buscar) throws SQLException {
        try {
            ResultSet prod = this.modelo.seleccionar(buscar);
            this.interfaz.tabla.setRowCount(0);
            if (prod != null) {
                while (prod.next()) {
                    this.interfaz.tabla.addRow(new Object[]{this.encriptar.desencriptar(prod.getString("usuario"))});
                }
            }
        } catch (SQLException e) {
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void actualizar(String usuario) throws SQLException {
        ResultSet rs = this.modelo.seleccionar(usuario);
        if (rs != null) {
            while (rs.next()) {
                try {
                    this.interfaz.Txt_Clave.setText(this.encriptar.desencriptar(rs.getString("clave")));
                    this.interfaz.Ch_Clientes.setSelected(rs.getBoolean("P_Clientes"));
                    this.interfaz.Ch_Compra.setSelected(rs.getBoolean("P_Factura"));
                    this.interfaz.Ch_Kardex.setSelected(rs.getBoolean("P_Kardex"));
                    this.interfaz.Ch_Productos.setSelected(rs.getBoolean("P_Productos"));
                    this.interfaz.Ch_Proveedores.setSelected(rs.getBoolean("P_Proveedores"));
                    this.interfaz.Ch_Provincias.setSelected(rs.getBoolean("P_ProvCiu"));
                    this.interfaz.Ch_Usuarios.setSelected(rs.getBoolean("P_Usuarios"));
                    this.interfaz.Ch_Venta.setSelected(rs.getBoolean("P_Venta"));
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalBlockSizeException ex) {
                    Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadPaddingException ex) {
                    Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == this.interfaz.Tabla_Usuarios.getSelectionModel() && this.interfaz.Tabla_Usuarios.getSelectedRow() >= 0) {
            try {
                this.actualizar(this.encriptar.encriptar(this.interfaz.tabla.getValueAt(this.interfaz.Tabla_Usuarios.getSelectedRow(), 0).toString()));
            } catch (SQLException ex) {
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(Controlador_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Interfaz_Usuarios getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Interfaz_Usuarios interfaz) {
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
