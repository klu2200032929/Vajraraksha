<%@ include file="common/header.jsp" %>
    <div class="hero-section" style="text-align: center; padding: 5rem 1rem; position: relative;">
        <div
            style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); width: 600px; height: 600px; background: radial-gradient(circle, rgba(6,182,212,0.1) 0%, transparent 70%); border-radius: 50%; z-index: -1;">
        </div>

        <h1 class="text-gradient" style="font-size: 4rem; margin-bottom: 1rem;">Master Cyber Defense</h1>
        <p style="font-size: 1.25rem; color: var(--text-secondary); max-width: 600px; margin: 0 auto 2rem;">
            Gamified. Interactive. Secure.<br>
            Join the elite force of cyber guardians with VajraRaksha.
        </p>

        <div style="margin-bottom: 4rem;">
            <a href="${pageContext.request.contextPath}/register" class="btn-cyber"
                style="font-size: 1.2rem; padding: 1rem 2rem;">Start Training <i class="fas fa-arrow-right"></i></a>
        </div>

        <div class="scene-3d dashboard-grid">
            <div class="glass-card card-3d" style="padding: 2rem;">
                <i class="fas fa-gamepad"
                    style="font-size: 3rem; color: var(--accent-magenta); margin-bottom: 1rem;"></i>
                <h3>Gamified Learning</h3>
                <p>Earn badges, climb leaderboards, and unlock achievements.</p>
            </div>
            <div class="glass-card card-3d" style="padding: 2rem;">
                <i class="fas fa-terminal" style="font-size: 3rem; color: var(--accent-cyan); margin-bottom: 1rem;"></i>
                <h3>Secure Labs</h3>
                <p>Practice safely in our sandboxed attack-defense environments.</p>
            </div>
            <div class="glass-card card-3d" style="padding: 2rem;">
                <i class="fas fa-user-shield" style="font-size: 3rem; color: #fff; margin-bottom: 1rem;"></i>
                <h3>Role-Based Training</h3>
                <p>Customized paths for Students, Instructors, and Professionals.</p>
            </div>
        </div>
    </div>
    <%@ include file="common/footer.jsp" %>