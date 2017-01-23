package Ejemplo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("profile")
public class ProfileService {

        @Path("getuser/{username}")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public User getUser(@PathParam("username") String userName) throws Exception {

            return WebFunctions.getInstance().getUser(userName);
        }
        @Path("setimage/{username}")
        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String setImage(@PathParam("username") String name) throws Exception {

                return WebFunctions.getInstance().setImage(name);
        }
        @Path("setnewimage/{username}/{imagename}")
        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String setnewImage(@PathParam("username") String name,@PathParam("imagename") String nameImage ) throws Exception {

                return WebFunctions.getInstance().setNewImage(name,nameImage);
        }
        @Path("getnewimage/")
        @POST
        @Produces(MediaType.TEXT_PLAIN)
        public int getnewImage(String nameImage ) throws Exception {

                return WebFunctions.getInstance().getNewImage(nameImage);
        }
        @Path("changepass/{oldPass}/{newPass}/{username}")
        @POST
        public Response changePassword(@PathParam("oldPass")String oldPass, @PathParam("newPass") String newPass,@PathParam("username") String userName) throws Exception{

                return  WebFunctions.getInstance().changePassword(userName,oldPass,newPass);
        }
        @Path("getconnected/")
        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public Integer getConnected() throws Exception {

                return WebFunctions.getInstance().getConnected();
        }
        @Path("/getimages")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public String getImagesInJSON() throws Exception {
                return WebFunctions.getInstance().getImagesInJSON();
        }



}