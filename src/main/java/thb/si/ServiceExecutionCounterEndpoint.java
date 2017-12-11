package thb.si;

import javax.json.Json;
import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("executions")
public class ServiceExecutionCounterEndpoint {
    @POST
    public Response registerExecution(@QueryParam("name") String serviceName) {
        ServiceExecutionCounter.getInstance()
                .put(serviceName, new Date().getTime());
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray giveFeedback(@QueryParam("name") String servceName) {
        return Json.createArrayBuilder()
                .add("name")
                .add(servceName)
                .add(ServiceExecutionCounter.getInstance()
                        .getActualNumberOfExecutionsByServiceName(servceName))
                .build();
    }
}