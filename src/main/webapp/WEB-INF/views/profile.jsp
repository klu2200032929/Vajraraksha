<%@ include file="common/header.jsp" %>
    <div class="container" style="max-width: 800px; margin-top: 2rem;">
        <h2 class="glow-text">My Profile</h2>

        <!-- Alert Messaging -->
        <c:if test="${not empty success}">
            <div class="glass-panel"
                style="border-color: var(--accent-color); color: var(--accent-color); margin-bottom: 1rem;">
                <i class="fas fa-check-circle"></i> ${success}
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="glass-panel" style="border-color: #ff5f56; color: #ff5f56; margin-bottom: 1rem;">
                <i class="fas fa-exclamation-circle"></i> ${error}
            </div>
        </c:if>
        <c:if test="${not empty info}">
            <div class="glass-panel" style="border-color: #00aaff; color: #00aaff; margin-bottom: 1rem;">
                <i class="fas fa-info-circle"></i> ${info}
            </div>
        </c:if>

        <div class="glass-panel" style="margin-bottom: 2rem;">
            <h3><i class="fas fa-id-card"></i> Personal Details</h3>
            <form action="/profile/update-info" method="post" style="margin-top: 1rem;">
                <div style="margin-bottom: 1rem;">
                    <label>Email (Verified)</label>
                    <input type="text" value="${user.email}" disabled style="opacity: 0.7; cursor: not-allowed;" />
                </div>
                <div style="margin-bottom: 1rem;">
                    <label>Username</label>
                    <input type="text" name="username" value="${user.username}" required />
                </div>
                <div style="margin-bottom: 1rem;">
                    <label>Role</label>
                    <input type="text" value="${user.role}" disabled style="opacity: 0.7; cursor: not-allowed;" />
                </div>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <button type="submit" class="btn-cyber">Update Details</button>
            </form>
        </div>

        <div class="glass-panel">
            <h3><i class="fas fa-lock"></i> Security</h3>
            <p style="color: var(--text-secondary); margin-bottom: 1.5rem;">To change your password, you must verify
                your identity via OTP sent to your email.</p>

            <c:if test="${empty otpSent}">
                <form action="/profile/send-otp" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <button type="submit" class="btn-cyber"
                        style="background: transparent; border: 1px solid var(--accent-color); color: var(--accent-color);">
                        <i class="fas fa-paper-plane"></i> Send OTP to Change Password
                    </button>
                </form>
            </c:if>

            <c:if test="${not empty otpSent}">
                <form action="/profile/change-password" method="post"
                    style="margin-top: 1rem; border-top: 1px solid var(--glass-border); padding-top: 1rem;">
                    <div style="margin-bottom: 1rem;">
                        <label style="color: var(--accent-color);">Enter OTP Code</label>
                        <input type="text" name="otp" required placeholder="123456"
                            style="letter-spacing: 5px; font-weight: bold; font-size: 1.2rem; width: 150px; text-align: center;" />
                    </div>

                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 1rem;">
                        <div style="margin-bottom: 1rem;">
                            <label>New Password</label>
                            <input type="password" name="newPassword" required />
                        </div>
                        <div style="margin-bottom: 1rem;">
                            <label>Confirm Password</label>
                            <input type="password" name="confirmPassword" required />
                        </div>
                    </div>

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                    <div style="display: flex; gap: 1rem; align-items: center;">
                        <button type="submit" class="btn-cyber">Change Password</button>
                        <a href="/profile" style="color: var(--text-secondary); font-size: 0.9rem;">Cancel</a>
                    </div>
                </form>
            </c:if>
        </div>
    </div>
    <%@ include file="common/footer.jsp" %>