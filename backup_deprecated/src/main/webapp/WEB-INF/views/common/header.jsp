<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VajraRaksha | Cyber Defense</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;800&display=swap" rel="stylesheet">
    <!-- FontAwesome for Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        /* Embedded critical CSS just in case */
    </style>
</head>
<body>
    <nav class="navbar glass-panel">
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
                <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" class="btn-cyber" style="padding: 0.5rem 1rem; margin-left: 1rem;">Logout</button>
                </form>
            </sec:authorize>
        </div>
    </nav>
    <div class="main-content">
