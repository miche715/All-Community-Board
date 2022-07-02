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

## 실행 화면  
<details>
    <summary>자세히</summary>
    
![회원가입 1이동](https://user-images.githubusercontent.com/44915367/176996292-205c7716-a32d-45db-bafb-7614f52c61a5.gif)

</details>

---

## 진행 기간
2022.06.17 ~ 진행중
