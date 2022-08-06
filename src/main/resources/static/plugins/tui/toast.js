const Toast = toastui.Editor;

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
                url: '/editor',
                data: formData,
                dataType: 'json',
                processData: false,
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    console.log("ajax 성공");
                    url += data.filename;
                    callback(url, '사진 대체 텍스트 입력');
                    console.log(url);
                },
                error: function (e) {
                    callback('image_load_fail', '사진 대체 텍스트 입력');
                    console.log(url);
                }
            });
        }
    }
});


// document.querySelector('#contents').insertAdjacentHTML('afterbegin' ,editor.getHTML());
// console.log(editor.getHTML());

const submitButton = document.getElementById('btn-submit');

submitButton.addEventListener('click', (event) => {
    const description = editor.getHTML();
    console.log(description);
    document.getElementById('toast-content').value = description;
})

// const imageButton = document.getElementById('btn-button');
//
// imageButton.addEventListener('click', (event) => {
//     const description = editor.getHTML();
//     console.log(description);
//     document.getElementById('toast-content').value = description;
// })
