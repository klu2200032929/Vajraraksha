<%@ include file="common/header.jsp" %>
    <div style="display: flex; justify-content: center; align-items: center; min-height: 80vh;">
        <div class="glass-panel" style="padding: 3rem; width: 100%; max-width: 400px; text-align: center;">
            <h2 class="text-gradient" style="margin-bottom: 2rem;">System Access</h2>

            <c:if test="${param.error != null}">
                <div
                    style="color: #ef4444; background: rgba(239, 68, 68, 0.1); padding: 0.5rem; margin-bottom: 1rem; border-radius: 4px;">
                    Invalid Credentials. Access Denied.
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/perform_login" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> <!-- CSRF Handling -->
                <div style="display: flex; flex-direction: column; gap: 1rem; text-align: left;">
                    <label style="color: var(--text-secondary);">Username</label>
                    <input type="text" name="username" required
                        style="background: rgba(0,0,0,0.2); border: 1px solid var(--glass-border); padding: 0.75rem; color: white; border-radius: 6px;">

                    <label style="color: var(--text-secondary);">Password</label>
                    <input type="password" name="password" required
                        style="background: rgba(0,0,0,0.2); border: 1px solid var(--glass-border); padding: 0.75rem; color: white; border-radius: 6px;">

                    <button type="submit" class="btn-cyber" style="margin-top: 1rem;">Authenticate</button>
                </div>
            </form>
            <p style="margin-top: 1rem; color: var(--text-secondary); font-size: 0.9rem;">
                No credentials? <a href="${pageContext.request.contextPath}/register"
                    style="color: var(--accent-cyan);">Register here</a>
            </p>
        </div>
    </div>
    <%@ include file="common/footer.jsp" %>