window.addEventListener('DOMContentLoaded', () => {
  const $autoComplete = document.getElementById('autoComplete');
  const $categoryCode = document.getElementById('categoryCode');
  $autoComplete.addEventListener('keydown', (event) => {
    console.log('foo')
    if (event.key === 'Enter') {
      const keyword = event.target.value;
      fetch(`/products/search?categoryId=${$categoryCode}&keyword=${keyword}&page=0`)
    }
  });
});
