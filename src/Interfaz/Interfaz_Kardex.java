/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author User
 */
public class Interfaz_Kardex extends javax.swing.JPanel {

    /**
     * Creates new form NewJPanel
     */
    public Interfaz_Kardex() {
        initComponents();
        String columnas[] = {"Fecha", "Descripción", "Entrada", "Salida", "Inv. Final"};
        this.tabla.setColumnIdentifiers(columnas);
        this.Tabla_Clientes.setModel(tabla);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ComboBox_QueBusca = new javax.swing.JComboBox<>();
        Txt_Nombre_Proveedor = new javax.swing.JTextField();
        Txt_RUC_Proveedor = new javax.swing.JTextField();
        Btn_Buscar_Cl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Clientes = new javax.swing.JTable();
        Texto_Buscar_Cl = new javax.swing.JTextField();
        Combo_Productos = new javax.swing.JComboBox<>();
        Img_QR = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(730, 250));
        setPreferredSize(new java.awt.Dimension(930, 490));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ComboBox_QueBusca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Codigo" }));
        add(ComboBox_QueBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, 70, 30));

        Txt_Nombre_Proveedor.setEnabled(false);
        add(Txt_Nombre_Proveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 110, 140, 30));

        Txt_RUC_Proveedor.setEnabled(false);
        add(Txt_RUC_Proveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, 140, 30));

        Btn_Buscar_Cl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/BTN_Buscar_Bl.png"))); // NOI18N
        add(Btn_Buscar_Cl, new org.netbeans.lib.awtextra.AbsoluteConstraints(415, 105, 40, 40));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        Tabla_Clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cedula1", "Nombre", "Direccion", "Provincia", "Ciudad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla_Clientes.setGridColor(new java.awt.Color(102, 102, 102));
        Tabla_Clientes.setRowHeight(30);
        Tabla_Clientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(Tabla_Clientes);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 850, 310));

        Texto_Buscar_Cl.setText("Buscar");
        add(Texto_Buscar_Cl, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 110, 30));

        add(Combo_Productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 170, 30));

        Img_QR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(Img_QR, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, 150, 150));

        jLabel3.setFont(new java.awt.Font("Yu Gothic Medium", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Proveedor:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 90, 30));

        jLabel4.setFont(new java.awt.Font("Yu Gothic Medium", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Tarjeta Kardex");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 380, 50));

        jLabel2.setFont(new java.awt.Font("Yu Gothic Medium", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Producto:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 80, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Fondo9.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 500));
    }// </editor-fold>//GEN-END:initComponents
    public void ordenarPorFecha() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(this.tabla);
        this.Tabla_Clientes.setRowSorter(sorter);
        Comparator<Object> dateComparator = new Comparator<Object>() {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public int compare(Object obj1, Object obj2) {
                Date date1 = null;
                Date date2 = null;

                try {
                    date1 = dateFormat.parse(obj1.toString());
                    date2 = dateFormat.parse(obj2.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return date1.compareTo(date2);
            }
        };
        sorter.setComparator(0, dateComparator);
        sorter.toggleSortOrder(0);
    }
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    public DefaultTableModel tabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Hacer todas las celdas no editables
        }
    };
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel Btn_Buscar_Cl;
    public javax.swing.JComboBox<String> ComboBox_QueBusca;
    public javax.swing.JComboBox<String> Combo_Productos;
    public javax.swing.JLabel Img_QR;
    public javax.swing.JTable Tabla_Clientes;
    public javax.swing.JTextField Texto_Buscar_Cl;
    public javax.swing.JTextField Txt_Nombre_Proveedor;
    public javax.swing.JTextField Txt_RUC_Proveedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
