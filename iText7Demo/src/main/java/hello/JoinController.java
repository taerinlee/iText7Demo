package hello;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    @GetMapping("/joinForm")
    public String joinForm(Model model) {
        model.addAttribute("member", new Member());
        return "joinForm";
    }

    @PostMapping("/joinForm")
    public String joinSubmit(@ModelAttribute Member member) throws IOException {
    	C04E03_CreateAndFill.confirmFlattenForm(member);
        return "result";
    }

}
