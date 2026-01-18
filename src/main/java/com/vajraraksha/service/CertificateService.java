package com.vajraraksha.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@SuppressWarnings("null")
public class CertificateService {

    public void generateCertificate(String studentName, String courseName, OutputStream out) throws IOException {
        Document document = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Colors
            Color darkBlue = new Color(0, 51, 102);
            Color gold = new Color(218, 165, 32);

            // Border
            float margin = 20;
            Rectangle border = new Rectangle(margin, margin, PageSize.A4.rotate().getWidth() - margin,
                    PageSize.A4.rotate().getHeight() - margin);
            border.setBorder(Rectangle.BOX);
            border.setBorderWidth(5);
            border.setBorderColor(darkBlue);
            document.add(border);

            // Inner Border
            Rectangle innerBorder = new Rectangle(margin + 5, margin + 5,
                    PageSize.A4.rotate().getWidth() - margin - 5,
                    PageSize.A4.rotate().getHeight() - margin - 5);
            innerBorder.setBorder(Rectangle.BOX);
            innerBorder.setBorderWidth(1);
            innerBorder.setBorderColor(gold);
            document.add(innerBorder);

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 36, darkBlue);
            Paragraph title = new Paragraph("CERTIFICATE OF COMPLETION", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingBefore(80);
            document.add(title);

            // Subtitle
            Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Color.GRAY);
            Paragraph subtitle = new Paragraph("This certifies that", subtitleFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingBefore(40);
            document.add(subtitle);

            // Scudent Name
            Font nameFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 32, Color.BLACK);
            Paragraph name = new Paragraph(studentName, nameFont);
            name.setAlignment(Element.ALIGN_CENTER);
            name.setSpacingBefore(20);
            document.add(name);

            // Body text
            Paragraph body = new Paragraph("has successfully learned the fundamental concepts of", subtitleFont);
            body.setAlignment(Element.ALIGN_CENTER);
            body.setSpacingBefore(20);
            document.add(body);

            // Course Name
            Font courseFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 26, darkBlue);
            Paragraph course = new Paragraph(courseName, courseFont);
            course.setAlignment(Element.ALIGN_CENTER);
            course.setSpacingBefore(20);
            document.add(course);

            // Date
            String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
            Paragraph date = new Paragraph("Date: " + dateStr, subtitleFont);
            date.setAlignment(Element.ALIGN_CENTER);
            date.setSpacingBefore(50);
            document.add(date);

            // Signature
            Paragraph signature = new Paragraph("VajraRaksha Team",
                    FontFactory.getFont(FontFactory.ZAPFDINGBATS, 18, gold)); // Using standard font as placeholder
            signature.setAlignment(Element.ALIGN_CENTER);
            signature.setSpacingBefore(20);
            document.add(signature);

        } catch (DocumentException e) {
            throw new IOException(e);
        } finally {
            document.close();
        }
    }
}
