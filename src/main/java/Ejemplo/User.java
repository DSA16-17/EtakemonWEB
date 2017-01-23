package Ejemplo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="User")
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends Dao {

    public int id;

    @XmlElement(required=true)
    public String name;
    @XmlElement(required=true)
    public String password;

    public int combatswon;
    public int hunted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCombatswon(){return  combatswon;}

    public void setCombatswon(int combatswon){this.combatswon=combatswon;}

    public int getHunted(){return  hunted;}

    public void setHunted(int hunted){this.hunted=hunted;}
}
