// SEE: https://tarekraafat.github.io/autoComplete.js
window.addEventListener('DOMContentLoaded', () => {
  let count = 0;
  document.getElementById('autoComplete').addEventListener('keydown', async (event) => {
    count++;
    if(count === 5) {
      const keyword = event.target.value;
      await fetch(`/products/suggest?keyword=${keyword}&page=0`).then(async (response) => await response.json())
        .then(async (data) => {
          autoCompleteJS.data.src = await copy(data);
        })
      count = 0;
    }
  });

  function copy(o) {
    let out, v, key;
    out = Array.isArray(o) ? [] : {};
    for(key in o) {
      v = o[key];
      out[key] = (typeof v === "object" && v !==null) ? copy(v) : v;
    }
    return out;
  }

  const autoCompleteJS = new autoComplete({
    selector: "#autoComplete",
    placeHolder: "Search for Food...",
    data: [],
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
          autoCompleteJS.input.value = event.detail.selection.value;
        }
      }
    }
  });
});
