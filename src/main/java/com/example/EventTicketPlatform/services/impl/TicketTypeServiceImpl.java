package com.example.EventTicketPlatform.services.impl;

import com.example.EventTicketPlatform.domain.entities.Ticket;
import com.example.EventTicketPlatform.domain.entities.TicketStatusEnum;
import com.example.EventTicketPlatform.domain.entities.TicketType;
import com.example.EventTicketPlatform.domain.entities.User;
import com.example.EventTicketPlatform.exceptions.TicketNotFoundException;
import com.example.EventTicketPlatform.exceptions.TicketsSoldOutException;
import com.example.EventTicketPlatform.exceptions.UserNotFoundException;
import com.example.EventTicketPlatform.repositories.TicketRepository;
import com.example.EventTicketPlatform.repositories.TicketTypeRepository;
import com.example.EventTicketPlatform.repositories.UserRepository;
import com.example.EventTicketPlatform.services.QrCodeService;
import com.example.EventTicketPlatform.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;
    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                String.format("User with ID %s was not found", userId)
        ));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(() -> new TicketNotFoundException(
                        String.format("Ticket type with ID %s was not found", ticketTypeId)
                ));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if (purchasedTickets + 1 > totalAvailable) {
            throw new TicketsSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);
    }
}
