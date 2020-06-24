package data;

import data.util.Price;
import data.util.Time;
import domain.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTicketRepository implements TicketRepository {

    private static final ConcurrentHashMap<String, Map<String, Set<String>>> BOOKED_SEATS = new ConcurrentHashMap<>();
    private static final String[] seatRowNums = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private static final String[] seatRowLetter = {"A", "B", "C", "D"};

    @Override
    public Ticket createTicket(TicketCreationRequestDTO ticketCreationRequestDTO) throws Exception {

        int TICKET_NUMBER_LENGTH = 6;
        Integer START_TIME = 8;
        String ticketNumber = RandomStringUtils.random(TICKET_NUMBER_LENGTH, true, true).toUpperCase();
        String ondForTime = ticketCreationRequestDTO.getOnd().replaceAll("/", "");
        String departureTime = START_TIME.toString() + ":00";
        String arrivalTime = (START_TIME + Time.valueOf(ondForTime).getTime()) + ":00";

        Ticket ticket = Ticket.builder()
                .ticketNumber(ticketNumber)
                .scheduledDateStr(ticketCreationRequestDTO.getScheduledDateStr())
                .paxCount(ticketCreationRequestDTO.getPaxCount())
                .ond(ticketCreationRequestDTO.getOnd())
                .departureTimeStr(departureTime)
                .arrivalTimeStr(arrivalTime)
                .totalPrice(ticketCreationRequestDTO.getTotalPrice())
                .seatNumbers(this.setSeatNumbers(ticketCreationRequestDTO.getPaxCount(), ticketCreationRequestDTO.getScheduledDateStr(), ondForTime))
                .build();

        if (ticket.getSeatNumbers().isEmpty()) {
            throw new Exception("No Availability");
        }

        return ticket;
    }

    @Override
    public Availability checkAvailability(AvailabilityRequestDTO availabilityRequest) throws Exception {

        int TICKET_NUMBER_LENGTH = 6;
        Integer START_TIME = 8;
        String ticketNumber = RandomStringUtils.random(TICKET_NUMBER_LENGTH, true, true).toUpperCase();

        String ondForPrice = availabilityRequest.getOnd().replaceAll("/", "");
        boolean isAvailable = false;

        Map<String, Set<String>> bookedSeatsForRequestedDate = BOOKED_SEATS.get(availabilityRequest.getScheduledDateStr());

        Set<String> ondWiseSeatCount = bookedSeatsForRequestedDate != null ? bookedSeatsForRequestedDate.get(ondForPrice) : new HashSet<>();

        if ((40 - ondWiseSeatCount.size()) >= availabilityRequest.getPaxCount()) {
            isAvailable = true;
        }
        int totalPrice = 0;
        if (isAvailable) {
            totalPrice = Price.valueOf(ondForPrice).getPrice() * availabilityRequest.getPaxCount();
        }

        Availability availability = Availability.builder()
                .scheduledDateStr(availabilityRequest.getScheduledDateStr())
                .paxCount(availabilityRequest.getPaxCount())
                .ond(availabilityRequest.getOnd())
                .totalPrice(totalPrice)
                .availability(isAvailable)
                .build();

        return availability;
    }

    private Set<String> setSeatNumbers(Integer paxCount, String scheduledDate, String ond) {

        Set<String> currentBookingSeatNumbers = new HashSet<>();
        Map<String, Set<String>> bookedSeatsForRequestedDate = BOOKED_SEATS.get(scheduledDate);
        Set<String> bookedSeatsForRequestedOnd = bookedSeatsForRequestedDate != null ? bookedSeatsForRequestedDate.get(ond) : new HashSet<>();

        for (int i = 1; i <= paxCount; i++) {
            String seatNum;
            if (bookedSeatsForRequestedOnd.size() != 40) {
                do {
                    Random random = new Random();
                    int rowNumIndex = random.nextInt(seatRowNums.length);
                    int rowLetterIndex = random.nextInt(seatRowLetter.length);
                    seatNum = seatRowNums[rowNumIndex] + seatRowLetter[rowLetterIndex];

                } while (bookedSeatsForRequestedOnd.contains(seatNum));

                currentBookingSeatNumbers.add(seatNum);
            }
        }

        Map<String, Set<String>> ondWiseSeats = BOOKED_SEATS.get(scheduledDate) != null ? BOOKED_SEATS.get(scheduledDate) : new HashMap<>();

        if (ondWiseSeats.get(ond) == null) {
            ondWiseSeats.put(ond, currentBookingSeatNumbers);
        } else {
            ondWiseSeats.get(ond).addAll(currentBookingSeatNumbers);
        }

        BOOKED_SEATS.put(scheduledDate, ondWiseSeats);

        return currentBookingSeatNumbers;

    }

}
