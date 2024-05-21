package src;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        View view = new View();
        Model model = new Model();
        Control ctrl = new Control(model, view);
        new NormalFrame(ctrl).setVisible(true);

    }
}