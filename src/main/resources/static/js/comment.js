const createCommentButton = document.getElementById('create-comment-btn');

if (createCommentButton) {
    createCommentButton.addEventListener('click', function(event) {
        event.preventDefault();

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
// 수정 기능
document.addEventListener('DOMContentLoaded', function() {
    const modifyCommentButtons = document.querySelectorAll('#modify-comment-btn');
    modifyCommentButtons.forEach(button => {
        button.addEventListener('click', event => {
            const commentContainer = button.closest('tr');
            const commentContent = commentContainer.querySelector('.comment-content');
            const commentText = commentContent.textContent.trim();
            const commentInfo = commentContent.nextElementSibling;

            // Create a textarea element
            const textarea = document.createElement('textarea');
            textarea.className = 'form-control';
            textarea.rows = '3';
            textarea.value = commentText;

            // Create a "Save" button
            const saveButton = document.createElement('button');
            saveButton.type = 'button';
            saveButton.className = 'btn btn-primary mr-2';
            saveButton.textContent = '저장';

            // Create a "Cancel" button
            const cancelButton = document.createElement('button');
            cancelButton.type = 'button';
            cancelButton.className = 'btn btn-secondary';
            cancelButton.textContent = '취소';

            // Function to handle saving changes
            saveButton.addEventListener('click', function() {
                const modifiedText = textarea.value.trim();
                if (modifiedText !== '') {
                    commentContent.textContent = modifiedText;

                    // Prepare the data for HTTP request
                    const commentId = button.dataset.commentId;
                    const params = new URLSearchParams(location.search);
                    const articleId = params.get('id');
                    const body = JSON.stringify({
                        content: modifiedText
                    });

                    // Function to handle success
                    function success() {
                        alert('수정 완료되었습니다.');
                        location.reload(); // Reload the page or perform any other desired action
                    }

                    // Function to handle failure
                    function fail() {
                        alert('수정 실패했습니다.');
                        location.reload(); // Reload the page or perform any other desired action
                    }

                    // Send HTTP request
                    httpRequest('PUT', `/api/articles/${articleId}/comments/${commentId}`, body, success, fail);
                }
                commentInfo.appendChild(button);
                commentInfo.appendChild(textarea);
                commentInfo.appendChild(cancelButton);
                saveButton.remove();
            });

            // Function to handle canceling changes
            cancelButton.addEventListener('click', function() {
                commentInfo.appendChild(button);
                commentInfo.appendChild(textarea);
                commentInfo.appendChild(saveButton);
                cancelButton.remove();
            });

            // Replace the comment text with the textarea
            commentContent.textContent = '';
            commentContent.appendChild(textarea);
            commentInfo.appendChild(saveButton);
            commentInfo.appendChild(cancelButton);
            button.remove(); // Remove the original modify button
        });
    });
});
function commentDelete(articleId, commentId, commentWriterId, sessionUserId) {
    // 본인이 작성한 글인지 확인
    if (commentWriterId !== sessionUserId) {
        alert("본인이 작성한 댓글만 삭제 가능합니다.");
    } else {
        const con_check = confirm("삭제하시겠습니까?");

        if (con_check) {
            function success() {
                alert('삭제가 완료되었습니다.');
                location.replace('/articles/'+articleId);
            }

            function fail() {
                alert('삭제 실패했습니다.');
                location.replace('/articles/'+articleId);
            }
            // 적절한 요청을 보내는 함수 (예: httpRequest)를 호출하여 댓글 삭제 요청을 서버에 전송합니다.
            httpRequest('DELETE',`/api/articles/${articleId}/comments/${commentId}`, null, success, fail);
        }
    }
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
