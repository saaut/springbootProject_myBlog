const createCommentButton = document.getElementById('create-comment-btn');

if (createCommentButton) {
    createCommentButton.addEventListener('click', function(event) {
        event.preventDefault();
        alert("success");

        const data = {
            postsId: $('#article-id').val(),
            comment: $('#comment').val()
        };

        if (!data.comment || data.comment.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            body = JSON.stringify({
                        comment: $('#comment').val()
                    });
                    function success() {
                        alert('등록 완료되었습니다.');
                        location.replace('/articles/'+data.postsId);
                    };
                    function fail() {
                        alert('등록 실패했습니다.');
                        location.replace('/articles/'+data.postsId);
                    };
                    httpRequest('POST', '/api/articles/' + data.postsId + '/comments', body, success, fail);
        }
    });
}
// 쿠키를 가져오는 함수
 function getCookie(key) {
     var result = null;
     var cookie = document.cookie.split(';');
     cookie.some(function (item) {
         item = item.replace(' ', '');

         var dic = item.split('=');

         if (key === dic[0]) {
             result = dic[1];
             return true;
         }
     });

     return result;
 }
function httpRequest(method, url, body, success, fail) {
    alert("success");

    fetch(url, {
        method: method,
        headers: { // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
        body: body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        const refresh_token = getCookie('refresh_token');
        if (response.status === 401 && refresh_token) {
            fetch('/api/token', {
                method: 'POST',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: getCookie('refresh_token'),
                }),
            })
                .then(res => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then(result => { // 재발급이 성공하면 로컬 스토리지값을 새로운 액세스 토큰으로 교체
                    localStorage.setItem('access_token', result.accessToken);
                    httpRequest(method, url, body, success, fail);
                })
                .catch(error => fail());
        } else {
            return fail();
        }
    });
}
