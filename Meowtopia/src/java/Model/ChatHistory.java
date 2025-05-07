package Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatHistory {
    
    private static final String DB_URL = "jdbc:derby://localhost:1527/Meowtopia";
    private static final String DB_USER = "Meowtopia";
    private static final String DB_PASSWORD = "Meowtopia";
    
    private String chatId;
    private String email;
    private String message;
    private Timestamp timestamp;
    private String role; 
    private String sessionId;  

    public ChatHistory() {}

    public ChatHistory(String chatId, String email, String message, Timestamp timestamp, String role, String sessionId) {
        this.chatId = chatId;
        this.email = email;
        this.message = message;
        this.timestamp = timestamp;
        this.role = role;
        this.sessionId = sessionId; 
    }

   
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getAllChatSessions() {
        List<String> sessions = new ArrayList<>();
        String sql = "SELECT SESSION_ID FROM CHATHISTORY GROUP BY SESSION_ID ORDER BY MAX(TIMESTAMP) DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                sessions.add(rs.getString("SESSION_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }
}
