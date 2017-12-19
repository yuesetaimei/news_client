package com.tlkg.news.app.util;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * Created by wuxiaoqi on 2017/12/19.
 * 二维码工具类
 */

public final class ZxQRUtil {

    /**
     * 生成一个二维码
     *
     * @param text
     * @return
     */
    public static Bitmap createQR(String text, int width, int height) {
        Bitmap bitmap = null;
        BitMatrix bitMatrix;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            bitMatrix = deleteWhite(bitMatrix);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
        } catch (Exception e) {

        }
        return bitmap;
    }

    /**
     * 擦除白边
     *
     * @param matrix
     * @return
     */
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }
}
