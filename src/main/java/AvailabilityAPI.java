import api.availability.AvailabilityResponse;
import com.google.gson.Gson;
import data.InMemoryTicketRepository;
import domain.Availability;
import domain.AvailabilityRequestDTO;
import domain.TicketService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/availability")
public class AvailabilityAPI extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        InMemoryTicketRepository ticketRepository = new InMemoryTicketRepository();

        TicketService ticketService = new TicketService(ticketRepository);

        Gson _gson = new Gson();

        String pathInfo = request.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")){

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String payload = buffer.toString();

            AvailabilityRequestDTO availabilityRequestDTO = _gson.fromJson(payload, AvailabilityRequestDTO.class);

            Availability availability = null;
            try {
                availability = ticketService.availability(availabilityRequestDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }

            AvailabilityResponse availabilityResponse = new AvailabilityResponse(availability);

            String res = _gson.toJson(availabilityResponse);

            PrintWriter out = response.getWriter();

            out.print(res);
            out.flush();
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}