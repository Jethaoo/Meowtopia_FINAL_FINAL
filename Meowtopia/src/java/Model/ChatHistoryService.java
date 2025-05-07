package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatHistoryService {

    private static final String DB_URL = "jdbc:derby://localhost:1527/Meowtopia";
    private static final String DB_USER = "Meowtopia";
    private static final String DB_PASSWORD = "Meowtopia";

    // Method to save a chat message with the chatSessionId
    public boolean saveChatMessage(String chatSessionId, String email, String message, String role) {
        String sql = "INSERT INTO CHATHISTORY (CHATID, EMAIL, MESSAGE, TIMESTAMP, ROLE, SESSION_ID) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String chatId = UUID.randomUUID().toString().substring(0, 4);

            pstmt.setString(1, chatId);
            pstmt.setString(2, email);
            pstmt.setString(3, message);
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(5, role);
            pstmt.setString(6, chatSessionId); // Store the sessionId

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get chat history by session ID
    public List<ChatHistory> getChatHistoryBySessionId(String chatSessionId) {
        List<ChatHistory> chatHistory = new ArrayList<>();
        String sql = "SELECT * FROM CHATHISTORY WHERE SESSION_ID = ? ORDER BY TIMESTAMP ASC";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, chatSessionId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ChatHistory chat = new ChatHistory(
                        rs.getString("CHATID"),
                        rs.getString("EMAIL"),
                        rs.getString("MESSAGE"),
                        rs.getTimestamp("TIMESTAMP"),
                        rs.getString("ROLE"),
                        rs.getString("SESSION_ID") // Fetch sessionId from database
                );
                chatHistory.add(chat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chatHistory;
    }

    // Method to get a chat summary
    public String getChatSummary(String sessionId) {
        String summary = "New Chat";
        String sql = "SELECT DISTINCT SUMMARY FROM CHATHISTORY WHERE SESSION_ID = ? AND SUMMARY IS NOT NULL";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sessionId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String dbSummary = rs.getString("SUMMARY");
                if (dbSummary != null && !dbSummary.trim().isEmpty()) {
                    summary = dbSummary;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return summary;
    }

    // Method to delete chat history for a specific user
    public boolean deleteChatHistory(String email) {
        String sql = "DELETE FROM CHATHISTORY WHERE EMAIL = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get all chat sessions for a user
    public List<String> getAllChatSessions(String email) {
        List<String> sessions = new ArrayList<>();
        String sql = "SELECT SESSION_ID FROM CHATHISTORY WHERE EMAIL = ? GROUP BY SESSION_ID ORDER BY MAX(TIMESTAMP) DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                sessions.add(rs.getString("SESSION_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sessions;
    }

    // Method to delete chat history for a specific session and user
    public boolean deleteChatSessionBySessionId(String email, String chatSessionId) {
        String sql = "DELETE FROM CHATHISTORY WHERE EMAIL = ? AND SESSION_ID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, chatSessionId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update chat summary
    public boolean updateChatSummary(String sessionId, String summary) {
        String sql = "UPDATE CHATHISTORY SET SUMMARY = ? WHERE SESSION_ID = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, summary);
            pstmt.setString(2, sessionId);
            
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
