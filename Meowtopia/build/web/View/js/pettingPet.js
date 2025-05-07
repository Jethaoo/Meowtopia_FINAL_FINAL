function startPetting() {
    const gifElement = document.getElementById('pusheen-gif');
    const countdown = document.getElementById('petting-countdown');
    const buttons = document.querySelectorAll('.circle-btn');
    const email = document.getElementById("userEmail").value;
    console.log("startPetting");
    hideDailyMission(); // If exists
    hideShop();
    hideToyShelf();
    hideFoodShelf();
    clearActionHighlight();
    //Immediately handle action here
    handleAction(email, "Pet", "NULL");
    hideMoodMessage();

    // Disable all buttons
    document.querySelectorAll('.circle-btn, .food-item').forEach(elem => {
        elem.classList.add('disabled');
    });

    // Set a loved gif
    const randomGif = lovedGifs[Math.floor(Math.random() * lovedGifs.length)];
    gifElement.src = randomGif;

    // Start 3s countdown
    let seconds = 3;
    countdown.style.display = 'block';
    countdown.textContent = seconds + "s";

    // Boost happiness
    increaseHappiness(3);

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
            const gifElement = document.getElementById('pusheen-gif');
            if (happinessLevel < 20) {
                gifElement.src = 'pusheen/pusheen-sad.gif';
            } else if (happinessLevel >= 20 && energyLevel < 20) {
                gifElement.src = 'pusheen/hungry.gif';
            } else {
                const randomGif = defaultGifs[Math.floor(Math.random() * defaultGifs.length)];
                gifElement.src = randomGif;
            }
        }
    }, 1000);
}

