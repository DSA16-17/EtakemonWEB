package Ejemplo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("room")
public class RoomService {

    @Path("/getconnected")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getConnected() throws Exception {

        return WebFunctions.getInstance().getConnectedInJSON();
    }

    @Path("/invite/{username}")
    @POST
    public Response invite(@PathParam("username") String name,String invited) throws Exception {

        return WebFunctions.getInstance().invite(name,invited);
    }

    @Path("/getinvitations/{username}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String invite(@PathParam("username") String name) throws Exception {

        return WebFunctions.getInstance().getinvitations(name);
    }

    @Path("/accepted/{username}/{invited}")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response accept(@PathParam("username") String name,@PathParam("invited") String invited) throws Exception {

        return WebFunctions.getInstance().accepted(name,invited);
    }

    @Path("/players/{username}/")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response players(@PathParam("username") String name) throws Exception {

        return WebFunctions.getInstance().players(name);
    }

    @Path("/denied/{username}/{invited}")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response denied(@PathParam("username") String name,@PathParam("invited") String invited) throws Exception {

        return WebFunctions.getInstance().denied(name,invited);
    }
}
