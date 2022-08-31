window.addEventListener('DOMContentLoaded', () => {
  const setViewportWidth = () => {
    let vw = document.documentElement.clientWidth / 100;
    document.documentElement.style.setProperty('--vw', `${vw}px`);
  };

  setViewportWidth();
  window.addEventListener('resize', setViewportWidth);
});
