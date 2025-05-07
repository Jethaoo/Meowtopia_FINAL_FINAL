/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */


function showDailyMission() {
    const panel = document.querySelector('.daily-mission-section');
    if (panel.style.display === 'block') {
        hideDailyMission();
    } else {
        panel.style.display = 'block';
        hideShop();
        hideFoodShelf();
        hideToyShelf();
        loadDailyMissions();
    }
}

function hideDailyMission() {
    document.querySelector('.daily-mission-section').style.display = 'none';
}

function loadDailyMissions() {
    const email = document.getElementById("userEmail").value;

    fetch("../DailyMission", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: 'email=' + email
    })
            .then(response => response.json())
            .then(data => {
                if (data.success && data.tasks) {
                    const missionPanel = document.querySelector(".mission-panel");
                    // Get the task container inside the panel
                    let taskContainer = missionPanel.querySelector(".task-container");
                    // If it doesn't exist (failsafe), create and insert it
                    if (!taskContainer) {
                        taskContainer = document.createElement("div");
                        taskContainer.className = "task-container";
                        const claimAllBtn = missionPanel.querySelector(".claim-all-btn");
                        missionPanel.insertBefore(taskContainer, claimAllBtn);
                    }

                    // Clear previous tasks
                    taskContainer.innerHTML = "";
                    // Track if all tasks are claimed (for disabling CLAIM ALL button)
                    let allClaimed = true;
                    // Loop through tasks and add them
                    data.tasks.forEach(task => {
                        const taskDiv = document.createElement("div");
                        taskDiv.className = "task";
                        const isClaimed = task.taskstatus === "Claimed";
                        if (!isClaimed) {
                            allClaimed = false; // If at least one task is not claimed, keep CLAIM ALL enabled
                        }
                        let buttonLabel = '';
                        let buttonDisabled = false;
                        let buttonColor = '';
                        if (task.taskstatus === "Claimed") {
                            buttonLabel = "CLAIMED";
                            buttonDisabled = true;
                            buttonColor = "gray";
                        } else if (task.taskcounter >= task.actiontimes) {
                            buttonLabel = "CLAIM";
                        } else {
                            buttonLabel = "GO";
                        }

                        taskDiv.innerHTML = `
                            <div class="task-header">
                                <span class="task-title">${task.tasktitle}</span>
                                <button class="claim-btn" data-taskid="${task.taskid}" 
                                onclick="claimTask('${task.taskid}')" 
                                ${buttonDisabled ? 'disabled' : ''} 
                                style="background-color: ${buttonColor}">
                                ${buttonLabel}
                                </button>
                            </div>
                            <div class="progress-reward">
                                <span>Progress: ${task.taskcounter}/${task.actiontimes}</span>
                                <span>Reward: $${task.reward}</span>
                            </div>
`;
                        taskContainer.appendChild(taskDiv);
                    });
                    // Optionally disable the "CLAIM ALL" button if all tasks are claimed
                    const claimAllBtn = document.getElementById("claimAllBtn");
                    if (claimAllBtn) {
                        claimAllBtn.disabled = allClaimed; // Disable if all tasks are claimed
                        claimAllBtn.textContent = allClaimed ? 'ALL CLAIMED' : 'CLAIM ALL';
                        claimAllBtn.style.backgroundColor = allClaimed ? 'gray' : ''; // Optional visual feedback
                    }

                    // Show the panel
                    document.querySelector('.daily-mission-section').style.display = 'block';
                } else {
                    alert(data.message || "Failed to load missions.");
                }
            })
            .catch(err => {
                console.error("Error loading missions:", err);
                alert("An error occurred.");
            });
}


function claimTask(taskId) {
    const email = document.getElementById("userEmail").value;

    fetch("../ClaimTask", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: 'taskid=' + taskId + '&email=' + email
    })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    const coinBar = document.querySelector(".coin-bar");
                    if (coinBar && data.balance !== undefined) {
                        coinBar.textContent = "$" + data.balance;
                    }

                    loadDailyMissions();

                } else {
                    // Task not yet complete — guide user to the next action
                    if (data.taskcounter < data.actiontimes) {
                        // Remove highlight from all buttons first
                        document.querySelectorAll('.bottom-buttons button').forEach(btn => {
                            btn.classList.remove('highlight-action');
                        });

                        // Highlight the correct button based on taskaction
                        const action = data.taskaction.toLowerCase(); // e.g. "pet", "feed", "play", "chat"
                        const buttonId = action + "-button"; // Construct button id, e.g., "pet-button"
                        const actionButton = document.getElementById(buttonId);

                        if (actionButton) {
                            actionButton.classList.add("highlight-action");
                        }
                    } else {
                        alert(data.message);
                    }
                }
            })
            .catch(error => {
                console.error("Error claiming task:", error);
                alert("Something went wrong.");
            });
}



function updateTaskButtonStatus(taskId, taskstatus) {
    const taskButton = document.querySelector(`.claim-btn[data-taskid='${taskId}']`);
    if (taskButton) {
        if (taskstatus === "Claimed" && !taskButton.disabled) {
            taskButton.disabled = true;
            taskButton.style.backgroundColor = 'gray';
            taskButton.textContent = "CLAIMED";
        }
    }
}

function claimAllTasks(email) {

    fetch("../ClaimAllTasks", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: 'email=' + email
    })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    const coinBar = document.querySelector(".coin-bar");
                    if (coinBar && data.balance !== undefined) {
                        coinBar.textContent = "$" + data.balance;
                    }

                    loadDailyMissions();
                } else {
                    alert("No tasks available to claim.");
                }
            })
            .catch(error => {
                console.error("Error claiming all tasks:", error);
                alert("Something went wrong.Fail to claim");
            });
}

