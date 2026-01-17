<%@ include file="../common/header.jsp" %>
    <div class="container" style="margin-top: 2rem;">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
            <h2 class="glow-text">Manage Labs</h2>
            <a href="/admin/labs/new" class="btn-cyber">Add New Lab</a>
        </div>

        <div class="glass-panel">
            <table class="cyber-table">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Difficulty</th>
                        <th>Points</th>
                        <th style="text-align: center;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${labs}" var="lab">
                        <tr>
                            <td>${lab.title}</td>
                            <td>${lab.description}</td>
                            <td>
                                <span
                                    class="badge ${lab.difficulty == 'Advanced' ? 'badge-advanced' : (lab.difficulty == 'Intermediate' ? 'badge-intermediate' : 'badge-beginner')}">
                                    ${lab.difficulty}
                                </span>
                            </td>
                            <td>${lab.points}</td>
                            <td class="action-cell">
                                <a href="/admin/labs/edit/${lab.id}" class="btn-cyber btn-sm"
                                    style="color: #000;">Edit</a>
                                <a href="/admin/labs/delete/${lab.id}" class="btn-cyber btn-sm btn-danger"
                                    onclick="return confirm('Are you sure?')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>