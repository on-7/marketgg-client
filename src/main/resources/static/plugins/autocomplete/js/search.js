window.addEventListener('DOMContentLoaded', () => {
  const $autoComplete = document.getElementById('autoComplete');
  const $categoryCode = document.getElementById('categoryCode').value;
  $autoComplete.addEventListener('keydown', (event) => {
    console.log('foo')
    if (event.key === 'Enter') {
      const keyword = event.target.value;
      location.href=`/products/search?categoryId=${$categoryCode}&keyword=${keyword}&page=0`;
    }
  });
});
