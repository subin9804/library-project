

window.addEventListener("DOMContentLoaded", function() {
    /* 도서이미지 캐러셀 */
    const right = document.getElementById("right");
    const left = document.getElementById ("left");
    const imgUl = document.getElementById('imgUl')
    const bookImg = document.getElementsByClassName("bookImg");

    let width = 0;
    let height = 0;
    if(window.innerWidth <= 900) {
        width = 250;
        height = 300;
    } else {
        width = 300;
        height = 400
    }
    let prevIndex = 0;
    let thisIndex = 0;

    if (bookImg.length === 1) {
        right.classList.add("dn");
        left.classList.add("dn");
    }

    if(right) {
        right.addEventListener("click", function() {
            thisIndex += 1;
            imgUl.style.transform = `translateX(-${thisIndex * width}px)`;

            if(thisIndex === bookImg.length - 1) {
                right.classList.add("dn");
                left.classList.remove("dn");
            } else {
                right.classList.remove("dn");
                left.classList.remove("dn");
            }
        })
    }
    if(left) {
        left.addEventListener("click", function() {
            thisIndex -= 1;
            imgUl.style.transform = `translateX(-${thisIndex * width}px)`;

            if(thisIndex === 0) {
                left.classList.add("dn");
                right.classList.remove("dn");
            } else {
                left.classList.remove("dn");
                right.classList.remove("dn");
            }

        })
    }


    /** 프로필 수정 시 기본이미지로 교체 */
    const defaultBtn = document.getElementById("defaultImg");
    const photo = document.getElementById("photo");

    if(defaultBtn){
        defaultBtn.addEventListener("click", function() {
            console.log(defaultBtn)
            console.log(photo)


            if(defaultBtn.checked) {
                photo.setAttribute("disabled", true);
            }
            else if(!defaultBtn.checked) {
                photo.removeAttribute("disabled");
                defaultBtn.value = "false";
            }
        })
    }
});
