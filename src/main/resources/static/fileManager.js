var commonLib = commonLib || {};

commonLib.fileManager = {
    /*
    * 파일 업로드
    */
    upload(files, location, imageOnly) {
        const { ajaxLoad } = commonLib;

        try {
            if(!files || files.length == 0) {
                throw new Error("업로드할 파일을 선택하세요.")
            }

            const gidEl = document.querySelector("[name='gid']");
            if(!gidEl || !gidEl.value.trim()) {
                throw new Error("잘못된 접근입니다.")
            }
            const gid = gidEl.value.trim();

            /** 이미지 전용 업로드 체크 */
            if(imageOnly) {
                for(const file of files) {
                    if(file.type.indexOf("image") == -1) {
                        throw new Error("이미지만 업로드 가능합니다.")
                    }
                }
            }

            const formData = new FormData();
            for(const file of files) {
                formData.append("files", file);
            }
            formData.append("gid", gid);
            formData.append("imageOnly", imageOnly ? true : false);

            if(location) formData.append("location", location.trim());

            ajaxLoad("POST", "/file/upload", formData, 'json')
            .then((res) => {
                if(typeof fileUploadCallback == 'function') {
                    fileUploadCallback(res);
                }
            })
            .catch((err) => {
                console.log(err);
            })

        } catch (err) {
            alert(err.message);
            console.error(err);
        }
    }
}

window.addEventListener("DOMContentLoaded", function() {
    const uploadBtn = document.getElementById("imgbtn");
    const fileEl = document.getElementById("imgInput");

    if(fileEl) {
        uploadBtn.addEventListener("click", function(e) {
            e.preventDefault();
            fileEl.value="";
            fileEl.click();
            const dataset = this.dataset;
            fileEl.location = dataset.location;
            fileEl.imageOnly = dataset.imageOnly === 'true' ? true : false;
        });

        fileEl.addEventListener("change", function(e) {
            const files = e.target.files;
            commonLib.fileManager.upload(files, fileEl.location, fileEl.imageOnly);
        });
    }
});