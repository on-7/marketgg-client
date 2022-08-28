$(document).ready(function () {

    const productId = document.getElementById("product-id").value;

    $(".reviewContainer").click(function () {
        $.getJSON('/products/' + productId + "/reviews", function (arr) {
            console.log(arr);
        })

    })

})
