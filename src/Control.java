package src;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;


public class Control {
    private ArrayList<JFrame> pages;
    private Model model;

    public void action(String command, String data) {

        if (command.equals("Enter-The-System")){
            String []infos = data.split(",");
            int userID = Integer.parseInt(infos[0]);
            String password = infos[1];



            try {
                String [] user = model.signIn(userID, password).split(",");

                if (user.length > 1){
                    String isStaff = user[2];
                    if (isStaff.equals("true")){
                        addNewPage(new StaffFrame());
                    } else {
                        addNewPage(new NormalFrame());
                    }
                } else {
                    System.out.println("there is no such a user");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
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
