let usePassword = document.getElementById("use-password");
document.getElementById("password-check-btn").addEventListener('click', () => {
    const password = document.getElementById('password').value;
    const passwordCheck = document.getElementById("password-check").value;

    if (password !== passwordCheck) {
        alert("암호가 일치하지 않습니다.")
    } else {
        alert("신중한 탈퇴 부탁드립니다...")
        usePassword.value = true;
        activeWithDrawEvent();
    }
});


function activeWithDrawEvent() {
    let enableUpdate = document.getElementById('withdraw-btn');

    enableUpdate.disabled = !(document.getElementById('use-password').value);
}

