package Ejemplo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("logros")
public class LogrosService {
    @Path("gethunt/{username}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int getUser(@PathParam("username") String userName) throws Exception {

        User u=WebFunctions.getInstance().getUser(userName);
        int hunted=u.getHunted();
        if (hunted<1000&&hunted>99){
            return 7;
        }
        if (hunted<=100&&hunted>21){
            return 6;
        }
        if (hunted<=21&&hunted>19){
            return 5;
        }
        if (hunted<=19&&hunted>14){
            return 4;
        }
        if (hunted<=14&&hunted>9){
            return 3;
        }
        if (hunted<=9&&hunted>4){
            return 2;
        }
        if (hunted<=4&&hunted>0){
            return 1;
        }
        else{return 0;}
    }
}
