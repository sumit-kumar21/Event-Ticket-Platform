package com.example.EventTicketPlatform.services;

import com.example.EventTicketPlatform.domain.entities.TicketValidation;

import java.util.UUID;

public interface TicketValidationService {

    TicketValidation validateTicketByQrCode(UUID qrCodeId);
    TicketValidation validateTicketManually(UUID ticketId);
}
