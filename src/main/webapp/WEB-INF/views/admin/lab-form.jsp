<%@ include file="../common/header.jsp" %>
    <div class="container" style="margin-top: 2rem; max-width: 600px;">
        <h2 class="glow-text">${lab.id == null ? 'Create New Lab' : 'Edit Lab'}</h2>

        <div class="glass-panel" style="padding: 2rem;">
            <form action="/admin/labs" method="post" modelAttribute="lab">
                <input type="hidden" name="id" value="${lab.id}" />

                <div class="form-group" style="margin-bottom: 1.5rem;">
                    <label for="title" style="display: block; margin-bottom: 0.5rem; color: var(--accent-color);">Lab
                        Title</label>
                    <input type="text" id="title" name="title" value="${lab.title}" required
                        style="width: 100%; padding: 0.8rem; background: rgba(0,0,0,0.5); border: 1px solid var(--glass-border); color: #fff; border-radius: 4px;">
                </div>

                <div class="form-group" style="margin-bottom: 1.5rem;">
                    <label for="description"
                        style="display: block; margin-bottom: 0.5rem; color: var(--accent-color);">Description</label>
                    <textarea id="description" name="description" rows="4" required
                        style="width: 100%; padding: 0.8rem; background: rgba(0,0,0,0.5); border: 1px solid var(--glass-border); color: #fff; border-radius: 4px;">${lab.description}</textarea>
                </div>

                <div class="form-group" style="margin-bottom: 1.5rem;">
                    <label for="difficulty"
                        style="display: block; margin-bottom: 0.5rem; color: var(--accent-color);">Difficulty</label>
                    <select id="difficulty" name="difficulty"
                        style="width: 100%; padding: 0.8rem; background: rgba(0,0,0,0.5); border: 1px solid var(--glass-border); color: #fff; border-radius: 4px;">
                        <option value="Beginner" ${lab.difficulty=='Beginner' ? 'selected' : '' }>Beginner</option>
                        <option value="Intermediate" ${lab.difficulty=='Intermediate' ? 'selected' : '' }>Intermediate
                        </option>
                        <option value="Advanced" ${lab.difficulty=='Advanced' ? 'selected' : '' }>Advanced</option>
                    </select>
                </div>

                <div class="form-group" style="margin-bottom: 1.5rem;">
                    <label for="points"
                        style="display: block; margin-bottom: 0.5rem; color: var(--accent-color);">Points
                        Awarded</label>
                    <input type="number" id="points" name="points" value="${lab.points}" required
                        style="width: 100%; padding: 0.8rem; background: rgba(0,0,0,0.5); border: 1px solid var(--glass-border); color: #fff; border-radius: 4px;">
                </div>

                <div style="display: flex; gap: 1rem;">
                    <button type="submit" class="btn-cyber" style="width: 100%;">Save Lab</button>
                    <button type="submit" formaction="/admin/labs/generate" class="btn-cyber"
                        style="width: 100%; background: linear-gradient(45deg, #a855f7, #ec4899); border: none;">
                        ✨ Auto-Generate Content
                    </button>
                </div>
            </form>
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>