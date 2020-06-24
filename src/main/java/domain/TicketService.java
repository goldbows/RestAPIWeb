package domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public Ticket create(TicketCreationRequestDTO ticketCreationRequest) throws Exception {
        return ticketRepository.createTicket(ticketCreationRequest);
    }

    public Availability availability(AvailabilityRequestDTO availabilityRequest) throws Exception {
        return ticketRepository.checkAvailability(availabilityRequest);
    }

}
