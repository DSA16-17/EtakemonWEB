package Ejemplo;

import java.util.Date;

public interface AndroidInterface {
    String getRelpositionsInJSON() throws Exception;
    String getPositionsInJSON() throws Exception;
    String getEtakemonsInJSON();
    String getEtakemonInJSON(String name);
    String Login(User usr);
    String Register(User usr) throws Exception;
    String huntEtakemon(String etakemon, String name, Date date, String position) throws Exception;
}
