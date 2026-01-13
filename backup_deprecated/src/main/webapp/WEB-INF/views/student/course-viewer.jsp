<%@ include file="../common/header.jsp" %>
    <div style="display: flex; height: calc(100vh - 80px);">
        <!-- Sidebar -->
        <div class="glass-panel"
            style="width: 300px; padding: 1rem; border-radius: 0; border-left: none; border-bottom: none; overflow-y: auto;">
            <h3 style="margin-bottom: 1.5rem; color: var(--accent-cyan);">${course.title}</h3>

            <div style="display: flex; flex-direction: column; gap: 0.5rem;">
                <div class="glass-card"
                    style="padding: 1rem; border-left: 4px solid var(--accent-cyan); background: rgba(6, 182, 212, 0.1);">
                    <strong>01. The CIA Triad</strong>
                    <div style="font-size: 0.8rem; color: var(--text-secondary); margin-top: 0.25rem;">10:00 mins</div>
                </div>

                <div class="glass-card" style="padding: 1rem; opacity: 0.6;">
                    <strong>02. Network Layers</strong>
                    <div style="font-size: 0.8rem; color: var(--text-secondary); margin-top: 0.25rem;">Locked</div>
                </div>

                <div class="glass-card" style="padding: 1rem; opacity: 0.6;">
                    <strong>03. Common Attacks</strong>
                    <div style="font-size: 0.8rem; color: var(--text-secondary); margin-top: 0.25rem;">Locked</div>
                </div>
            </div>
        </div>

        <!-- Main Content -->
        <div style="flex-grow: 1; padding: 2rem; overflow-y: auto;">
            <h1 style="margin-bottom: 1rem;">1. The CIA Triad</h1>

            <!-- Video Player Placeholder -->
            <div
                style="width: 100%; aspect-ratio: 16/9; background: #000; border-radius: 12px; margin-bottom: 2rem; display: flex; align-items: center; justify-content: center; position: relative; overflow: hidden;">
                <div class="glass-panel" style="padding: 1rem; position: absolute;">
                    <i class="fas fa-play" style="font-size: 2rem;"></i>
                </div>
                <!-- In real app, iframe here -->
                <div style="width:100%; height:100%; background: linear-gradient(45deg, #1e1b4b, #000);"></div>
            </div>

            <div class="glass-panel" style="padding: 2rem;">
                <h3>Lesson Content</h3>
                <p style="line-height: 1.6; color: var(--text-secondary);">
                    Confidentiality ensures that sensitive information is accessed only by an authorized person.
                    Integrity ensures that the information is accurate and complete.
                    Availability ensures that information and resources are available to those who need them.
                </p>

                <hr style="margin: 2rem 0; border-color: var(--glass-border);">

                <h3>Knowledge Check</h3>
                <div id="quiz-container">
                    <p style="margin-bottom: 1rem;">Which component reflects the assurance that data has not been
                        altered?</p>
                    <div style="display: flex; flex-direction: column; gap: 0.5rem;">
                        <label class="glass-card" style="padding: 1rem; cursor: pointer;">
                            <input type="radio" name="q1" value="C"> Confidentiality
                        </label>
                        <label class="glass-card" style="padding: 1rem; cursor: pointer;">
                            <input type="radio" name="q1" value="I"> Integrity
                        </label>
                        <label class="glass-card" style="padding: 1rem; cursor: pointer;">
                            <input type="radio" name="q1" value="A"> Availability
                        </label>
                    </div>
                    <button class="btn-cyber" style="margin-top: 1rem;" onclick="checkAnswer()">Submit Answer</button>
                    <div id="quiz-result" style="margin-top: 1rem; font-weight: bold;"></div>
                </div>
            </div>
        </div>
    </div>

    <script>
        function checkAnswer() {
            const selected = document.querySelector('input[name="q1"]:checked');
            const result = document.getElementById('quiz-result');
            if (!selected) {
                result.innerText = "Please select an answer.";
                return;
            }

            if (selected.value === 'I') {
                result.innerHTML = '<span style="color: #27c93f;">Correct! +50 XP Awarded</span>';
                // In real app, AJAX call to update points
            } else {
                result.innerHTML = '<span style="color: #ff5f56;">Incorrect. Try again.</span>';
            }
        }
    </script>
    <%@ include file="../common/footer.jsp" %>