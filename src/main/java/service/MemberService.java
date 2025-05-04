package service;

import dao.MemberDAO;
import model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MemberService {
    private MemberDAO memberDAO;

    public MemberService(Connection conn) {
        this.memberDAO = new MemberDAO(conn);
    }

    public List<User> getAllMembers() throws SQLException {
        return memberDAO.getAllMembers();
    }

    public void blacklistUser(int userId) throws SQLException {
        memberDAO.blacklistMember(userId, true);
    }

    public void unblacklistUser(int userId) throws SQLException {
        memberDAO.blacklistMember(userId, false);
    }

    public void setUserRole(int userId, String role) throws SQLException {
        memberDAO.updateUserRole(userId, role);
    }

    public void setUserDiscount(int userId, double discountRate) throws SQLException {
        memberDAO.updateDiscount(userId, discountRate);
    }
}
