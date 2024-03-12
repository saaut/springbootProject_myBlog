# 📝 My Blog



- SpringBoot 3 를 이용한 개인 프로젝트입니다.
웹 애플리케이션의 기본이 되는 게시판 기능을 구현해봄으로써 스프링과 웹 애플리케이션의 전반적인 흐름을 이해하고자 하였습니다.
---

## 🚀 주요 기능
- CRUD 기능을 갖춘 게시판
- CRUD 기능을 갖춘 댓글
- OAuth2 Google 로그인

---

## 🛠️ 사용된 기술
### 주요 프레임워크/라이브러리
#### 백엔드
- Java17
- SpringBoot 3
- JPA
- OAuth 2.0

#### 빌드 도구
- Gradle

#### 데이터베이스
- ORM

### 프론트엔드
- HTML
- JavaScript
- Thymeleaf

### 배포
- AWS Elastic Beanstalk

### CI/CD
- GitHub Action

---

## 🖥️ 실행 화면
- 로그인 화면 
![Run Screen](https://github.com/saaut/springbootProject_myBlog/assets/109278065/bc152d02-1c49-430d-9d6d-8ed60f6646ef)

- 글 목록
![Article List](https://github.com/saaut/springbootProject_myBlog/assets/109278065/c0b277b6-860c-4502-b117-371a7ebfa270)

- 글 조회
![view Article](https://github.com/saaut/springbootProject_myBlog/assets/109278065/868e3d63-2dbc-4afc-b1cd-3e191870a88c)
- 글 생성
  ![create Article](https://github.com/saaut/springbootProject_myBlog/assets/109278065/da908f9d-9580-49d6-a8c6-63f5a8abb299)
- 글 작성 완료
  ![created Article](https://github.com/saaut/springbootProject_myBlog/assets/109278065/901504e9-8e36-40a2-a191-7517f627deca)
- 댓글 작성
  ![댓글CRUD](https://github.com/saaut/springbootProject_myBlog/assets/109278065/b6094854-1685-4b80-bea2-d7aed97db4dd)

---

## 📊 구조 및 디자인
### (1) 도메인 설계
- Article
  ![article](https://github.com/saaut/springbootProject_myBlog/assets/109278065/e84fed61-1e84-49d6-876e-92bbe11625d4)
- User
  ![user](https://github.com/saaut/springbootProject_myBlog/assets/109278065/b8825ffa-a5ca-4992-b0c8-4bc716c003bd)
- Comment
  ![comment](https://github.com/saaut/springbootProject_myBlog/assets/109278065/e2327e79-cd9d-449a-ab27-056e0403c5dd)


---


## 후기

- 프로젝트를 시작했을 때의 목표처럼, 웹 애플리케이션과 스프링 백엔드의 흐름을 이해할 수 있었습니다.
  애플리케이션의 이론상 작동 방법에 대해서는 알고 있었지만, 엔티티 설계를 제외하고는 추상적으로 이해하고있다는 느낌이 강했는데, 직접 기능을 구현하고 추가하면서 데이터의 흐름과 유저에게 보여주는 과정이 구체적으로 이해가 되기 시작했습니다.
  역시 강의만 들을 때보다 직접 코드를 작성하면서 이러한 것들이 더 깊게 와닿는다는 사실을 느꼈습니다. 기능을 구현하기 위해 로직을 설계하고, 어떻게 구현할지 막막했던 기능도 열심히 공부하고 검색하면서 해결해나가는 과정이 즐거웠습니다.
- 그리고 웹 애플리케이션의 베이스가 되는 게시판이니만큼 이 프로젝트를 기반으로 하여 더 다양한 기능을 추가해보고 싶어졌습니다.