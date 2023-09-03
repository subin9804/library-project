window.addEventListener("DOMContentLoaded", function() {
    try {
        CKEDITOR.replace("description", {
            height: 350
        })
    } catch (e) {}
});

/**
* 파일 업로드 콜백 처리
* @param files : 업로드 완료된 파일 목록
*/
function fileUploadCallback(files) {
    console.log(files);
    if(!files || files.length == 0) return;
    const uploadedImages = document.getElementById("imgContainer");

    const tpl = document.getElementById("tpl_image1").innerHTML;

    const domParser = new DOMParser();
    for(const file of files) {
        let html = tpl;
        let target = uploadedImages;
        const location = file.location;

        html = html.replace(/\[id\]/g, file.id)
                .replace(/\[fileName\]/g, file.fileName)
                .replace(/\[url\]/g, file.fileUrl);
    console.log(html)
        const dom = domParser.parseFromString(html, "text/html");
        const el = dom.querySelector(".image_main");

        if(target) target.appendChild(el);
    }
}

/**
* 파일 삭제 성공시 콜백 처리
* @param fileId: 파일 등록 번호
*/
function fileDeleteCallback(fileId) {
    if(!fileId) return;

    const el = document.getElementById(`file_${fileId}`);
    if(el) el.parentElement.removeChild(el);
}
