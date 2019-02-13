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
package hello;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

/**
 * Simple filling out form example.
 */
@Controller
public class C04E03_CreateAndFill {

    public static final String DEST = "results/chapter04/create_and_fill.pdf";

    public static void confirmFlattenForm(@ModelAttribute Member member) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C04E03_CreateAndFill().createPdf(DEST, member);
    }

    public void createPdf(String dest, Member member) throws IOException {

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));

        // Initialize document
        Document doc = new Document(pdf);

        PdfAcroForm form = C04E02_JobApplication.addAcroForm(doc);
        Map<String, PdfFormField> fields = form.getFormFields();

        String name = member.getName();
        String language = member.getLanguage();
        String experience = member.getExperience();
        String shift = member.getShift();
        String info = member.getInformation();
        
        fields.get("name").setValue(name);
        fields.get("language").setValue(language);
        
        int experience1 = experience.indexOf("cooking");
        int experience2 = experience.indexOf("driving");
        int experience3 = experience.indexOf("software development");
        
        if (-1 != experience1) {
            fields.get("experience1").setValue("Yes");
        } else {
            fields.get("experience1").setValue("Off");
        }
        if (-1 != experience2) {
            fields.get("experience2").setValue("Yes");
        } else {
            fields.get("experience2").setValue("Off");
        }
        if (-1 != experience3) {
            fields.get("experience3").setValue("Yes");
        } else {
            fields.get("experience3").setValue("Off");
        }
        
        fields.get("shift").setValue(shift);
        fields.get("info").setValue(info);
        
        form.flattenFields();
        
        try {
			BarcodePlacement.manipulatePdf(pdf, doc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        doc.close();

    }
}
