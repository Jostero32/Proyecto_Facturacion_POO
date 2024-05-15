/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QR;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author User
 */
public class QR {

    public void generarQR(String nombre) throws WriterException, IOException {
        int size = 220;
        String FileType = "png";
        // Elegir la ruta de la imagen
        String filePath = getClass().getResource("/").getPath().substring(1);
        
        filePath=getClass().getResource("/").getPath().substring(1, filePath.length()-13);
        QRCodeWriter qrcode = new QRCodeWriter();
        try {
            BitMatrix matrix = qrcode.encode(nombre, BarcodeFormat.QR_CODE, size, size);
            File f = new File(filePath+"src/QR/" + nombre + "." + FileType);
            int matrixWidth = matrix.getWidth();
            BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D gd = (Graphics2D) image.getGraphics();
            gd.setColor(Color.WHITE); // Fondo
            gd.fillRect(0, 0, size, size);
            gd.setColor(Color.black); // Qr

            for (int b = 0; b < matrixWidth; b++) {
                for (int j = 0; j < matrixWidth; j++) {
                    if (matrix.get(b, j)) {
                        gd.fillRect(b, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, FileType, f);
        } catch (Exception ex) {
            Logger.getLogger(QR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

