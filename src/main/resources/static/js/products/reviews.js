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
                    html += '<p>'+json+'</p>';
                    console.log(json)
                })
            $('#review-container').html(html);
        })
    }
    retrieveReviews();
})