package src;

import src.repository.BorrowingHistory;

import java.util.ArrayList;
import java.util.List;


public class Control {
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
