<%@ include file="../common/header.jsp" %>
    <div class="container" style="margin-top: 2rem;">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
            <h2 class="glow-text">Manage Courses</h2>
            <a href="/admin/courses/new" class="btn-cyber">Add New Course</a>
        </div>

        <div class="glass-panel">
            <table class="cyber-table">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Description</th>
                        <th style="text-align: center;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${courses}" var="course">
                        <tr>
                            <td>${course.title}</td>
                            <td>${course.description}</td>
                            <td class="action-cell">
                                <c:if test="${not empty course.id}">
                                    <a href="${pageContext.request.contextPath}/admin/courses/edit/${course.id}"
                                        class="btn-cyber btn-sm" style="color: #000;">Edit</a>
                                    <a href="${pageContext.request.contextPath}/admin/courses/delete/${course.id}"
                                        class="btn-cyber btn-sm btn-danger"
                                        onclick="return confirm('Are you sure?')">Delete</a>
                                </c:if>
                                <c:if test="${empty course.id}">
                                    <span style="color: #ff5f56; font-size: 0.8rem;">Error: No ID</span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>