package com.example.asset_qrgenerator_service.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class QRGenerator {
    public static byte[] generateQRCode(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);

        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream png = new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(qrImage, "png", png);
        return png.toByteArray();
    }
}
