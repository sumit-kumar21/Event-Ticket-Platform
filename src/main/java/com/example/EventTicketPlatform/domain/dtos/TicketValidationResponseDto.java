package com.example.EventTicketPlatform.domain.dtos;

import com.example.EventTicketPlatform.domain.entities.TicketValidationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationResponseDto {
    private UUID ticketId;
    private TicketValidationStatusEnum status;
}
