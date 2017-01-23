package Ejemplo;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class AndroidFunctions implements AndroidInterface {
    private  static AndroidFunctions instance;
    private HashMap<String, User> listUser;
    private HashMap<String, Etakemon> listEtakemons;


    private void orderEtakemons (String meshetk){
        String[] jointetk=meshetk.split("-");
        for(int i=0;i<jointetk.length;i++){
            String[] uniqueetk=jointetk[i].split(",");
            Etakemon etk = new Etakemon();
            etk.setId(Integer.parseInt(uniqueetk[0]));
            etk.setName(uniqueetk[1]);
            etk.setDescription(uniqueetk[2]);
            etk.setHealth(Integer.parseInt(uniqueetk[3]));
            etk.setType(uniqueetk[4]);
            listEtakemons.put(uniqueetk[1],etk);
        }

    }
    private List makeJsonEtakemon (String meshetk){
        List<Etakemon> listetk= new ArrayList<>();
        String[] jointetk=meshetk.split("-");
        for(int i=0;i<jointetk.length;i++){
            String[] uniqueetk=jointetk[i].split(",");
            Etakemon etk = new Etakemon();
            etk.setId(Integer.parseInt(uniqueetk[0]));
            etk.setName(uniqueetk[1]);
            etk.setDescription(uniqueetk[2]);
            etk.setHealth(Integer.parseInt(uniqueetk[3]));
            etk.setType(uniqueetk[4]);
            listetk.add(etk);
        }
        return listetk;

    }
    private List makeJsonPos (String meshpos){
        List<Position> listpos= new ArrayList<>();
        String[] jointpos=meshpos.split("-");
        for(int i=0;i<jointpos.length;i++){
            String[] uniquepos=jointpos[i].split(",");
            Position pos = new Position();
            pos.setId(Integer.parseInt(uniquepos[0]));
            pos.setName(uniquepos[1]);
            pos.setXposition(Float.parseFloat(uniquepos[2]));
            pos.setYposition(Float.parseFloat(uniquepos[3]));
            listpos.add(pos);
        }
        return listpos;

    }
    public AndroidFunctions() throws Exception {

        listEtakemons=new HashMap<>();
        listUser=WebFunctions.getInstance().getMap();
        Etakemon etk = new Etakemon();
        orderEtakemons(etk.selectEtakemons());
        ReschedulableTimer timer =new ReschedulableTimer();
        UpdatePositions updatePositions= new UpdatePositions();
        timer.schedule(updatePositions,1000,900000);

    }
    public  static AndroidFunctions getInstance() throws Exception {
        if (instance==null) instance=new AndroidFunctions();
        return instance;
    }
    private List<Etakemon> constructList(HashMap<String, Etakemon> listEtakemons)
    {
        List<Etakemon> etk = new ArrayList<>(listEtakemons.values());

                return etk;
    }


    @Override
    public String getPositionsInJSON() throws Exception {

        Position pos = new Position();
        String posiciones = pos.selectPos();
        List <Position> positions = makeJsonPos(posiciones);

        try {
            String json = new Gson().toJson(positions);
            return  json;
        }
        catch (Exception e){
            return "ERROR";
        }
    }
    @Override
    public String getRelpositionsInJSON() throws Exception {
        Position pos = new Position();
        return pos.selectRelpos();
    }


    @Override
    public String getEtakemonsInJSON() {

        try {
            String json = new Gson().toJson(constructList(listEtakemons));
            return  json;
        }
        catch (Exception e){
            return "ERROR";
        }
    }
    @Override
    public String getEtakemonInJSON(String name) {

        try {
            User found = listUser.get(name);
            String json = new Gson().toJson(makeJsonEtakemon(found.userEtakemons(found)));
            return  json;
        }
        catch (Exception e){
            return "ERROR";
        }
    }


    @Override
    public String Login(User usr) {
        try {
            String found = listUser.get(usr.getName()).getName();
            return "200";
        }
        catch (Exception e){
            return "500";
        }
    }

    @Override
    public String Register(User usr) throws Exception {
        try {
            String found = listUser.get(usr.getName()).getName();
            return "500";


        }catch (Exception e){
            usr.insert();
            WebFunctions.getInstance().orderUsers(usr.selectUsers());
            listUser=WebFunctions.getInstance().getMap();

            return "200";
        }
    }

    @Override
    public String huntEtakemon(String etakemon,String name , Date date, String position) {

        try {
            User usr = listUser.get(name);
            Etakemon etk= listEtakemons.get(etakemon);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            usr.hunt(usr,etk,sqlDate,position);
            return "200";
        }
        catch (Exception e){
            return "500";
        }

    }
}
