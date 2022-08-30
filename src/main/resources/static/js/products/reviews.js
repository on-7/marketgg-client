const productId = document.getElementById("id");
const reviewId = document.getElementById("reviewId");

window.addEventListener('DOMContentLoaded', () => {

    // let reviewsRetrieveBtn = document.getElementById("review-retrieve");
    let reviewUpdateBtn = document.getElementById("btn-update");
    const reviewDeleteBtns = document.querySelectorAll('.btn-delete');

    // function retrieveReviews() {
    //     let html = "";
    //     reviewsRetrieveBtn.addEventListener("click", () => {
    //         console.log(productId);
    //         fetch("/products/" + productId + "/reviews?" + new URLSearchParams({
    //             page: 1
    //         })).then(result => result.json())
    //             .then(json => {
    //                 html += '<p>'+json+'</p>';
    //                 console.log(json)
    //             })
    //         $('#review-container').html(html);
    //     })
    // }
    //
    // retrieveReviews();

    function updateReview() {
        let html = "";
        reviewUpdateBtn.addEventListener("click", () => {
            console.log("수정버튼 클릭!");
            html += "<div id=\"editor-update\"></div>\n" + "<input type=\"hidden\" id=\"toast-content\" name=\"content\" value=\"\">"
            $("#review-update-box").append(html);
        })
    }

    updateReview();

    function deleteReview() {
        reviewDeleteBtns.forEach(element => {
            element.addEventListener('click', (e) => {
                const attribute = e.target.getAttribute("btn-delete-id");
                const myHeaders = getHeaders();
                fetch('/products/' + productId + '/reviews/' + reviewId, {
                    method: 'DELETE',
                    // headers: myHeaders
                })
                    .then(() => document.location.reload())
            })
        })
    }

    deleteReview()

})

// function getHeaders() {
//     let csrfHeader = document.getElementById("_csrf_header").content;
//     let csrfToken = document.getElementById("_csrf").content;
//
//     let myHeaders = new Headers();
//
//     myHeaders.append("Content-Type", "application/json");
//     myHeaders.append(csrfHeader, csrfToken);
//
//     return myHeaders;
// }
