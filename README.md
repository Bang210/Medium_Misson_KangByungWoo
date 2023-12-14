# Medium_Misson_KangByungWoo
- [x] 1단계

- 구현한 기능
  - 회원가입/로그인/로그아웃시 알림
  - 로그인 여부에 따른 접근권한
  - navbar, 로그인한 멤버 정보 표시
  - 

- [x] 가입
    - GET /member/join : 가입 폼

    - POST /member/join : 가입 폼 처리

- [x] 로그인
    - GET /member/login : 로그인 폼

    - POST /member/login : 로그인 폼 처리

- [x] 로그아웃
    - POST /member/logout : 로그아웃

- [x] 폼
- [x] 회원가입 폼
    - username

    - password

    - passwordConfirm

- [x] 로그인 폼
    - username

    - password

- [x] 2단계
- 구현한 기능
  - 뒤로가기 버튼
  - 글 목록(전체 글, 특정 멤버의 글)에서 공개 설정된 글만 노출
  - 내 글 목록에서는 비공개 설정인 글도 노출, 공개여부 표시
  
- [x] 홈
    - GET / : 홈

    - 최신글 30개 노출

- [x] 글 목록 조회
    - GET /post/list : 전체 글 리스트

    - 공개된 글만 노출
    - 페이징(버튼형태, 액티브버튼 사용)
- [x] 내 글 목록 조회
    - GET /post/myList : 내 글 리스트
    - 비공개 글도 노출(표시?)

- [x] 글 상세내용 조회
    - GET /post/detail/1 : 1번 글 상세보기

- [x] 글 작성
    - GET /post/write : 글 작성 폼

    - POST /post/write : 글 작성 처리

- [x] 글 수정
    - GET /post/modify/1 : 1번 글 수정 폼

    - PUT /post/modify/1 : 1번 글 수정 폼 처리

- [x] 글 삭제
    - DELETE /post/delete/1 : 1번 글 삭제

- [x] 특정 회원의 글 모아보기
    - GET /post/user1/list : 회원 user1 의 전체 글 리스트

    - GET /b/user1/3 : 회원 user1 의 글 중에서 3번글 상세보기
    - 글 상세보기로 링크되도록 구현
    - 전체 글 목록의 하단 검색창을 통해 접근
    - 검색한 유저의 검색결과를 먼저 제공
    - 제공된 결과의 유저명을 클릭하여 접근
    - 
- [x] 폼
- [x] 글 쓰기 폼
    - title

    - body

    - isPublished

    - 체크박스

    - value="true"

- [x] 글 수정 폼
    - title

    - body

    - isPublished

    - 체크박스

    - value="true"
    - isPublished가 true면 해당 포스트 디테일로, false면 포스트 리스트로 이동

- 조회수 기능 추가
  - post detail에 최초로 접근할 때에만 증가하도록 구현(새로고침 해도 오르지 않음)
  - myPost에서 접근할 때는 증가하지 않도록 구현
  - post detail 화면에 조회수 표시

- 추천 기능 추가
  - 로그인한 회원만 추천 가능
  - 1회만 추천 가능
  - 추천(추천취소) 버튼에 추천수 표시
  - 추천시 추천 버튼이 추천 취소 버튼으로 바뀌도록 구현
  - 메인 페이지에서 추천순으로 글이 표시되도록 구현

- 댓글 기능 추가
  - post detail에서 최근 댓글이 상위에 노출되도록 구현
  - 수정, 삭제 버튼이 자신의 댓글에만 나타나도록 구현
  - 