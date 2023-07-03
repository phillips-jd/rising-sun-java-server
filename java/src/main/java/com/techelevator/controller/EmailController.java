package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.model.EmailService;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class EmailController {


    @Autowired
    private EmailService emailService;
    private User user;
    private UserDao userDao;

    public EmailController(EmailService emailService, UserDao userDao) {
        this.emailService = emailService;
        this.userDao = userDao;
    }

    @PostMapping("/messages/approved")
    String sendEmailApproveMessage(@RequestBody String email) {

        email = email.replaceAll("%40","@"); // Gets rid of the encoded garbage
        email = email.replaceAll("=","");

        this.emailService.sendApproveEmail(
                email,
                "Rising Sun Animal Shelter: Volunteer Request",
                "Thank you for applying to be a volunteer at Rising Sun Animal Shelter! We are happy to say that you meet all of the qualifications we are looking for! Please visit our website and login! " +
                        "\n" +
                        "\n" +
                        "Here are your username and temporary password. You'll be prompted to change your password upon first logging in: " +
                        "\n" +
                        "\n" +
                        "Username: " + userDao.getUsername() +
                        "\n" +
                        "Password: " + userDao.getTempPassword()
        );
        return "Message Sent (approved)";
    }

    @PostMapping("/messages/declined")
    String sendEmailDeclineMessage(@RequestBody String email) {

        email = email.replaceAll("%40","@"); // Gets rid of the encoded garbage
        email = email.replaceAll("=","");


        this.emailService.sendDeclineEmail(
                email,
                "Rising Sun Animal Shelter: Volunteer Request",
                "Thank you for applying to be a volunteer at Rising Sun Animal Shelter! Unfortunately, we are sad to say that one or more submits on your form conflict with our volunteer criteria. You may apply again after reviewing our terms or try calling us directly (555) 555-5555"
        );
        return "Message Sent (declined)";
    }
}