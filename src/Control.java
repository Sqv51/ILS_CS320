package src;

import java.util.ArrayList;
import java.util.List;

import src.Model.BorrowingHistory;
import src.Model.Member;

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
