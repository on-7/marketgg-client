const url = "/cart";

function calcTotalPrice() {
    let itemPrice = document.getElementsByClassName("item-price");
    let sum = 0;
    for (let i = 0; i < itemPrice.length; i++) {
        let price = itemPrice[i].innerText;
        sum += parseInt(price);
        itemPrice[i].innerText = "₩ " + formatting(price);
    }
    sum = formatting(sum)
    let totalPrice = document.getElementById("total-price");
    totalPrice.innerHTML = "₩ " + sum;
}

function addUpdateListener() {
    let updateBtns = document.getElementsByClassName("update-btn");
    if (updateBtns === null) {
        return;
    }
    for (let i = 0; i < updateBtns.length; i++) {
        updateBtns[i].addEventListener("click", (e) => {
            let productId = e.target.parentNode.id.split("-")[1];
            console.log(productId);
            let amount = document.getElementById("amount-" + productId).value;

            let productToCartRequest = {
                id: parseInt(productId),
                amount: parseInt(amount)
            };

            const myHeaders = getHeaders();

            fetch(url, {
                method: "PATCH",
                headers: myHeaders,
                body: JSON.stringify(productToCartRequest)
            }).then(r => {
                if (r.status === 200) {
                    document.getElementById("amount-" + productId).value = amount;
                    alert("수량이 변경되었습니다.");
                }
            }).catch(e => console.log("error: " + e))
                .finally(() => location.reload());
        });
    }
}

function addDeleteListener() {
    let deleteBtns = document.getElementsByClassName("delete-btn");
    if (deleteBtns === null) {
        return;
    }
    for (let i = 0; i < deleteBtns.length; i++) {
        deleteBtns[i].addEventListener("click", (e) => {
            console.log(e.target);
            let productId = e.path[0].id.split("-")[1];
            let deleteRequest = [productId];

            const myHeaders = getHeaders();

            fetch(url, {
                method: "DELETE",
                headers: myHeaders,
                body: JSON.stringify(deleteRequest)
            }).then(r => {
                if (r.status === 200) {
                    document.getElementById("cart-" + productId).remove();
                    alert("삭제되었습니다.");
                }
            }).catch(e => console.log("error: " + e))
                .finally(() => location.reload());
        })
    }
}

function addDeleteAll() {
    let deleteAllBtn = document.getElementById("delete-all-btn");
    if (deleteAllBtn === null) {
        return;
    }
    let deleteItem = [];

    deleteAllBtn.addEventListener("click", (e) => {
        let carts = document.getElementsByClassName("cart-item");
        for (let i = 0; i < carts.length; i++) {
            let productId = carts[i].id.split("-")[1];
            deleteItem.push(productId);
        }

        const myHeaders = getHeaders();

        fetch(url, {
            method: "DELETE",
            headers: myHeaders,
            body: JSON.stringify(deleteItem)
        }).then(r => {
            if (r.status === 200) {
                for (let i = 0; i < carts.length; i++) {
                    carts[i].remove();
                }
                alert("삭제되었습니다.");
            }
        }).catch(e => console.log("error: " + e))
            .finally(() => location.reload());
    });
}

function addDeleteSelection() {
    let deleteSelectionBtn = document.getElementById("delete-selection-btn");
    if (deleteSelectionBtn === null) {
        return;
    }
    let deleteProductId = [];

    deleteSelectionBtn.addEventListener("click", (e) => {
        let carts = document.getElementsByClassName("cart-item");
        let deletedProducts = [];
        for (let i = 0; i < carts.length; i++) {
            let productId = carts[i].id.split("-")[1];

            let checked = document.getElementById("check-" + productId).checked;
            if (!checked) {
                continue;
            }
            deleteProductId.push(productId);
            deletedProducts.push(carts[i]);
        }

        const myHeaders = getHeaders();

        fetch(url, {
            method: "DELETE",
            headers: myHeaders,
            body: JSON.stringify(deleteProductId)
        }).then(r => {
            if (r.status === 200) {
                for (let i = 0; i < deletedProducts.length; i++) {
                    deletedProducts[i].remove();
                    alert("삭제되었습니다.");
                }
            }
        }).catch(e => console.log("error: " + e))
            .finally(() => location.reload());
    });
}

function formatting(s) {
    return s.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
}

function getHeaders() {
    let csrfHeader = document.getElementById("_csrf_header").content;
    let csrfToken = document.getElementById("_csrf").content;

    const myHeaders = new Headers();

    myHeaders.append("Content-Type", "application/json");
    myHeaders.append(csrfHeader, csrfToken);

    return myHeaders;
}

function blockButton() {
    let items = document.getElementsByClassName("item-name");
    if (items.length > 0) {
        return;
    }

    let orderBtn = document.getElementById("order-btn");
    orderBtn.disabled = true;

}

function main() {
    calcTotalPrice();
    addUpdateListener();
    addDeleteListener();
    addDeleteAll();
    addDeleteSelection();
    blockButton();
}

main();
