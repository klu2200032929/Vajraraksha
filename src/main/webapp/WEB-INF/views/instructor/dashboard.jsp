<%@ include file="../common/header.jsp" %>

    <div class="container">
        <h1 class="text-gradient">Instructor Hub</h1>
        <p style="color: var(--text-secondary); margin-bottom: 2rem;">Manage your courses and student progress.</p>

        <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 2rem; margin-bottom: 3rem;">
            <div class="glass-panel">
                <h3 style="color: var(--accent-color);"><i class="fas fa-book"></i> Active Courses</h3>
                <p style="font-size: 2rem; font-weight: bold; margin: 1rem 0;">3</p>
                <a href="#" class="btn-cyber" style="font-size: 0.9rem;">View Courses</a>
            </div>
            <div class="glass-panel">
                <h3 style="color: #00aaff;"><i class="fas fa-flask"></i> Lab Instances</h3>
                <p style="font-size: 2rem; font-weight: bold; margin: 1rem 0;">12</p>
                <a href="#" class="btn-cyber" style="font-size: 0.9rem;">Manage Labs</a>
            </div>
            <div class="glass-panel">
                <h3 style="color: #ffbd2e;"><i class="fas fa-users"></i> Students</h3>
                <p style="font-size: 2rem; font-weight: bold; margin: 1rem 0;">150</p>
                <a href="#" class="btn-cyber" style="font-size: 0.9rem;">View Class</a>
            </div>
        </div>

        <div class="glass-panel">
            <h3>Class Progress</h3>
            <p style="color: var(--text-secondary);">Recent student activity</p>
            <table style="width: 100%; border-collapse: collapse; margin-top: 1rem;">
                <thead>
                    <tr style="border-bottom: 1px solid var(--glass-border); text-align: left;">
                        <th style="padding: 1rem;">Student</th>
                        <th style="padding: 1rem;">Activity</th>
                        <th style="padding: 1rem;">Time</th>
                    </tr>
                </thead>
                <tbody>
                    <tr style="border-bottom: 1px solid var(--glass-border);">
                        <td style="padding: 1rem;">Alice</td>
                        <td style="padding: 1rem;">Completed "SQL Injection" Lab</td>
                        <td style="padding: 1rem;">2 mins ago</td>
                    </tr>
                    <tr style="border-bottom: 1px solid var(--glass-border);">
                        <td style="padding: 1rem;">Bob</td>
                        <td style="padding: 1rem;">Started "Network Recon"</td>
                        <td style="padding: 1rem;">15 mins ago</td>
                    </tr>
                </tbody>
            </table>
        </div>

    </div>

    <%@ include file="../common/footer.jsp" %>