window.addEventListener('DOMContentLoaded', () => {
  const $backToTop = document.getElementById('back-to-top');
  window.onscroll = () => {
    let isInViewport = document.body.scrollTop > 20 || document.documentElement.scrollTop > 20;
    $backToTop.style.display = isInViewport ? "block" : "none";
  }

  /**
   * Back To Top 버튼 클릭 시 웹 문서 최상단으로 이동시켜주는 이벤트
   * document.body.scrollTop 은 사파리 브라우저, document.documentElement.scrollTop 은 크롬, 파이어폭스 호환 적용
   */
  const $backToTopButton = document.getElementById('back-to-top-btn');
  $backToTopButton.addEventListener('click', () => {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
  });
});
