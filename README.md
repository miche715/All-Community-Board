# All-Community-Board
서버와 클라이언트로 이루어진 게시판.  
누구나 한 번씩은 거쳐 가는 게시판.  
제목이 저런 이유는 내가 서버, 클라이언트, 데이터베이스 혼자 다(All) 할거라서...

---

## 사용 기술, 키워드
+ 서버  
Kotlin, Spring Boot, H2 DataBase, Spring Data JPA, REST API, MVC, DI, ...

+ 클라이언트  
Kotlin, Android, Restrofit2, Recycler View, View Holder, Coroutine, ...

---

## 코틀린 서버 패키지 구조
> configuration
>> ApplicationConfiguration

> domain
>> User  
>> Content  
>> Comment  

> repository
>> UserRepository  
>> UserSpringDataRepository  
>> ContentRepository  
>> ContentSpringDataRepository  
>> CommentRepository  
>> CommentSpringDataRepository  

> service
>> UserService  
>> ContentService  
>> CommentService  

> controller
>> UserController  
>> ContentController  
>> CommentController  

---

## 서버-클라이언트 예상 시나리오
+ signUp(회원 가입) - POST  
클라이언트에서 User를 만들어서 서버에 전송.  
-> 서버는 user.username, user.email 중 중복되는 값이 DB (user) table에 있는지 검사.  
-> 중복되는 값이 하나도 없으면 User를 DB에 등록. or [하나라도 중복되면 중복된 값이 있다고 클라이언트에 전송.]  
-> [클라이언트로 회원가입 성공 메세지 전송].  

+ signIn(로그인) - GET  
클라이언트에서 username과 password를 서버에 전송.  
-> 서버는 username과 password가 동시에 일치하는 User가 DB (user) table에 있는지 검사.  
-> [있다면 클라이언트에 User전송.] or [없다면 로그인 실패 메세지를 클라이언트에 전송.]  

+ findUsername(아이디 찾기) - GET  
클라이언트에서 name과 email을 서버에 전송.  
-> 서버는 name과 email가 동시에 일치하는 User가 DB (user) table에 있는지 검사.  
-> [있다면 클라이언트에 user.username전송.] or [없다면 없다고 메세지를 클라이언트에 전송.]

+ findPassword(비밀번호 찾기) - GET  
클라이언트에서 username과 name을 서버에 전송.  
-> 서버는 username과 name이 동시에 일치하는 User가 DB (user) table에 있는지 검사.  
-> [있다면 클라이언트에 user.password전송.] or [없다면 없다고 메세지를 클라이언트에 전송.]  

계속 추가중...

---

## 진행 기간
2022.06.17 ~ 진행중
