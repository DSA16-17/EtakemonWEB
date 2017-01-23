package Ejemplo;

import com.google.gson.Gson;

import javax.ws.rs.core.Response;
import java.util.*;

public class WebFunctions implements WebInterface {
    private Random rnd;
    private HashMap<String,Integer> listImages;
    private HashMap<String, User> listUser;
    private HashMap<String, User> connectedUsers;
    private static WebFunctions instance;
    private HashMap<String, Etakemon> listEtakemons1;
    private HashMap<Integer, Attack> listAttacks;
    private HashMap<Integer,String> listRelAttacks;
    private HashMap<String,String> invitations;
    private HashMap<String,String> players;
    private HashMap<String,Etakemon> combat;
    private String[] names;
    private HashMap<String, Attack> inGame;
    public void orderUsers (String meshusers){
        String[] jointuser=meshusers.split("-");
        for(int i=0;i<jointuser.length;i++){
            String[] uniqueuser=jointuser[i].split(",");
            User usr = new User();
            usr.setId(Integer.parseInt(uniqueuser[0]));
            usr.setName(uniqueuser[1]);
            usr.setPassword((uniqueuser[2]));
            usr.setCombatswon(Integer.parseInt(uniqueuser[3]));
            usr.setHunted(Integer.parseInt(uniqueuser[4]));
            listUser.put(uniqueuser[1],usr);
        }

    }

    @Override
    public String getHunted(String name) throws Exception {
        HashMap<String,Etakemon> hs=new HashMap<>();
        User usr=new User();
        usr.setName(name);
        String res=usr.userEtakemons(listUser.get(name));
        String[] jointetk = res.split("-");
        for (int i = 0; i < jointetk.length; i++) {
            String[] uniqueetk = jointetk[i].split(",");
            Etakemon etk = new Etakemon();
            etk.setId(Integer.parseInt(uniqueetk[0]));
            etk.setName(uniqueetk[1]);
            etk.setDescription(uniqueetk[2]);
            etk.setHealth(Integer.parseInt(uniqueetk[3]));
            etk.setType(uniqueetk[4]);
            hs.put(etk.getName(),etk);
        }
        List<Etakemon> lst = new ArrayList<>(hs.values());
        String json = new Gson().toJson(lst);
        return  json;
    }
    @Override
    public String position(String name) throws Exception {
        HashMap<Integer,DatePos> hs=new HashMap<>();
        User usr=new User();
        usr.setName(name);
        String res=usr.position(listUser.get(name));
        int idetakemon=0;

        String[] jointetk = res.split("¬");
        for (int i = 0; i < jointetk.length; i++) {
            DatePos p=new DatePos();

                String[] uniqueetk = jointetk[i].split(",");
                idetakemon = Integer.parseInt(uniqueetk[0]);
                p.setDate(uniqueetk[1]);
                p.setPos(uniqueetk[2]);
                hs.put(idetakemon,p);

            }
        for (int i=1;i<22;i++){
            DatePos p=new DatePos();
            if (!hs.containsKey(i)) {
                idetakemon = i;
                p.setDate("");
                p.setPos("");
                hs.put(idetakemon, p);
            }
        }

        List<DatePos> lst = new ArrayList<>(hs.values());
        String json = new Gson().toJson(lst);
        return  json;
    }

    @Override
    public String attack(String name){
        List<Attack> lst =new ArrayList<>();
        for(int i=1;i<listRelAttacks.size()+1;i++){
            if(listRelAttacks.get(i).equals(name))
            {
                lst.add(listAttacks.get(i));
            }
        }
        String json = new Gson().toJson(lst);
        return  json;
    }

    public HashMap getMap(){
        return listUser;
    }

    public WebFunctions()throws  Exception {
        names= new String[]{"Mister Masmaso","Otto","Spatula Khan","Cortex","Merlin","Handsome Jack","Hammerlock","Torgue","Trappy","Pizza Steve","Ellie","Lapis","Peridot","Finn","Star Butterfly","SpongeBob","Skidward","Spy","Shades","Piston","Badassaurus"};
        rnd= new Random();
        listImages= new HashMap<>();
        listEtakemons1=new HashMap<>();
        connectedUsers= new HashMap<>();
        listUser = new HashMap<>();
        listAttacks=new HashMap<>();
        listRelAttacks=new HashMap<>();
        invitations= new HashMap<>();
        players=new HashMap<>();
        combat=new HashMap<>();
        inGame=new HashMap<>();
        getAttack();
        getRelAttack();
        User usr = new User();
        orderUsers(usr.selectUsers());
        Etakemon etk = new Etakemon();
        orderEtakemons(etk.selectEtakemons());
    }
    public  static WebFunctions getInstance() throws Exception {
        if (instance==null) instance=new WebFunctions();
        return instance;
    }
    @Override
    public String getRnd(String n){

        String s = players.get(n);
        List<String> list = new ArrayList<String>();
        list.add(s);
        list.add(n);
        Collections.sort(list);
        return list.get(0);

    }
    @Override
    public String turno(String user) throws Exception {
        Attack atk=inGame.get(players.get(user));
        String s;
        try {
            s=atk.getName();
            if (s.equals(null)) {
                s = "null";
            }
            else {
                inGame.remove(players.get(user));
                s = new Gson().toJson(atk);
            }
        }
        catch (Exception e){
            s = "null";
            if (combat.get(players.get(user)).getHealth()<=0){
                Attack attk = new Attack();
                attk.setName("victory");
                s = new Gson().toJson(attk);
                invitations.remove(invitations.get(user));
                invitations.remove(user);
                players.remove(players.get(user));
                players.remove(user);
                User us = listUser.get(user);
                us.won(us);

            }
        }

        return s;
    }
    @Override
    public String doattack(String user, Integer id, String estado){
        int id2=id;
        Attack atk = listAttacks.get(id2);
        inGame.put(user,atk);
        Etakemon e = combat.get(players.get(user));
        Etakemon emio = combat.get(user);
        String tipo = atk.getType();

        if(tipo.equals("Fisico")) {
            if(estado.equals("Confuso")){

            }
            else
            {
                if(estado.equals("Buff"))
                {
                    e.setHealth(e.getHealth() - (listAttacks.get(id2).damage)*2);
                }
                else {
                    e.setHealth(e.getHealth() - listAttacks.get(id2).damage);
                }
            }

        }
        if(tipo.equals("Estado")){

            if(estado.equals("Confuso")){

            }
            else
            {
                if(estado.equals("Buff"))
                {
                    e.setHealth(e.getHealth() - (listAttacks.get(id2).damage)*2);
                }
                else {
                    e.setHealth(e.getHealth() - listAttacks.get(id2).damage);
                }
            }

        }
        if(tipo.equals("Vida")){

            emio.setHealth(emio.getHealth()+ listAttacks.get(id2).damage);
        }

        combat.put(players.get(user),e);
        return players.get(user);
    }

    @Override
    public Response setEtakemon(String user,String etakemon){
        Etakemon e = listEtakemons1.get(etakemon);
        combat.put(user,e);
        return Response.status(200).build();

    }
    @Override
    public String getCont(String user){
        Etakemon etk =new Etakemon();
        String s=players.get(user);
            if (!s.equals(null)) {
               etk = combat.get(s);
            }
        String json = new Gson().toJson(etk);
        return  json;
    }
    @Override
    public Integer estado(String user){

        return combat.get(user).getHealth();
    }


    public void getRelAttack() throws Exception {
        Etakemon e=new Etakemon();
        String attacks=e.getRelAttacks();
        String[] jointetk = attacks.split("-");
        for (int i = 0; i < jointetk.length; i++) {
            String[] uniqueetk = jointetk[i].split(",");
            listRelAttacks.put(Integer.parseInt(uniqueetk[0]),uniqueetk[1]);
        }
    }
    public void getAttack() throws Exception {
        Etakemon e=new Etakemon();
        String attacks=e.getAttacks();
        String[] jointetk = attacks.split("-");
        for (int i = 0; i < jointetk.length; i++) {
            String[] uniqueetk = jointetk[i].split(",");
            Attack att=new Attack();
            att.setId(Integer.parseInt(uniqueetk[0]));
            att.setName(uniqueetk[1]);
            att.setDescription(uniqueetk[2]);
            att.setType(uniqueetk[3]);
            att.setDamage(Integer.parseInt(uniqueetk[4]));
            listAttacks.put(Integer.parseInt(uniqueetk[0]), att);
        }
    }


    @Override
    public Integer getConnected() {
        return connectedUsers.size();
    }

    @Override
    public Response logout(String user) {

        if (!user.equals("null"))
        {
            connectedUsers.remove(user);
            return  Response.status(200).build();
        }else {
            return Response.status(500).build();
        }


    }
    @Override
    public User getUser(String usr) throws Exception {

        User us = new User();
        orderUsers(us.selectUsers());
        return this.listUser.get(usr);
    }
    @Override
    public String setImage(String name) {

        int i = rnd.nextInt(21);

        try {
            i=listImages.get(name);
        }
        catch (Exception e) {

            listImages.put(name, i);
        }

        return "/images/"+i+".png";
    }

    private  int searchImage(String name){
        int i=-1;
        boolean found = false;
        while (!found){
            i++;
            if(names[i].equals(name)){
                found=true;
            }
        }
        return  i;
    }
    @Override
    public String setNewImage(String name, String nameImage) {

        int newImage=searchImage(nameImage);
        listImages.put(name,newImage);
        return "/images/"+newImage+".png";
    }
    @Override
    public int getNewImage(String nameImage) {

        return searchImage(nameImage);
    }
    @Override
    public Response newUser(User usr) throws Exception {

        try {
            String found = listUser.get(usr.getName()).getName();
            return Response.status(500).entity("El nombre de usuario ya existe").build();


        }catch (Exception e){
            usr.insert();
            listUser.put(usr.getName(), usr);
            return Response.status(201).build();
        }

    }
    @Override
    public Response logUser(User usr) {
        try {
            User found = listUser.get(usr.getName());

            try {
                User reconnected = connectedUsers.get(usr.getName());
                if(usr.getName().equals(reconnected.getName())){
                    return Response.status(500).entity("Usuario ya conectado").build();
                }
            }
            catch (Exception e){

            }

            if(usr.getPassword().equals(found.getPassword())) {
                connectedUsers.put(usr.getName(), usr);
                return Response.status(200).build();
            }else {
                return Response.status(500).entity("Error en los datos introducidos").build();
            }
        }
        catch (Exception e){
            return Response.status(500).entity("Error en los datos introducidos").build();
        }

    }
    @Override
    public Response changePassword(String userName, String oldPass, String newPass) {
        try {
            if(listUser.get(userName).getPassword().equals(oldPass)){
                User u =listUser.get(userName);
                u.update(newPass,u);
                u.setPassword(newPass);
                listUser.put(u.getName(),u);
                return Response.status(200).build();
            }
            else
            {
                return Response.status(500).entity("Error en los datos introducidos").build();
            }

        } catch (Exception e) {
            return Response.status(500).entity("Error en los datos introducidos").build();
        }
    }
    @Override
    public String getConnectedInJSON() {

        List<User> list = new ArrayList<>(constructListC(connectedUsers));
        String json = new Gson().toJson(list);
        return  json;
    }
    private List<User> constructListC(HashMap<String, User> listconnected)
    {
        List<User> usr = new ArrayList<>(listconnected.values());

        return usr;
    }


    @Override
    public Response invite(String user,String invited ) {

        try {
            if (invitations.get(user).equals("")) {
                if(!user.equals(invited)) {
                    invitations.put(user, invited);
                    invitations.put(invited,user);
                    return Response.status(200).build();
                }else {
                    return Response.status(501).build();
                }
            }
            return Response.status(500).build();
        }
        catch (Exception e) {
            if(!user.equals(invited)) {
                invitations.put(user, invited);
                invitations.put(invited,user);
                return Response.status(200).build();
            }else {
                return Response.status(501).build();
            }
        }
    }

    @Override
    public String getinvitations(String user) {

        String s=invitations.get(user);

        try {
            if (s.equals(null)) {
                s = "null";
            }
        }
        catch (Exception e){
            s = "null";
        }
        return s;

    }

    @Override
    public Response accepted(String name,String invited) {

        String s=invitations.get(name);
        try {
            if(!s.equals(null)) {
                players.put(name, invited);
                players.put(invited, name);
                return Response.status(200).build();
            }
            return Response.status(500).build();
        }
        catch (Exception e){
            return Response.status(500).build();
        }

    }

    @Override
    public Response denied(String name, String invited) {

        String s=invitations.get(name);
        try {
            if(!s.equals(null)) {
                invitations.remove(name);
                invitations.remove(invited);
                return Response.status(500).build();
            }
            return Response.status(200).build();
        }
        catch (Exception e){
            return Response.status(200).build();
        }
    }

    @Override
    public Response players(String name) {

        String s=players.get(name);
        try {
            if(!s.equals(null)) {
                return Response.status(200).build();
            }
            return Response.status(500).build();
        }
        catch (Exception e){
            return Response.status(500).build();
        }
    }


    private List<Etakemon> constructList(HashMap<String, Etakemon> listEtakemons)
    {
        List<Etakemon> etk = new ArrayList<>(listEtakemons.values());

        return etk;
    }
    private List<Image> constructListImages()
    {
        List<Image> img = new ArrayList<Image>();
        for (int i = 0; i < 21 ; i++){

            Image image = new Image();
            image.setName(names[i]);
            img.add(image);
        }
        return img;
    }

    private void orderEtakemons (String meshetk) {
        String[] jointetk = meshetk.split("-");
        for (int i = 0; i < jointetk.length; i++) {
            String[] uniqueetk = jointetk[i].split(",");
            Etakemon etk = new Etakemon();
            etk.setId(Integer.parseInt(uniqueetk[0]));
            etk.setName(uniqueetk[1]);
            etk.setDescription(uniqueetk[2]);
            etk.setHealth(Integer.parseInt(uniqueetk[3]));
            etk.setType(uniqueetk[4]);
            listEtakemons1.put(uniqueetk[1], etk);
        }
    }

    @Override
    public String getImagesInJSON() {

        try {
            final List<Image> list = new ArrayList<Image>(constructListImages());
            String json = new Gson().toJson(list);
            return  json;
        }
        catch (Exception e){
            return "ERROR";
        }
    }


    @Override
    public String getEtakemonsInJSON() {

        try {
            final List<Etakemon> list = new ArrayList<Etakemon>(constructList(listEtakemons1));
            Collections.sort(list, new Comparator<Etakemon>(){
                @Override
                public int compare(Etakemon o, Etakemon t1) {
                    return new Integer(o.getId()).compareTo(new Integer(t1.getId()));
                }
            });
            String json = new Gson().toJson(list);
            return  json;
        }
        catch (Exception e){
            return "ERROR";
        }
    }
    @Override
    public Etakemon getEtakemon(String name) {
        return listEtakemons1.get(name);
    }
}
