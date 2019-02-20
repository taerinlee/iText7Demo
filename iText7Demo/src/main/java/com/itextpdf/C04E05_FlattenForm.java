/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2019 iText Group NV
    Authors: iText Software.

    For more information, please contact iText Software at this address:
    sales@itextpdf.com
 */
/*
 * This example is part of the iText 7 tutorial.
 */
package com.itextpdf;

import java.io.IOException;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.PdfDocument;

/**
 * Simple filling out form example.
 */
public class C04E05_FlattenForm {

    public static void manipulatePdf(String src, PdfDocument pdf) throws IOException {

        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        form.flattenFields();
    }
}
