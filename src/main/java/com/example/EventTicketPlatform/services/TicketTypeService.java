package com.example.EventTicketPlatform.services;

import com.example.EventTicketPlatform.domain.entities.Ticket;

import java.util.UUID;

public interface TicketTypeService {

    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);

}
