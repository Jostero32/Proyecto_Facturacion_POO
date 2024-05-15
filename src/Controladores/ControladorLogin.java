/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Clases.Encriptar;
import Clases.Usuario;
import Interfaz.Interfaz_Login;
import Modelos.ModeloLogin;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class ControladorLogin implements MouseListener {

    private Usuario usuario;
    private Interfaz_Login interfaz;
    private ModeloLogin modelo;
    private Controlador_Programa menu;
    private Encriptar encriptar=new Encriptar();

    public ControladorLogin(Usuario usuario, Interfaz_Login interfaz, ModeloLogin modelo, Controlador_Programa menu) {
        this.usuario = usuario;
        this.interfaz = interfaz;
        this.modelo = modelo;
        this.menu = menu;
        this.interfaz.BotonEntrar.addMouseListener(this);
        this.menu.getInterfaz().Btn_CerrarSesion.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        try {
            if (me.getSource() == this.interfaz.BotonEntrar) {
                ResultSet rs = null;
                ResultSet rs1 = null;
                usuario.setUsuario(this.encriptar.encriptar(interfaz.TextField_Usuario.getText()));
                usuario.setClave(this.encriptar.encriptar(String.valueOf(interfaz.TextField_Clave.getPassword())));
                rs1 = modelo.seleccionarIntFallido(this.encriptar.encriptar(interfaz.TextField_Usuario.getText()));
                rs = modelo.seleccionar(usuario);
                boolean bloqueo = false;
                int fallido = -1;
                while (rs1.next()) {
                    bloqueo = rs1.getBoolean("bloqueo");
                    fallido = rs1.getInt("int_Fallido");
                }
                boolean b=rs.next();
                boolean b1 = rs != null && b && !bloqueo;
                if (b1) {
                    rs.previous();
                    while (rs.next()) {
                        usuario.setUsuario(rs.getString("usuario"));
                        usuario.setClave(rs.getString("clave"));
                        usuario.setInt_fallido(rs.getInt("int_fallido"));
                        usuario.setBloqueo(rs.getBoolean("bloqueo"));
                        usuario.setP_Clientes(rs.getBoolean("P_Clientes"));
                        usuario.setP_Factura(rs.getBoolean("P_Factura"));
                        usuario.setP_Kardex(rs.getBoolean("P_Kardex"));
                        usuario.setP_Productos(rs.getBoolean("P_Productos"));
                        usuario.setP_ProvCiu(rs.getBoolean("P_ProvCiu"));
                        usuario.setP_Proveedores(rs.getBoolean("P_Proveedores"));
                        usuario.setP_Usuarios(rs.getBoolean("P_Usuarios"));
                        usuario.setP_Venta(rs.getBoolean("P_Venta"));
                    }
                    menu.setUsuario(usuario);
                    menu.insertarPestañas();
                    menu.getInterfaz().setVisible(true);
                    interfaz.dispose();
                    modelo.intentoFallido(usuario.getUsuario(), 0, false);
                } else if (bloqueo) {
                    JOptionPane.showMessageDialog(null, "El usuario esta bloqueado", "ERROR", 0);
                } else {
                    if (fallido > 2) {
                        JOptionPane.showMessageDialog(null, "El usuario se ha bloqueado", "ERROR", 0);
                        modelo.intentoFallido(this.encriptar.encriptar(interfaz.TextField_Usuario.getText()), 0, true);
                    } else if (!b && fallido<0) {
                        JOptionPane.showMessageDialog(null, "Usuario no existe", "ERROR", 0);
                    } else {
                        modelo.intentoFallido(this.encriptar.encriptar(interfaz.TextField_Usuario.getText()), fallido + 1, false);
                        JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "ERROR", 0);
                    }
                }
            }else if(me.getSource()==this.menu.getInterfaz().Btn_CerrarSesion){
                this.menu.getInterfaz().dispose();
                this.interfaz.setVisible(true);
                this.usuario=new Usuario();
                this.interfaz.TextField_Clave.setText("");
                this.interfaz.TextField_Usuario.setText("");
            }
        } catch (HeadlessException | SQLException ex) {
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
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
