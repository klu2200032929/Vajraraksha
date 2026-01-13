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

        .cursor {
            width: 8px;
            height: 1.2em;
            background: #0f0;
            animation: blink 1s infinite;
            display: inline-block;
            vertical-align: middle;
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

        @keyframes blink {

            0%,
            50% {
                opacity: 1;
            }

            51%,
            100% {
                opacity: 0;
            }
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

    <div style="padding: 2rem; max-width: 1200px; margin: 0 auto;">
        <div style="margin-bottom: 1rem; display: flex; justify-content: space-between; align-items: center;">
            <div>
                <h2 class="glow-text">${lab.title}</h2>
                <p style="color: var(--text-secondary); max-width: 600px; white-space: pre-wrap;">${lab.description}</p>
            </div>
            <button class="btn-cyber" onclick="resetTerminal()">Reset Environment</button>
        </div>

        <div class="terminal-window glass-panel">
            <div class="terminal-header">
                <div class="dot red"></div>
                <div class="dot yellow"></div>
                <div class="dot green"></div>
                <span style="margin-left: 1rem; color: #888; font-size: 0.8rem;">root@vajra-kali:~</span>
            </div>
            <div class="terminal-body" id="terminal-output">
                <div class="output-line output-info">Initializing Secure Sandbox Environment v2.0...</div>
                <div class="output-line output-success">Connection Established.</div>
                <div class="output-line">Type 'help' for available commands.</div>

                <div class="command-line">
                    <span class="prompt">root@vajra:~#</span>
                    <input type="text" id="input-cmd" autofocus autocomplete="off">
                </div>
            </div>
        </div>
    </div>

    <script>
        const input = document.getElementById('input-cmd');
        const output = document.getElementById('terminal-output');
        const history = [];
        let historyIndex = -1;

        input.addEventListener('keydown', function (e) {
            if (e.key === 'Enter') {
                const cmd = this.value.trim();
                this.value = '';
                processCommand(cmd);
            }
        });

        function processCommand(cmd) {
            // Add previous command line to history view
            const cmdLine = document.createElement('div');
            cmdLine.className = 'output-line';
            cmdLine.innerHTML = '<span class="prompt">root@vajra:~#</span> ' + cmd;
            // Insert before the input line (which is last)
            output.insertBefore(cmdLine, output.lastElementChild);

            if (cmd === '') return;
            history.push(cmd);
            historyIndex = history.length;

            // Simulate processing delay
            setTimeout(() => {
                let response = '';
                const args = cmd.split(' ');
                const mainCmd = args[0].toLowerCase();

                switch (mainCmd) {
                    case 'help':
                        response = `Available Commands:
  <span class="output-info">nmap [target]</span>    - Network exploration tool
  <span class="output-info">ping [target]</span>    - Check connectivity
  <span class="output-info">ls</span>                - List directory contents
  <span class="output-info">cat [file]</span>       - Read file content
  <span class="output-info">clear</span>             - Clear terminal screen
  <span class="output-info">whoami</span>            - Print effective userid`;
                        break;
                    case 'clear':
                        // Remove all children except the last one (input line)
                        while (output.childNodes.length > 2) { // Keep init msg + input
                            output.removeChild(output.firstChild);
                        }
                        // Re-add init message if cleared logic desires, or just empty. 
                        // Simpler: reload page or just hide simplified.
                        // For now, let's just clear previous lines logic
                        Array.from(output.children).forEach(child => {
                            if (!child.contains(input)) child.style.display = 'none';
                        });
                        return; // Special case
                    case 'ls':
                        response = 'documents/  downloads/  <span class="output-success">secret.txt</span>  tools/';
                        break;
                    case 'whoami':
                        response = 'root';
                        break;
                    case 'nmap':
                        if (args.length < 2) response = '<span class="output-error">Error: Target required. Usage: nmap <ip></span>';
                        else response = `Starting Nmap 7.92 ( https://nmap.org )
Nmap scan report for \${args[1]}
Host is up (0.0002s latency).
Not shown: 998 closed ports
PORT   STATE SERVICE
22/tcp open  ssh
80/tcp open  http

Nmap done: 1 IP address (1 host up) scanned in 0.15 seconds`;
                        break;
                    case 'cat':
                        if (args.length < 2) response = '<span class="output-error">Error: File required.</span>';
                        else if (args[1] === 'secret.txt') response = '<span class="output-success">FLAG{V4JR4_S3CUR3_H4CK3R}</span>\n\nCongratulations! You found the flag.';
                        else response = `cat: \${args[1]}: No such file or directory`;
                        break;
                    case 'sudo':
                        response = 'User is already root.';
                        break;
                    default:
                        response = `<span class="output-error">Command not found: \${mainCmd}</span>`;
                }

                const respLine = document.createElement('div');
                respLine.className = 'output-line';
                respLine.innerHTML = response;
                output.insertBefore(respLine, output.lastElementChild);

                // Auto scroll
                output.scrollTop = output.scrollHeight;
            }, 300);
        }

        function resetTerminal() {
            location.reload();
        }

        // Always keep focus
        document.addEventListener('click', () => input.focus());
    </script>
    <%@ include file="../common/footer.jsp" %>