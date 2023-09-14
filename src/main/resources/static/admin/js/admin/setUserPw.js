/** 비밀번호 초기화 */
window.addEventListener("DOMContentLoaded", function () {
    let resetBtn = document.querySelector("#resetBtn");
    const inputPw = document.getElementById("inputPw");
    const p = document.getElementById("pw");

    console.log(resetBtn);
    console.log(inputPw.value);

    resetBtn.addEventListener("click", () => {
        inputPw.value = "11111111";
        console.log(inputPw);
        p.innerText = "비밀번호가 '11111111'로 변경되었습니다.";
    })
})