package Ejemplo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Dao {

    PreparedStatement prst;
    StringBuffer command= new StringBuffer();
    Field[] fields;
    Method m;
    Random rnd = new Random();
    List <Integer> usedetak = new ArrayList<>();
    List <Integer> usedpos = new ArrayList<>();
    private Connection getConnection() throws ClassNotFoundException, SQLException
    {
        Connection connect=null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connect=DriverManager.getConnection("jdbc:mysql://localhost/Etakemon?useLegacyDatetimeCode=false&serverTimezone=America/New_York","root","root");
        return  connect;

    }
    public  void setName(PreparedStatement prst) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {

        for(Field f:fields){

            m=this.getClass().getMethod(getMethod((f.getName())),null);
            Object ret=m.invoke(this,null);

            if(ret instanceof String){

                if(f.getName().equals("name")) {
                    String id = ret.toString();
                    prst.setString(1, id);
                }
            }

        }


    }
    public void addEtkParams(PreparedStatement prst, User usr, Etakemon etk, Date date, String position) throws Exception{

        prst.setInt(1,etk.getId());
        prst.setInt(2,usr.getId());
        prst.setString(3, String.valueOf(date));
        prst.setString(4,position);

    }
    public void addPosParams(PreparedStatement prst) throws Exception{


        int random = rnd.nextInt(21)+1;
        while(usedetak.contains(random))
        {
            random = rnd.nextInt(21)+1;
        }
        prst.setInt(1,random);
        usedetak.add(random);
        random = rnd.nextInt(15)+1;
        while(usedpos.contains(random))
        {
            random = rnd.nextInt(15)+1;
        }
        prst.setInt(2,random);
        usedpos.add(random);


    }
    public void setIdUser(PreparedStatement prst, User usr) throws  Exception{
     prst.setInt(1,usr.getId());
        }

    public void addParams(PreparedStatement prst) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        int i=1;
        for(Field f:fields){

            m=this.getClass().getMethod(getMethod((f.getName())),null);
            Object ret=m.invoke(this,null);

            if(ret instanceof String){
                prst.setString(i, (String) ret);
            }
            if(ret instanceof Integer){

                String id= ret.toString();
                int id2=Integer.parseInt(id);
                prst.setInt(i, id2);
            }
            i++;

        }
    }
    public void setParams(PreparedStatement prst) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        int i=1;
        for(Field f:fields){

            m=this.getClass().getMethod(getMethod((f.getName())),null);
            Object ret=m.invoke(this,null);

            if(ret instanceof Integer){
                    String id = ret.toString();
                    int id2 = Integer.parseInt(id);
                    prst.setInt(i, id2);

            }
            i++;

        }
    }
    private  String getMethod(String m){

        m =m.substring(0, 1).toUpperCase() +m.substring(1);
        return "get"+m;

    }

    public void insert() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, SQLException, ClassNotFoundException {

        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("INSERT INTO ").append(this.getClass().getSimpleName().toLowerCase()+" (");
        fields= this.getClass().getFields();

        for(Field f : fields)
        {
            command.append(f.getName()+",");
        }

        command.replace(command.length()-1,command.length(),") VALUES (");
        for(Field f : fields)
        {
            command.append("?,");

        }
        command.replace(command.length()-1,command.length(),");");
        String query=command.toString();
        prst= con.prepareStatement(query);
        this.addParams(prst);
        prst.execute();



    }

    public void hunt(User usr, Etakemon etk, Date date, String position) throws Exception {

        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("INSERT INTO hunted (idetakemon,iduser,date,position) VALUES (?,?,?,?);");
        String query=command.toString();
        prst= con.prepareStatement(query);
        this.addEtkParams(prst,usr,etk,date,position);
        prst.execute();
        command= new StringBuffer();
        command.append("UPDATE ").append(this.getClass().getSimpleName().toLowerCase()+" SET user.hunted=user.hunted+1 WHERE user.id=?;");
        query=command.toString();
        prst= con.prepareStatement(query);
        this.setIdUser(prst,usr);
        prst.execute();

    }
    public  void update(String password, User u) throws Exception {

        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("UPDATE ").append(this.getClass().getSimpleName().toLowerCase()+" SET password = '"+password+"'");
        command.append(" WHERE ");
        fields= this.getClass().getFields();
        for(Field f :fields){
            if(f.getName().toString().equals("id")){
                command.append(f.getName().toString()+"=?;");
            }
        }

        String query=command.toString();
        prst= con.prepareStatement(query);
        this.setIdUser(prst,u);
        prst.execute();
    }

    public  void deletePos() throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {

        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("DELETE FROM relpos");
        String query=command.toString();
        prst= con.prepareStatement(query);
        prst.execute();

    }

    public String selectUsers() throws Exception {

        StringBuffer tablas=new StringBuffer();
        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("SELECT * FROM ").append(this.getClass().getSimpleName().toLowerCase());
        String query=command.toString();
        prst= con.prepareStatement(query);
        ResultSet rs= prst.executeQuery();
        rs.beforeFirst();
        ResultSetMetaData rsmd= rs.getMetaData();
        rs.next();
        for(int i=1;i!=0;i++){
            try {

                if (rsmd.getColumnTypeName(i).equals("INT")) {
                    tablas.append(rs.getInt(i)+",");
                }
                if (rsmd.getColumnTypeName(i).equals("VARCHAR")) {
                    tablas.append(rs.getString(i)+",");
                }
                if(i==rsmd.getColumnCount()){
                    rs.next();
                    tablas.append("-");
                    i=0;

                }
            }
            catch (Exception e){
                i=-1;
            }

        }
        return tablas.toString();
    }


    public String selectEtakemons() throws Exception {
        StringBuffer tablas=new StringBuffer();
        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("SELECT * FROM ").append(this.getClass().getSimpleName().toLowerCase());
        String query=command.toString();
        prst= con.prepareStatement(query);
        ResultSet rs= prst.executeQuery();
        rs.beforeFirst();
        ResultSetMetaData rsmd= rs.getMetaData();
        rs.next();
        for(int i=1;i!=0;i++){
            try {

                if (rsmd.getColumnTypeName(i).equals("INT")) {
                    tablas.append(rs.getInt(i)+",");
                }
                if (rsmd.getColumnTypeName(i).equals("VARCHAR")) {
                    tablas.append(rs.getString(i)+",");
                }
                if(i==rsmd.getColumnCount()){
                    rs.next();
                    tablas.append("-");
                    i=0;

                }
            }
            catch (Exception e){
                i=-1;
            }

        }
        return tablas.toString();
    }
    public String getAttacks() throws Exception{
        StringBuffer tablas=new StringBuffer();
        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("SELECT * FROM etakemon.attack");
        String query=command.toString();
        prst= con.prepareStatement(query);
        ResultSet rs= prst.executeQuery();
        rs.beforeFirst();
        ResultSetMetaData rsmd= rs.getMetaData();
        rs.next();
        for(int i=1;i!=0;i++){
            try {

                if (rsmd.getColumnTypeName(i).equals("INT")) {
                    tablas.append(rs.getInt(i)+",");
                }
                if (rsmd.getColumnTypeName(i).equals("VARCHAR")) {
                    tablas.append(rs.getString(i)+",");
                }
                if(i==rsmd.getColumnCount()){
                    rs.next();
                    tablas.append("-");
                    i=0;

                }
            }
            catch (Exception e){
                i=-1;
            }

        }
        return tablas.toString();
    }
    public String getRelAttacks() throws Exception{
        StringBuffer tablas=new StringBuffer();
        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("SELECT relattack.idattack,etakemon.name FROM etakemon.etakemon JOIN relattack ON etakemon.id=relattack.idetakemon");
        String query=command.toString();
        prst= con.prepareStatement(query);
        ResultSet rs= prst.executeQuery();
        rs.beforeFirst();
        ResultSetMetaData rsmd= rs.getMetaData();
        rs.next();
        for(int i=1;i!=0;i++){
            try {

                if (rsmd.getColumnTypeName(i).equals("INT")) {
                    tablas.append(rs.getInt(i)+",");
                }
                if (rsmd.getColumnTypeName(i).equals("VARCHAR")) {
                    tablas.append(rs.getString(i)+",");
                }
                if(i==rsmd.getColumnCount()){
                    rs.next();
                    tablas.append("-");
                    i=0;

                }
            }
            catch (Exception e){
                i=-1;
            }

        }
        return tablas.toString();
    }

    public String userEtakemons(User usr) throws Exception{
        StringBuffer tablas=new StringBuffer();
        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("SELECT etakemon.* FROM etakemon.hunted JOIN etakemon.etakemon ON hunted.idetakemon=etakemon.id JOIN etakemon.user ON hunted.iduser=user.id AND user.id=?");
        String query=command.toString();
        prst= con.prepareStatement(query);
        this.setIdUser(prst,usr);
        ResultSet rs= prst.executeQuery();
        rs.beforeFirst();
        ResultSetMetaData rsmd= rs.getMetaData();
        rs.next();
        for(int i=1;i!=0;i++){
            try {

                if (rsmd.getColumnTypeName(i).equals("INT")) {
                    tablas.append(rs.getInt(i)+",");
                }
                if (rsmd.getColumnTypeName(i).equals("VARCHAR")) {
                    tablas.append(rs.getString(i)+",");
                }
                if(i==rsmd.getColumnCount()){
                    rs.next();
                    tablas.append("-");
                    i=0;

                }
            }
            catch (Exception e){
                i=-1;
            }

        }
        return tablas.toString();
    }
    public String position(User usr) throws Exception{
        StringBuffer tablas=new StringBuffer();
        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("SELECT idetakemon,date,position FROM etakemon.hunted WHERE iduser=? ORDER BY idetakemon ASC");
        String query=command.toString();
        prst= con.prepareStatement(query);
        this.setIdUser(prst,usr);
        ResultSet rs= prst.executeQuery();
        rs.beforeFirst();
        ResultSetMetaData rsmd= rs.getMetaData();
        rs.next();
        for(int i=1;i!=0;i++){
            try {

                if (rsmd.getColumnTypeName(i).equals("INT")) {
                    tablas.append(rs.getInt(i)+",");
                }
                if (rsmd.getColumnTypeName(i).equals("DATE")) {
                    tablas.append(rs.getString(i)+",");
                }
                if (rsmd.getColumnTypeName(i).equals("VARCHAR")) {
                    tablas.append(rs.getString(i)+",");
                }
                if(i==rsmd.getColumnCount()){
                    rs.next();
                    tablas.append("¬");
                    i=0;

                }
            }
            catch (Exception e){
                i=-1;
            }

        }
        return tablas.toString();
    }
    public void won(User usr) throws Exception {

        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("UPDATE ").append(this.getClass().getSimpleName().toLowerCase()+" SET user.combatswon=user.combatswon+1 WHERE user.id=?;");
        String query=command.toString();
        prst= con.prepareStatement(query);
        this.setIdUser(prst,usr);
        prst.execute();
    }

    public void positions() throws Exception {

        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("INSERT INTO relpos (idetakemon,idposition) VALUES (?,?);");
        String query=command.toString();
        prst= con.prepareStatement(query);
        this.addPosParams(prst);
        prst.execute();
    }

    public String selectPos() throws Exception {
        StringBuffer tablas=new StringBuffer();
        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("SELECT * FROM ").append(this.getClass().getSimpleName().toLowerCase());
        String query=command.toString();
        prst= con.prepareStatement(query);
        ResultSet rs= prst.executeQuery();
        rs.beforeFirst();
        ResultSetMetaData rsmd= rs.getMetaData();
        rs.next();
        for(int i=1;i!=0;i++){
            try {

                if (rsmd.getColumnTypeName(i).equals("INT")) {
                    tablas.append(rs.getInt(i)+",");
                }
                if (rsmd.getColumnTypeName(i).equals("VARCHAR")) {
                    tablas.append(rs.getString(i)+",");
                }
                if (rsmd.getColumnTypeName(i).equals("FLOAT")) {
                    tablas.append(rs.getFloat(i)+",");
                }
                if(i==rsmd.getColumnCount()){
                    rs.next();
                    tablas.append("-");
                    i=0;
                }
            }
            catch (Exception e){
                i=-1;
            }

        }
        return tablas.toString();
    }
    public String selectRelpos() throws Exception {
        StringBuffer tablas=new StringBuffer();
        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("SELECT * FROM relpos");
        String query=command.toString();
        prst= con.prepareStatement(query);
        ResultSet rs= prst.executeQuery();
        rs.beforeFirst();
        ResultSetMetaData rsmd= rs.getMetaData();
        rs.next();
        for(int i=1;i!=0;i++){
            try {

                if (rsmd.getColumnTypeName(i).equals("INT")) {
                    tablas.append(rs.getInt(i)+",");
                }
                if(i==rsmd.getColumnCount()){
                    rs.next();
                    tablas.append("-");
                    i=0;
                }
            }
            catch (Exception e){
                i=-1;
            }
        }
        return tablas.toString();
    }
}
