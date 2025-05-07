function showToyShelf() {
    const shelf = document.getElementById('toy-shelf');
    if (shelf.style.display === 'flex') {
        hideToyShelf();
    } else {
        hideDailyMission(); // If exists
        hideShop();
        hideFoodShelf();
        clearActionHighlight();
        const shelfRows = shelf.querySelector('.shelf-rows');
        shelfRows.innerHTML = ''; // Clear previous toy items

        // Fetch available toys from servlet
        fetch('../DisplayAvailableToy', {method: 'GET'})
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Failed to load toy shelf');
                    }
                    return response.json();
                })
                .then(availableToys => {
                    if (availableToys.length === 0) {
                        shelfRows.innerHTML = '<p style="color: #fff; text-align: center; margin-top: 75px;">No toys available.</p>';
                        return;
                    }

                    for (let i = 0; i < availableToys.length; i += 3) {
                        const row = document.createElement('div');
                        row.className = 'shelf-row';

                        for (let j = i; j < i + 3 && j < availableToys.length; j++) {
                            const toys = availableToys[j];

                            const toyItem = document.createElement('div');
                            toyItem.className = 'toy-item';
                            toyItem.setAttribute('onclick', `playPet('${toys.toyid}', ${toys.happiness}, ${toys.energyused})`);
                            toyItem.setAttribute('title', `${toys.toyname} - Happiness Added: ${toys.happiness}, Energy Consumed: ${toys.energyused}`);

                            const img = document.createElement('img');
                            img.src = toys.toypic;
                            img.alt = toys.toyname;

                            toyItem.appendChild(img);
                            row.appendChild(toyItem);
                        }

                        shelfRows.appendChild(row);
                    }

                    shelf.style.display = 'flex';
                })
                .catch(error => {
                    console.error('Error loading toys shelf:', error);
                });

    }
}

function hideToyShelf() {
    document.getElementById('toy-shelf').style.display = 'none';
}

function playPet(toyId, happyValue, energyValue) {
    const gifElement = document.getElementById('pusheen-gif');
    const countdown = document.getElementById('petting-countdown');
    const email = document.getElementById("userEmail").value;

    // Disable all buttons
    document.querySelectorAll('.circle-btn, .toy-item').forEach(elem => {
        elem.classList.add('disabled');
    });

    console.log("happiness be4 play: ", happinessLevel);
    console.log("energy be4 play: ", energyLevel);
    if (energyLevel < 20) {
        gifElement.src = 'pusheen/pusheen-tired.gif';
        showMoodMessage("Meow~~I'm too tired to play...");

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

                // Re-enable all buttons
                document.querySelectorAll('.circle-btn, .toy-item').forEach(elem => {
                    elem.classList.remove('disabled');
                });

                // Switch back to default/random GIF
                updateGifBasedOnMood();
            }
        }, 1000);

        return;
    } else {

        handleAction(email, "Play", toyId);
        let seconds = 3;
        countdown.style.display = 'block';
        countdown.textContent = seconds + "s";
        hideMoodMessage();

        // Get the specific gif for this toy
        const playGif = getPlayGif(toyId);
        gifElement.src = playGif;

        increaseHappiness(happyValue);
        decreaseEnergy(energyValue);

        const countdownInterval = setInterval(() => {
            seconds--;
            if (seconds > 0) {
                countdown.textContent = seconds + "s";
            } else {
                clearInterval(countdownInterval);
                countdown.style.display = 'none';

                // Re-enable all buttons
                document.querySelectorAll('.circle-btn, .toy-item').forEach(elem => {
                    elem.classList.remove('disabled');
                });

                // Switch back to default/random GIF
                updateGifBasedOnMood();
                console.log("happiness after play: ", happinessLevel);
                console.log("energy after play: ", energyLevel);
            }
        }, 1000);
    }
}


function getPlayGif(toyId) {
    const playGifMap = {
        'T001': 'pusheen/gif/PlayBall-pusheen.gif',
        'T002': 'pusheen/gif/mouse.gif',
        'T003': 'pusheen/gif/DouMaoBang-pusheen.gif',
        'T004': 'pusheen/gif/playTablet.gif',
        'T005': 'pusheen/gif/playShoes.gif',
        'T006': 'pusheen/gif/playRecorder.gif',
        'T007': 'pusheen/gif/playPlasticBubble.gif',
        'T008': 'pusheen/gif/playPiano.gif',
        'T009': 'pusheen/gif/playScratch.gif'
    };
    return playGifMap[toyId] || playGifs[Math.floor(Math.random() * playGifs.length)];
}


