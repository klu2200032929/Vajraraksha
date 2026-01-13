<%@ include file="../common/header.jsp" %>
    <div class="container" style="margin-top: 2rem;">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
            <h2 class="glow-text">Manage Labs</h2>
            <a href="/admin/labs/new" class="btn-cyber">Add New Lab</a>
        </div>

        <div class="glass-panel">
            <table class="table" style="color: #fff;">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Difficulty</th>
                        <th>Points</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${labs}" var="lab">
                        <tr>
                            <td>${lab.title}</td>
                            <td>${lab.description}</td>
                            <td>
                                <span
                                    class="badge ${lab.difficulty == 'Advanced' ? 'bg-danger' : (lab.difficulty == 'Intermediate' ? 'bg-warning text-dark' : 'bg-success')}">
                                    ${lab.difficulty}
                                </span>
                            </td>
                            <td>${lab.points}</td>
                            <td>
                                <a href="/admin/labs/edit/${lab.id}" class="btn-cyber"
                                    style="padding: 0.25rem 0.5rem; font-size: 0.8rem;">Edit</a>
                                <a href="/admin/labs/delete/${lab.id}" class="btn-cyber"
                                    style="padding: 0.25rem 0.5rem; font-size: 0.8rem; background: rgba(255, 95, 86, 0.2); border-color: #ff5f56;"
                                    onclick="return confirm('Are you sure?')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>