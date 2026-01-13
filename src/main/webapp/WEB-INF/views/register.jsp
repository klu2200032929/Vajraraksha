<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ include file="common/header.jsp" %>

            <div class="container"
                style="display: flex; justify-content: center; align-items: center; min-height: 80vh;">
                <div class="glass-panel" style="width: 100%; max-width: 400px;">
                    <h2 class="glow-text" style="text-align: center; margin-bottom: 2rem;">Join the Force</h2>

                    <form action="/register" method="post">
                        <div style="margin-bottom: 1rem;">
                            <label>Username</label>
                            <input type="text" name="username" required placeholder="Choose a codename" />
                        </div>

                        <div style="margin-bottom: 1rem;">
                            <label>Email</label>
                            <input type="email" name="email" required placeholder="contact@secure.net" />
                        </div>

                        <div style="margin-bottom: 1rem;">
                            <label>Password</label>
                            <input type="password" name="password" required placeholder="********" />
                        </div>

                        <div style="margin-bottom: 1.5rem;">
                            <label>Role</label>
                            <select name="role">
                                <option value="STUDENT">Student</option>
                                <option value="INSTRUCTOR">Instructor</option>
                            </select>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                        <button type="submit" class="btn-cyber" style="width: 100%;">Initialize Account</button>
                    </form>

                    <p style="text-align: center; margin-top: 1rem; color: var(--text-secondary);">
                        Already authorized? <a href="/login">Login here</a>
                    </p>
                </div>
            </div>

            <%@ include file="common/footer.jsp" %>