<%@ include file="common/header.jsp" %>
    <div style="display: flex; justify-content: center; align-items: center; min-height: 80vh;">
        <div class="glass-panel" style="padding: 3rem; width: 100%; max-width: 500px; text-align: center;">
            <h2 class="text-gradient" style="margin-bottom: 2rem;">New User Registration</h2>

            <form action="${pageContext.request.contextPath}/register" method="post" modelAttribute="user">
                <div style="display: flex; flex-direction: column; gap: 1rem; text-align: left;">
                    <label style="color: var(--text-secondary);">Username</label>
                    <input type="text" name="username" required
                        style="background: rgba(0,0,0,0.2); border: 1px solid var(--glass-border); padding: 0.75rem; color: white; border-radius: 6px;">

                    <label style="color: var(--text-secondary);">Email</label>
                    <input type="email" name="email" required
                        style="background: rgba(0,0,0,0.2); border: 1px solid var(--glass-border); padding: 0.75rem; color: white; border-radius: 6px;">

                    <label style="color: var(--text-secondary);">Password (Will be Encrypted)</label>
                    <input type="password" name="password" required
                        style="background: rgba(0,0,0,0.2); border: 1px solid var(--glass-border); padding: 0.75rem; color: white; border-radius: 6px;">

                    <label style="color: var(--text-secondary);">Select Role</label>
                    <select name="role"
                        style="background: rgba(0,0,0,0.2); border: 1px solid var(--glass-border); padding: 0.75rem; color: white; border-radius: 6px;">
                        <option value="STUDENT" style="color: black;">Student / Learner</option>
                        <option value="INSTRUCTOR" style="color: black;">Instructor</option>
                    </select>

                    <button type="submit" class="btn-cyber" style="margin-top: 1rem;">Initialize Profile</button>
                </div>
            </form>
        </div>
    </div>
    <%@ include file="common/footer.jsp" %>