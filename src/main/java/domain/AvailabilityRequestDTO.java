package domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AvailabilityRequestDTO {

    Integer paxCount;
    String scheduledDateStr;
    String ond;
}
