package Ejemplo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "myresource" path)
 */

@Path("web")
public class LoginService  {

    @Path("register")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response newUser(User usr)throws Exception{

        return WebFunctions.getInstance().newUser(usr);

    }
    @Path("login")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response logUser(User usr)throws Exception{

        return  WebFunctions.getInstance().logUser(usr);

    }
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */

    @Path("logout/{username}/")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response logout(@PathParam("username") String userName)throws Exception{

        return WebFunctions.getInstance().logout(userName);

    }

}