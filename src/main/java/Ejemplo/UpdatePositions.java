package Ejemplo;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class UpdatePositions implements Runnable {
    @Override
    public void run() {
        Position pos = new Position();
        try {
            pos.deletePos();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            try {
                pos.positions();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
