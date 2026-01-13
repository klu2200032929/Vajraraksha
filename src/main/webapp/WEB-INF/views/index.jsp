<%@ include file="common/header.jsp" %>

    <div class="container" style="text-align: center; margin-top: 5rem;">
        <h1 class="text-gradient" style="font-size: 4rem; margin-bottom: 2rem;">Master Cybersecurity</h1>
        <p
            style="font-size: 1.2rem; color: var(--text-secondary); margin-bottom: 3rem; max-width: 600px; margin-left: auto; margin-right: auto;">
            Join the elite force of cyber defenders. Practice on real-world scenarios in a secure, sandboxed
            environment.
        </p>

        <div style="display: flex; gap: 1rem; justify-content: center;">
            <a href="/register" class="btn-cyber" style="font-size: 1.2rem; padding: 1rem 2rem;">Start Training</a>
            <a href="/login" class="btn-cyber"
                style="background: transparent; border: 1px solid var(--accent-color); color: var(--accent-color);">Login</a>
        </div>

        <div style="margin-top: 5rem; display: grid; grid-template-columns: repeat(3, 1fr); gap: 2rem;">
            <div class="glass-panel">
                <i class="fas fa-desktop" style="font-size: 2rem; color: var(--accent-color); margin-bottom: 1rem;"></i>
                <h3>Virtual Labs</h3>
                <p>Interactive Linux terminals in your browser.</p>
            </div>
            <div class="glass-panel">
                <i class="fas fa-shield-alt" style="font-size: 2rem; color: #00aaff; margin-bottom: 1rem;"></i>
                <h3>Real Scenarios</h3>
                <p>Defend against simulated attacks.</p>
            </div>
            <div class="glass-panel">
                <i class="fas fa-trophy" style="font-size: 2rem; color: #ffbd2e; margin-bottom: 1rem;"></i>
                <h3>Earn Badges</h3>
                <p>Track progress and show off your skills.</p>
            </div>
        </div>
    </div>

    <%@ include file="common/footer.jsp" %>