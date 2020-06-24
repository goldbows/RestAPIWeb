package domain;

public interface TicketRepository {

    Ticket createTicket(TicketCreationRequestDTO ticket) throws Exception;

    Availability checkAvailability(AvailabilityRequestDTO availabilityRequest) throws Exception;
}
