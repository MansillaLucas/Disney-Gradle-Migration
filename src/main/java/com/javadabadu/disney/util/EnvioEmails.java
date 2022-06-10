package com.javadabadu.disney.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EnvioEmails {

    @Autowired
    private JavaMailSender sender;

    private static  String SUBJECT = "Le damos la bienvenida a la Api de Disney";
    private static  String SUBTEXT = ", nos alegra que te unas a esta familia.Ya pudes disfrutar de toda nuestra Api.";


    public void send(String to,String name){
        SimpleMailMessage message = new SimpleMailMessage();
        String TEXT="Hola "+name+SUBTEXT;
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText(TEXT);
        sender.send(message);
    }

}
