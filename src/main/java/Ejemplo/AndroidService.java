package Ejemplo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/android")
public class AndroidService  {

    @GET
    @Path("/all/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEtakemonsInJSON(@PathParam("name") String name) throws Exception {

        return AndroidFunctions.getInstance().getEtakemonsInJSON();
    }
    @GET
    @Path("/positions")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPositionsInJSON() throws Exception {

        return AndroidFunctions.getInstance().getPositionsInJSON();
    }
    @GET
    @Path("/relpositions")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRelpositionsInJSON() throws Exception {

        return AndroidFunctions.getInstance().getRelpositionsInJSON();
    }

    @GET
    @Path("/get/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEtakemonInJSON(@PathParam("name") String name) throws Exception {

        return AndroidFunctions.getInstance().getEtakemonInJSON(name);
    }

    @POST
    @Path("/hunt/{etakemon}/{name}/{position}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String newEtakemon(@PathParam("name") String name,@PathParam("etakemon")String etakemon,String date,@PathParam("position") String position) throws Exception {


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date datetrue = formatter.parse(date);
        return  AndroidFunctions.getInstance().huntEtakemon(etakemon,name,datetrue,position);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String Login(User user) throws Exception {

        return AndroidFunctions.getInstance().Login(user);
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String Register(User user) throws Exception {

        return AndroidFunctions.getInstance().Register(user);

    }
}
