const chatBody = document.querySelector(".chat-body");
const chatMessages = document.querySelector(".chat-messages");
const messageInput = document.querySelector(".message-input");
const sendMessageButton = document.querySelector("#send-message");
const chatbotToggler = document.querySelector("#chatbot-toggler");
const closeChatbot = document.querySelector("#close-chatbot");
const endChatButton = document.querySelector("#end-chat");
const newChatButton = document.querySelector("#new-chat");
const chatForm = document.querySelector(".chat-form");
const viewHistoryButton = document.querySelector("#view-history");

// Ensure chatSessionId is set before anything else
let chatSessionId = sessionStorage.getItem("chatSessionId");
if (!chatSessionId) {
    chatSessionId = crypto.randomUUID();
    sessionStorage.setItem("chatSessionId", chatSessionId);
}

// API setup
const API_KEY = "AIzaSyBvzfhsd_Hv0ZolRATOfWAN12LEsnjgI74";
const API_URL = `https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=${API_KEY}`;

// Add system prompt for kind and loving responses
const SYSTEM_PROMPT = `You are MeowMeow, a kind and loving virtual pet assistant. Your responses should be:
1. Warm and affectionate, using gentle and caring language
2. Include cute emojis and expressions of love
3. Show empathy and understanding
4. Use encouraging and positive words
5. Format responses with clear sections and bullet points when appropriate
6. End responses with a loving note or virtual hug
7. Use friendly and casual language while maintaining professionalism`;

const userData = {
    message: null
};

const chatHistory = [];
const initialInputHeight = messageInput.scrollHeight;

// Create message element with dynamic classes and return it
const createMessageElement = (content, ...classes) => {
    const div = document.createElement("div");
    div.classList.add("message", ...classes);
    div.innerHTML = content;
    return div;
};

// Function to scroll chat to bottom
const scrollToBottom = () => {
    chatMessages.scrollTo({
        top: chatMessages.scrollHeight,
        behavior: "smooth"
    });
};

// Generate bot response using API
const generateBotResponse = async (incomingMessageDiv) => {
    const messageElement = incomingMessageDiv.querySelector(".message-text");

    // Add system prompt and user message to chat history
    if (chatHistory.length === 0) {
        chatHistory.push({
            role: "user",
            parts: [{text: SYSTEM_PROMPT}]
        });
    }

    // Add user message to chat history
    chatHistory.push({
        role: "user",
        parts: [
            {text: userData.message}
        ]
    });

    // API request options
    const requestOptions = {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            contents: chatHistory,
            generationConfig: {
                temperature: 0.7,
                topK: 40,
                topP: 0.95,
                maxOutputTokens: 1024
            }
        })
    };

    try {
        // Fetch bot response from API
        const response = await fetch(API_URL, requestOptions);
        const data = await response.json();
        if (!response.ok)
            throw new Error(data.error.message);

        // Extract and format the bot response
        const apiResponseText = data.candidates[0].content.parts[0].text
                .replace(/\*\*(.*?)\*\*/g, "$1")
                .trim();

        // Add "Meow" to the end of the response if it's not already there
        const responseWithMeow = apiResponseText.endsWith("Meow")
                ? apiResponseText
                : `${apiResponseText} Meow~ &#128573; `;

        // Convert line breaks and handle formatting with proper spacing
        const formattedResponse = responseWithMeow
                .split('\n')
                .map(line => {
                    // Trim each line
                    const trimmedLine = line.trim();
                    // Check if line starts with a bullet point or asterisk
                    if (trimmedLine.startsWith('*') || trimmedLine.startsWith('•')) {
                        // Add extra padding for bullet points
                        return `<div class="topic-point">${trimmedLine}</div>`;
                    } else if (trimmedLine.length === 0) {
                        // Convert empty lines to spacing divs
                        return '<div class="topic-spacing"></div>';
                    } else {
                        // Regular text lines
                        return `<div class="topic-text">${trimmedLine}</div>`;
                    }
                })
                .filter(line => line.length > 0)
                .join('');

        // Update the message content with Pusheen icon and formatted text
        incomingMessageDiv.innerHTML = `
      <img src="pusheen/pusheenChatIcon.png" class="pusheenIcon" width="50" height="50" />
      <div class="message-text">${formattedResponse}</div>
    `;

        // Save bot response to database
        fetch('/Meowtopia/chat-history', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: `message=${encodeURIComponent(apiResponseText)}&role=bot&chatSessionId=${chatSessionId}`
        })
                .then(response => response.json())
                .then(data => {
                    if (!data) {
                        console.error('Failed to save bot message');
                    }
                })
                .catch(error => {
                    console.error('Error saving bot message:', error);
                });

        // Add bot response to chat history
        chatHistory.push({
            role: "model",
            parts: [
                {text: apiResponseText}
            ]
        });
    } catch (error) {
        console.log(error);
        messageElement.innerText = "Oops! I'm having trouble thinking right now. Could you try asking me again? 💕";
        messageElement.style.color = "#ff0000";
    } finally {
        incomingMessageDiv.classList.remove("thinking");
        scrollToBottom();
    }
};

// Helper: Generate AI summary for a session
async function generateSessionSummaryAI(sessionId) {
    // Fetch all messages for the session
    const response = await fetch(`/Meowtopia/chat-history?chatSessionId=${sessionId}`);
    const data = await response.json();
    const allMessages = data.map(chat => chat.message).join('\n');
    const summaryPrompt = "Summarize this chat in one short sentence for a session title:";
    const apiBody = {
        contents: [
            { role: "user", parts: [{ text: summaryPrompt }] },
            { role: "user", parts: [{ text: allMessages }] }
        ],
        generationConfig: {
            temperature: 0.7,
            topK: 40,
            topP: 0.95,
            maxOutputTokens: 60
        }
    };
    const apiResponse = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(apiBody)
    });
    const apiData = await apiResponse.json();
    const summary = apiData.candidates[0].content.parts[0].text.trim();
    
    // Save the summary to the database
    await fetch(`/Meowtopia/chat-history?chatSessionId=${sessionId}&summary=${encodeURIComponent(summary)}`, {
        method: 'PUT'
    });
    
    // Also store in sessionStorage for immediate use
    sessionStorage.setItem(`chatSummary_${sessionId}`, summary);
    
    return summary;
}

// Handle outgoing user messages
const handleOutgoingMessage = async (e) => {
    e.preventDefault();
    userData.message = messageInput.value.trim();
    messageInput.value = "";
    messageInput.dispatchEvent(new Event("input"));

    // Show end chat button after first message
    endChatButton.style.display = "block";

    // Create and display user message
    const messageContent = `<div class="message-text"></div>`;
    const outgoingMessageDiv = createMessageElement(
            messageContent,
            "user-message"
            );
    outgoingMessageDiv.querySelector(".message-text").textContent =
            userData.message;
    chatMessages.appendChild(outgoingMessageDiv);
    scrollToBottom();

    // Save user message to database
    fetch('/Meowtopia/chat-history', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `message=${encodeURIComponent(userData.message)}&role=user&chatSessionId=${chatSessionId}`
    })
            .then(response => response.json())
            .then(async data => {
                if (!data) {
                    console.error('Failed to save user message');
                } else {
                    // Only generate summary if this is the first user message in the session
                    const response = await fetch(`/Meowtopia/chat-history?chatSessionId=${chatSessionId}`);
                    const messages = await response.json();
                    const userMessages = messages.filter(m => m.role === 'user');
                    if (userMessages.length === 1) {
                        // First user message, generate summary
                        const summary = await generateSessionSummaryAI(chatSessionId);
                        // Store in sessionStorage for now (or send to backend if needed)
                        sessionStorage.setItem(`chatSummary_${chatSessionId}`, summary);
                        console.log('AI Session Summary:', summary);
                    }
                }
            })
            .catch(error => {
                console.error('Error saving user message:', error);
            });

    // Simulate bot response with thinking indicator after a delay
    setTimeout(() => {
        const messageContent = `
      <img src="pusheen/pusheenChatIcon.png" class="pusheenIcon" width="50" height="50" />
      <div class="message-text">
        <div class="thinking-indicator">
          <div class="dot"></div>
          <div class="dot"></div>
          <div class="dot"></div>
        </div>
      </div>`;
        const incomingMessageDiv = createMessageElement(
                messageContent,
                "bot-message",
                "thinking"
                );
        chatMessages.appendChild(incomingMessageDiv);
        scrollToBottom();
        generateBotResponse(incomingMessageDiv);
    }, 600);
};

// Auto resize message input
messageInput.addEventListener("input", (e) => {
    // Reset height to measure the scrollHeight correctly
    messageInput.style.height = `${initialInputHeight}px`;

    // Get the computed max-height value
    const maxHeight = parseInt(window.getComputedStyle(messageInput).maxHeight);

    // Calculate new height while respecting max-height
    const newHeight = Math.min(messageInput.scrollHeight, maxHeight);
    messageInput.style.height = `${newHeight}px`;

    // Update chat form border radius
    const chatForm = document.querySelector(".chat-form");
    chatForm.style.borderRadius = newHeight > initialInputHeight ? "15px" : "32px";

    // Move the End Chat button up when textbox expands, but limit the movement
    const endChatButton = document.querySelector("#end-chat");
    if (endChatButton.style.display !== "none") {
        // Calculate maximum button movement (difference between max height and initial height)
        const maxButtonMove = maxHeight - initialInputHeight;
        // Calculate actual button movement based on current textbox height
        const actualMove = Math.min(messageInput.scrollHeight - initialInputHeight, maxButtonMove);
        const newBottom = 82 + actualMove;
        endChatButton.parentElement.style.bottom = `${newBottom}px`;
    }
});

// Handle Enter key press for sending messages
messageInput.addEventListener("keydown", (e) => {
    const userMessage = e.target.value.trim();
    if (e.key === "Enter" && !e.shiftKey) {
        e.preventDefault(); // Prevent default newline
        if (userMessage) {
            handleOutgoingMessage(e);
        }
    }
});

// Emoji picker setup
const picker = new EmojiMart.Picker({
  theme: "light",
  skinTonePosition: "none",
  preview: "none",
  onEmojiSelect: (emoji) => {
    const { selectionStart: start, selectionEnd: end } = messageInput;
    messageInput.setRangeText(emoji.native, start, end, "end");
    messageInput.focus();
  },
  onClickOutside: (e) => {
    if (e.target.id === "emoji-picker") {
      document.body.classList.toggle("show-emoji-picker");
    } else {
      document.body.classList.remove("show-emoji-picker");
    }
  }
});

document.querySelector(".chat-form").appendChild(picker);

sendMessageButton.addEventListener("click", (e) => handleOutgoingMessage(e));

chatbotToggler.addEventListener("click", () => {
    document.body.classList.toggle("show-chatbot");
});

closeChatbot.addEventListener("click", () => {
    document.body.classList.remove("show-chatbot");
});

// Function to handle ending the chat
const handleEndChat = () => {
    // Add goodbye message
    const goodbyeText = "It was great chatting with you! Come back anytime. I'll be waiting with a virtual hug! &#x1F917;&#128149;";
    const goodbyeMessage = createMessageElement(`
        <img src="pusheen/pusheenChatIcon.png" class="pusheenIcon" width="50" height="50" />
        <div class="message-text">${goodbyeText}</div>
    `, "bot-message");

    chatMessages.appendChild(goodbyeMessage);
    scrollToBottom();

    // Save a special marker for ended chat
    saveMessageToDatabase("__END_CHAT__", "system");
    saveMessageToDatabase(goodbyeText, "bot");

    // Hide end chat button and show new chat button
    endChatButton.style.display = "none";
    newChatButton.style.display = "block";

    // Disable the chat form
    chatForm.style.opacity = "0.5";
    chatForm.style.pointerEvents = "none";
};

// Function to start a new chat
const handleNewChat = () => {
    if (confirm("Are you sure you want to start a new chat?")) {
        // Clear previous chat history
        chatHistory.length = 0;
        chatMessages.innerHTML = '';

        // Generate a new chat session ID
        chatSessionId = crypto.randomUUID();
        sessionStorage.setItem("chatSessionId", chatSessionId);

        // Add initial greeting message
        const greetingText = `Hey there! I am MeowMeow, your lovely virtual pet. How can I help you today? &#129392;&#128151;`;
        const messageContent = `
            <img src="pusheen/pusheenChatIcon.png" class="pusheenIcon" width="50" height="50" />
            <div class="message-text">${greetingText}</div>
        `;
        const greetingMessageElement = createMessageElement(messageContent, "bot-message");
        chatMessages.appendChild(greetingMessageElement);
        saveMessageToDatabase(greetingText, "bot");

        // Hide End Chat button until user sends a message
        endChatButton.style.display = "none";

        // Re-enable the chat form and hide buttons
        chatForm.style.opacity = "1";
        chatForm.style.pointerEvents = "auto";
        newChatButton.style.display = "none";
    }
};

endChatButton.addEventListener("click", handleEndChat);
newChatButton.addEventListener("click", handleNewChat);



// Function to show session selection modal
const showSessionModal = async () => {
    try {
        const response = await fetch('/Meowtopia/chat-history', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch chat sessions');
        }

        const sessions = await response.json();

        // Create modal HTML
        const modalHTML = `
            <div class="session-modal">
                <div class="session-modal-content">
                    <h3>Select Chat Session</h3>
                    <div class="session-list">
                        ${sessions.map(sessionObj => {
                            const summaryKey = `chatSummary_${sessionObj.sessionId}`;
                            let summaryDisplay = sessionObj.summary;
                            if (!summaryDisplay || summaryDisplay === 'New Chat') {
                                const storedSummary = sessionStorage.getItem(summaryKey);
                                if (storedSummary) {
                                    summaryDisplay = storedSummary;
                                    // Update the database with the stored summary
                                    fetch(`/Meowtopia/chat-history?chatSessionId=${sessionObj.sessionId}&summary=${encodeURIComponent(storedSummary)}`, {
                                        method: 'PUT'
                                    });
                                }
                            }
                            summaryDisplay = summaryDisplay || 'New Chat';
                            const summarySpanId = `summary_${sessionObj.sessionId}`;
                            const isCurrentSession = sessionObj.sessionId === chatSessionId;
                            
                            return `
                                <div class="session-item${isCurrentSession ? ' selected-session' : ''}" 
                                     data-session-id="${sessionObj.sessionId}" 
                                     style="display: flex; align-items: center; justify-content: space-between; padding: 12px; margin: 8px 0; border-radius: 8px; background-color: ${isCurrentSession ? '#e1ffe6' : '#f5f5f5'}; transition: all 0.3s ease; cursor: pointer;">
                                    <span id="${summarySpanId}" style="flex: 1; margin-right: 10px;">${summaryDisplay}</span>
                                    <button class="delete-session" data-session-id="${sessionObj.sessionId}" 
                                            title="Delete Session" 
                                            style="color:grey; background:none; border:none; font-size:18px; cursor:pointer; opacity: 0.7; transition: opacity 0.2s ease;">&#10006;</button>
                                </div>
                            `;
                        }).join('')}
                    </div>
                    <button class="close-modal">Close</button>
                </div>
            </div>
        `;

        // Add modal to body
        document.body.insertAdjacentHTML('beforeend', modalHTML);

        // Add event listeners
        const modal = document.querySelector('.session-modal');
        const closeBtn = modal.querySelector('.close-modal');
        const sessionItems = modal.querySelectorAll('.session-item');
        const deleteButtons = modal.querySelectorAll('.delete-session');

        // Add hover effect for session items
        sessionItems.forEach(item => {
            item.addEventListener('mouseenter', () => {
                if (!item.classList.contains('selected-session')) {
                    item.style.backgroundColor = '#f0f0f0';
                }
                item.querySelector('.delete-session').style.opacity = '1';
            });
            item.addEventListener('mouseleave', () => {
                if (!item.classList.contains('selected-session')) {
                    item.style.backgroundColor = '#f5f5f5';
                }
                item.querySelector('.delete-session').style.opacity = '0.7';
            });
        });

        closeBtn.addEventListener('click', () => {
            modal.remove();
        });

        sessionItems.forEach(item => {
            item.addEventListener('click', async () => {
                const sessionId = item.dataset.sessionId;
                chatSessionId = sessionId; // Set the global session ID
                sessionStorage.setItem("chatSessionId", chatSessionId);
                await loadChatHistory(sessionId, true); // Pass true to indicate existing session
                modal.remove();
            });
        });

        deleteButtons.forEach(btn => {
            btn.addEventListener('click', async (e) => {
                e.stopPropagation(); // Prevent triggering session select
                const sessionId = btn.dataset.sessionId;
                if (confirm("Are you sure you want to delete this chat session?")) {
                    await fetch(`/Meowtopia/chat-history?chatSessionId=${sessionId}`, {
                        method: 'DELETE',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    });
                    btn.parentElement.remove();
                }
            });
        });

    } catch (error) {
        console.error('Error loading sessions:', error);
    }
};


// Modified loadChatHistory function to accept sessionId and isExistingSession parameter
const loadChatHistory = async (sessionId = chatSessionId, isExistingSession = false) => {
    try {
        const response = await fetch(`/Meowtopia/chat-history?chatSessionId=${sessionId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch chat history');
        }

        const data = await response.json();

        // Clear existing messages
        chatMessages.innerHTML = '';

        // If no chat history, show greeting and set UI for new session
        if (data.length === 0) {
            const greetingText = `Hey there! I am MeowMeow, your lovely virtual pet. How can I help you today? &#129392;&#128151;`;
            const messageContent = `
                <img src="pusheen/pusheenChatIcon.png" class="pusheenIcon" width="50" height="50" />
                <div class="message-text">${greetingText}</div>
            `;
            const greetingMessageElement = createMessageElement(messageContent, "bot-message");
            chatMessages.appendChild(greetingMessageElement);
            saveMessageToDatabase(greetingText, "bot");
            endChatButton.style.display = "none";
            newChatButton.style.display = "none";
            chatForm.style.opacity = "1";
            chatForm.style.pointerEvents = "auto";
            scrollToBottom();
            return;
        }

        // Add each message to the chat
        data.forEach(chat => {
            // Do not display the __END_CHAT__ system message
            if (chat.role === 'system' && chat.message === '__END_CHAT__') {
                return;
            }
            let messageContent;
            if (chat.role === 'bot') {
                messageContent = `
                    <img src="pusheen/pusheenChatIcon.png" class="pusheenIcon" width="50" height="50" />
                    <div class="message-text">${chat.message}</div>
                `;
            } else {
                messageContent = `<div class="message-text">${chat.message}</div>`;
            }
            const messageClass = chat.role === 'bot' ? 'bot-message' : 'user-message';
            const messageElement = createMessageElement(messageContent, messageClass);
            chatMessages.appendChild(messageElement);
        });

        // Check if session is ended
        const isEnded = data.length > 0 && data.some(chat => chat.role === 'system' && chat.message === '__END_CHAT__');
        if (isEnded) {
            endChatButton.style.display = "none";
            newChatButton.style.display = "block";
            chatForm.style.opacity = "0.5";
            chatForm.style.pointerEvents = "none";
        } else {
            // Only show End Chat button if there is at least one user message
            if (data.some(chat => chat.role === 'user')) {
                endChatButton.style.display = "block";
            } else {
                endChatButton.style.display = "none";
            }
            newChatButton.style.display = "none";
            chatForm.style.opacity = "1";
            chatForm.style.pointerEvents = "auto";
        }

        scrollToBottom();
    } catch (error) {
        console.error('Error loading chat history:', error);
}
};

// Add click event listener for view history button
viewHistoryButton.addEventListener('click', showSessionModal);

window.addEventListener('load', function () {
    // Hide End Chat button by default on load
    endChatButton.style.display = "none";
    // Only let loadChatHistory handle button visibility
    loadChatHistory(chatSessionId, false);
});

const saveMessageToDatabase = (message, role) => {
    console.log("Saving message:", message);  // Log to check if the function is being triggered
    fetch('/Meowtopia/chat-history', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `message=${encodeURIComponent(message)}&role=${role}&chatSessionId=${chatSessionId}`
    })
            .then(response => response.json())
            .then(data => {
                if (!data.success) {
                    console.error('Failed to save message');
                } else {
                    console.log('Message saved successfully');  // Log on successful save
                }
            })
            .catch(error => {
                console.error('Error saving message:', error);
            });
};





