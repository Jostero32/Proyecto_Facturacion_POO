/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public class Factura {
    private String codF;
    private Date fecha;
    private Productos producto=new Productos();
    private String tipoFactura;
    private String codCl;
    private int cantidad;

    public String getCodF() {
        return this.codF;
    }

    public void setCodF(String codF) {
        this.codF = codF;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Productos getProducto() {
        return this.producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public String getTipoFactura() {
        return this.tipoFactura;
    }

    public void setTipoFactura(String tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public String getCodCl() {
        return this.codCl;
    }

    public void setCodCl(String codCl) {
        this.codCl = codCl;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}
