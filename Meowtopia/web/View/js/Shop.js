/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

function showShop() {
    const panel = document.querySelector('.shop-panel');
    if (panel.style.display === 'flex') {
        hideShop();
    } else {
        panel.style.display = 'flex';
        hideDailyMission(); // If exists
        hideFoodShelf();
        hideToyShelf();
        showOnlyCategory('Toys'); // Default to Toys
        document.getElementById('toys-category').classList.add('active');
    }
}

function hideShop() {
    document.querySelector('.shop-panel').style.display = 'none';
    document.getElementById('food-category').classList.remove('active');
}

function toggleCategory(category) {
    showOnlyCategory(category);

    // Update title

    // Toggle button active states
    document.getElementById('toys-category').classList.remove('active');
    document.getElementById('food-category').classList.remove('active');

    if (category === 'Toys') {
        document.getElementById('toys-category').classList.add('active');
    } else {
        document.getElementById('food-category').classList.add('active');
    }
}

function showOnlyCategory(category) {
    const allItems = document.querySelectorAll('.shop-item');
    allItems.forEach(item => {
        const itemCategory = item.getAttribute('data-category');
        item.style.display = (itemCategory === category) ? 'flex' : 'none';
    });
}


//function buyItem(category, itemId, price, event) {
//    const email = document.getElementById("userEmail").value;
//    if (!email) {
//        alert("User not logged in.");
//        return;
//    }
//
//    // Send data to backend via AJAX
//    fetch("../BuyItem", {
//        method: "POST",
//        headers: {
//            "Content-Type": "application/x-www-form-urlencoded"
//        },
//        body: 'email=' + email + '&itemId=' + itemId + '&category=' + category + '&price=' + price
//    })
//
//            .then(response => response.json())
//            .then(data => {
//                if (data.success) {
//                    if (data.category === "Toys") {
//                        const button = event.target;
//                        button.textContent = "OWNED";
//                        button.disabled = true;
//                        button.style.background = "gray";
//                    }
//                    const coinBar = document.querySelector(".coin-bar");
//                    if (coinBar && data.balance !== undefined) {
//                        coinBar.textContent = "$" + data.balance;
//                    }
//                    
//                } else {
//                    alert(data.message || "Purchase failed.");
//                }
//            })
//            .catch(err => {
//                console.error("Error purchasing item:", err);
//                alert("An error occurred.");
//            });
//}

function buyItem(category, itemId, price, event) {
    const email = document.getElementById("userEmail").value;
    if (!email) {
        alert("User not logged in.");
        return;
    }

    fetch("../BuyItem", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: 'email=' + encodeURIComponent(email) +
                '&itemId=' + encodeURIComponent(itemId) +
                '&category=' + encodeURIComponent(category) +
                '&price=' + encodeURIComponent(price)
    })
            .then(response => response.json())
            .then(data => {
                if (data.itemName) {
                    if (data.category === "Toys") {
                        const button = event.target;
                        button.textContent = "OWNED";
                        button.disabled = true;
                        button.style.background = "gray";
                    }
                    const coinBar = document.querySelector(".coin-bar");
                    if (coinBar && data.balance !== undefined) {
                        coinBar.textContent = "$" + data.balance;
                    }
                    const message = document.createElement("div");
                    message.textContent = `${data.itemName} +1`;
                    message.className = "floating-message-top";
                    document.body.appendChild(message);

                    // Auto-remove after animation
                    setTimeout(() => {
                        message.remove();
                    }, 2000);

                } else {
                    alert(data.message || "Purchase failed.");
                }
            })
            .catch(err => {
                console.error("Error purchasing item:", err);
                alert("An error occurred.");
            });
}



function showItemInfo(imageBase64, name, price, happyValue, energyUsed, category) {
    document.getElementById("info-image").src = "data:image/png;base64," + imageBase64;
    document.getElementById("info-name").textContent = name;
    document.getElementById("info-price").textContent = "$" + price;

    if (category === "Toys") {
        document.getElementById("info-happy").textContent = "Happy Value: " + happyValue;
    } else {
        document.getElementById("info-happy").textContent = "";
    }

    document.getElementById("info-energy").textContent = "Energy Used: " + energyUsed;

    document.getElementById("item-info-panel").style.display = "flex";
}

document.addEventListener('DOMContentLoaded', function () {
    const shopItems = document.querySelectorAll('.shop-item');
    const tooltip = document.getElementById('item-tooltip');

    shopItems.forEach(item => {
        item.addEventListener('mouseenter', function (e) {
            const category = item.getAttribute('data-category');
            const happyValue = item.getAttribute('data-happyvalue');
            const energy = item.getAttribute('data-energy');

            let tooltipText = "";
            if (category === "Food") {
                tooltipText = `Energy Gained: ${energy}`;
            } else {
                tooltipText = `Happy Value: ${happyValue}<br>Energy Used: ${energy}`;
            }

            tooltip.innerHTML = tooltipText;
            tooltip.style.display = 'block';
            positionTooltip(e);
        });

        item.addEventListener('mousemove', function (e) {
            positionTooltip(e);
        });

        item.addEventListener('mouseleave', function () {
            tooltip.style.display = 'none';
        });
    });

    function positionTooltip(e) {
        const tooltip = document.getElementById('item-tooltip');
        tooltip.style.left = (e.pageX + 15) + 'px';
        tooltip.style.top = (e.pageY + 15) + 'px';
    }
});


