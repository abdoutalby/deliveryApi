package com.example.api.services;


import com.example.api.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletResponseWrapper;
import java.io.*;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(Order  order  ) throws MessagingException, IOException {
          final   String FILE = "C:\\Projekt\\lul.pdf";
        File pdf = new File(FILE);
        pdf.createNewFile();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document ,  new FileOutputStream(pdf) );

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("Delivery Order", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph p = new Paragraph();
        p.add("\n Type : "+order.getType());
        p.add("\n Customer Name : " +order.getCustomerName());
        p.add("\n Contact Person : "+ order.getContactPerson());
        p.add("\n Size/ Capacity : " + order.getSize());
        p.add("\n Time in : "+order.getTimeIn());
        p.add("\n Time out : "+order.getTimeOut());
        p.add("\n Vehicule number : "+order.getVehiculeNo());
        p.add("\n Reciver Name  : "+order.getReceiverName());
        p.add("\n Driver Name : "+order.getDriverName());
        p.add("\n Qty : "+order.getQty());
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        document.add(p);
        document.close();

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom("abderrahmentalby@gmail.com");
        helper.setTo(order.getContactPerson());
        helper.setText("well done !! ");
        helper.setSubject("api test");
        helper.addAttachment("fatoura", pdf);
        mailSender.send(msg);
        System.out.println("Mail sent successfully...");
    }





}
