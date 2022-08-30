window.addEventListener('DOMContentLoaded', () => {
    function dibView() {
        const productId = document.getElementById('product-id');
        
        $.ajax({
            url: '/dibs',
            type: 'GET',
            async: true,
            data: {
                'productId': productId
            },
            dataType: "json",
            success: function (data) {
                if (data.exist === 1) {
                    document.getElementById('o').style.display = 'none';
                    document.getElementById('x').style.display = '';
                } else {
                    document.getElementById('x').style.display = 'none';
                    document.getElementById('o').style.display = '';
                }
                location.reload();
            }
        })
    }
    dibView();
});

function dibInsert(productId) {
    $.ajax({
        url: '/dibs/insert/' + productId,
        // headers: getHeaders(),
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
