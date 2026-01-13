<%@ include file="../common/header.jsp" %>
    <div style="padding: 2rem;">
        <div style="display: flex; justify-content: space-between; align-items: end; margin-bottom: 2rem;">
            <div>
                <h1 class="glow-text">Welcome, ${user.username}</h1>
                <p style="color: var(--text-secondary);">Cyber Recruit - Level 1</p>
            </div>
            <div style="text-align: right;">
                <div class="stat-value text-gradient">${user.points} XP</div>
                <p>Current Rank</p>
            </div>
        </div>

        <h3 style="margin-bottom: 1rem;">Active Missions</h3>
        <div class="dashboard-grid">
            <div class="glass-card card-3d" style="padding: 2rem;">
                <div style="display: flex; justify-content: space-between; margin-bottom: 1rem;">
                    <span
                        style="background: var(--accent-cyan); color: black; padding: 2px 8px; border-radius: 4px; font-weight: bold;">Beginner</span>
                    <i class="fas fa-lock module-icon"></i>
                </div>
                <h3>Intro to Cryptography</h3>
                <p style="color: var(--text-secondary); margin-bottom: 1rem;">Learn the basics of encryption and
                    hashing.</p>
                <div style="background: rgba(255,255,255,0.1); height: 6px; border-radius: 3px; overflow: hidden;">
                    <div style="width: 30%; height: 100%; background: var(--accent-cyan);"></div>
                </div>
                <p style="font-size: 0.8rem; margin-top: 0.5rem;">30% Complete</p>
                <button class="btn-cyber" style="width: 100%; margin-top: 1rem;">Continue</button>
            </div>

            <div class="glass-card card-3d" style="padding: 2rem;">
                <div style="display: flex; justify-content: space-between; margin-bottom: 1rem;">
                    <span
                        style="background: var(--accent-magenta); color: white; padding: 2px 8px; border-radius: 4px; font-weight: bold;">Challenge</span>
                    <i class="fas fa-bug module-icon"></i>
                </div>
                <h3>SQL Injection Lab</h3>
                <p style="color: var(--text-secondary); margin-bottom: 1rem;">Practice exploitation in a sandboxed DB.
                </p>
                <button class="btn-cyber" style="width: 100%; margin-top: 1rem; filter: grayscale(1);">Locked</button>
            </div>
        </div>

        <h3 style="margin-top: 3rem; margin-bottom: 1rem;">Badges & Achievements</h3>
        <div style="display: flex; gap: 1rem; flex-wrap: wrap;">
            <div class="glass-panel" style="padding: 1rem; text-align: center; width: 100px;">
                <i class="fas fa-shield-virus"
                    style="font-size: 2rem; color: var(--accent-cyan); margin-bottom: 0.5rem;"></i>
                <div style="font-size: 0.8rem;">First Login</div>
            </div>
            <div class="glass-panel" style="padding: 1rem; text-align: center; width: 100px; opacity: 0.5;">
                <i class="fas fa-trophy" style="font-size: 2rem; color: gold; margin-bottom: 0.5rem;"></i>
                <div style="font-size: 0.8rem;">Top Hacker</div>
            </div>
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>