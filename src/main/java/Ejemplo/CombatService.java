package Ejemplo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("combat")
public class CombatService {
    @Path("getattacks/{etakemon}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAttack(@PathParam("etakemon") String name) throws Exception {

        return WebFunctions.getInstance().attack(name);
    }
    @Path("setEtak/{user}")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response getEtak(@PathParam("user") String user,String etakemon) throws Exception {

        return WebFunctions.getInstance().setEtakemon(user,etakemon);
    }
    @Path("getCont/{user}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEtak(@PathParam("user") String user) throws Exception {

        return WebFunctions.getInstance().getCont(user);
    }
    @Path("atacar/{id}/{estado}")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String atacar(String user,@PathParam("id") Integer id,@PathParam("estado") String estado) throws Exception {

        return WebFunctions.getInstance().doattack(user,id,estado);
    }
    @Path("start")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String getRnd(String user) throws Exception {

        return WebFunctions.getInstance().getRnd(user);
    }
    @Path("idCombat")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String turno(String user) throws Exception {

        return WebFunctions.getInstance().turno(user);
    }
    @Path("estado/{name}")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Integer estado(@PathParam("name")String user) throws Exception {


        return WebFunctions.getInstance().estado(user);
    }
}