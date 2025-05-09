package com.pricedrop.Controller;

import com.pricedrop.dao.UserRepository;
import com.pricedrop.entities.User;
import com.pricedrop.helper.Message;
import com.pricedrop.services.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.Random;

import com.pricedrop.dao.SequenceGeneratorRepository;

@Controller
@ComponentScan("com.pricedrop.services.SessionHelper")
public class MyController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SequenceGeneratorRepository sequenceGenerator;


    @RequestMapping("/")
    public String Home(Principal principal){
        if(principal!=null){
            return "redirect:/user/dashboard";
        }
        else {
            return "home1";
        }
    }

    @RequestMapping("/about")
    public String About(Principal principal){
        if(principal!=null){
            return "redirect:/user/dashboard";
        }
        else {
            return "about1";
        }
    }

    @RequestMapping("/signup")
    public String SignUp(Model model,Principal principal){
        if(principal!=null){
            return "redirect:/user/dashboard";
        }
        else {
            model.addAttribute("user",new User());
            return "signup1";
        }

    }

    @RequestMapping("/signin")
    public String SignIn(Principal principal){
        if(principal!=null){
            return "redirect:/user/dashboard";
        }
        else {
            return "signin";
        }


    }

    @PostMapping("/do_register")
    public String registerUser(@Valid @ModelAttribute("user") User user, HttpSession session){
        try{
            User user1 = this.userRepository.getUserByUserName(user.getEmail());
            if(user1 == null){
                user.setRole("ROLE_USER");
                System.out.println(user.getPassword());
                user.setPassword(passwordEncoder.encode(user.getPassword()));
               user.setU_id(sequenceGenerator.getNextUserId());
                System.out.println(user);
                this.userRepository.save(user);
                session.setAttribute("message", new Message("Successfully Registered!!", "alert-success"));
                return "redirect:/signup";
            }
            else{
                session.setAttribute("message", new Message("Email-Id Already Exist!!", "alert-danger"));
                session.setAttribute("user",user);
                return "redirect:/signup";
            }
        }catch (Exception e){
            session.setAttribute("message", new Message("Something went wrong!!", "alert-danger"));
            return "signup";
        }

    }

    @PostMapping("/sendOTP")
    public String verifyUser(@Valid @ModelAttribute("user") User user,BindingResult result, HttpSession session, Model model){
        try{
            User user1 = this.userRepository.getUserByUserName(user.getEmail());
            if(result.hasErrors()){
                model.addAttribute("user", user);
                return "signup1";
            }
            if(user1 == null){
                Random random = new Random();
                model.addAttribute("title","Email");
                //generating otp of 4 digit
                int otp = random.nextInt(9999999);
                System.out.println(user.getEmail());
                System.out.println(otp);

                //write a code for send otp to email
                String subject = "OTP for verification of email from price Drop Alert";
                String message = "<div style='border:1px solid #e2e2e2: padding:20px'>" +
                        "<h1>" +
                        "OTP is " +
                        "<b>" + otp +
                        "</n>" +
                        "</h1>" +
                        "</div>";
                String to = user.getEmail();

        boolean flag = this.emailService.sendEmail(subject,message,to);
//                boolean flag = true;
                if(flag){

                    session.setAttribute("user",user);
                    session.setAttribute("myotp",otp);
                    session.setAttribute("email",user.getEmail());
//                    session.setAttribute("message",new Message("We have sent OTP to your email.."," alert-success "));
                    return "verify_otp1";
                }
                else{
                    session.setAttribute("message",new Message("Enter your correct email-Id!!"," alert-danger "));
                    return "signup1";
                }
            }
            else{
                session.setAttribute("message", new Message("Email-Id Already Exist!!", "alert-danger"));
                session.setAttribute("user",user);
                return "redirect:/signup";
            }
        }catch (Exception e){
            session.setAttribute("message", new Message("Something went wrong!!", "alert-danger"));
            return "signup1";
        }
    }


//    @PostMapping("/sendsOTP")
//    public ResponseEntity<?> verifyUser(@Valid @RequestBody User user, BindingResult result, HttpSession session) {
//        try {
//            User user1 = this.userRepository.getUserByUserName(user.getEmail());
//
//            // If there are validation errors
//            if (result.hasErrors()) {
//                return ResponseEntity.badRequest().body("message",new Message("Validation errors occurred."));
//            }
//
//            // Check if user does not exist
//            if (user1 == null) {
//                Random random = new Random();
//                // Generating OTP of 4 digits
//                int otp = random.nextInt(999999);
//                System.out.println(user.getEmail());
//                System.out.println(otp);
//
//                // Code for sending OTP to email
//                String subject = "OTP for verification of email from Price Drop Alert";
//                String message = "<div style='border:1px solid #e2e2e2; padding:20px'>" +
//                        "<h1>OTP is <b>" + otp + "</b></h1></div>";
//                String to = user.getEmail();
//
//                boolean flag = this.emailService.sendEmail(subject, message, to);
//
//                if (flag) {
//                    session.setAttribute("user", user);
//                    session.setAttribute("myotp", otp);
//                    session.setAttribute("email", user.getEmail());
//
//                    // Return success response with a message
//                    return ResponseEntity.ok("message",new Message("OTP sent to your email. Please check your inbox."));
//                } else {
//                    return ResponseEntity.badRequest().body("message",new Message("Enter your correct email-Id!!"));
//                }
//            } else {
//                // User already exists
//                return ResponseEntity.badRequest().body("message",new Message("Email-Id already exists!"));
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("message",new Message("Something went wrong!!"));
//        }
//    }

//    @PostMapping("/sendsOTP")
//    public ResponseEntity<String> verifyUsers(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, Model model) {
//        try {
//            User user1 = this.userRepository.getUserByUserName(user.getEmail());
//
////            if (result.hasErrors()) {
////                model.addAttribute("user", user);
////
////                return ResponseEntity.badRequest().body("Validation errors occurred."+user);
////            }
//            if (result.hasErrors()) {
//                model.addAttribute("user", user);
//
//                // Log the validation errors for debugging
//                result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
//
//                return ResponseEntity.badRequest().body("Validation errors occurred: " + result.getAllErrors()+user.getEmail());
//            }
//
//
//            if (user1 == null) {
//                Random random = new Random();
//                model.addAttribute("title", "Email");
//                // Generating OTP of 4 digits
//                int otp = random.nextInt(9999999);
//                System.out.println(user.getEmail());
//                System.out.println(otp);
//
//                // Send OTP to email
//                String subject = "OTP for verification of email from Price Drop Alert";
//                String message = "<div style='border:1px solid #e2e2e2; padding:20px'>" +
//                        "<h1>" +
//                        "OTP is " +
//                        "<b>" + otp +
//                        "</b>" +
//                        "</h1>" +
//                        "</div>";
//                String to = user.getEmail();
//
//                boolean flag = this.emailService.sendEmail(subject, message, to);
//
//                if (flag) {
//                    session.setAttribute("user", user);
//                    session.setAttribute("myotp", otp);
//                    session.setAttribute("email", user.getEmail());
//                    return ResponseEntity.ok("OTP sent successfully. Please verify your email.");
//                } else {
//                    session.setAttribute("message", new Message("Enter your correct email-Id!!", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP. Please try again.");
//                }
//            } else {
//                session.setAttribute("message", new Message("Email-Id already exists!", "alert-danger"));
//                session.setAttribute("user", user);
//                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email-Id already exists.");
//            }
//        } catch (Exception e) {
//            session.setAttribute("message", new Message("Something went wrong!!", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
//        }
//    }

    @PostMapping("/sendsOTP")
    public ResponseEntity<String> verifyUsers(@Valid @RequestBody User user, HttpSession session) {
        // Check if the user already exists
        User existingUser = this.userRepository.getUserByUserName(user.getEmail());

        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email-Id already exists.");
        }

        // Generate OTP
        int otp = (int) (Math.random() * 1000000); // Generate a 6-digit OTP
        System.out.println("Generated OTP: " + otp);
        System.out.println(user.getEmail());

        // Send OTP to the user's email
        String subject = "OTP for verification of email";
        String message = "<div style='border:1px solid #e2e2e2; padding:20px'>" +
                "<h1>OTP is <b>" + otp + "</b></h1></div>";
        String to = user.getEmail();

        boolean emailSent = this.emailService.sendEmail(subject, message, to);
        if (emailSent) {
            session.setAttribute("user",user);
            session.setAttribute("myotp",otp);
            session.setAttribute("email",user.getEmail());
            return ResponseEntity.ok("OTP sent successfully. Please verify your email.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP. Please try again.");
        }
    }


    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") String uotp,HttpSession session){
        try {
            Integer ogOTP = (Integer) session.getAttribute("myotp");
            String numericRegex = "\\d+";
            if (uotp.matches(numericRegex)) {
                int uOTP = Integer.parseInt(uotp);
                if (uOTP == ogOTP) {
                    User user1 = (User) session.getAttribute("user");
                    System.out.println(user1.getEmail());
                    user1.setRole("ROLE_USER");
                    user1.setPassword(passwordEncoder.encode(user1.getPassword()));
                    user1.setU_id(sequenceGenerator.getNextUserId());
                    this.userRepository.save(user1);
                    session.setAttribute("message", new Message("Successfully Registered!!", "alert-success"));
                    return "redirect:/signup";
                } else {
                    session.setAttribute("message", new Message("Wrong otp", "alert-danger"));
                    return "verify_otp1";
                }
            } else {
                session.setAttribute("message", new Message("Enter number only", "alert-danger"));
                return "verify_otp1";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "signup";
        }
    }





//    @PostMapping("/verifys-otp")
//    public ResponseEntity<String> verifysOtp(@RequestBody Map<String, String> requestBody, HttpSession session) {
//        try {
//            String uotp = requestBody.get("otp"); // Retrieve OTP from the request body
//
//            Integer ogOTP = (Integer) session.getAttribute("myotp");
//            String numericRegex = "\\d+";
//            if (uotp.matches(numericRegex)) {
//                int uOTP = Integer.parseInt(uotp);
//                if (uOTP == ogOTP) {
//                    User user1 = (User) session.getAttribute("user");
//                    user1.setRole("ROLE_USER");
//                    user1.setPassword(user1.getPassword()); // Encrypt the password here
//                    this.userRepository.save(user1);
//                    session.removeAttribute("myotp"); // Clear OTP from session
//
//                    return ResponseEntity.ok("Successfully Registered!!");
//                } else {
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong OTP.");
//                }
//            } else {
//
//                return ResponseEntity.badRequest().body("Invalid OTP format."+uotp+ogOTP);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
//        }
//    }



    @PostMapping("/verifys-otp")
    public ResponseEntity<String> verifysOtp(@RequestBody Map<String, String> requestBody, HttpSession session) {
        try {
            String uotp = requestBody.get("otp"); // Retrieve OTP from the request body
            Integer ogOTP = (Integer) session.getAttribute("myotp");

            System.out.println("Received OTP: " + uotp);
            System.out.println("Original OTP: " + ogOTP);

            if (uotp != null && uotp.matches("\\d+")) { // Check for null and numeric regex
                int uOTP = Integer.parseInt(uotp);
                if (uOTP == ogOTP) {
                    User user1 = (User) session.getAttribute("user");
                    user1.setRole("ROLE_USER");
                    user1.setPassword(passwordEncoder.encode(user1.getPassword()));
                    user1.setU_id(sequenceGenerator.getNextUserId());
                    this.userRepository.save(user1);
                    session.removeAttribute("myotp"); // Clear OTP from session

                    return ResponseEntity.ok("Successfully Registered!!");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong OTP.");
                }
            } else {
                return ResponseEntity.badRequest().body("Invalid OTP format: " + uotp);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }


}
