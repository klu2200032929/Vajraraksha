document.addEventListener('DOMContentLoaded', () => {
    // Glass Panel Hover Effect
    const panels = document.querySelectorAll('.glass-panel');

    panels.forEach(panel => {
        panel.addEventListener('mousemove', (e) => {
            const rect = panel.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;

            panel.style.background = `radial-gradient(circle at ${x}px ${y}px, rgba(255,255,255,0.08), rgba(255,255,255,0.05))`;
        });

        panel.addEventListener('mouseleave', () => {
            panel.style.background = 'rgba(255, 255, 255, 0.05)';
        });
    });

    console.log("VajraRaksha System Initialized v2.0");
});
