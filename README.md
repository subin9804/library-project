# 📖library-project📖

## 📍개요
- 도서 대여 및 관리 서비스를 제공하기 위해 SpringFramework로 제작한 개인프로젝트입니다.
    - 개발기간: 2023-05 ~ 2023-09
    - 개발언어: <img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=javascript&logoColor=black">
    - 개발환경: <img id="main-logo-img" src="https://img.shields.io/badge/IntelliJ-000.svg?style=for-the-badge&logo=IntelliJ&logoColor=white"> <img id="main-logo-img" src="https://img.shields.io/badge/maven-C71A36.svg?style=for-the-badge&logo=maven&logoColor=white">
    - DB: <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white">
    - 프레임워크/라이브러리: <img id="main-logo-img" src="https://img.shields.io/badge/springboot-green.svg?style=for-the-badge&logo=springboot&logoColor=white"> <img id="main-logo-img" src="https://img.shields.io/badge/thymeleaf-005F0F.svg?style=for-the-badge&logo=thymeleaf&logoColor=white"> <img id="main-logo-img" src="https://img.shields.io/badge/JPA-000.svg?style=for-the-badge&logo=JPA&logoColor=white"> <img id="main-logo-img" src="https://img.shields.io/badge/querydsl-000.svg?style=for-the-badge&logo=querydsl&logoColor=white"> <img id="main-logo-img" src="https://img.shields.io/badge/springsecurity-6DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white">

## 📍ERD
![Library data structure](https://github.com/subin9804/library-project/assets/116933612/968977a8-0fd7-4fe5-b8b6-9e2dffdddfa8)

## 📍아키텍처
- AWS EC2를 통해 리눅스 서버에서 빌드하여 서버를 배포했습니다.
![library아키텍처](https://github.com/subin9804/library-project/assets/116933612/68fa8897-e0bd-4c8e-a2c2-b72c0dd58652)

## 📍프로젝트 소개
- Spring-security의 인증/인가 설정을 통해 최고관리자(SUPER), 일반관리자(MANAGER)와 회원(USER)으로 권한을 나누었습니다.
- 관리자는 회원 정보 및 도서 정보를 관리할 수 있으며 모든 회원의 대여 정보를 조회할 수 있습니다.
- 회원은 본인 프로필 수정 및 도서 조회, 대여, 반납, 대여기간 연장 기능을 사용할 수 있습니다.

### 1. 홈 화면
- 관리자의 경우 상단바에 관리자 페이지로 이동할 수 있는 버튼이 생성됩니다.
- 가장 최근에 등록된 도서가 보여집니다.
![user-index](https://github.com/subin9804/library-project/assets/116933612/266849a2-f9a4-4cfb-9c27-198f2b1746cf)

### 2. 회원 - 로그인 및 회원가입
- 유효성 검사를 통과하지 못할 시 에러메시지가 표시됩니다.
- 공통 예외 클래스를 따로 구현하여 관리했습니다.
![signup signin](https://github.com/subin9804/library-project/assets/116933612/c21f4ae8-db8b-4c2d-91e2-9f75fad62414)

### 3. 회원 - 도서 대여
- 도서를 검색하고 조회할 수 있습니다.
- 바로 대여가 가능하며 상세페이지에서 도서 정보를 확인할 수 있습니다.
- 회원 정보와 도서의 상태에 따라 대여 가능 여부가 결정됩니다.
![user-book-detail](https://github.com/subin9804/library-project/assets/116933612/28f8a0a4-634d-4daa-b3e4-fb9440a9b864)
![user-rent](https://github.com/subin9804/library-project/assets/116933612/7c129258-cf37-4299-98a7-024e2ec04bd1)

### 4. 회원 - 프로필
- 본인의 프로필을 조회 및 수정할 수 있습니다.
- 수정의 경우 비밀번호를 확인하는 페이지로 이동합니다. 올바른 비밀번호를 입력하지 않을 시 예외가 발생합니다.
- 프로필 사진은 각 회원 당 하나씩만 저장되며 기본 이미지를 선택할 수 있습니다.
![user-profile](https://github.com/subin9804/library-project/assets/116933612/8b814834-f9b0-43f2-871d-a4dd6cf227df)

### 5. 회원 - 대여 관리
- 대여한 도서에 대해 반납할지, 대여 기간을 연장할지 선택할 수 있습니다.
- 대여 기간 연장 단위는 7일 입니다.
![user-rent](https://github.com/subin9804/library-project/assets/116933612/7c129258-cf37-4299-98a7-024e2ec04bd1)

### 6. 관리자 - 회원 관리
- 회원 정보를 조회하고 수정 및 삭제할 수 있습니다.
- 회원 비밀번호를 임의로 수정 시 보안을 위해 정해진 번호로만 수정이 가능합니다.
- 회원정보를 삭제할 시 즉시 완전히 삭제되며 대여 기록에는 '탈퇴 회원'으로 표시됩니다.
![admin-user](https://github.com/subin9804/library-project/assets/116933612/e9db5074-b6ef-406d-8a08-5b354217838a)

### 7. 관리자 - 도서 관리
- 도서를 새로 등록하거나 수정, 삭제할 수 있습니다.
- 등록한 도서 목록을 조회하고 검색할 수 있습니다.
- 도서 정보를 삭제할 시 즉시 완전히 삭제되며 대여 기록에는 '*'으로 표시됩니다.
![admin-book](https://github.com/subin9804/library-project/assets/116933612/226c7dda-1774-4a45-8381-66346991a264)

### 8. 관리자 - 대여 관리
- 모든 회원의 대여 정보를 조회할 수 있다.
- 삭제된 도서나 탈퇴한 회원의 대여 정보 역시 확인할 수 있다.
![admin-return-list](https://github.com/subin9804/library-project/assets/116933612/11929384-12b5-48c1-9e4f-5bb9f4c0cfa4)

## 📍아쉬운점
- 기획 단계에서 데이터 모델링에 대한 아쉬움이 있다. 데이터가 변경될 때 연결된 데이터들에 대한 처리에 대해 구체적으로 설계하지 않아 개발 시 즉각적으로 결정해야 했다. 특히 파일데이터 관리에 대해서는 추후 리팩토링을 진행할 계획이다.
- 관리자 기능이 부족하다고 생각한다. meta데이터 설정 등 조금 더 자유도를 높인 관리자 기능을 추가해야 한다고 생각한다.
- 배포에 대한 이해가 많이 부족함을 느꼈다. 도커와 Github Actions을 공부해서 CI/CD를 구축할 생각이다.