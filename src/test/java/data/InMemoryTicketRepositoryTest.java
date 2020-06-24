package data;

import data.util.Time;
import domain.Availability;
import domain.AvailabilityRequestDTO;
import domain.Ticket;
import domain.TicketCreationRequestDTO;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class InMemoryTicketRepositoryTest {

    @Test
    public void createTicket() {

        TicketCreationRequestDTO ticketCreationRequestDTO = TicketCreationRequestDTO.builder()
                .scheduledDateStr("2020/06/30")
                .paxCount(3)
                .ond("A/B")
                .totalPrice(100)
                .build();

        Integer START_TIME = 8;
        String ticketNumber = "12ASD3";
        String ondForTime = ticketCreationRequestDTO.getOnd().replaceAll("/", "");
        String departureTime = START_TIME.toString() + ":00";
        String arrivalTime = (START_TIME + Time.valueOf(ondForTime).getTime()) + ":00";

        Set<String> seatNumber = new HashSet<>();
        seatNumber.add("1A");
        seatNumber.add("2B");
        seatNumber.add("3C");

        Ticket ticket = Ticket.builder()
                .ticketNumber(ticketNumber)
                .scheduledDateStr(ticketCreationRequestDTO.getScheduledDateStr())
                .paxCount(ticketCreationRequestDTO.getPaxCount())
                .ond(ticketCreationRequestDTO.getOnd())
                .departureTimeStr(departureTime)
                .arrivalTimeStr(arrivalTime)
                .totalPrice(ticketCreationRequestDTO.getTotalPrice())
                .seatNumbers(seatNumber)
                .build();

        TestCase.assertEquals(ticket.getOnd(), "A/B");
        TestCase.assertEquals(ticket.getScheduledDateStr(), "2020/06/30");
        TestCase.assertEquals(ticket.getPaxCount(), Integer.valueOf(3));
        TestCase.assertEquals(ticket.getTicketNumber(), "12ASD3");
        TestCase.assertEquals(ticket.getDepartureTimeStr(), "8:00");
        TestCase.assertEquals(ticket.getArrivalTimeStr(), "9:00");
        TestCase.assertEquals(ticket.getTotalPrice(), Integer.valueOf(100));
    }

    @Test
    public void checkAvailability() {

        AvailabilityRequestDTO availabilityRequestDTO = AvailabilityRequestDTO.builder()
                .scheduledDateStr("2020/06/30")
                .paxCount(3)
                .ond("A/B")
                .build();

        Availability availability = Availability.builder()
                .scheduledDateStr(availabilityRequestDTO.getScheduledDateStr())
                .paxCount(availabilityRequestDTO.getPaxCount())
                .ond(availabilityRequestDTO.getOnd())
                .totalPrice(100)
                .build();

        TestCase.assertEquals(availability.getOnd(), "A/B");
        TestCase.assertEquals(availability.getScheduledDateStr(), "2020/06/30");
        TestCase.assertEquals(availability.getPaxCount(), Integer.valueOf(3));
        TestCase.assertEquals(availability.getTotalPrice(), Integer.valueOf(100));
    }
}