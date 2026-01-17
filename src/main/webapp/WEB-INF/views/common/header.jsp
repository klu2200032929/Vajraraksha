<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>VajraRaksha | Cyber Defense</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
                <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;800&display=swap"
                    rel="stylesheet">
                <!-- FontAwesome for Icons -->
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
                <script src="${pageContext.request.contextPath}/js/main.js" defer></script>
            </head>

            <body>
                <nav class="navbar glass-panel" style="border-radius: 0; margin: 0;">
                    <div class="logo">
                        <h2 class="text-gradient"><i class="fas fa-shield-alt"></i> VajraRaksha</h2>
                    </div>
                    <div class="nav-links">
                        <a href="${pageContext.request.contextPath}/">Home</a>
                        <sec:authorize access="!isAuthenticated()">
                            <a href="${pageContext.request.contextPath}/login">Login</a>
                            <a href="${pageContext.request.contextPath}/register" class="btn-cyber">Get Started</a>
                        </sec:authorize>
                        <sec:authorize access="isAuthenticated()">
                            <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
                            <a href="${pageContext.request.contextPath}/profile" style="margin-left: 1rem;"
                                title="My Profile">
                                <i class="fas fa-user-circle" style="font-size: 1.2rem;"></i>
                            </a>
                            <form action="${pageContext.request.contextPath}/logout" method="post"
                                style="display:inline;">
                                <!-- CSRF Token Handling -->
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <button type="submit" class="btn-cyber"
                                    style="padding: 0.5rem 1rem; margin-left: 1rem; background: transparent; border: 1px solid var(--accent-color); color: var(--accent-color);">Logout</button>
                            </form>
                        </sec:authorize>
                    </div>
                </nav>
                <div class="main-content">