let dibBtn;
let productId;

window.addEventListener('DOMContentLoaded', () => {
    productId = document.getElementById("product-id").value;
    dibBtn = document.getElementById("dib-btn");
    addDibEventListener();
})

function dibInsert(productId) {
    $.ajax({
        url: '/dibs/insert/' + productId,
        type: 'get',
        success: function (data) {
        }
    });
    alert('해당 상품이 찜목록에 추가되었습니다.')
}

function dibDelete(productId) {
    $.ajax({
        url: '/dibs/delete/' + productId,
        type: 'get',
        success: function (data) {
        }
    });
    alert('해당 상품이 찜목록에서 제거되었습니다.')
}

/* =================== */

function addDibEventListener() {

    dibBtn.addEventListener("click", () => {
        if (dibBtn.value === "dib") {
            dibDelete(productId)
        } else {
            dibInsert(productId);
        }
        location.href=`/products/${productId}`;
    })
}
