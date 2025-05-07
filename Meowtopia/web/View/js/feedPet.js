function showFoodShelf() {
    const shelf = document.getElementById('food-shelf');
    if (shelf.style.display === 'flex') {
        hideFoodShelf();
    } else {
        hideDailyMission(); // If exists
        hideShop();
        hideToyShelf();
        clearActionHighlight();
        const shelfRows = shelf.querySelector('.shelf-rows');
        shelfRows.innerHTML = ''; // Clear previous food items

        // Fetch available foods from servlet
        fetch('../DisplayAvailableFood', {method: 'GET'})
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Failed to load food shelf');
                    }
                    return response.json();
                })
                .then(availableFoods => {
                    if (availableFoods.length === 0) {
                        shelfRows.innerHTML = '<p style="color: #fff; text-align: center; margin-top: 75px;">No food available.</p>';
                        return;
                    }

                    for (let i = 0; i < availableFoods.length; i += 3) {
                        const row = document.createElement('div');
                        row.className = 'shelf-row';

                        for (let j = i; j < i + 3 && j < availableFoods.length; j++) {
                            const food = availableFoods[j];

                            const foodItem = document.createElement('div');
                            foodItem.className = 'food-item';
                            foodItem.setAttribute('onclick', `feedPet('${food.foodid}', ${food.energy})`);
                            foodItem.setAttribute('title', `${food.foodname} - Energy: ${food.energy}`);

                            const img = document.createElement('img');
                            img.src = food.foodpic;
                            img.alt = food.foodname;

                            const quantity = document.createElement('span');
                            quantity.className = 'food-quantity';
                            quantity.textContent = 'x' + food.qty;

                            foodItem.appendChild(img);
                            foodItem.appendChild(quantity);
                            row.appendChild(foodItem);
                        }

                        shelfRows.appendChild(row);
                    }

                    shelf.style.display = 'flex';
                })
                .catch(error => {
                    console.error('Error loading food shelf:', error);
                });
    }
}

function hideFoodShelf() {
    document.getElementById('food-shelf').style.display = 'none';
}

function feedPet(foodId, amount) {
    const gifElement = document.getElementById('pusheen-gif');
    const countdown = document.getElementById('petting-countdown');
    const email = document.getElementById("userEmail").value;

    // Disable all buttons
    document.querySelectorAll('.circle-btn, .food-item').forEach(elem => {
        elem.classList.add('disabled');
    });

    console.log("energy be4 feed: ", energyLevel);

    if (energyLevel === 100) {
        gifElement.src = 'pusheen/pusheen-full.gif';
        showMoodMessage("Meow~~I'm too full to eat!");

        let seconds = 3;
        countdown.style.display = 'block';
        countdown.textContent = seconds + "s";

        const countdownInterval = setInterval(() => {
            seconds--;
            if (seconds > 0) {
                countdown.textContent = seconds + "s";
            } else {
                clearInterval(countdownInterval);
                countdown.style.display = 'none';
                hideMoodMessage();

                // Re-enable all buttons
                document.querySelectorAll('.circle-btn, .food-item').forEach(elem => {
                    elem.classList.remove('disabled');
                });

                
                // Switch back to default/random GIF
                updateGifBasedOnMood();
            }
        }, 1000);
        
        return;
    } else {
        handleAction(email, "Feed", foodId);
        hideMoodMessage();
        fetch('../UpdateFoodQty', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'foodId=' + encodeURIComponent(foodId)
        })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        const foodItems = document.querySelectorAll('.food-item');
                        foodItems.forEach(item => {
                            if (item.onclick.toString().includes(foodId)) {
                                if (data.removed) {
                                    // Remove the food item from the shelf
                                    item.remove();
                                    rebuildShelf();
                                } else {
                                    // Update quantity if not removed
                                    let quantitySpan = item.querySelector('.food-quantity');
                                    let qty = parseInt(quantitySpan.textContent.substring(1));
                                    qty = Math.max(0, qty - 1);
                                    quantitySpan.textContent = 'x' + qty;
                                }
                            }
                        });
                    } else {
                        alert("Failed to update food quantity.");
                    }
                });

        let seconds = 3;
        countdown.style.display = 'block';
        countdown.textContent = seconds + "s";

        // Get the specific gif for this food
        const eatGif = getEatGif(foodId);
        gifElement.src = eatGif;

        // Boost energy
        increaseEnergy(amount);



        const countdownInterval = setInterval(() => {
            seconds--;
            if (seconds > 0) {
                countdown.textContent = seconds + "s";
            } else {
                clearInterval(countdownInterval);
                countdown.style.display = 'none';

                // Re-enable all buttons
                document.querySelectorAll('.circle-btn, .food-item').forEach(elem => {
                    elem.classList.remove('disabled');
                });

                // Switch back to default/random GIF
                updateGifBasedOnMood();
                console.log("energy after feed: ", energyLevel);
            }
        }, 1000);

    }

}

function getEatGif(foodId) {
    // Map toy IDs to their corresponding GIFs
    const eatGifMap = {
        'F001': 'pusheen/cat5-catgrass.gif',
        'F002': 'pusheen/pusheen-eating-donut.gif',
        'F003': 'pusheen/pusheen-eating-icecream.gif',
        'F004': 'pusheen/pusheen-eating-mcd.gif',
        'F005': 'pusheen/pusheen-eating-noodles.gif',
        'F006': 'pusheen/pizza1.gif',
        'F007': 'pusheen/pusheen-eating-popcorn.gif',
        'F008': 'pusheen/pusheen-eating-rice.gif',
        'F009': 'pusheen/sushi.gif'
    };

    return eatGifMap[foodId] || eatGifs[Math.floor(Math.random() * eatGifs.length)];
}

function rebuildShelf() {
    const shelfContainer = document.querySelector('.shelf-rows');
    const foodItems = Array.from(document.querySelectorAll('.food-item'));

    // Clear existing shelf rows
    shelfContainer.innerHTML = '';

    for (let i = 0; i < foodItems.length; i += 3) {
        const rowDiv = document.createElement('div');
        rowDiv.className = 'shelf-row';

        for (let j = i; j < i + 3 && j < foodItems.length; j++) {
            rowDiv.appendChild(foodItems[j]);
        }

        shelfContainer.appendChild(rowDiv);
    }
}

