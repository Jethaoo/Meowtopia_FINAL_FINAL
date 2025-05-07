<%@page import="Model.Toys, Model.Food, java.util.List"%>
<%
    List<Toys> toyList = (List<Toys>) request.getAttribute("toyList");
    List<Food> foodList = (List<Food>) request.getAttribute("foodList");
    
    if (toyList != null && !toyList.isEmpty()) {
        for (Toys toy : toyList) { 
%>
<div class="shop-item">
    <div class="item-info">
        <img src="data:image/png;base64,<%= toy.getPicBase64() %>" alt="<%= toy.getToyname() %>" class="item-image" onerror="this.src='pusheen/default.gif'">
        <div class="item-details">
            <div class="item-name"><%= toy.getToyname() %></div>
            <div class="item-price">$<%= toy.getPrice() %></div>
        </div>
    </div>
    <button class="buy-btn" onclick="buyItem('Toys', '<%= toy.getToyid() %>')">BUY</button>
</div>
<%  }} 
    if (foodList != null && !foodList.isEmpty()) {
        for (Food food : foodList) { 
%>
<div class="shop-item">
    <div class="item-info">
        <img src="data:image/png;base64,<%= food.getPicBase64() %>" alt="<%= food.getFoodname() %>" class="item-image" onerror="this.src='pusheen/default.gif'">
        <div class="item-details">
            <div class="item-name"><%= food.getFoodname() %></div>
            <div class="item-price">$<%= food.getPrice() %></div>
        </div>
    </div>
    <button class="buy-btn" onclick="buyItem('Food', '<%= food.getFoodid() %>')">BUY</button>
</div>
<%  }} %> 