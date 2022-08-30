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
    const productId = document.getElementById("product-id").value;

    const dibBtn = document.getElementById("dib-btn");
    dibBtn.addEventListener("click", () => {
        if (dibBtn.value === "dib") {
            dibDelete(productId)
        } else {
            dibInsert(productId);
        }
        location.href=`/products/${productId}`;
    })
}

function main() {

    addDibEventListener()
}
main();
