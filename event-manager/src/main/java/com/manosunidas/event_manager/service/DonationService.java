package com.manosunidas.event_manager.service;

import com.manosunidas.event_manager.model.Donation;
import com.manosunidas.event_manager.model.User;
import com.manosunidas.event_manager.repository.DonationRepository;
import com.manosunidas.event_manager.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DonationService {
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    public DonationService(DonationRepository donationRepository, UserRepository userRepository) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
    }

    public Donation makeDonation(Double amount, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Donation donation = new Donation();
        donation.setAmount(amount);
        donation.setDonorName(user.getUsername());
        donation.setDate(LocalDate.now());
        donation.setUser(user);

        return donationRepository.save(donation);
    }

    public List<Donation> getUserDonations(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return donationRepository.findByUserId(user.getId());
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }
}