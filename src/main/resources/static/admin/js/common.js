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


});