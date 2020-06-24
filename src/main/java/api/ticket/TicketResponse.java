package api.ticket;

import domain.Ticket;
import lombok.Value;

@Value
public class TicketResponse {

    Ticket ticket;
}