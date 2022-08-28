// $(document).ready(function () {
//
//     const productId = document.getElementById("product-id").value;
//
//     $(".reviewContainer").click(function () {
//         $.getJSON('/products/' + productId + "/reviews", function (arr) {
//             console.log(arr);
//         })
//
//     })
//
// })

window.addEventListener('DOMContentLoaded', () => {

    let productId = document.getElementById("product-id").value;
    let reviewsRetrieveBtn = document.getElementById("review-retrieve");

    function retrieveReviews() {

        let html = "";
        reviewsRetrieveBtn.addEventListener("click", () => {
            console.log(productId);
            fetch("/products/" + productId + "/reviews?" + new URLSearchParams({
                page: 1
            })).then(result => result.json())
                .then(json => {

                })
        })

    }

    retrieveReviews()
})