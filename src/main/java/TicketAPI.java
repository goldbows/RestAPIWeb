import api.ticket.TicketResponse;
import com.google.gson.Gson;
import data.InMemoryTicketRepository;
import domain.Ticket;
import domain.TicketCreationRequestDTO;
import domain.TicketService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/createBooking")
public class TicketAPI extends HttpServlet {

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

            TicketCreationRequestDTO ticketCreationRequestDTO = _gson.fromJson(payload, TicketCreationRequestDTO.class);

            Ticket ticket = null;
            try {
                ticket = ticketService.create(ticketCreationRequestDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }

            TicketResponse ticketResponse = new TicketResponse(ticket);

            String res = _gson.toJson(ticketResponse);

            PrintWriter out = response.getWriter();

            out.print(res);
            out.flush();
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
