/** 공통 이벤트 처리 */
window.addEventListener("DOMContentLoaded", function () {

    /** 전체 선택 토글 처리 */
    const chkAlls = document.getElementsByClassName("checkall");
    for(const el of chkAlls) {
        el.addEventListener("click", function() {
            const target = this.dataset.targetName;
            console.log(this.dataset)
            if(!target) return;

            const targets = document.getElementsByName(target);
            for (const ta of targets) {
                ta.checked = this.checked;
            }
        });
   }

   /* 파일 추가 모달 */
    let closebtn = document.getElementById("close");
    let openbtn = document.getElementById("fileUploadImg");
    let confirmbtn = document.getElementById("confirm");
    let modal = document.getElementById("modal");
    let back = document.getElementById("back");
    let imageInput = document.getElementById("bookImage");
    let thumbnail = document.getElementById("thumbnail");
    let imageName = new Array();

    const body = document.querySelector('body');
    // 파일추가버튼
    openbtn.addEventListener("click", function(e) {
        e.preventDefault();
        modal.classList.remove("dn");
        back.classList.remove("dn");
        body.style.overflow = 'hidden'
    })

    // 모달창 닫기버튼
    closebtn.addEventListener("click", function() {
        modal.classList.add("dn");
        back.classList.add("dn");
        body.style.overflow = 'auto'

        imageInput.value = '';
        thumbnail.replaceChildren();
    })

