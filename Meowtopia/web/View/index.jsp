<%@page import="Model.*" %>
<%@page import="java.util.*"%>
<jsp:useBean id="login" scope="session" class="Model.Account" />
<jsp:useBean id="petcat" scope="session" class="Model.Petcat" />

<!DOCTYPE html>

<html lang="en">
    <head>
        <title>Meowtopia</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="https://fonts.googleapis.com/css?family=Montserrat:200,300,400,500,600,700,800&display=swap" rel="stylesheet">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

        <link rel="stylesheet" href="css/animate.css">

        <link rel="stylesheet" href="css/owl.carousel.min.css">
        <link rel="stylesheet" href="css/owl.theme.default.min.css">
        <link rel="stylesheet" href="css/magnific-popup.css">


        <link rel="stylesheet" href="css/bootstrap-datepicker.css">
        <link rel="stylesheet" href="css/jquery.timepicker.css">

        <link rel="stylesheet" href="css/flaticon.css">
        <link rel="stylesheet" href="css/style.css">

        <!-- Icon Font Stylesheet -->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/bootstrap.min.css" rel="stylesheet">


        <!-- Template Stylesheet -->
        <link href="css/style.css" rel="stylesheet">

        <link rel="stylesheet" href="css/index.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@48,400,0,0&family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,1,0" />
        <link rel="stylesheet" href="css/ai.css">
    </head>

    <body>

        <%@ include file="header.jsp" %>

        <div class="hero-wrap js-fullheight" style="background-image: url('images/background.png'); position: relative; background-position: center;">

            <!--         Chatbot-->
            <div class="chatbot-popup">
                <!-- Chatbot Header -->
                <div class="chat-header">
                    <div class="header-info">
                        <img src="pusheen/pusheenChatIcon.png" class="chatbot-logo" width="50" height="50" />

                        <h2 class="logo-text" style="margin-bottom: 0px;">MeowMeow</h2>
                    </div>
                    <div class="header-buttons">
                        <button id="view-history" class="material-symbols-rounded">history</button>
                        <button id="close-chatbot" class="material-symbols-rounded">keyboard_arrow_right</button>
                    </div>
                </div>

                <!-- Chatbot Body -->
                <div class="chat-body">
                    <div class="chat-messages">

                    </div>
                </div>

                <div class="chat-buttons">
                    <button id="end-chat" class="end-chat-btn">End Chat</button>
                    <button id="new-chat" class="new-chat-btn">Start New Chat</button>
                </div>

                <!-- Chatbot Footer -->
                <div class="chat-footer">
                    <form action="#" class="chat-form">
                        <textarea placeholder="Message..." class="message-input" required=""></textarea>
                        <div class="chat-controls">
                            <button type="button" id="emoji-picker" class="material-symbols-rounded">sentiment_satisfied</button>
                            <button type="submit" id="send-message" class="material-symbols-rounded">arrow_upward</button>
                        </div>
                    </form>
                </div>
            </div>
            <!--  END Chatbot-->

            <div class="top-bar">
                <div class="icon-btn">
                    <button class="CheckInButton" onclick="showCheckIn()"><img src="pusheen/daily-check-in.png" alt="checkin"></button>
                </div>

                <div class="icon-btn">
                    <button class="DailyButton" onclick="showDailyMission()" img src="pusheen/mission.png"><img src="pusheen/mission.png" alt="mission" style="width:170%;"></button>
                        <% session.setAttribute("email", login.getEmail()); %>
                </div>
                <div class="icon-btn">
                    <button class="Shop" onclick="showShop()"><img src="pusheen/shop.png" alt="shop" style="width:150%;"></button>
                </div>
                <div class="icon-btn">
                    <div class="currency" style="position:relative;">
                        <div class="coin-bar" style="position:absolute;">$<%= login.getCatcoin() %></div>
                        <input type="hidden" id="userEmail" value="<%= login.getEmail() %>">
                        <img src="pusheen/coins.png" alt="coin" class="coin-icon">
                    </div>
                </div>
                <div class="progress-bars"> <!-- Will float to right -->
                    <div class="happiness-bar">
                        <img src="pusheen/happy.png" alt="happy" style="width:70px; height: 65px ;">
                        <div class="level">
                            <div class="progress-fill health-bar" style="width: <%= petcat.getHappiness() %>%;"> 
                                <span class="happiness-percentage" style="color:black; padding-left: 5px; font-family: cursive;"><%= petcat.getHappiness() %>%</span>
                            </div>
                        </div>
                    </div>
                    <div class="lightning-bar">
                        <img src="pusheen/energyIcon.png" alt="energy" style="width:55px; height: 60px;">
                        <div class="level">
                            <div class="progress-fill energy-bar" style="width: <%= petcat.getHungriness() %>%;">
                                <span class="energy-percentage" style="color:black; padding-left: 5px; font-family: cursive;"><%= petcat.getHungriness() %>%</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Pusheen gif showing-->
            <%
               String selectedGif;
               String[] gifs = {"default.gif", "cat6-sleeping.gif", "TianMao-pusheen.gif", "Rest-pusheen.gif", "HI-pusheen.gif", "dance-dancing.gif", "sleeping-pusheen.gif", "Sleep-pusheen.gif", "pusheen-round-cat.gif"};
               int index = (int) (Math.random() * gifs.length);
               selectedGif = gifs[index];
               String moodMessage = "";
                
               String gifToShow;
               if (petcat.getHappiness() < 20) {
                   gifToShow = "pusheen-sad.gif";
                   moodMessage = "Meow~~I'm feeling sad...";
               } else if (petcat.getHungriness() < 20) {
                   gifToShow = "hungry.gif";
                   moodMessage = "Meow~~I'm so hungry...";
               } else {
                   gifToShow = selectedGif;
                   moodMessage = "";
               }
            %>
            <div id="mood-message" class="bubble-message" style="display: none;"></div>
            

            <div style="position: absolute; bottom: 15%; display: flex; right: 30%; flex-direction: column; align-items: center;">
                <div id="petting-countdown" style="font-size: 32px; font-weight: bold; color: white; display: none;"></div>
                <img id="pusheen-gif" src="pusheen/<%= gifToShow %>" alt="pusheen cat" style="width: 45%">
            </div>           
            <!-- END Pusheen gif showing-->

            <!--            checkin panel-->
            <% Boolean anotherDay = (Boolean) session.getAttribute("firstTimeCheckIn");
                    if(anotherDay){ 
            session.setAttribute("firstTimeCheckIn", false);
            
            %>
            <div id="checkinModal" class="modal" style="display: flex" >
                <%    }else{%>
                <div id="checkinModal" class="modal" style="display: none" >
                    <%  } %>
                    <div class="checkin-popup">
                        <div class="checkin-panel">
                            <%
                            
                                int loginCounter = login.getLogincounter();
                                int[] coins = {5, 5, 5, 5, 10, 10, 15}; // Coin rewards for each day
                                for (int i = 0; i < 7; i++) {
                                    String extraClass = ""; 
                                    if (i + 1 < loginCounter) {
                                        extraClass = "grayed"; // Gray out the past days
                                    } else if (i + 1 == loginCounter) {
                                        extraClass = "today"; // Highlight the current day
                                    } else if (i + 1 > loginCounter) {
                                        extraClass = "future"; // Future days remain white
                                    }
                            %>
                            <div class="day <%= extraClass %>">
                                <div class="coins">+ $<%= coins[i] %></div>
                                <div class="day-label"><%= (i + 1 == loginCounter) ? "Today" : "Day " + (i + 1) %></div>
                            </div>
                            <% } %>
                        </div>
                        <%
                            boolean alreadyClaimed = "Claimed".equals(login.getCheckin());
                            String buttonState = alreadyClaimed ? "disabled" : "";
                            String buttonStyle = alreadyClaimed ? "background-color: #ccc; color: #666; cursor: not-allowed;" : "";
                            String buttonText = alreadyClaimed ? "Checked In!" : "Check-in Today!";
                        %>
                        <button id="checkin-Button" class="checkin-button" style="<%= buttonStyle %>" <%= buttonState %> onclick="handleDailyCheckin()">
                            <%= buttonText %>
                        </button>
                        <button class="close-button" onclick="closeCheckIn()">X</button>
                    </div>
                </div>
                <!-- END checkin panel -->

                <!-- Daily Mission Panel Start here -->
                <div class="daily-mission-section" style="display: none;">
                    <div class="mission-panel">
                        <div class="mission-header">
                            <h2>Daily Missions</h2>
                            <button class="close-btn" onclick="hideDailyMission()">&times;</button>
                        </div>

                        <!-- Task container where tasks will be dynamically inserted -->
                        <div class="task-container">
                            <!-- Tasks will be inserted here by JavaScript -->
                        </div>

                        <button class="claim-all-btn" id="claimAllBtn" onclick="claimAllTasks('<%= login.getEmail() %>')" >CLAIM ALL</button>
                    </div>
                </div>
                <!-- end Daily Mission -->

                <!-- Shop Panel Start -->
                <div class="shop-panel">
                    <div class="shop-header">
                        <div class="category-tabs">
                            <button class="tab-btn" id="toys-category" onclick="toggleCategory('Toys')" id="btn-toys">Toys</button>
                            <button class="tab-btn" id="food-category" onclick="toggleCategory('Food')" id="btn-food">Food</button>
                        </div>
                        <button class="shop-close-btn" onclick="hideShop()">&times;</button>
                    </div>

                    <%
                        List<ToysFood> shopItems = (List<ToysFood>) session.getAttribute("shopItems");
                        List<Cattoys> purchasedToys = (List<Cattoys>) session.getAttribute("purchasedToys");
                    %>

                    <div id="shop-items">
                        <% 
                        for (ToysFood item : shopItems) {
                            String category = item.getCategory();
                            boolean show = category.equals("Toys"); // Only show toys by default
                            boolean alreadyBought = false;

                            if (purchasedToys != null) {
                                for (int i = 0; i < purchasedToys.size(); i++) {
                                    if (purchasedToys.get(i).getCattoysPK().getToyid().equals(item.getId())) {
                                        alreadyBought = true;
                                        break; // Found it, no need to continue
                                    }
                                }
                            }
                        %>
                        <div 
                            class="shop-item" 
                            data-category="<%= category %>" 
                            data-happyvalue="<%= item.getHappyValue() %>" 
                            data-energy="<%= item.getEnergyUsed() %>" 
                            style="display: <%= show ? "block" : "none" %>;"
                            >
                            <div class="item-info">
                                <img 
                                    src="data:image/png;base64,<%= item.getBase64Image() %>" 
                                    alt="<%= item.getName() %>" 
                                    class="item-image" 
                                    onerror="this.src='pusheen/default.gif'"
                                    />
                                <div class="item-details">
                                    <div class="item-name"><%= item.getName() %></div>
                                    <div class="item-price">$<%= item.getPrice() %></div>
                                </div>
                            </div>

                            <!-- Hover message next to the image -->
                            <div class="hover-tooltip">
                                <%
                                    if (category.equals("Toys")) {
                                %>
                                <p>Happy Value: <%= item.getHappyValue() %></p>
                                <p>Energy Used: <%= item.getEnergyUsed() %></p>
                                <%
                                    } else if (category.equals("Food")) {
                                %>
                                <p>Energy Gained: <%= item.getEnergyUsed() %></p>
                                <%
                                    }
                                %>
                            </div>

                            <% if (alreadyBought) { %>
                            <button class="buy-btn" disabled style="background:gray; ">OWNED</button>
                            <% } else { %>
                            <button class="buy-btn" onclick="buyItem('<%= item.getCategory() %>', '<%= item.getId() %>', <%= item.getPrice() %>, event)">BUY</button>
                            <% } %>
                        </div>
                        <%  }   %>
                    </div>
                </div>

                <!-- Shop Panel End -->



                <!--CatFood Shelf Display-->
                <%
                               List<AvailableFood> availableFoods = (List<AvailableFood>) session.getAttribute("availableFoods");
                %>
                <div id="food-shelf" class="shelf-container">
                    <div class="shelf-rows">
                        <% if (availableFoods != null && !availableFoods.isEmpty()) { %>
                        <!-- First Row -->
                        <div class="shelf-row">
                            <% for (int i = 0; i < Math.min(3, availableFoods.size()); i++) { 
                                AvailableFood item = availableFoods.get(i);
                            %>
                            <div class="food-item" onclick="feedPet('<%= item.getFoodid() %>', <%= item.getEnergy() %>)" title="<%= item.getFoodname() %> - Energy: <%= item.getEnergy() %>">
                                <img src="data:image/png;base64,<%= item.getPicBase64() %>" alt="<%= item.getFoodname() %>" />
                                <span class="food-quantity">x<%= item.getQuantity() %></span>
                            </div>
                            <% } %>
                        </div>

                        <!-- Second Row -->
                        <% if (availableFoods.size() > 3) { %>
                        <div class="shelf-row">
                            <% for (int i = 3; i < Math.min(6, availableFoods.size()); i++) { 
                                AvailableFood item = availableFoods.get(i);
                            %>
                            <div class="food-item" onclick="feedPet('<%= item.getFoodid() %>', <%= item.getEnergy() %>)" title="<%= item.getFoodname() %> - Energy: <%= item.getEnergy() %>">
                                <img src="data:image/png;base64,<%= item.getPicBase64() %>" alt="<%= item.getFoodname() %>" />
                                <span class="food-quantity">x<%= item.getQuantity() %></span>
                            </div>
                            <% } %>
                        </div>
                        <% } %>

                        <!-- Third Row -->
                        <% if (availableFoods.size() > 6) { %>
                        <div class="shelf-row">
                            <% for (int i = 6; i < Math.min(9, availableFoods.size()); i++) { 
                                AvailableFood item = availableFoods.get(i);
                            %>
                            <div class="food-item" onclick="feedPet('<%= item.getFoodid() %>', <%= item.getEnergy() %>)" title="<%= item.getFoodname() %> - Energy: <%= item.getEnergy() %>">
                                <img src="data:image/png;base64,<%= item.getPicBase64() %>" alt="<%= item.getFoodname() %>" />
                                <span class="food-quantity">x<%= item.getQuantity() %></span>
                            </div>
                            <% } %>
                        </div>
                        <% } %>
                        <% } else { %>
                        <p style="color: #fff; text-align: center; margin-top: 75px;">No food available.</p>
                        <% } %>
                    </div>
                </div>
                <!-- End CatFood Shelf Display-->



                <!-- Start CatToys Shelf Display-->
                <%
                    List<AvailableToy> availableToys = (List<AvailableToy>) session.getAttribute("availableToys");
                %>
                <div id="toy-shelf" class="shelf-container">
                    <div class="shelf-rows">
                        <% if (availableToys != null && !availableToys.isEmpty()) { %>

                        <!-- First Row -->
                        <div class="shelf-row">
                            <% for (int i = 0; i < Math.min(3, availableToys.size()); i++) {
                                AvailableToy toy = availableToys.get(i);
                            %>
                            <div class="toy-item"
                                 onclick="playPet('<%= toy.getToyid() %>', <%= toy.getHappyvalue() %>, <%= toy.getEnergyused() %>)"
                                 title="<%= toy.getToyname() %> - Happy: <%= toy.getHappyvalue() %>, Energy: <%= toy.getEnergyused() %>"
                                 data-toy-id="<%= toy.getToyid() %>"
                                 data-toy-name="<%= toy.getToyname() %>">
                                <img src="data:image/png;base64,<%= toy.getPicBase64() %>"
                                     alt="<%= toy.getToyname() %>"
                                     onerror="this.src='pusheen/default.gif'">
                            </div>
                            <% } %>
                        </div>

                        <!-- Second Row -->
                        <% if (availableToys.size() > 3) { %>
                        <div class="shelf-row">
                            <% for (int i = 3; i < Math.min(6, availableToys.size()); i++) {
                                AvailableToy toy = availableToys.get(i);
                            %>
                            <div class="toy-item"
                                 onclick="playPet('<%= toy.getToyid() %>', <%= toy.getHappyvalue() %>, <%= toy.getEnergyused() %>)"
                                 title="<%= toy.getToyname() %> - Happy: <%= toy.getHappyvalue() %>, Energy: <%= toy.getEnergyused() %>"
                                 data-toy-id="<%= toy.getToyid() %>"
                                 data-toy-name="<%= toy.getToyname() %>">
                                <img src="data:image/png;base64,<%= toy.getPicBase64() %>"
                                     alt="<%= toy.getToyname() %>"
                                     onerror="this.src='pusheen/default.gif'">
                            </div>
                            <% } %>
                        </div>
                        <% } %>

                        <!-- Third Row -->
                        <% if (availableToys.size() > 6) { %>
                        <div class="shelf-row">
                            <% for (int i = 6; i < Math.min(9, availableToys.size()); i++) {
                                AvailableToy toy = availableToys.get(i);
                            %>
                            <div class="toy-item"
                                 onclick="playPet('<%= toy.getToyid() %>', <%= toy.getHappyvalue() %>, <%= toy.getEnergyused() %>)"
                                 title="<%= toy.getToyname() %> - Happy: <%= toy.getHappyvalue() %>, Energy: <%= toy.getEnergyused() %>"
                                 data-toy-id="<%= toy.getToyid() %>"
                                 data-toy-name="<%= toy.getToyname() %>">
                                <img src="data:image/png;base64,<%= toy.getPicBase64() %>"
                                     alt="<%= toy.getToyname() %>"
                                     onerror="this.src='pusheen/default.gif'">
                            </div>
                            <% } %>
                        </div>
                        <% } %>

                        <% } else { %>
                        <p style="color: #fff; text-align: center; margin-top: 75px;">
                            No toys available. Visit the shop to purchase some toys!
                        </p>
                        <% } %>
                    </div>
                </div>

                <!-- End CatToys Shelf Display-->



                <!-- Bottom Buttons -->
                <div class="bottom-buttons">
                    <button id="pet-button" class="circle-btn" onclick="startPetting()" title="Pet"><img src="pusheen/pet.png" alt="Pet"></button>
                    <button id="feed-button" class="circle-btn" onclick="showFoodShelf()" title="Feed"><img src="pusheen/feed.png" alt="Feed"></button>
                    <button id="play-button" class="circle-btn" onclick="showToyShelf()" title="Pay"><img src="pusheen/play.png" alt="Play"></button>
                    <button class="circle-btn" id="chatbot-toggler" title="Chat"><img src="pusheen/chat.png" alt="Chat"></button>
                </div>

                <!-- loader -->
                <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>



                <script src="https://cdn.jsdelivr.net/npm/emoji-mart@latest/dist/browser.js"></script>
                <script src="js/ai.js"></script>

                <script>
                        const lovedGifs = [
                            "pusheen/cat4-loved.gif",
                            "pusheen/Love-pusheen.gif",
                            "pusheen/Petting-pusheen.gif",
                            "pusheen/pusheen-yay.gif"
                        ];

                        const defaultGifs = [
                            "pusheen/default.gif",
                            "pusheen/cat6-sleeping.gif",
                            "pusheen/TianMao-pusheen.gif",
                            "pusheen/Rest-pusheen.gif",
                            "pusheen/HI-pusheen.gif",
                            "pusheen/dance-dancing.gif",
                            "pusheen/sleeping-pusheen.gif",
                            "pusheen/Sleep-pusheen.gif",
                            "pusheen/pusheen-round-cat.gif"
                        ];

                        var happinessLevel = <%= petcat.getHappiness() %>;
                        var energyLevel = <%= petcat.getHungriness() %>;

                        // Every 60s, decrease by 5%
                        setInterval(function () {
                            decreaseHappiness(5); // happiness
                            decreaseEnergy(5); // energy
                            updateGifBasedOnMood();
                        }, 60000); // 60000 ms = 1 minute


                        function updatePetStatus(action, amount) {
                            fetch('../UpdatePetStatus', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/x-www-form-urlencoded'
                                },
                                body: 'action=' + encodeURIComponent(action) + '&amount=' + encodeURIComponent(amount)
                            })
                                    .then(response => response.json())
                                    .then(data => {
                                        if (data.success) {
                                            // Update the progress bars with the new values from the server
                                            document.querySelector('.health-bar').style.width = data.happiness + '%';
                                            document.querySelector('.happiness-percentage').textContent = data.happiness + '%';
                                            happinessLevel = data.happiness;

                                            document.querySelector('.energy-bar').style.width = data.energy + '%';
                                            document.querySelector('.energy-percentage').textContent = data.energy + '%';
                                            energyLevel = data.energy;

                                        } else {
                                            console.error('Error updating pet status:', data.message);
                                        }
                                    })
                                    .catch(error => {
                                        console.error('Error:', error);
                                    });
                        }

                        function increaseHappiness(amount) {
                            const bar = document.querySelector('.health-bar');
                            const happiness = document.querySelector('.happiness-percentage');
                            if (bar) {
                                let currentWidth = parseFloat(bar.style.width);
                                if (isNaN(currentWidth))
                                    currentWidth = 0;
                                let newWidth = Math.min(100, currentWidth + amount);
                                bar.style.width = newWidth + '%';
                                happiness.textContent = newWidth + '%';
                                happinessLevel = newWidth;
                                // Update database via AJAX
                                updatePetStatus('increaseHappiness', amount);
                            }
                        }

                        function decreaseHappiness(amount) {
                            const bar = document.querySelector('.health-bar');
                            const happiness = document.querySelector('.happiness-percentage');
                            if (bar) {
                                let currentWidth = parseFloat(bar.style.width);
                                if (isNaN(currentWidth))
                                    currentWidth = 0;
                                let newWidth = Math.max(0, currentWidth - amount);
                                bar.style.width = newWidth + '%';
                                happiness.textContent = newWidth + '%';
                                happinessLevel = newWidth;
                                // Update database via AJAX
                                updatePetStatus('decreaseHappiness', amount);
                            }
                        }

                        function increaseEnergy(amount) {
                            const bar = document.querySelector('.energy-bar');
                            const energy = document.querySelector('.energy-percentage');
                            if (bar) {
                                let currentWidth = parseFloat(bar.style.width);
                                if (isNaN(currentWidth))
                                    currentWidth = 0;
                                let newWidth = Math.min(100, currentWidth + amount);
                                bar.style.width = newWidth + '%';
                                energy.textContent = newWidth + '%';
                                energyLevel = newWidth;
                                // Update database via AJAX
                                updatePetStatus('increaseEnergy', amount);
                            }
                        }

                        function decreaseEnergy(amount) {
                            const bar = document.querySelector('.energy-bar');
                            const energy = document.querySelector('.energy-percentage');
                            if (bar) {
                                let currentWidth = parseFloat(bar.style.width);
                                if (isNaN(currentWidth))
                                    currentWidth = 0;
                                let newWidth = Math.max(0, currentWidth - amount);
                                bar.style.width = newWidth + '%';
                                energy.textContent = newWidth + '%';
                                energyLevel = newWidth;
                                // Update database via AJAX
                                updatePetStatus('decreaseEnergy', amount);
                            }
                        }

                        function handleAction(email, action, id) {
                            let bodyData = '';

                            if (action === 'Pet') {
                                bodyData = 'action=' + encodeURIComponent(action) + '&email=' + encodeURIComponent(email);
                            } else {
                                bodyData = 'action=' + encodeURIComponent(action) + '&id=' + encodeURIComponent(id) + '&email=' + encodeURIComponent(email);
                            }

                            fetch("../UpdateTaskProgress", {
                                method: "POST",
                                headers: {
                                    "Content-Type": "application/x-www-form-urlencoded"
                                },
                                body: bodyData
                            })
                                    .then(response => response.json())
                                    .then(data => {
                                        console.log("Action handled successfully:", data);
                                    })
                                    .catch(error => {
                                        console.error("Error handling action:", error);
                                    });
                        }

                        function updateGifBasedOnMood() {
                            const gifElement = document.getElementById('pusheen-gif');
                            if (happinessLevel < 20) {
                                gifElement.src = 'pusheen/pusheen-sad.gif';
                                showMoodMessage("Meow~~I'm feeling sad...");
                            } else if (happinessLevel >= 20 && energyLevel < 20) {
                                gifElement.src = 'pusheen/hungry.gif';
                                showMoodMessage("Meow~~I'm so hungry...");
                            } else {
                                const randomGif = defaultGifs[Math.floor(Math.random() * defaultGifs.length)];
                                gifElement.src = randomGif;
                                hideMoodMessage();
                            }
                        }

                        function showCheckIn() {
                            hideShop();
                            hideDailyMission();
                            document.getElementById("checkinModal").style.display = "flex";
                            document.body.classList.remove("show-chatbot");
                        }

                        function closeCheckIn() {
                            document.getElementById("checkinModal").style.display = "none";
                        }



                        function handleDailyCheckin() {
                            const email = document.getElementById("userEmail").value;
                            const checkinButton = document.getElementById("checkin-Button");

                            // Prevent multiple clicks
                            checkinButton.disabled = true;
                            checkinButton.textContent = "Checking in...";

                            fetch("../Checkin", {
                                method: "POST",
                                headers: {
                                    "Content-Type": "application/x-www-form-urlencoded"
                                },
                                body: 'email=' + email
                            })
                                    .then(response => response.json())
                                    .then(data => {
                                        if (data.success) {
                                            alert(" added to your balance.");
                                            // Update button style
                                            checkinButton.disabled = true;
                                            checkinButton.style.backgroundColor = "#ccc";
                                            checkinButton.style.color = "#666";
                                            checkinButton.style.cursor = "not-allowed";
                                            checkinButton.textContent = "Checked In!";
                                            const coinBar = document.querySelector(".coin-bar");
                                            if (coinBar && data.balance !== undefined) {
                                                coinBar.textContent = "$" + data.balance;
                                            }

                                            // Optional: update the panel colors or counter via JS if needed
                                        } else {
                                            alert("Check-in failed: " + (data.message || "Unknown error"));
                                            checkinButton.disabled = false;
                                            checkinButton.textContent = "Check-in Today!";
                                        }
                                    })
                                    .catch(error => {
                                        console.error("Error during check-in:", error);
                                        alert("Check-in error. Please try again.");
                                        checkinButton.disabled = false;
                                        checkinButton.textContent = "Check-in Today!";
                                    });
                        }

                        function clearActionHighlight() {
                            document.querySelectorAll('.bottom-buttons button').forEach(btn => {
                                btn.classList.remove('highlight-action');
                            });
                        }

                        function updateGifBasedOnMood() {
                            const gifElement = document.getElementById('pusheen-gif');

                            if (happinessLevel < 20) {
                                gifElement.src = 'pusheen/pusheen-sad.gif';
                                showMoodMessage("Meow~~I'm feeling sad...");
                            } else if (happinessLevel >= 20 && energyLevel < 20) {
                                gifElement.src = 'pusheen/hungry.gif';
                                showMoodMessage("Meow~~I'm so hungry!");
                            } else {
                                const randomGif = defaultGifs[Math.floor(Math.random() * defaultGifs.length)];
                                gifElement.src = randomGif;
                            }
                        }

                        const initialMoodMessage = "<%= moodMessage %>";
                        window.addEventListener('DOMContentLoaded', () => {
                            if (initialMoodMessage && initialMoodMessage.trim() !== "") {
                                showMoodMessage(initialMoodMessage);
                            }
                        });

                        function showMoodMessage(message) {
                            const msgElement = document.getElementById("mood-message");
                            msgElement.textContent = message;
                            msgElement.style.display = "block";
                        }

                        function hideMoodMessage() {
                            const msgElement = document.getElementById("mood-message");
                            msgElement.style.display = "none";
                        }

                </script>

                <script src="js/playPet.js"></script>
                <script src="js/pettingPet.js"></script>
                <script src="js/feedPet.js"></script>
                <script src="js/Shop.js"></script>
                <script src="js/DailyMission.js"></script>
                <script src="js/jquery.min.js"></script>
                <script src="js/jquery-migrate-3.0.1.min.js"></script>
                <script src="js/popper.min.js"></script>
                <script src="js/bootstrap.min.js"></script>
                <script src="js/jquery.easing.1.3.js"></script>
                <script src="js/jquery.waypoints.min.js"></script>
                <script src="js/jquery.stellar.min.js"></script>
                <script src="js/jquery.animateNumber.min.js"></script>
                <script src="js/bootstrap-datepicker.js"></script>
                <script src="js/jquery.timepicker.min.js"></script>
                <script src="js/owl.carousel.min.js"></script>
                <script src="js/jquery.magnific-popup.min.js"></script>
                <script src="js/scrollax.min.js"></script>
                <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
                <script src="js/google-map.js"></script>
                <script src="js/main.js"></script>

                </body>
                </html>