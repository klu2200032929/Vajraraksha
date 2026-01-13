<%@ include file="../common/header.jsp" %>
    <div style="padding: 2rem;">
        <h1 style="margin-bottom: 2rem;">Instructor Console</h1>

        <div class="dashboard-grid">
            <div class="glass-card" style="padding: 2rem; text-align: center; cursor: pointer;">
                <i class="fas fa-plus-circle"
                    style="font-size: 3rem; color: var(--accent-cyan); margin-bottom: 1rem;"></i>
                <h3>Create New Course</h3>
            </div>
            <div class="glass-card" style="padding: 2rem; text-align: center; cursor: pointer;">
                <i class="fas fa-flask" style="font-size: 3rem; color: var(--accent-magenta); margin-bottom: 1rem;"></i>
                <h3>Manage Labs</h3>
            </div>
        </div>

        <div class="glass-panel" style="margin-top: 2rem; padding: 2rem;">
            <h3>Your Courses</h3>
            <p style="color: var(--text-secondary);">No active courses found. Create one to get started.</p>
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>