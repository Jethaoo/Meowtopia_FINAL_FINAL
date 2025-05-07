package Servlet;

import Model.Account;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Model.ChatHistory;
import Model.ChatHistoryService;

import java.io.IOException;
import java.util.List;

public class ChatHistoryServlet extends HttpServlet {

    private ChatHistoryService chatHistoryService;

    @Override
    public void init() throws ServletException {
        chatHistoryService = new ChatHistoryService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("login") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }

        Account account = (Account) session.getAttribute("login");
        String email = account.getEmail();

        // Retrieve the unique chat session ID from the request
        String chatSessionId = request.getParameter("chatSessionId");
        if (chatSessionId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Chat session ID is missing");
            return;
        }

        String message = request.getParameter("message");
        String role = request.getParameter("role");

        if (message == null || role == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters");
            return;
        }

        boolean success = chatHistoryService.saveChatMessage(chatSessionId, email, message, role);

        response.setContentType("application/json");
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("success", success);
        response.getWriter().write(jsonBuilder.build().toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("login") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }

        Account account = (Account) session.getAttribute("login");
        String email = account.getEmail();

        // Check if we're getting all sessions or a specific session
        String chatSessionId = request.getParameter("chatSessionId");

        response.setContentType("application/json");

        if (chatSessionId == null) {
            // Get all sessions
            List<String> sessions = chatHistoryService.getAllChatSessions(email);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (String sessionId : sessions) {
                // Get the session summary (first and last message)
                String summary = chatHistoryService.getChatSummary(sessionId);
                arrayBuilder.add(Json.createObjectBuilder()
                        .add("sessionId", sessionId)
                        .add("summary", summary));
            }
            response.getWriter().write(arrayBuilder.build().toString());
        } else {
            // Get specific session history
            List<ChatHistory> chatHistory = chatHistoryService.getChatHistoryBySessionId(chatSessionId);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            for (ChatHistory chat : chatHistory) {
                JsonObjectBuilder chatBuilder = Json.createObjectBuilder()
                        .add("chatId", chat.getChatId())
                        .add("email", chat.getEmail())
                        .add("message", chat.getMessage())
                        .add("timestamp", chat.getTimestamp().toString())
                        .add("role", chat.getRole());
                arrayBuilder.add(chatBuilder);
            }

            response.getWriter().write(arrayBuilder.build().toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("login") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }

        Account account = (Account) session.getAttribute("login");
        String email = account.getEmail();

        // Check if chatSessionId is provided for session-specific deletion
        String chatSessionId = request.getParameter("chatSessionId");
        boolean success;
        if (chatSessionId != null) {
            // Delete all messages for this session
            success = chatHistoryService.deleteChatSessionBySessionId(email, chatSessionId);
        } else {
            // Delete all chat history for the user (existing behavior)
            success = chatHistoryService.deleteChatHistory(email);
        }

        response.setContentType("application/json");
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("success", success);
        response.getWriter().write(jsonBuilder.build().toString());
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("login") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }

        Account account = (Account) session.getAttribute("login");
        String email = account.getEmail();
        
        // Get the chat session ID and summary from the request
        String chatSessionId = request.getParameter("chatSessionId");
        String summary = request.getParameter("summary");
        
        if (chatSessionId == null || summary == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters");
            return;
        }
        
        boolean success = chatHistoryService.updateChatSummary(chatSessionId, summary);
        
        response.setContentType("application/json");
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("success", success);
        response.getWriter().write(jsonBuilder.build().toString());
    }
}
