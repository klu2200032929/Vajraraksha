<%@ include file="../common/header.jsp" %>
    <div style="display: flex; height: calc(100vh - 80px);">
        <!-- Sidebar -->
        <div class="glass-panel"
            style="width: 300px; padding: 1rem; border-radius: 0; border-left: none; border-bottom: none; overflow-y: auto;">
            <h3 style="margin-bottom: 1.5rem; color: var(--accent-color);">${course.title}</h3>

            <div style="display: flex; flex-direction: column; gap: 0.5rem;">
                <c:forEach items="${course.lessons}" var="lesson" varStatus="loop">
                    <div class="glass-panel"
                        style="padding: 1rem; border-radius: 8px; ${loop.index == currentLessonIndex ? 'border-left: 4px solid var(--accent-color); background: rgba(0, 255, 136, 0.1);' : 'opacity: 0.6;'}">
                        <strong>
                            0${loop.index + 1}. ${lesson.title}
                            <c:if test="${completedLessonsSet.contains(course.id + '_' + loop.index)}">
                                <span style="color: #27c93f; float: right;">✅</span>
                            </c:if>
                        </strong>
                        <div style="font-size: 0.8rem; color: var(--text-secondary); margin-top: 0.25rem;">
                            <a href="?lessonIndex=${loop.index}" style="color: inherit; text-decoration: none;">Watch
                                Now</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <!-- Main Content -->
        <div style="flex-grow: 1; padding: 2rem; overflow-y: auto;">
            <!-- currentLesson is now set by Controller -->

            <h1 style="margin-bottom: 1rem;">${currentLesson.title}</h1>

            <!-- Video Player -->
            <div
                style="width: 100%; aspect-ratio: 16/9; background: #000; border-radius: 12px; margin-bottom: 2rem; display: flex; align-items: center; justify-content: center; position: relative; overflow: hidden;">

                <!-- Hybrid Player Container or Fallback -->
                <c:choose>
                    <c:when test="${not empty currentLesson.videoUrl}">
                        <c:choose>
                            <c:when
                                test="${currentLesson.videoUrl.contains('youtube') || currentLesson.videoUrl.contains('youtu.be')}">
                                <div id="yt-player"></div>
                            </c:when>
                            <c:otherwise>
                                <!-- Native Video Player for AI Generated/MP4 content -->
                                <video id="native-player" width="100%" height="100%" controls controlsList="nodownload"
                                    style="outline: none;">
                                    <source src="${currentLesson.videoUrl}" type="video/mp4">
                                    Your browser does not support the video tag.
                                </video>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <!-- Fallback for Missing Video -->
                        <div class="glass-panel"
                            style="width: 100%; height: 100%; display: flex; flex-direction: column; align-items: center; justify-content: center; text-align: center; color: var(--text-secondary);">
                            <i class="fas fa-video-slash"
                                style="font-size: 3rem; margin-bottom: 1rem; color: #ff5f56;"></i>
                            <h3>Video Recommendation Unavailable</h3>
                            <p style="max-width: 600px;">
                                We could not find a highly relevant video for this specific lesson topic at the moment.
                                Please proceed by reading the provided text material below.
                            </p>
                            <p style="color: #27c93f; font-weight: bold;">
                                <i class="fas fa-unlock"></i> Quiz Unlocked Automatically
                            </p>
                        </div>
                        <script>
                            // Auto-unlock quiz since there is no video to watch
                            document.addEventListener('DOMContentLoaded', function () {
                                console.log("No video content. Auto-unlocking quiz.");
                                unlockQuiz();
                            });
                        </script>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="glass-panel" style="padding: 2rem;">
                <h3>Lesson Content</h3>
                <p style="line-height: 1.6; color: var(--text-secondary);">
                    ${currentLesson.content}
                </p>

                <hr style="margin: 2rem 0; border-color: var(--glass-border);">

                <h3>Knowledge Check</h3>

                <div id="quiz-locked-msg" class="glass-panel"
                    style="padding: 1rem; text-align: center; border: 1px solid #ffbd2e; color: #ffbd2e; display: block;">
                    <div style="margin-top: 1rem;">
                        <button id="manual-check-btn" class="btn-cyber"
                            style="display: none; font-size: 0.8rem; padding: 0.5rem 1rem;" onclick="unlockQuiz()">
                            <i class="fas fa-sync"></i> Verify Completion
                        </button>
                    </div>
                </div>

                <div id="quiz-content" style="display: none;">
                    <div id="quiz-container">
                        <c:forEach items="${quizzes}" var="quiz" varStatus="loop">
                            <div id="quiz-wrapper-${loop.index}" class="quiz-wrapper"
                                style="display: ${loop.first ? 'block' : 'none'}; margin-bottom: 2rem;">
                                <h4>${quiz.title}</h4>
                                <c:forEach items="${quiz.questions}" var="q" varStatus="qStatus">
                                    <div class="question-block" id="q-block-${loop.index}-${qStatus.index}"
                                        style="margin-bottom: 1.5rem; display: ${qStatus.first ? 'block' : 'none'};">
                                        <p style="margin-bottom: 0.5rem;"><strong>Q${qStatus.index + 1}:</strong>
                                            ${q.questionText}</p>
                                        <div style="display: flex; flex-direction: column; gap: 0.5rem;">
                                            <c:forEach items="${q.options}" var="opt" varStatus="optStatus">
                                                <label class="glass-panel"
                                                    style="padding: 1rem; cursor: pointer; border-radius: 8px;">
                                                    <input type="radio" name="q_${loop.index}_${qStatus.index}"
                                                        value="${optStatus.index}"
                                                        data-correct="${q.correctOptionIndex}"> ${opt}
                                                </label>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:forEach>
                                <button id="submit-quiz-btn-${loop.index}" class="btn-cyber"
                                    style="margin-top: 1rem; display: none;"
                                    onclick="checkAllAnswers(${loop.index}, ${quizzes.size()})">Submit Quiz</button>
                                <div id="quiz-result-${loop.index}" style="margin-top: 1rem; font-weight: bold;"></div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <!-- Navigation -->
                <div style="margin-top: 2rem; text-align: right;">
                    <c:if test="${currentLessonIndex + 1 < course.lessons.size()}">
                        <a href="?lessonIndex=${currentLessonIndex + 1}" id="next-lesson-btn" class="btn-cyber"
                            style="display: none;">Next Lesson <i class="fas fa-arrow-right"></i></a>
                    </c:if>
                    <c:if test="${currentLessonIndex + 1 >= course.lessons.size()}">
                        <c:choose>
                            <c:when test="${isCourseFullyCompleted}">
                                <a href="${pageContext.request.contextPath}/certificate/download/${course.id}"
                                    class="btn-cyber" style="background: var(--accent-color); color: #000;">
                                    <i class="fas fa-certificate"></i> Download Certificate
                                </a>
                            </c:when>
                            <c:otherwise>
                                <span id="next-lesson-btn" style="color: #27c93f; display: none;">Course Complete! 🏆
                                    (Refresh to claim certificate)</span>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <script>
        var isLessonCompleted = ${ isLessonCompleted };
        var player;
        var nativePlayer;

        // Load YouTube API
        var tag = document.createElement('script');
        tag.src = "https://www.youtube.com/iframe_api";
        var firstScriptTag = document.getElementsByTagName('script')[0];
        firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

        function onYouTubeIframeAPIReady() {
            var videoUrl = "${currentLesson.videoUrl}";

            // Check if we are using YouTube
            if (document.getElementById('yt-player')) {
                var videoId = "";
                if (videoUrl.includes("embed/")) {
                    videoId = videoUrl.split("embed/")[1].split("?")[0];
                } else if (videoUrl.includes("v=")) {
                    videoId = videoUrl.split("v=")[1].split("&")[0];
                }

                player = new YT.Player('yt-player', {
                    height: '100%',
                    width: '100%',
                    videoId: videoId,
                    events: {
                        'onStateChange': onPlayerStateChange
                    }
                });
            } else {
                // We are using Native Player
                nativePlayer = document.getElementById('native-player');
                if (nativePlayer) {
                    nativePlayer.addEventListener('ended', function () {
                        unlockQuiz();
                    });

                    nativePlayer.addEventListener('timeupdate', function () {
                        var duration = nativePlayer.duration;
                        var current = nativePlayer.currentTime;

                        // Unlock if within last 15s or 90%
                        if ((duration > 0) && ((duration - current) < 15 || (current / duration) > 0.90)) {
                            unlockQuiz();
                        }
                    });
                }
            }

            // Polling for near-end detection (robustness common logic)
            setInterval(function () {
                var duration = 0;
                var current = 0;

                if (player && player.getCurrentTime && player.getDuration) {
                    duration = player.getDuration();
                    current = player.getCurrentTime();
                } else if (nativePlayer) {
                    // Logic already in event listener, but polling failsafe doesn't hurt
                    duration = nativePlayer.duration;
                    current = nativePlayer.currentTime;
                }

                if (duration > 0) {
                    // Relaxed threshold: unlock if within last 15 seconds
                    // Also unlock if video is > 90% done (useful for short videos)
                    var isNearEnd = (duration > 0) && ((duration - current) < 15 || (current / duration) > 0.90);

                    if (isNearEnd) {
                        unlockQuiz();
                    } else {
                        // Failsafe: If stalled near end, show a manual check button
                        var checkBtn = document.getElementById('manual-check-btn');
                        if (checkBtn) {
                            if (duration > 0 && (current / duration) > 0.80) {
                                checkBtn.style.display = 'inline-block';
                            } else {
                                checkBtn.style.display = 'none';
                            }
                        }
                    }
                }
            }, 1000);
        }

        function onPlayerStateChange(event) {
            if (event.data == YT.PlayerState.ENDED) {
                unlockQuiz();
            }
        }

        function unlockQuiz() {
            var lockedMsg = document.getElementById('quiz-locked-msg');
            var quizContent = document.getElementById('quiz-content');
            if (lockedMsg) lockedMsg.style.display = 'none';
            if (quizContent) quizContent.style.display = 'block';

            if (isLessonCompleted) {
                var nextBtn = document.getElementById('next-lesson-btn');
                if (nextBtn) nextBtn.style.display = 'inline-block';
            }
        }

        document.addEventListener('DOMContentLoaded', function () {
            if (isLessonCompleted) {
                unlockQuiz();
            }
            // Scoped Sequential Logic
            // We need to attach listeners to all radios, but check logic relative to their quiz wrapper
            const wrappers = document.querySelectorAll('.quiz-wrapper');
            wrappers.forEach((wrapper, wIndex) => {
                const questions = wrapper.querySelectorAll('.question-block');
                questions.forEach((q, qIndex) => {
                    const radios = q.querySelectorAll('input[type="radio"]');
                    radios.forEach(radio => {
                        radio.addEventListener('change', function () {
                            // If this is not the last question in THIS wrapper
                            if (qIndex + 1 < questions.length) {
                                setTimeout(() => {
                                    // wrapper id is quiz-wrapper-{wIndex}
                                    // question id is q-block-{wIndex}-{qIndex+1}
                                    var nextQ = document.getElementById('q-block-' + wIndex + '-' + (qIndex + 1));
                                    if (nextQ) {
                                        nextQ.style.display = 'block';
                                        nextQ.scrollIntoView({ behavior: 'smooth' });
                                    }
                                }, 300);
                            } else {
                                var btn = document.getElementById('submit-quiz-btn-' + wIndex);
                                if (btn) {
                                    btn.style.display = 'inline-block';
                                    btn.scrollIntoView({ behavior: 'smooth' });
                                }
                            }
                        });
                    });
                });
            });
        });

        function checkAllAnswers(quizIndex, totalQuizzes) {
            // DEBUG: Remove after fixing
            // alert("Debug: Index=" + quizIndex + ", Total=" + totalQuizzes);
            console.log("CheckAnswers: Index=" + quizIndex + " Total=" + totalQuizzes);

            let score = 0;
            let total = 0;

            // Scope to current quiz
            const wrapper = document.getElementById('quiz-wrapper-' + quizIndex);
            if (!wrapper) return;

            const questions = wrapper.querySelectorAll('.question-block');

            questions.forEach((qBlock, index) => {
                total++;
                // name format: q_{quizIndex}_{qIndex}
                const selected = qBlock.querySelector('input[name="q_' + quizIndex + '_' + index + '"]:checked');
                if (selected) {
                    const correctIndex = selected.getAttribute('data-correct');
                    if (selected.value === correctIndex) {
                        score++;
                        qBlock.style.border = "1px solid #27c93f";
                    } else {
                        qBlock.style.border = "1px solid #ff5f56";
                    }
                }
            });

            const result = document.getElementById('quiz-result-' + quizIndex);
            result.innerHTML = "You scored " + score + " / " + total;

            if (score === total) {
                result.innerHTML += '<br><span style="color: #27c93f;">Perfect Score! Processing...</span>';

                fetch('${pageContext.request.contextPath}/courses/${course.id}/quiz/complete', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        score: score,
                        lessonIndex: ${ currentLessonIndex }
                    })
            })
                .then(response => {
                if (!response.ok) throw new Error("HTTP " + response.status);
                return response.text();
            })
                .then(data => {
                    // Minimize and Show Summary with styling
                    const now = new Date();
                    const dateStr = now.toLocaleDateString();

                    wrapper.innerHTML = `
                        <div class="glass-panel" style="padding: 1.5rem; text-align: left; display: flex; align-items: center; justify-content: space-between; border-left: 5px solid #27c93f; background: rgba(39, 201, 63, 0.05);">
                            <div>
                                <h4 style="margin: 0; color: #fff; font-size: 1.1rem; margin-bottom: 0.5rem;">
                                    <i class="fas fa-check-circle" style="color: #27c93f; margin-right: 0.5rem;"></i> Quiz ` + (quizIndex + 1) + ` Completed
                                </h4>
                                <div style="color: var(--text-secondary); font-size: 0.9rem;">
                                    Questions: <strong>` + total + `</strong> &bull; Score: <strong>` + score + `</strong> &bull; Completed: ` + dateStr + `
                                </div>
                            </div>
                            <div style="text-align: right;">
                                <div style="font-size: 1.8rem; font-weight: bold; color: #27c93f;">100%</div>
                                <div style="font-size: 0.8rem; color: #27c93f;">PASSED</div>
                            </div>
                        </div>
                    `;

                    if (quizIndex + 1 < totalQuizzes) {
                        // Reveal next quiz
                        var nextWrapper = document.getElementById('quiz-wrapper-' + (quizIndex + 1));
                        if (nextWrapper) {
                            nextWrapper.style.display = 'block';
                            setTimeout(() => {
                                nextWrapper.scrollIntoView({ behavior: 'smooth' });
                            }, 100);
                        }
                    } else {
                        // Last quiz done
                        var nextBtn = document.getElementById('next-lesson-btn');
                        if (nextBtn) nextBtn.style.display = 'inline-block';

                        if (data.includes("Success")) {
                            alert("Congratulations! Lesson Complete. Points awarded. If this was your last lesson, refresh to download your certificate.");
                        }
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    result.innerHTML += '<br><span style="color: #ff5f56;">Connection Error.</span>';
                });
        } else {
            result.innerHTML += '<br><span style="color: #ffbd2e;">Try again for a perfect score!</span>';
        }
        }
    </script>
    <%@ include file="../common/footer.jsp" %>