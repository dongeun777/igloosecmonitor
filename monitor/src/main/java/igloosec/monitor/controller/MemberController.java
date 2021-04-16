package igloosec.monitor.controller;



import igloosec.monitor.service.MemberService;
import igloosec.monitor.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public String Login(MemberVo memberVo, HttpSession session) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        MemberVo user = (MemberVo) memberService.loadUserByUsername(memberVo.getEmail());
        if (user == null){
            return "redirect:/";
        }else{

            if(!passwordEncoder.matches(memberVo.getPasswd(), user.getPassword())) {

                return "redirect:/";
            }else{

                session.setAttribute("loginCheck",true);
                session.setAttribute("email",memberVo.getEmail());
                session.setAttribute("auth",user.getAuth());
                System.out.println(user.getAuth());
              //  session.setAttribute("auth",memberVo.getAuthorities());
                return "redirect:/main";
            }

        }




                

    }

    @RequestMapping(value="/logout")
    public String logoutProcess(HttpSession session) {

        session.setAttribute("loginCheck",null);
        session.setAttribute("id",null);

        return "/";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        return "join";
    }



    @PostMapping("/user/join")
    public String joinMember(MemberVo memberVo) {
        memberService.joinUser(memberVo);
        return "redirect:/join";
    }


}
