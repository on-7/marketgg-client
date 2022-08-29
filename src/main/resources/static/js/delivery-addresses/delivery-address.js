window.onload = function () {
    const myHeaders = getHeaders();
    document.querySelectorAll(".deleteButton")
        .forEach(element => {
            element.addEventListener('click', (event) => {
                const deliveryAddressId = event.target.getAttribute("delivery-address-id");
                fetch(`/delivery-address/${deliveryAddressId}`, {
                    method: 'DELETE',
                    headers: myHeaders
                })
                    .then(() => document.location.reload());
            });
        })
}

function getHeaders() {
    let csrfHeader = document.getElementById("_csrf_header").content;
    let csrfToken = document.getElementById("_csrf").content;

    let myHeaders = new Headers();

    myHeaders.append("Content-Type", "application/json");
    myHeaders.append(csrfHeader, csrfToken);

    return myHeaders;
}

