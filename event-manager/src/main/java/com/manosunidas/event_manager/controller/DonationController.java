package com.manosunidas.event_manager.controller;

import com.manosunidas.event_manager.model.Donation;
import com.manosunidas.event_manager.service.DonationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {
    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping
    public Donation makeDonation(@RequestParam Double amount, Authentication authentication) {
        return donationService.makeDonation(amount, authentication);
    }

    @GetMapping("/my-donations")
    public List<Donation> getUserDonations(Authentication authentication) {
        return donationService.getUserDonations(authentication);
    }

    @GetMapping("/all")
    public List<Donation> getAllDonations() {
        return donationService.getAllDonations();
    }
}
