package com.example.EventTicketPlatform.services;

import com.example.EventTicketPlatform.domain.entities.QrCode;
import com.example.EventTicketPlatform.domain.entities.Ticket;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
