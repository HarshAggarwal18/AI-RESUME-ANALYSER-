package com.resumeanalyser.resume_analyser_backend.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ResumeParserService {

    public String extractTextFromResume(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty.");
        }

        // Only support PDF right now
        if (!file.getOriginalFilename().endsWith(".pdf")) {
            throw new IllegalArgumentException("Please upload a PDF file.");
        }

        try (InputStream inputStream = file.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            // Clean the extracted text
            text = text.replaceAll("\\s+", " ").trim();
            return text;
        }
    }
}

//Handles PDF upload and reading
//
//Extracts text from each page
//
//Cleans and returns it as a single string