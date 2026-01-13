<%@ include file="../common/header.jsp" %>
    <div class="container" style="margin-top: 2rem;">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
            <h2 class="glow-text">Manage Courses</h2>
            <a href="/admin/courses/new" class="btn-cyber">Add New Course</a>
        </div>

        <div class="glass-panel">
            <table class="table" style="color: #fff;">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${courses}" var="course">
                        <tr>
                            <td>${course.title}</td>
                            <td>${course.description}</td>
                            <td>
                                <c:if test="${not empty course.id}">
                                    <a href="${pageContext.request.contextPath}/admin/courses/edit/${course.id}"
                                        class="btn-cyber" style="padding: 0.25rem 0.5rem; font-size: 0.8rem;">Edit</a>
                                    <a href="${pageContext.request.contextPath}/admin/courses/delete/${course.id}"
                                        class="btn-cyber"
                                        style="padding: 0.25rem 0.5rem; font-size: 0.8rem; background: rgba(255, 95, 86, 0.2); border-color: #ff5f56;"
                                        onclick="return confirm('Are you sure?')">Delete</a>
                                </c:if>
                                <c:if test="${empty course.id}">
                                    <span style="color: red; font-size: 0.8rem;">Error: No ID</span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>