package info.whitelynx.simplepage.rest;


import com.google.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Clock;
import java.time.Instant;

@Path("/rest")
public class MainResource {
    @Inject
    private Clock clock;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/time")
    public String getTime() {
        return Instant.now(clock).toString();
    }
}
