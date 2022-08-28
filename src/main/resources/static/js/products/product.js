function addToCart() {

    let cartBtn = document.getElementById("cart-btn");

    cartBtn.addEventListener("click", () => {
        const productId = document.getElementById("product-id").value;
        const amount = document.getElementById("inputQuantity").value;

        let productToCartRequest = {
            id: productId,
            amount: amount
        }

        const myHeaders = getHeaders();

        fetch("/cart", {
            method: "POST",
            headers: myHeaders,
            body: JSON.stringify(productToCartRequest)
        }).then(r => {
            if (r.status === 201) {
                alert("상품이 장바구니에 추가되었습니다.");
            } else {
                alert("문제가 발생하여 상품이 추가되지 않았습니다. 죄송합니다.");
            }
        }).catch(e => console.log("error: " + e));
    });
}

function getHeaders() {
    let csrfHeader = document.getElementById("_csrf_header").content;
    let csrfToken = document.getElementById("_csrf").content;

    const myHeaders = new Headers();

    myHeaders.append("Content-Type", "application/json");
    myHeaders.append(csrfHeader, csrfToken);

    return myHeaders;
}

function main() {
    addToCart();
}


main();
