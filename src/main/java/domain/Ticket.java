package domain;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class Ticket {

    String ticketNumber;
    String scheduledDateStr;
    Integer paxCount;
    Set<String> seatNumbers;
    String ond;
    String departureTimeStr;
    String arrivalTimeStr;
    Integer totalPrice;
}
