window.addEventListener('DOMContentLoaded', () => {
  let productId = 19;

  let csrfToken = document.getElementById("_csrf").content;

  function dibView() {
    $.ajax({
      // TODO: 올릴때 주소 바꾸기
      url: 'http://localhost:5050/dibs',
      type: 'POST',
      headers: { "Content-Type": "application/json", "X-CSRF-TOKEN": csrfToken },
      async: true,
      data: {
        'productId': productId
      },
      dataType: "json",
      success: function (data) {
        let htmlBody = '';
        if (data.exist === 1) {
          htmlBody += '<button onclick="dibDelete(productId);"'
            + '><span>찜제거</span></button>'
        } else {
          htmlBody += '<button onclick="dibInsert(productId);"'
            + '><span>찜</span></button>'
        }
        $('#dib').html(htmlBody);
      }
    })
  }

  function dibInsert(productId) {
    $.ajax({
      url: '/dibs/insert/' + productId,
      type: 'get',
      success: function (data) {
        if (data === 1)
          dibView();
      }
    });
  }

  function dibDelete(productId) {
    $.ajax({
      url: '/dibs/delete/' + productId,
      type: 'get',
      success: function (data) {
        if (data === 1)
          dibView();
      }
    });
  }

  dibView();

});
