function dibInsert(productId) {
    $.ajax({
        url: '/dibs/insert/' + productId,
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
        url: '/dibs/delete/' + productId,
        type: 'get',
        success: function (data) {
            if (data === 1)
                dibView();
        }
    });
    console.log("success");
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
        location.reload();
    })
}

function main() {

    addDibEventListener()
}
main();
