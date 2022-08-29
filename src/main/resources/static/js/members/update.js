window.onload =  function () {
    let usePassword = document.getElementById("use-password");
    document.getElementById("password-check-btn").addEventListener('click', () => {
        const password = document.getElementById('password').value;
        const passwordCheck = document.getElementById("password-check").value;

        const num = password.search(/[0-9]/g);
        const eng = password.search(/[a-z]/ig);
        const spe = password.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

        if (password !== passwordCheck) {
            alert("암호가 일치하지 않습니다.")
        } else if (password.length < 10 || password.length > 20) {
            alert("10자리 ~ 20자리 이내로 입력 부탁드려요 ^_^77")
        } else if (password.search(/\s/) !== -1) {
            alert("비밀번호는 공백 없이 입력 부탁드려요 ^_^77")
        } else if ((num < 0 && eng < 0) || (eng < 0 && spe < 0) || (spe < 0 && num < 0)) {
            alert("영문,숫자,특수문자 중 2가지 이상을 혼합하여 입력 부탁드려요 ^_^77")
        } else {
            alert("사용 가능한 암호입니다.")
            usePassword.value = true;
            activeUpdateEvent();
        }
    });
};

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

    if(!(name.match(regName))) {
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