window.addEventListener('DOMContentLoaded', () => {
    const productId = document.getElementById("product-id").value;
    let csrfToken = document.getElementById("_csrf").content;

    function dibView() {

        $.ajax({
            url: 'http://localhost:5050/dibs',
            type: 'POST',
            headers: {"Content-Type": "application/json", "X-CSRF-TOKEN": csrfToken},
            async: true,
            data: {
                'productId': productId
            },
            dataType: "json",
            success: function (data) {
                let htmlBody = '';
                if (data.exist === 1) {
                    htmlBody += '<button class="btn btn-success" onclick="dibDelete(productId);"'
                        + '><span>찜제거</span></button>'
                } else {
                    htmlBody += '<button class="btn btn-success" onclick="dibInsert(productId);"'
                        + '><span>찜</span></button>'
                }
                $('#dib').html(htmlBody);
                console.log(productId);
            }
        })
    }

    dibView();

});

function dibInsert(productId) {
    $.ajax({
        url: 'http://localhost:5050/dibs/insert/' + productId,
        type: 'get',
        success: function (data) {
            if (data === 1)
                dibView();
        }
    });
    console.log("success");
}

function dibDelete(productId) {
    $.ajax({
        url: 'http://localhost:5050/dibs/delete/' + productId,
        type: 'get',
        success: function (data) {
            if (data === 1)
                dibView();
        }
    });
    console.log("success");
}