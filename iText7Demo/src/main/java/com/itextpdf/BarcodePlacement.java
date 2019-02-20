/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf;

import com.itextpdf.barcodes.BarcodePDF417;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

public class BarcodePlacement {

	public static void manipulatePdf(PdfDocument pdf, Document doc) throws Exception {
        Image img = createBarcode(1, 1, pdf);
        doc.add(new Paragraph(String.format("This barcode measures %s by %s user units",
                img.getImageScaledWidth(), img.getImageScaledHeight())));
        doc.add(img);
    }

    // We've changed the order of arguments (in comparison with itext5 example) to make it more clear
    public static Image createBarcode(float mw, float mh, PdfDocument pdf) {
        BarcodePDF417 barcode = new BarcodePDF417();
        barcode.setCode("BarcodePDF417 barcode");
        return new Image(barcode.createFormXObject(ColorConstants.BLACK, pdf)).scale(mw, mh);
    }
}
