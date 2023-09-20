let interval = setInterval(navigate, 7000);

const banners = document.getElementsByClassName("bannerImg");
let prevIdx = banners.length - 1;
let bannerIdx = 0;
let nextIdx = 1;


function navigate() {
    if(bannerIdx >= banners.length - 1) bannerIdx = -1;
    if(nextIdx >= banners.length -1) nextIdx = -1;

    prevIdx++;
    bannerIdx++;
    nextIdx++;

    for(let i = 0; i < banners.length; i++) {
        if(i == bannerIdx) banners[i].classList.remove("dn");
        else {
            banners[i].classList.remove("active");
            banners[i].classList.add("dn");
        }
    }
    banners[nextIdx].classList.remove("dn");
    banners[bannerIdx].classList.add("active");

    prevIdx = bannerIdx;
}

// 마우스 over 시 배너교체 정지
window.onload = function() {
    const bannerBox = document.getElementById("bannerBox");
    bannerBox.onmouseover = function(e) {
            clearInterval(interval);
    }
    bannerBox.onmouseout = function(e) {
            interval = setInterval(navigate, 5000);
    }
}

/**
// 클릭으로 배너변경
function navigateBanner(value) {
    if(bannerIdx >= banners.length - 1) bannerIdx = -1;
    else if(bannerIdx <= 0) bannerIdx = banners.length;

    if(nextIdx >= banners.length -1) nextIdx = -1;
    else if(nextIdx <= 0) nextIdx = banners.length;

    if(prevIdx >= banners.length -1) prevIdx = -1;
    else if(prevIdx <= 0) prevIdx = banners.length;

    if(value == 1) {
        prevIdx++;
        bannerIdx++;
        nextIdx++;

        for(let i = 0; i < banners.length; i++) {
            if(i == bannerIdx) banners[i].classList.remove("dn");
            else {
                banners[i].classList.remove("active");
                banners[i].classList.add("dn");
            }
        }

        banners[nextIdx].classList.remove("dn");
        banners[bannerIdx].classList.add("active")
    }
    else if (value == 0) {
        prevIdx--;
        bannerIdx--;
        nextIdx--;

        for(let i = 0; i < banners.length; i++) {
            if(i == bannerIdx) banners[i].classList.remove("dn");
            else {
                banners[i].classList.remove("active");
                banners[i].classList.add("dn");
            }
        }
        banners[prevIdx].classList.remove("dn");
        banners[bannerIdx].classList.add("active")

    }
}
*/

// 최신도서 화살표로 스크롤 넘기기

