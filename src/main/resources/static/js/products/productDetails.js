window.addEventListener('DOMContentLoaded', (event) => {
    let thumbnail = document.querySelector('.thumbnail');
    let ggLogo = document.querySelector('.gg-logo');

    document.querySelectorAll('img').forEach(r => {
        if (r === ggLogo) {
            return;
        }

        if (r === thumbnail) {
            return;
        }

        r.style.width = '600px';
    })
});