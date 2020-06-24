package api.availability;

import domain.Availability;
import lombok.Value;

@Value
public class AvailabilityResponse {

    Availability availability;
}