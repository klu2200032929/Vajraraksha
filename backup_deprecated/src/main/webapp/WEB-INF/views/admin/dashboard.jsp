<%@ include file="../common/header.jsp" %>
    <div style="padding: 2rem;">
        <h1 style="margin-bottom: 2rem;">Admin Command Center</h1>

        <div class="dashboard-grid">
            <div class="glass-panel" style="padding: 1.5rem;">
                <div class="stat-value">Active</div>
                <p>System Status</p>
            </div>
            <div class="glass-panel" style="padding: 1.5rem;">
                <div class="stat-value">${users.size()}</div>
                <p>Total Users</p>
            </div>
            <div class="glass-panel" style="padding: 1.5rem;">
                <div class="stat-value">0</div>
                <p>Security Incidents</p>
            </div>
        </div>

        <div class="glass-panel" style="padding: 2rem; margin-top: 2rem;">
            <h3>Platform Analytics</h3>
            <div style="display: flex; gap: 2rem; margin-top: 1rem; flex-wrap: wrap;">
                <div style="flex: 1; min-width: 300px;">
                    <canvas id="userGrowthChart"></canvas>
                </div>
                <div style="flex: 1; min-width: 300px;">
                    <canvas id="labActivityChart"></canvas>
                </div>
            </div>
        </div>

        <div class="glass-panel" style="padding: 2rem; margin-top: 2rem;">
            <h3>User Management</h3>
            <table>
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Role</th>
                        <th>Points</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="u">
                        <tr>
                            <td>${u.username}</td>
                            <td><span
                                    style="padding: 4px 8px; background: rgba(6, 182, 212, 0.2); border-radius: 4px; color: var(--accent-cyan); font-size: 0.9em;">${u.role}</span>
                            </td>
                            <td>${u.points}</td>
                            <td><a href="#" style="color: var(--accent-magenta);">Manage</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        // User Growth Chart
        const ctx1 = document.getElementById('userGrowthChart').getContext('2d');
        new Chart(ctx1, {
            type: 'line',
            data: {
                labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
                datasets: [{
                    label: 'New Registrations',
                    data: [5, 12, 19, 25, 30, 45],
                    borderColor: '#06b6d4',
                    backgroundColor: 'rgba(6, 182, 212, 0.1)',
                    tension: 0.4,
                    fill: true
                }]
            },
            options: {
                plugins: { legend: { labels: { color: '#94a3b8' } } },
                scales: {
                    y: { grid: { color: 'rgba(255,255,255,0.1)' }, ticks: { color: '#94a3b8' } },
                    x: { grid: { color: 'rgba(255,255,255,0.1)' }, ticks: { color: '#94a3b8' } }
                }
            }
        });

        // Lab Activity Chart
        const ctx2 = document.getElementById('labActivityChart').getContext('2d');
        new Chart(ctx2, {
            type: 'bar',
            data: {
                labels: ['SQL Inj', 'XSS', 'Phishing', 'Network', 'Crypto'],
                datasets: [{
                    label: 'Labs Completed',
                    data: [12, 19, 3, 5, 2],
                    backgroundColor: [
                        'rgba(217, 70, 239, 0.6)',
                        'rgba(6, 182, 212, 0.6)',
                        'rgba(59, 130, 246, 0.6)',
                        'rgba(16, 185, 129, 0.6)',
                        'rgba(245, 158, 11, 0.6)'
                    ],
                    borderColor: 'rgba(255, 255, 255, 0.1)',
                    borderWidth: 1
                }]
            },
            options: {
                plugins: { legend: { labels: { color: '#94a3b8' } } },
                scales: {
                    y: { grid: { color: 'rgba(255,255,255,0.1)' }, ticks: { color: '#94a3b8' } },
                    x: { grid: { color: 'rgba(255,255,255,0.1)' }, ticks: { color: '#94a3b8' } }
                }
            }
        });
    </script>
    <%@ include file="../common/footer.jsp" %>