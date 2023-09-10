var commonLib = commonLib || {};

/**
* ajax 처리
*
* @param method : 요청 메서드 -GET, POST, PUT ...
* @param url : 요청 url
* @param responseType: json - 응답 결과물을 json으로 변환, 아닌 경우는 문자열로 변환
*/
commonLib.ajaxLoad = function (method, url, params, responseType) {
    method = !method || !method.trim() ? "GET" : method.toUpperCase();
    const token = document.querySelector("meta[name='_csrf']").content;
    const header = document.querySelector("meta[name='_csrf_header']").content;

    return new Promise ((response, reject) => {
        const xhr = new XMLHttpRequest();

        xhr.open(method, url);
        xhr.setRequestHeader(header, token);

        if(method === "GET") xhr.send();
        else xhr.send(params);

        responseType = responseType ? responseType.toLowerCase() : undefined;
        if(responseType == 'json') {
            xhr.responseType = responseType;
        }

        xhr.onreadystatechange = function() {
            if(xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE) {
                const resultData = responseType == 'json' ? xhr.response : xhr.responseText;

                response(resultData);
            }
        };

        xhr.onabort = function(err) {
            reject(err);
        }

        xhr.ontimeout = function(err) {
            reject(err);
        }

        xhr.ontimeout = function(err) {
            reject(err);
        }
    });
}
