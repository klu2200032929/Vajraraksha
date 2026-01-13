<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ include file="common/header.jsp" %>

            <div class="container"
                style="display: flex; justify-content: center; align-items: center; min-height: 80vh;">
                <div class="glass-panel" style="width: 100%; max-width: 400px;">
                    <h2 style="text-align: center; margin-bottom: 2rem;">Login</h2>

                    <c:if test="${not empty error}">
                        <div
                            style="background: rgba(255, 95, 86, 0.2); color: #ff5f56; padding: 1rem; border-radius: 8px; margin-bottom: 1rem; border: 1px solid #ff5f56;">
                            ${error}
                        </div>
                    </c:if>

                    <form action="/perform_login" method="post">
                        <label>Username</label>
                        <input type="text" name="username" required placeholder="Enter username" />

                        <label>Password</label>
                        <input type="password" name="password" required placeholder="Enter password" />

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                        <button type="submit" class="btn-cyber" style="width: 100%;">Access System</button>
                    </form>

                    <p style="text-align: center; margin-top: 1rem; color: var(--text-secondary);">
                        New recruit? <a href="/register">Register here</a>
                    </p>
                </div>
            </div>

            <%@ include file="common/footer.jsp" %>