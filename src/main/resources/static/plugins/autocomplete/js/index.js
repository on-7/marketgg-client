// SEE: https://tarekraafat.github.io/autoComplete.js
const $autoComplete = document.getElementById('autoComplete');
$autoComplete.addEventListener('keydown', (event) => {
  const keyword = event.target.value;
  alert(keyword)
  fetch(`/products/search?categoryId=001&keyword=${keyword}&page=0`).then((response) => console.log("response: ", response));
});
window.addEventListener('DOMContentLoaded', () => {
  const autoCompleteJS = new autoComplete({
    selector: "#autoComplete",
    placeHolder: "검색어를 입력해주세요",
    data: {
      // FIXME: 이곳에 상품 검색 결과를 문자열 배열로 넣는다.
      src: ["Sauce - Thousand Island", "Wild Boar - Tenderloin", "Goat - Whole Cut"],
      cache: true,
    },
    resultsList: {
      element: (list, data) => {
        if (!data.results.length) {
          // Create "No Results" message element
          const message = document.createElement("div");
          // Add class to the created element
          message.setAttribute("class", "no_result");
          // Add message text content
          message.innerHTML = `<span>Found No Results for "${data.query}"</span>`;
          // Append message element to the results list
          list.prepend(message);
        }
      },
      noResults: true,
    },
    resultItem: {
      highlight: true
    },
    events: {
      input: {
        selection: (event) => {
          const selection = event.detail.selection.value;
          autoCompleteJS.input.value = selection;
        }
      }
    }
  });
});
