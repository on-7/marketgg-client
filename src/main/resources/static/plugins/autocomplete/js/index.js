window.addEventListener('DOMContentLoaded', () => {
  let count = 0;
  const regex = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]+$/;
  const alpha = /^[a-z|A-Z|0-9|]+$/;

  const SPACE_BAR = 32;
  const BACKSPACE = 8;

  /**
   *
   * @param o
   * @returns {*[]}
   */
  function copy(o) {
    let out, v, key;

    out = Array.isArray(o) ? [] : {};

    for (key in o) {
      v = o[key];
      out[key] = (typeof v === "object" && v !== null) ? copy(v) : v;
    }

    return out;
  }

  const autoCompleteJS = new autoComplete(
    {
      selector: "#autoComplete",
      placeHolder: "검색어를 입력해주세요",
      data: {
        src: [],
        store: []
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
          keydown: async (event) => {
            console.log(event);
            let keyword = event.target.value;

            if (event.key !== 'BackSpace' && event.key !== 'Space' && event.keyCode !== SPACE_BAR && event.keyCode !== BACKSPACE &&
              (event.key === 'Process' || alpha.test(event.key) || event.keyCode === 229)) {
              count++;
            }

            if (keyword && count >= 3) {
              if (keyword.split(' ').join('').indexOf(']') || keyword.split(' ').join('').indexOf(')')) {
                keyword = keyword.split(']').join('');
                keyword = keyword.split('[').join('');
                keyword = keyword.split(')').join('');
                keyword = keyword.split('(').join('');
              }
              console.log(keyword);
              await fetch(`/products/suggest?keyword=${keyword}&page=0`)
                .then(async (response) => await response.json())
                .then((data) => {
                  autoCompleteJS.data.src = copy(data);
                });

              count = 0;
            }
            const $categoryCode = document.getElementById('categoryCode').value;

            if (keyword.split(' ').join('').indexOf(']')) {
              keyword = keyword.split(']').join('');
              keyword = keyword.split('[').join('');
            }
            if (event.key === 'Enter') {
              console.log(keyword.split(' ').join(''));
              if (regex.test(keyword.split(' ').join(''))) {
                if(keyword.length > 20) {
                  alert('검색어는 20글자를 초과할 수 없습니다.')
                  event.target.value = '';
                  document.getElementById('autoComplete').focus();
                } else {
                  location.href = `/products/search?categoryId=${$categoryCode}&keyword=${keyword}&page=0`;
                }
              }
              else {
                alert(keyword + '는(은) 허용되지 않는 검색어입니다. 한글, 영어, 숫자를 통해서 검색해주세요.')
                event.target.value = '';
                document.getElementById('autoComplete').focus();
              }
            }
          },
          selection: (event) => {
            autoCompleteJS.input.value = event.detail.selection.value;
          }
        }
      }
    }
  );
});
