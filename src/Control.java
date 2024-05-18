package src;

import src.repository.BorrowingHistory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Control implements ActionListener {
    private ArrayList<JFrame> pages;
    private Model model;
    private String tmp;
    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        if (command.equals("Enter-The-System")){
            String []data = tmp.split(",");
            int userID = Integer.parseInt(data[0]);
            String password = data[1];

            try {
                if (!model.signIn(userID, password, "Student").equals("Invalid Input")){
                    addNewPage(new StaffFrame());
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

    public void setTmp(String tmp){
        this.tmp = tmp;
    }


    public class PenaltyController {
    public void applyPenalty(Member member, double penaltyAmount) {
        member.setPenaltyAmount(member.getPenaltyAmount() + penaltyAmount);
    }
}

public class BorrowingHistoryController {
    public List<BorrowingHistory> getBorrowingHistory(Member member) {
        //Return borrowed books
        return new ArrayList<>();
    }
}

}
