window.addEventListener('DOMContentLoaded', () => {
    let usePassword = document.getElementById("use-password");
    document.getElementById("password-check-btn").addEventListener('click', () => {
        const password = document.getElementById('password').value;
        const passwordCheck = document.getElementById("password-check").value;

        if (password !== passwordCheck) {
            alert("암호가 일치하지 않습니다.")
        } else {
            usePassword.value = true;
            activeUpdateEvent();
        }
    });
});


function activeUpdateEvent() {
    let enableUpdate = document.getElementById('enable-update');

    enableUpdate.disabled = !(document.getElementById('use-password').value);
}

function formValidation() {
    const passwordCheck = document.getElementById("password-check").value;

    if (document.getElementById('use-password').value === false) {
        alert('비밀번호 확인버튼을 눌러주세용.')
        passwordCheck.focus();
        return false;
    }

    const regName = /^[가-힣a-zA-Z]{2,10}}$/;
    const name = document.getElementById("name").value;

    if (!(name.match(regName))) {
        alert('이름을 확인해 주세요.')
        name.focus();
        return false;
    }

    const regPhoneNumber = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
    const phoneNumber = document.getElementById("phone-number").value;

    if (!(phoneNumber.match(regPhoneNumber))) {
        alert('올바른 휴대폰번호를 입력해주세요.')
        phoneNumber.focus();
        return false;
    }
}
