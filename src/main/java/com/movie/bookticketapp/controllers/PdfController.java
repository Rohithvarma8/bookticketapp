package com.movie.bookticketapp.controllers;

import com.movie.bookticketapp.dao.MovieDao;
import com.movie.bookticketapp.dao.ScreenDao;
import com.movie.bookticketapp.dao.SeatDao;
import com.movie.bookticketapp.dao.TheatreDao;
import com.movie.bookticketapp.models.Movie;
import com.movie.bookticketapp.models.Screen;
import com.movie.bookticketapp.models.Seat;
import com.movie.bookticketapp.models.Theatre;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.OutputStream;

@Controller
public class PdfController {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private TheatreDao theatreDao;

    @Autowired
    private ScreenDao screenDao;

    @Autowired
    private SeatDao seatDao;

    @GetMapping("/booking/PDF")
    public void generatePdf(
            @RequestParam int movieId,
            @RequestParam int theatreId,
            @RequestParam int screenId,
            @RequestParam int seatId,
            HttpServletResponse response
    ) {
        try {
            // Fetch details for the booking
            Movie movie = movieDao.getMovieById(movieId);
            Theatre theatre = theatreDao.getTheatreById(theatreId);
            Screen screen = screenDao.getScreenById(screenId);
            Seat seat = seatDao.getSeatById(seatId);

            // Configure response to output PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=BookingConfirmation.pdf");

            // Create a new PDF document
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // Title
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                    contentStream.newLineAtOffset(100, 700);
                    contentStream.showText("Booking Confirmation");
                    contentStream.endText();

                    // Spacing
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(100, 650);
                    contentStream.showText("Movie: " + movie.getTitle());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, 630);
                    contentStream.showText("Theatre: " + theatre.getName());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, 610);
                    contentStream.showText("Screen: " + screen.getScreenNumber());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, 590);
                    contentStream.showText("Seat: " + seat.getSeatNumber());
                    contentStream.endText();

                    // Thank You Note
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
                    contentStream.newLineAtOffset(100, 550);
                    contentStream.showText("Thank you for booking with us!");
                    contentStream.endText();
                }

                // Write the PDF content to the response output stream
                OutputStream out = response.getOutputStream();
                document.save(out);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}
