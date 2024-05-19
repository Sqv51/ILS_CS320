package src;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;


public class Control {
    private ArrayList<JFrame> pages;
    private Model model;

    public void action(String command, String data) {

        if ("Enter-The-System".equals(command)){
            String []infos = data.split(",");
            if (infos.length >= 2) {
                try {
                    if (!infos[0].matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "Invalid user ID: " + infos[0], "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String [] user = model.signIn(Integer.parseInt(infos[0]), infos[1]).split(",");
                    if (user.length > 2 && "true".equals(user[2])){
                        addNewPage(new StaffFrame());
                    } else if (user.length > 1) {
                        addNewPage(new NormalFrame());
                        //send pop up message to the user
                        checkForNotification(Integer.parseInt(user[0]));
                    } else {
                        JOptionPane.showMessageDialog(null, "Check ID and password", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }


    }

    private void checkForNotification(int parseInt) {
        boolean [] notifications = model.Notification(parseInt);
        if (notifications[0]){
            JOptionPane.showMessageDialog(null, "You have overdue books", "Notification", JOptionPane.INFORMATION_MESSAGE);
        }
        if (notifications[1]){
            JOptionPane.showMessageDialog(null, "You have books that are due soon", "Notification", JOptionPane.INFORMATION_MESSAGE);
        }


    }

    public Control(Model model, View first){
        this.model = model;
        first.setActionListener(this);
        pages = new ArrayList<>();
        addNewPage(first);

    }
    public void addNewPage(JFrame page){
        if (pages.size() > 0){
            pages.get(pages.size()-1).setVisible(false);
        }
        pages.add(page);
        page.setVisible(true);
    }
    public void closePage(){
        int index = pages.size()-1;
        pages.get(index).setVisible(false);
        pages.remove(index);
        pages.get(index-1).setVisible(true);
    }






}
