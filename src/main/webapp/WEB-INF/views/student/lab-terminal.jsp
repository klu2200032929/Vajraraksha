<%@ include file="../common/header.jsp" %>
    <style>
        .terminal-window {
            background-color: #0c0c0c;
            border: 1px solid #333;
            border-radius: 8px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.5);
            font-family: 'Courier New', Courier, monospace;
            overflow: hidden;
            height: 70vh;
            display: flex;
            flex-direction: column;
            margin-top: 2rem;
        }

        .terminal-header {
            background: #1a1a1a;
            padding: 0.5rem 1rem;
            display: flex;
            gap: 0.5rem;
            align-items: center;
            border-bottom: 1px solid #333;
        }

        .dot {
            width: 12px;
            height: 12px;
            border-radius: 50%;
        }

        .red {
            background: #ff5f56;
        }

        .yellow {
            background: #ffbd2e;
        }

        .green {
            background: #27c93f;
        }

        .terminal-body {
            padding: 1rem;
            color: #0f0;
            flex-grow: 1;
            overflow-y: auto;
        }

        .command-line {
            display: flex;
            margin-top: 0.5rem;
        }

        .prompt {
            color: #00ff00;
            margin-right: 0.5rem;
        }

        #input-cmd {
            background: transparent;
            border: none;
            color: #fff;
            font-family: inherit;
            font-size: inherit;
            flex-grow: 1;
            outline: none;
        }

        .output-line {
            color: #ccc;
            margin: 2px 0;
            white-space: pre-wrap;
        }

        .output-success {
            color: #27c93f;
        }

        .output-error {
            color: #ff5f56;
        }

        .output-info {
            color: #06b6d4;
        }
    </style>

    <div style="display: flex; height: calc(100vh - 80px);">
        <!-- Lab Guide Sidebar -->
        <div class="glass-panel"
            style="width: 300px; padding: 1rem; border-radius: 0; border-left: none; border-bottom: none; overflow-y: auto; display: flex; flex-direction: column;">
            <h3 style="color: var(--accent-color); margin-bottom: 1rem;">Lab Guide</h3>
            <p><strong>Goal:</strong> ${lab.description}</p>
            <hr style="margin: 1rem 0; border-color: var(--glass-border);">

            <div id="steps-container">
                <c:forEach items="${lab.steps}" var="step" varStatus="status">
                    <div class="step-card glass-panel" id="step-${step.stepNumber}"
                        style="padding: 1rem; margin-bottom: 1rem; border-radius: 8px; ${status.first ? 'border-left: 4px solid var(--accent-color); background: rgba(0, 255, 136, 0.1);' : 'opacity: 0.6;'}">
                        <strong>Step ${step.stepNumber}:</strong>
                        <p style="font-size: 0.9rem; margin-top: 0.5rem; color: #eee;">${step.instruction}</p>
                        <div
                            style="margin-top: 0.5rem; background: #000; padding: 0.5rem; font-family: monospace; font-size: 0.8rem; color: #aaa;">
                            <i class="fas fa-terminal"></i> ${step.command}
                        </div>
                        <input type="hidden" class="expected-output" value="${step.outputExpected}" />
                        <input type="hidden" class="expected-command" value="${step.command}" />
                    </div>
                </c:forEach>
            </div>

            <div id="lab-complete-msg" class="glass-panel"
                style="display: none; border-left: 4px solid gold; background: rgba(255, 215, 0, 0.1);">
                <h3>🎉 Lab Complete!</h3>
                <p>You have earned ${lab.points} points.</p>
                <a href="/student/dashboard" class="btn-cyber"
                    style="margin-top: 1rem; text-align: center; display: block;">Back to Dashboard</a>
            </div>
        </div>

        <!-- Terminal Window -->
        <div class="container" style="flex-grow: 1; display:flex; flex-direction: column;">
            <div style="margin-bottom: 1rem; display: flex; justify-content: space-between; align-items: center;">
                <h2 class="glow-text">${lab.title}</h2>
                <button class="btn-cyber" onclick="location.reload()">Reset</button>
            </div>

            <div class="terminal-window glass-panel" style="padding: 0; flex-grow:1; height: auto;">
                <div class="terminal-header">
                    <div class="dot red"></div>
                    <div class="dot yellow"></div>
                    <div class="dot green"></div>
                    <span style="margin-left: 1rem; color: #888; font-size: 0.8rem;">root@vajra-kali:~</span>
                </div>
                <div class="terminal-body" id="terminal-output">
                    <div class="output-line output-info">Initializing Secure Sandbox Environment v2.0...</div>
                    <div class="output-line output-success">Connection Established.</div>

                    <div class="command-line">
                        <span class="prompt">root@vajra:~#</span>
                        <input type="text" id="input-cmd" autofocus autocomplete="off">
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        const input = document.getElementById('input-cmd');
        const output = document.getElementById('terminal-output');
        let currentStep = 1;
        const totalSteps = ${ fn: length(lab.steps)};

        input.addEventListener('keydown', function (e) {
            if (e.key === 'Enter') {
                const cmd = this.value.trim();
                const cmdLine = document.createElement('div');
                cmdLine.className = 'output-line';
                cmdLine.innerHTML = '<span class="prompt">root@vajra:~#</span> ' + cmd;
                output.insertBefore(cmdLine, output.lastElementChild);

                this.value = '';
                processCommand(cmd);
            }
        });

        function processCommand(cmd) {
            if (!cmd) return;

            // Simulate processing time
            setTimeout(() => {
                let response = '';

                // Check if command matches current step requirements
                const stepCard = document.getElementById('step-' + currentStep);
                if (stepCard) {
                    const expectedCmd = stepCard.querySelector('.expected-command').value;
                    const expectedOut = stepCard.querySelector('.expected-output').value;

                    if (cmd.includes(expectedCmd.split(' ')[0])) { // Simple check: command starts with expected tool
                        // If strict match needed: cmd === expectedCmd
                        response = expectedOut;

                        // Mark step as done
                        stepCard.style.borderLeft = "4px solid #27c93f";
                        stepCard.style.opacity = "0.5";

                        // Move to next step
                        currentStep++;
                        const nextStep = document.getElementById('step-' + currentStep);
                        if (nextStep) {
                            nextStep.style.opacity = "1";
                            nextStep.style.borderLeft = "4px solid var(--accent-color)";
                            nextStep.style.background = "rgba(0, 255, 136, 0.1)";
                            nextStep.scrollIntoView({ behavior: 'smooth' });
                        } else {
                            // All steps done

                            document.getElementById('lab-complete-msg').style.display = 'block';
                            response += '\n<span class="output-success">MISSION ACCOMPLISHED.</span>';

                            // Send completion to server
                            // Send completion to server
                            fetch('/student/lab/${lab.id}/complete', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                }
                            }).then(res => {
                                if (!res.ok) throw new Error(res.status);
                                return res.text();
                            })
                                .then(msg => {
                                    console.log("Server response: " + msg);

                                    // Visual feedback
                                    if (msg.includes("Success")) {
                                        response += '\n<span class="output-success">Server: Points Awarded!</span>';
                                        alert("Mission Accomplished! Points added to your profile.");
                                    } else {
                                        response += '\n<span class="output-info">Server: ' + msg + '</span>';
                                    }

                                    // Update terminal with server response
                                    const respDiv = document.createElement('div');
                                    respDiv.className = 'output-line';
                                    respDiv.innerHTML = '<span class="output-info">Server: ' + msg + '</span>';
                                    output.insertBefore(respDiv, output.lastElementChild);
                                    output.scrollTop = output.scrollHeight;
                                })
                                .catch(err => {
                                    console.error(err);
                                    alert("Error contacting server. Check console.");
                                });
                        }
                    } else {
                        // Generic Fallback similar to linux
                        if (cmd === 'ls') response = 'bin  boot  dev  etc  home  lib  proc  root  sys  tmp  usr  var';
                        else if (cmd === 'clear') { window.location.reload(); return; }
                        else if (cmd === 'whoami') response = 'root';
                        else response = 'bash: ' + cmd + ': command not found';
                    }
                }

                const respDiv = document.createElement('div');
                respDiv.className = 'output-line';
                respDiv.innerHTML = response;
                output.insertBefore(respDiv, output.lastElementChild);

                output.scrollTop = output.scrollHeight;
            }, 300);
        }
    </script>
    <%@ include file="../common/footer.jsp" %>