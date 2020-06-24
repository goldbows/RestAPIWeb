package domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TicketCreationRequestDTO {

    Integer paxCount;
    String scheduledDateStr;
    String ond;
    Integer totalPrice;
}
