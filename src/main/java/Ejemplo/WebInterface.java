package Ejemplo;

import javax.ws.rs.core.Response;

public interface WebInterface {

    String getCont(String user);
    int getNewImage(String nameImage);
    Response newUser(User usr) throws Exception;
    Response logUser(User usr) ;
    String getEtakemonsInJSON();
    Etakemon getEtakemon(String name);
    User getUser(String usr) throws Exception;
    String setImage(String name);
    Response logout(String name);
    Response changePassword(String name, String oldPass, String newPass) throws Exception;

    Integer estado(String user);

    Integer getConnected();
    String getConnectedInJSON();
    Response invite(String user, String invited);
    String getinvitations(String user);
    Response accepted(String name, String invited);
    Response denied(String name, String invited);
    Response players(String name);
    String setNewImage(String name, String nameImage);
    String getImagesInJSON();
    String attack(String name);
    String getRnd(String n);
    String turno(String user) throws Exception;
    String doattack(String user, Integer id, String estado);
    Response setEtakemon(String etakemon, String user);
    String getHunted(String name) throws Exception;
    String position(String name) throws Exception;
}






