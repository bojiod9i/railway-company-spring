package ru.tsystems.railway.service.impl;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import ru.tsystems.railway.domain.service.Route;
import ru.tsystems.railway.domain.service.Ticket;
import ru.tsystems.railway.domain.service.Train;
import ru.tsystems.railway.service.DocumentService;
import ru.tsystems.railway.util.VelocityUtil;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private static final String TEMPLATE_TICKET_FILENAME = "ticketTemplate.vm";

    public byte[] ticketPdfView(Ticket ticket) throws Exception {
        String html = getHtmlFromVelocity(ticket);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(new String(out.toByteArray(), VelocityUtil.VELOCITY_ENCODING));
        renderer.layout();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        renderer.createPDF(outputStream);

        renderer.finishPDF();
        out.flush();
        out.close();

        byte[] result = outputStream.toByteArray();
        outputStream.close();

        return result;
    }

    private String getHtmlFromVelocity(Ticket ticket) {
        VelocityContext context = new VelocityContext();
        List<Train> trainList = new ArrayList<>();
        Iterator<Route> it = ticket.getRoutes().iterator();
        Route route = it.next();
        context.put("departureStation", route.getDepartureStation());
        context.put("departureDate", DATETIME_FORMAT.format(route.getDepartureDate()));
        trainList.add(route.getTrain());
        double cost = route.getCost();
        while (it.hasNext()) {
            route = it.next();
            cost += route.getCost();
            if (!trainList.contains(route.getTrain())) {
                trainList.add(route.getTrain());
            }
        }
        context.put("arrivalStation", route.getArrivalStation());
        context.put("arrivalDate", DATETIME_FORMAT.format(route.getArrivalDate()));

        StringBuilder trainsStringBuilder = new StringBuilder("");
        for (int i = 0; i < trainList.size() - 1; ++i) {
            trainsStringBuilder.append(trainList.get(i));
            trainsStringBuilder.append(", ");
        }
        trainsStringBuilder.append(trainList.size() - 1);
        context.put("trainId", trainsStringBuilder.toString());
        context.put("firstName", ticket.getPassenger().getFirstName());
        context.put("lastName", ticket.getPassenger().getLastName());
        context.put("birthday", DATE_FORMAT.format(ticket.getPassenger().getBirthday()));
        context.put("purchaseDate", DATETIME_FORMAT.format(ticket.getPurchaseDate()));
        context.put("cost", cost);


        Template velocityTemplate = Velocity.getTemplate(VelocityUtil.VELOCITY_TEMPLATES_FOLDER + TEMPLATE_TICKET_FILENAME,
                VelocityUtil.VELOCITY_ENCODING);
        StringWriter writer = new StringWriter();
        velocityTemplate.merge(context, writer);
        return writer.toString();
    }
}