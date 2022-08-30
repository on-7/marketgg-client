window.addEventListener('DOMContentLoaded', (event) => {
    let thumbnail = true;
    const img = document.querySelectorAll('img').forEach(r => {
        if (thumbnail === false) {
            r.style.width = '600px';
        }
        thumbnail = false;
    })
});