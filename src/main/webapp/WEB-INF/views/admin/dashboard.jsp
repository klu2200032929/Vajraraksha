<%@ include file="../common/header.jsp" %>

    <div class="container">
        <h1 class="text-gradient">Admin Command Center</h1>

        <div
            style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1.5rem; margin-bottom: 2rem;">
            <div class="glass-panel" style="text-align: center;">
                <div style="font-size: 2.5rem; font-weight: 800; color: #27c93f;">Active</div>
                <p style="color: var(--text-secondary);">System Status</p>
            </div>
            <div class="glass-panel" style="text-align: center;">
                <div style="font-size: 2.5rem; font-weight: 800; color: #06b6d4;">${totalUsers}</div>
                <p style="color: var(--text-secondary);">Total Users</p>
            </div>
            <div class="glass-panel" style="text-align: center;">
                <div style="font-size: 2.5rem; font-weight: 800; color: #ff5f56;">0</div>
                <p style="color: var(--text-secondary);">Security Incidents</p>
            </div>
        </div>

        <div style="display: flex; gap: 1rem; margin-bottom: 2rem;">
            <a href="${pageContext.request.contextPath}/admin/courses" class="btn-cyber"
                style="text-align: center; flex: 1;">
                <i class="fas fa-book"></i> Manage Courses
            </a>
            <a href="${pageContext.request.contextPath}/admin/labs" class="btn-cyber"
                style="text-align: center; flex: 1;">
                <i class="fas fa-flask"></i> Manage Labs
            </a>
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
            <table style="width: 100%; border-collapse: collapse; margin-top: 1rem;">
                <thead>
                    <tr style="border-bottom: 1px solid var(--glass-border); text-align: left;">
                        <th style="padding: 1rem;">Username</th>
                        <th style="padding: 1rem;">Role</th>
                        <th style="padding: 1rem;">Points</th>
                        <th style="padding: 1rem;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="u">
                        <tr style="border-bottom: 1px solid var(--glass-border);">
                            <td style="padding: 1rem;">${u.username}</td>
                            <td style="padding: 1rem;">
                                <span
                                    style="background: ${u.role == 'ADMIN' ? 'rgba(255, 95, 86, 0.2); color: #ff5f56' : (u.role == 'INSTRUCTOR' ? 'rgba(39, 201, 63, 0.2); color: #27c93f' : 'rgba(6, 182, 212, 0.2); color: #06b6d4')}; padding: 0.2rem 0.5rem; border-radius: 4px;">
                                    ${u.role}
                                </span>
                            </td>
                            <td style="padding: 1rem;">${u.points}</td>
                            <td style="padding: 1rem;">
                                <a href="${pageContext.request.contextPath}/admin/delete-user/${u.id}" class="btn-cyber"
                                    style="font-size: 0.8rem; padding: 0.4rem 0.8rem; background: rgba(255, 95, 86, 0.1); border-color: #ff5f56; color: #ff5f56;"
                                    onclick="return confirm('Are you sure you want to delete this user?');">
                                    Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty users}">
                        <tr>
                            <td colspan="4" style="padding: 1rem; text-align: center;">No users found.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        // User Growth Chart
        const ctx1 = document.getElementById('userGrowthChart').getContext('2d');

        // Inject Data from Backend
        const userLabels = [
            <c:forEach items="${chartLabels}" var="label" varStatus="loop">
                '${label}'${!loop.last ? ',' : ''}
            </c:forEach>
        ];
        const userData = [
            <c:forEach items="${chartDataUsers}" var="data" varStatus="loop">
                ${data}${!loop.last ? ',' : ''}
            </c:forEach>
        ];

        new Chart(ctx1, {
            type: 'line',
            data: {
                labels: userLabels,
                datasets: [{
                    label: 'Total Users',
                    data: userData,
                    borderColor: '#06b6d4',
                    backgroundColor: 'rgba(6, 182, 212, 0.1)',
                    tension: 0.4,
                    fill: true
                }]
            },
            options: {
                plugins: { legend: { labels: { color: '#94a3b8' } } },
                scales: {
                    y: { grid: { color: 'rgba(255,255,255,0.1)' }, ticks: { color: '#94a3b8' }, beginAtZero: true },
                    x: { grid: { color: 'rgba(255,255,255,0.1)' }, ticks: { color: '#94a3b8' } }
                }
            }
        });

        // Lab Activity Chart
        const ctx2 = document.getElementById('labActivityChart').getContext('2d');

        // Inject Data from Backend
        const labLabels = [
            <c:forEach items="${labActivityLabels}" var="label" varStatus="loop">
                '${label}'${!loop.last ? ',' : ''}
            </c:forEach>
        ];
        const labData = [
            <c:forEach items="${labActivityData}" var="data" varStatus="loop">
                ${data}${!loop.last ? ',' : ''}
            </c:forEach>
        ];

        new Chart(ctx2, {
            type: 'bar',
            data: {
                labels: labLabels,
                datasets: [{
                    label: 'Completions',
                    data: labData,
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
                    y: { grid: { color: 'rgba(255,255,255,0.1)' }, ticks: { color: '#94a3b8' }, beginAtZero: true },
                    x: { grid: { color: 'rgba(255,255,255,0.1)' }, ticks: { color: '#94a3b8' } }
                }
            }
        });
    </script>
    <%@ include file="../common/footer.jsp" %>