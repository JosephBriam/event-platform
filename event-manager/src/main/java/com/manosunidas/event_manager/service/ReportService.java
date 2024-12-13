package com.manosunidas.event_manager.service;

import com.manosunidas.event_manager.model.Donation;
import com.manosunidas.event_manager.model.Event;
import com.manosunidas.event_manager.repository.DonationRepository;
import com.manosunidas.event_manager.repository.EventRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportService {
    private final EventRepository eventRepository;
    private final DonationRepository donationRepository; // Agregado

    public ReportService(EventRepository eventRepository, DonationRepository donationRepository) {
        this.eventRepository = eventRepository;
        this.donationRepository = donationRepository; // Inicializado
    }

    public ByteArrayInputStream exportEventsToExcel() throws IOException {
        List<Event> events = eventRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Events");

            // Header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Name", "Description", "Location", "Date", "Available Seats", "Categories"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Data rows
            int rowIdx = 1;
            for (Event event : events) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(event.getId());
                row.createCell(1).setCellValue(event.getName());
                row.createCell(2).setCellValue(event.getDescription());
                row.createCell(3).setCellValue(event.getLocation());
                row.createCell(4).setCellValue(event.getDate().toString());
                row.createCell(5).setCellValue(event.getAvailableSeats());

                String categories = String.join(", ", event.getCategory());
                row.createCell(6).setCellValue(categories);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public ByteArrayInputStream exportDonationsToExcel() throws IOException {
        List<Donation> donations = donationRepository.findAll(); // Uso de donationRepository

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Donations");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Donor Name", "Amount", "Date"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (Donation donation : donations) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(donation.getId());
                row.createCell(1).setCellValue(donation.getDonorName());
                row.createCell(2).setCellValue(donation.getAmount());
                row.createCell(3).setCellValue(donation.getDate().toString());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
