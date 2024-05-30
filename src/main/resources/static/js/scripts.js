document.addEventListener("DOMContentLoaded", function() {
    const scrollButton = document.getElementById('scroll-btn');
    const sections = document.querySelectorAll('main > section');
    let currentSectionIndex = 0;

    scrollButton.addEventListener('click', function() {
        currentSectionIndex = (currentSectionIndex + 1) % sections.length;
        sections[currentSectionIndex].scrollIntoView({ behavior: 'smooth' });
    });
});
