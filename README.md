# Medium_Misson_KangByungWoo(2차)
## 목표
 - [x] 모바일용 웹화면으로 구성
 - [x] ncp 배포
## 체크리스트
 - [x] 1단계
   - [x] member엔티티에 paid 필드 추가
     - role_paid
 - [x] 2단계
   - [x] post엔티티에 paid 필드 추가
     - 유료회원만 조회 가능
     - 유료회원에게만 글 작성 페이지에서 유료 글 설정 체크박스가 노출되도록 구현
 - [x] 3단계
   - NotProd에서 유료회원과 유료글 샘플데이터 100개 이상 생성
 - [ ] 배포
   - [x] 도커만으로 배포
     - 도커에서 깃으로 pull한 뒤 빌드해서 run
   - [ ] 젠킨스 or 깃허브액션을 통한 배포
## 기존 코드 수정사항
### 뒤로가기 -> 목록으로
글 상세 페이지에서 '뒤로가기'를 눌렀을 때, URL에 msg가 추가되어있으면
msg만 제거된 현재의 URL로 이동하거나, 
이전의 msg가 포함된 URL로 이동되던 점을 개선함
-> 글 상세 페이지에 접근 가능한 페이지에서는 
현재의 URL을 msg없이 파싱하여 sessionstorage에 저장,
'목록으로' 버튼을 통해 저장된 URL로 이동하도록 구현함.