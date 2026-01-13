<%@ include file="../common/header.jsp" %>

    <div class="container">
        <h1 class="text-gradient">Student Dashboard</h1>

        <div style="display: grid; grid-template-columns: 2fr 1fr; gap: 2rem; margin-bottom: 2rem;">
            <!-- Progress Section -->
            <div class="glass-panel">
                <h2><i class="fas fa-chart-line"></i> Your Progress</h2>
                <p>Rank: <span style="color: var(--accent-color);">
                        <c:choose>
                            <c:when test="${user.points < 100}">Cyber Novice</c:when>
                            <c:when test="${user.points < 500}">Script Kiddie</c:when>
                            <c:when test="${user.points < 1000}">Pro Hacker</c:when>
                            <c:otherwise>Elite Hat</c:otherwise>
                        </c:choose>
                    </span> | Points: ${user.points}</p>
            </div>

            <!-- Leaderboard Widget -->
            <div class="glass-panel" style="padding: 1.5rem;">
                <h3 style="font-size: 1.2rem; margin-bottom: 1rem; color: #fbbf24;"><i class="fas fa-trophy"></i> Top
                    Hackers</h3>
                <table style="width: 100%; font-size: 0.9rem;">
                    <c:forEach items="${leaderboard}" var="topUser" varStatus="status">
                        <tr style="border-bottom: 1px solid rgba(255,255,255,0.05);">
                            <td style="padding: 0.5rem; color: var(--text-secondary);">#${status.index + 1}</td>
                            <td style="padding: 0.5rem; font-weight: bold;">${topUser.username}</td>
                            <td style="padding: 0.5rem; text-align: right; color: var(--accent-color);">
                                ${topUser.points} pts</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty leaderboard}">
                        <tr>
                            <td colspan="3" style="text-align: center; color: var(--text-secondary);">No data yet</td>
                        </tr>
                    </c:if>
                </table>
            </div>
        </div>

        <h2 style="margin-bottom: 1rem;">Continue Learning (Courses)</h2>
        <div
            style="display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 1.5rem; margin-bottom: 2rem;">
            <c:forEach items="${courses}" var="course">
                <div class="glass-panel">
                    <h3 style="color: #d8b4fe;">${course.title}</h3>
                    <p style="color: var(--text-secondary); margin-bottom: 1rem;">${course.description}</p>
                    <div style="display: flex; justify-content: space-between; align-items: center;">
                        <span class="badge"
                            style="background: rgba(216, 180, 254, 0.2); color: #d8b4fe; padding: 0.2rem 0.5rem; border-radius: 4px; font-size: 0.8rem;">Course</span>
                        <a href="/courses/${course.id}" class="btn-cyber" style="font-size: 0.9rem;">View Course</a>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty courses}">
                <p>No courses available right now.</p>
            </c:if>
        </div>

        <h2 style="margin-bottom: 1rem;">Available Labs</h2>
        <div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 1.5rem;">
            <c:forEach items="${labs}" var="lab">
                <div class="glass-panel">
                    <h3 style="color: var(--accent-color);">${lab.title}</h3>
                    <p style="color: var(--text-secondary); margin-bottom: 1rem;">${lab.description}</p>
                    <div style="display: flex; justify-content: space-between; align-items: center;">
                        <span class="badge"
                            style="background: rgba(39, 201, 63, 0.2); color: #27c93f; padding: 0.2rem 0.5rem; border-radius: 4px; font-size: 0.8rem;">${lab.difficulty}</span>
                        <a href="/student/lab/${lab.id}" class="btn-cyber" style="font-size: 0.9rem;">Launch Lab</a>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty labs}">
                <p>No labs available right now.</p>
            </c:if>
        </div>
    </div>

    <%@ include file="../common/footer.jsp" %>