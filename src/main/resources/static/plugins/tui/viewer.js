const Toast = toastui.Editor;

const viewer = new Toast.factory({
    el: document.querySelector('#viewer'),
    viewer: true,
    initialValue: ""
});

let elem = document.getElementById('editor-viewer');
viewer.setMarkdown(elem.value);


