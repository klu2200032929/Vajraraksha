<%@ include file="../common/header.jsp" %>
    <div class="container" style="margin-top: 2rem; max-width: 600px;">
        <h2 class="glow-text">${course.id == null ? 'Create New Course' : 'Edit Course'}</h2>

        <div class="glass-panel" style="padding: 2rem;">
            <form action="/admin/courses" method="post" modelAttribute="course">
                <input type="hidden" name="id" value="${course.id}" />

                <div class="form-group" style="margin-bottom: 1.5rem;">
                    <label for="title" style="display: block; margin-bottom: 0.5rem; color: var(--accent-color);">Course
                        Title</label>
                    <input type="text" id="title" name="title" value="${course.title}" required
                        style="width: 100%; padding: 0.8rem; background: rgba(0,0,0,0.5); border: 1px solid var(--glass-border); color: #fff; border-radius: 4px;">
                </div>

                <div class="form-group" style="margin-bottom: 1.5rem;">
                    <label for="description"
                        style="display: block; margin-bottom: 0.5rem; color: var(--accent-color);">Description</label>
                    <textarea id="description" name="description" rows="4" required
                        style="width: 100%; padding: 0.8rem; background: rgba(0,0,0,0.5); border: 1px solid var(--glass-border); color: #fff; border-radius: 4px;">${course.description}</textarea>
                </div>

                <div style="display: flex; gap: 1rem;">
                    <button type="submit" class="btn-cyber" style="width: 100%;">Save Course</button>
                    <button type="submit" formaction="/admin/courses/generate" class="btn-cyber"
                        style="width: 100%; background: linear-gradient(45deg, #a855f7, #ec4899); border: none;">
                        ✨ Auto-Generate Content
                    </button>
                </div>
            </form>
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>