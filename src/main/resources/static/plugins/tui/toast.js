const Toast = toastui.Editor;

const editor = new Toast({
  el: document.querySelector('#editor'),
  height: 'auto',
  initialEditType: 'wysiwyg',
  previewStyle: 'vertical',
  placeholder: '내용을 입력해주세요.'
});

const submitButton = document.getElementById('btn-submit');

submitButton.addEventListener('click', (event) => {
  const description = editor.getHTML();
  console.log(description);
  document.getElementById('toast-content').value = description;
})
