package domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Availability {

    String scheduledDateStr;
    Integer paxCount;
    String ond;
    Integer totalPrice;
    Boolean availability;
}
