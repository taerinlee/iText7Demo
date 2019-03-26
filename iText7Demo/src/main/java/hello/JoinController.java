package hello;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.itextpdf.C04E03_CreateAndFill;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;

@Controller
public class JoinController {

    @GetMapping("/joinForm")
    public String joinForm(Model model) {
        model.addAttribute("member", new Member());
        return "joinForm";
    }

    @PostMapping("/joinForm")
    public String joinSubmit(@ModelAttribute Member member) throws IOException {
        return "result";
    }

    @GetMapping("/getData")
    public String getData(@ModelAttribute Member member) throws IOException {
    	
    	String pdfPath = "upload-dir/job_application.pdf";

        PdfReader reader = new PdfReader(pdfPath);
        PdfDocument document = new PdfDocument(reader);
        PdfAcroForm acroForm = PdfAcroForm.getAcroForm(document, false);
        Map<String,PdfFormField> fields = acroForm.getFormFields();
        String experience ="";
        
        for (String fldName : fields.keySet()) {
        	if (fldName.equals("name")) {
                member.setName(fields.get( fldName ).getValueAsString());
        	} else if (fldName.equals("language")) {
                member.setLanguage(fields.get( fldName ).getValueAsString());
        	} else if (fldName.equals("experience1")) {
        		if (fields.get( fldName ).getValueAsString().equals("Yes")) {
        			experience = "cooking";
        		}
        	} else if (fldName.equals("experience2")) {
        		if (fields.get( fldName ).getValueAsString().equals("Yes")) {
        			if (experience.isEmpty()) {
        				experience = "driving";
        			} else {
            			experience = experience +", driving";
        			}
        		}
        	} else if (fldName.equals("experience3")) {
        		if (fields.get( fldName ).getValueAsString().equals("Yes")) {
        			if (experience.isEmpty()) {
        				experience = "software development";
        			} else {
            			experience = experience +", software development";
        			}
        		}
        		member.setExperience(experience);
        	} else if (fldName.equals("shift")) {
                member.setShift(fields.get( fldName ).getValueAsString());
        	} else if (fldName.equals("info")) {
                member.setInformation(fields.get( fldName ).getValueAsString());
        	}
            System.out.println( fldName + ": " + fields.get( fldName ).getValueAsString() );
        }
        document.close();
        reader.close();
 	    
        return "result";
    }

    @PostMapping("/downXFAForm")
    public String downXFAForm(@ModelAttribute Member member, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	File file = C04E03_CreateAndFill.confirmFlattenForm(member);
    	
    	renderMergedOutputModel(file, request, response);
    	
    	return "/result";
    }
    
    protected void renderMergedOutputModel(File file, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setContentType("application/download; charset=utf-8");
		response.setContentLength((int)file.length());
		
		String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1;
		String fileName = null;
		
		if(ie) {
			fileName = URLEncoder.encode(file.getName(), "utf-8");
		} else {
			fileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1");
		}
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch(IOException ioe) {}
			}
		}
		out.flush();
		out.close();
	}

}
