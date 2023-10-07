package com.krouba.Cedounga;

import com.krouba.Cedounga.upload.FileUploadResponse;
import com.krouba.Cedounga.upload.FileUploadService;
import com.krouba.Cedounga.upload.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;

@Controller
public class MainController {
    @Autowired
    private JavaMailSender mailSender;

    private EmailSenderService emailSenderService;

    public MainController (EmailSenderService emailSenderService){
        this.emailSenderService = emailSenderService;
    }
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileUploadService fileUploadService;



    @GetMapping("/")
    public String ShowMainPage() {
        return "index";
    }

    @GetMapping("/admin/login")
    public String showAdminLoginPage() {
        return "admin/admin_login";
    }

    @GetMapping("/admin/home")
    public String showAdminHomePage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/admin_home";

    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "Registration";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user) {
        userRepository.save(user);
        return "index";

    }


    @GetMapping("/user/login")
    public String showUserLoginPage() {
        return "user/user_login";
    }

    @GetMapping("/user/home")
    public String showUserHomePage() {
        return "user/user_home";

    }

    @GetMapping("/contact")
   public String showContactForm(Model model){
     model.addAttribute("contact", new Contact());
       return "contact_form";

   }

    @PostMapping("/contact")
    public String sendMessage(@RequestBody Contact contact){
        this.emailSenderService.sendEmail(contact.getFullName(),contact.getTo(),contact.getSubject(),contact.getFullName());
        return "email_success";
    }

    @GetMapping("/upload")
    public String showUploadPage() {
        return "/user/user_upload";
    }

    @PostMapping("/upload/db")
    public String uploadDb(@RequestParam("file") MultipartFile multipartFile){
        UploadedFile uploadedFile = fileUploadService.uploadToDb(multipartFile);
        FileUploadResponse response = new FileUploadResponse();
        if(uploadedFile != null){
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/user/download")
                    .path(uploadedFile.getFileId())
                    .toUriString();
            response.setDownloadUri(downloadUri);
            response.setFileId(uploadedFile.getFileId());
            response.setFileType(uploadedFile.getFileType());
            response.setUploadStatus(true);
            response.setMessage("File Uploaded Successfully!");
            return "/user/user_upload";
        }
        response.setMessage("Oops 1 something went wrong please re-upload.");
        return "message( Oops1 something went wrong please re-upload";
    }

    @GetMapping("user/home/document/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable String id) {
        UploadedFile uploadedFileToRet = fileUploadService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploadedFileToRet.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +uploadedFileToRet.getFileName())
                .body((Resource) new ByteArrayResource(uploadedFileToRet.getFileData()));

    }





}
