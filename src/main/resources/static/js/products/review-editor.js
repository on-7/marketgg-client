const Toast = toastui.Editor;
let csrfToken = document.getElementById("_csrf").content;
const productId = document.getElementById("product-id").value;

const editor = new Toast({
    el: document.querySelector('#editor'),
    height: 'auto',
    initialEditType: 'wysiwyg',
    previewStyle: 'vertical',
    placeholder: '내용을 입력해주세요.',
    hooks: {
        addImageBlobHook: (blob, callback) => {
            console.log(blob);

            const formData = new FormData();
            formData.append('image', blob);

            let url = '';
            $.ajax({
                type: 'POST',
                enctype: 'multipart/form-data',
                headers: {"Content-Type": "application/json", "X-CSRF-TOKEN": csrfToken},
                url: '/editor',
                data: formData,
                dataType: 'text',
                processData: false,
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    console.log("ajax 성공");
                    url += data;
                    callback(url);
                    console.log(url);
                },
                error: function (e) {
                    console.log("ajax 업로드 실패!")
                    console.log(e.abort([statusText]));
                    callback('image_load_fail', '실패용');
                    console.log(url);
                    console.log(e)
                }
            });
        }
    }
});

const submitButton = document.getElementById('btn-submit');

submitButton.addEventListener('click', (event) => {
    const description = editor.getHTML();
    console.log(description);
    document.getElementById('toast-content').value = description;
})
