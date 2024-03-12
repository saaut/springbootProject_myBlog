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
document.addEventListener('click', function(event) {
    if (event.target.classList.contains('edit-comment-btn')) {
        const commentId = event.target.dataset.commentId;
        const commentContent = event.target.dataset.commentContent;

        // Populate textarea with existing comment content
        const textarea = document.createElement('textarea');
        textarea.className = 'form-control';
        textarea.rows = '3';
        textarea.value = commentContent;

        // Replace the comment text with the textarea
        const commentContainer = event.target.closest('tr');
        const commentContentElement = commentContainer.querySelector('.comment-content');
        commentContentElement.textContent = '';
        commentContentElement.appendChild(textarea);

        // Change button text to "Save"
        event.target.textContent = 'Save';
        event.target.classList.add('save-comment-btn');
        event.target.classList.remove('edit-comment-btn');

        if (event.target.classList.contains('save-comment-btn')) {
                const commentId = event.target.dataset.commentId;
                const articleId = event.target.dataset.articleId;
                const textarea = event.target.closest('tr').querySelector('textarea');

                const modifiedText = textarea.value.trim();
                if (modifiedText !== '') {
                    const body = JSON.stringify({
                        content: modifiedText
                    });

                    function success() {
                        alert('Modification completed.');
                        location.reload(); // Reload the page or perform any other desired action
                    }

                    function fail() {
                        alert('Edit failed.');
                        location.reload(); // Reload the page or perform any other desired action
                    }

                    // Send HTTP request to update the comment content
                    httpRequest('PUT', `/api/articles/${articleId}/comments/${commentId}`, body, success, fail);
                } else {
                    alert('Please provide a valid comment.');
                }
            }
            if (event.target.classList.contains('cancel-comment-btn')) {
                    const commentId = event.target.dataset.commentId;
                    const commentContent = event.target.dataset.commentContent;

                    // Restore the original comment content
                    const commentContainer = event.target.closest('tr');
                    const commentContentElement = commentContainer.querySelector('.comment-content');
                    commentContentElement.textContent = commentContent;

                    // Change button text back to "Edit"
                    const editButton = document.createElement('button');
                    editButton.type = 'button';
                    editButton.textContent = 'Edit';
                    editButton.classList.add('btn', 'btn-sm', 'btn-outline-primary', 'edit-comment-btn');
                    editButton.dataset.commentId = commentId;
                    editButton.dataset.commentContent = commentContent;

                    const buttonContainer = commentContainer.querySelector('.comment-buttons');
                    buttonContainer.replaceChild(editButton, event.target);
                }
    }
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
