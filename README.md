# All-Community-Board
## 0. 순서
[1. 배경](#1-배경)  
[2. 기술 및 환경](#2-기술-및-환경)  
[3. 패키지 구조](#3-패키지-구조)  
[4. API 설계](#4-api-설계)  
[5. 데이터베이스 구조](#5-데이터베이스-구조)  
[6. 실행 화면](#6-실행-화면)  
[7. 후기 및 회고](#7-후기-및-회고)

---

## 1. 배경  
서버와 클라이언트로 이루어진 게시판 프로젝트이다.  
개발자 지망생이라면 적어도 게시판 정도는 한 번씩 만들어 보라는 말을 많이 들었다.  
현업에서 만드는 게 다 게시판 만들기의 응용이라고 말이다.  
그래서 나도 만들어 보고 싶어서 Spring Boot 독학했다. 클라이언트는 안드로이드로 정했다.  
내가 안드로이드를 공부해서 그런지 서버도 Java 대신 Kotlin을 이용하기로 했다. 그게 그거라 별문제는 없을 거다.  
게시판에 들어갈 CRUD 기능과, Clean Architecture 구성을 목표로 제작에 들어갔다.  

---

## 2. 기술 및 환경
+ 서버
    + 개발 환경  
        + Windows 10
        + IntelliJ IDEA Ultimate
    + 언어  
        + Kotlin
    + 프레임워크 & 라이브러리
        + Spring Boot
        + Spring Data JPA
        + Hibernate
        + Jackson
    + 데이터베이스
        + H2 DataBase
    + 빌드 툴
        + Gradle
    + 아키텍처
        + MVC

<br/> 

+ 클라이언트
    + 개발 환경
        + Windows 10
        + Android Studio
    + 언어
        + Kotlin
    + 프레임워크 & 라이브러리
        + Android(SDK Level: 32)
        + Retrofit2
        + Coroutine
        + LiveData
        + ViewModel
        + Gson, Scalars
    + 빌드 툴
        + Gradle
    + 아키텍처
        + MVVM

---

## 3. 패키지 구조  
<details>
<summary>서버</summary>

```  
📦main  
 ┣ 📂kotlin  
 ┃ ┗ 📂com  
 ┃ ┃ ┗ 📂example  
 ┃ ┃ ┃ ┗ 📂server  
 ┃ ┃ ┃ ┃ ┣ 📂configuration  
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ApplicationConfiguration.kt  
 ┃ ┃ ┃ ┃ ┣ 📂controller  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentController.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜ContentController.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜GoodController.kt  
 ┃ ┃ ┃ ┃ ┃ ┗ 📜UserController.kt  
 ┃ ┃ ┃ ┃ ┣ 📂domain  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Comment.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentResponse.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Content.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜ContentResponse.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Good.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜GoodResponse.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜User.kt  
 ┃ ┃ ┃ ┃ ┃ ┗ 📜UserResponse.kt  
 ┃ ┃ ┃ ┃ ┣ 📂repository  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentRepository.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentSpringDataRepository.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜ContentRepository.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜ContentSpringDataRepository.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜GoodRepository.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜GoodSpringDataRepository.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜UserRepository.kt  
 ┃ ┃ ┃ ┃ ┃ ┗ 📜UserSpringDataRepository.kt  
 ┃ ┃ ┃ ┃ ┣ 📂service  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentService.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜ContentService.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📜GoodService.kt  
 ┃ ┃ ┃ ┃ ┃ ┗ 📜UserService.kt  
 ┃ ┃ ┃ ┃ ┗ 📜ServerApplication.kt  
 ┗ 📂resources  
 ┃ ┣ 📂static  
 ┃ ┣ 📂templates  
 ┃ ┗ 📜application.properties  
 ```  
 
</details>

<details>
<summary>클라이언트</summary>

```  
📦main  
 ┣ 📂java  
 ┃ ┗ 📂com  
 ┃ ┃ ┗ 📂example  
 ┃ ┃ ┃ ┗ 📂client  
 ┃ ┃ ┃ ┃ ┣ 📂comment  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂adapter  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CommentListItemAdapter.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂domain  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Comment.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CommentResponse.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂repository  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CommentRepository.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentRetrofitService.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CommentRetrofitServiceObject.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂view  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CommentListFragment.kt  
 ┃ ┃ ┃ ┃ ┃ ┗ 📂viewmodel  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CommentViewModel.kt  
 ┃ ┃ ┃ ┃ ┣ 📂content  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂adapter  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ContentListItemAdapter.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂domain  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Content.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ContentResponse.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂repository  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ContentRepository.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ContentRetrofitService.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ContentRetrofitServiceObject.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂view  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AddContentActivity.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ContentListActivity.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜GetContentActivity.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ModifyContentActivity.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SearchContentListActivity.kt  
 ┃ ┃ ┃ ┃ ┃ ┗ 📂viewmodel  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ContentViewModel.kt  
 ┃ ┃ ┃ ┃ ┣ 📂good  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂domain  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Good.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜GoodResponse.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂repository  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜GoodRepository.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜GoodRetrofitService.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜GoodRetrofitServiceObject.kt  
 ┃ ┃ ┃ ┃ ┃ ┗ 📂viewmodel  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜GoodViewModel.kt  
 ┃ ┃ ┃ ┃ ┣ 📂jsonconverter  
 ┃ ┃ ┃ ┃ ┃ ┗ 📜NullOnEmptyConverterFactory.kt  
 ┃ ┃ ┃ ┃ ┗ 📂user  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂domain  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜User.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserResponse.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂repository  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRepository.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UserRetrofitService.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRetrofitServiceObject.kt  
 ┃ ┃ ┃ ┃ ┃ ┣ 📂view  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FindAccountActivity.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FindPasswordFragment.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FindUsernameFragment.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SignInActivity.kt  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SignUpActivity.kt  
 ┃ ┃ ┃ ┃ ┃ ┗ 📂viewmodel  
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserViewModel.kt  
 ┣ 📂res  
 ┃ ┣ 📂drawable  
 ┃ ┃ ┣ 📜basic_edge.xml  
 ┃ ┃ ┣ 📜comment_num_edge.xml  
 ┃ ┃ ┣ 📜good_edge.xml  
 ┃ ┃ ┗ 📜ic_launcher_background.xml  
 ┃ ┣ 📂drawable-v24  
 ┃ ┃ ┣ 📜chat_48px.xml  
 ┃ ┃ ┣ 📜ic_launcher_foreground.xml  
 ┃ ┃ ┗ 📜thumb_up_48px.xml  
 ┃ ┣ 📂font  
 ┃ ┃ ┣ 📜font.xml  
 ┃ ┃ ┗ 📜jalnan_otf.otf  
 ┃ ┣ 📂layout  
 ┃ ┃ ┣ 📜activity_add_content.xml  
 ┃ ┃ ┣ 📜activity_content_list.xml  
 ┃ ┃ ┣ 📜activity_find_account.xml  
 ┃ ┃ ┣ 📜activity_get_content.xml  
 ┃ ┃ ┣ 📜activity_modify_content.xml  
 ┃ ┃ ┣ 📜activity_search_content_list.xml  
 ┃ ┃ ┣ 📜activity_sign_in.xml  
 ┃ ┃ ┣ 📜activity_sign_up.xml  
 ┃ ┃ ┣ 📜comment_list_item.xml  
 ┃ ┃ ┣ 📜content_list_item.xml  
 ┃ ┃ ┣ 📜fragment_comment_list.xml  
 ┃ ┃ ┣ 📜fragment_find_password.xml  
 ┃ ┃ ┗ 📜fragment_find_username.xml  
 ┃ ┣ 📂menu  
 ┃ ┃ ┗ 📜menu_content_list.xml  
 ┃ ┣ 📂mipmap-anydpi-v26  
 ┃ ┃ ┣ 📜ic_launcher.xml  
 ┃ ┃ ┗ 📜ic_launcher_round.xml  
 ┃ ┣ 📂mipmap-hdpi  
 ┃ ┃ ┣ 📜ic_launcher.webp  
 ┃ ┃ ┗ 📜ic_launcher_round.webp  
 ┃ ┣ 📂mipmap-mdpi  
 ┃ ┃ ┣ 📜ic_launcher.webp  
 ┃ ┃ ┗ 📜ic_launcher_round.webp  
 ┃ ┣ 📂mipmap-xhdpi  
 ┃ ┃ ┣ 📜ic_launcher.webp  
 ┃ ┃ ┗ 📜ic_launcher_round.webp  
 ┃ ┣ 📂mipmap-xxhdpi  
 ┃ ┃ ┣ 📜ic_launcher.webp  
 ┃ ┃ ┗ 📜ic_launcher_round.webp  
 ┃ ┣ 📂mipmap-xxxhdpi  
 ┃ ┃ ┣ 📜ic_launcher.webp  
 ┃ ┃ ┗ 📜ic_launcher_round.webp  
 ┃ ┣ 📂values  
 ┃ ┃ ┣ 📜colors.xml  
 ┃ ┃ ┣ 📜strings.xml  
 ┃ ┃ ┗ 📜themes.xml  
 ┃ ┣ 📂values-night  
 ┃ ┃ ┗ 📜themes.xml  
 ┃ ┗ 📂xml  
 ┃ ┃ ┣ 📜backup_rules.xml  
 ┃ ┃ ┗ 📜data_extraction_rules.xml  
 ┗ 📜AndroidManifest.xml  
```  

</details>

---

## 4. API 설계  
- UserController  

|기능|Method|URL|Response Body|
|:------|:---|:---|:---|
|회원가입|POST|/users/sign-up|Boolean|
|로그인|GET|/users/sign-in|User|
|아이디 찾기|GET|/users/find-username|String|
|비밀번호 찾기|GET|/users/find-password|String|

<br/>  

- ContentController  

|기능|Method|URL|Response Body|
|:------|:---|:---|:---|
|게시글 등록|POST|/contents/create|Boolean|
|모든 게시글 로딩|GET|/contents/all|MutableList< Content >|
|게시글 검색|GET|/contents/search|MutableList< Content >|
|게시글 수정|PUT|/contents/update|Content|
|게시글 삭제|DELETE|/contents/delete|Boolean|

<br/>  

- CommentController  

|기능|Method|URL|Response Body|
|:------|:---|:---|:---|
|댓글 등록|POST|/comments/create|Content|
|모든 댓글 로딩|GET|/comments/all|MutableList< Comment >|
|댓글 삭제|DELETE|/comments/delete|Content|

<br/>  

- GoodController  

|기능|Method|URL|Response Body|
|:------|:---|:---|:---|
|좋아요 증가|POST|/goods/create|Content|

---

## 5. 데이터베이스 구조  
- USER(사용자)  

|이름|자료형|비고|
|:------|:---|:---|
|user_id|bigint|PRIMARY KEY|
|username|varchar|-|
|password|varchar|-|
|name|varchar|-|
|email|varchar|-|

<br/>  

- CONTENT(게시글)  

|이름|자료형|비고|
|:------|:---|:---|
|content_id|bigint|PRIMARY KEY|
|writer|varchar|-|
|title|varchar|-|
|text|varchar|-|
|created_at|varchar|-|
|good_num|integer|-|
|comment_num|integer|-|
|user_id|bigint|FOREIGN KEY|

<br/>  

- COMMENT(댓글)  

|이름|자료형|비고|
|:------|:---|:---|
|comment_id|bigint|PRIMARY KEY|
|writer|varchar|-|
|text|varchar|-|
|created_at|varchar|-|
|content_id|bigint|FOREIGN KEY|
|user_id|bigint|FOREIGN KEY|

<br/>  

- GOOD(좋아요)  

|이름|자료형|비고|
|:------|:---|:---|
|good_id|bigint|PRIMARY KEY|
|content_id|bigint|FOREIGN KEY|
|user_id|bigint|FOREIGN KEY|
---

## 6. 실행 화면  
<details>
<summary>공통</summary>

- 뒤로 버튼 두 번 누르면 종료  
![뒤로 두 번](https://user-images.githubusercontent.com/44915367/176999449-f81c312e-240c-415b-8607-65ae995b4ed1.gif)
    
 ```kotlin
    private var backKeyPressedTime = 0L
    override fun onBackPressed()
    {
        if(System.currentTimeMillis() > backKeyPressedTime + 2000)
        {
            backKeyPressedTime = System.currentTimeMillis()
            Snackbar.make(binding.mainLayout, "\'뒤로\'버튼 한번 더 누르시면 종료됩니다.", Snackbar.LENGTH_SHORT).show()

            return
        }

        if(System.currentTimeMillis() <= backKeyPressedTime + 2000)
        {
            finishAffinity()
        }
    }
 ```
뒤로 버튼을 누르고 Snackbar를 통해 알려준 뒤, 2초 이내 다시 클릭 시 앱을 종료시킨다.  
화면을 뒤로 가고 싶으면 Toolbar에 백 버튼을 누른다.  
    
- 빈 화면 클릭 시 키보드 내리기  
![키보드 내리기](https://user-images.githubusercontent.com/44915367/176999663-35734d23-41d7-4250-b85e-ab54b97f7a8f.gif)   
                                           
```kotlin
    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run()
        {
            this.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

        return true
    }
```                                                          
키보드가 올라오는 곳은 빈 화면을 클릭 시 키보드가 내려간다.  
버튼 클릭도 마찬가지이다.  

</details>

<details>
<summary>회원가입</summary>

- 회원가입 화면으로 이동  
![회원가입 1이동](https://user-images.githubusercontent.com/44915367/176997395-9e1a5c36-ee24-426d-a8c1-b41b0dfbdce1.gif)
    
회원 가입 TextView를 누르면 회원가입 화면으로 이동한다.  

- 사용자 정보 입력 양식을 잘못 지킨 경우  
 ![회원가입 2잘못된 입력](https://user-images.githubusercontent.com/44915367/176997133-e9666bb9-c5f7-43a2-8504-3fee0bb64c6f.gif)  
 ```kotlin
    private val nameRegex = "^[가-힣]*$".toRegex()  // 한글만
    private val usernameRegex = "^[a-z0-9]{2,8}$".toRegex()  // 소문자 + 숫자 2~8자리
    private val passwordRegex = "^[a-z0-9]{4,20}$".toRegex()  // 소문자 + 숫자 4~20자리
    private val emailRegex = "^[a-z0-9\\.\\-_]+@([a-z0-9\\-]+\\.)+[a-z]{2,6}$".toRegex()  // 이메일 형식
 ```
 지정해 둔 정규식에 맞지 않는 입력이 들어오면, Snackbar를 통해 뭐가 잘못됐는지 알려준다.  

- 이미 있는 계정인 경우  
![회원가입 3이미 있는 계정](https://user-images.githubusercontent.com/44915367/176997309-669dfd79-ef50-43a1-95c9-4d458023fd32.gif)  
    
아이디와 이메일은 중복될 수 없다.  
둘 중 하나라도 DB에 겹치는 게 있다면 Snackbar를 통해 가입이 안 됐음을 알려준다.  
    
- 회원가입 성공  
![회원가입 4 회원가입 성공](https://user-images.githubusercontent.com/44915367/176997449-ff1b5634-72a4-4328-872b-90ca81d41432.gif)  
    
회원가입에 성공하면 로그인 화면으로 돌아가고 Snackbar를 통해 가입이 성공했음을 알려준다.  
    
</details>  

<details>
<summary>계정 찾기</summary>

- 계정 찾기 화면으로 이동  
![계정 찾기 1이동](https://user-images.githubusercontent.com/44915367/176997569-0b6ab49d-37ef-4e5a-b6e9-b3429a99f83a.gif)  

아이디 / 비밀번호 찾기 TextView를 누르면 계정을 찾는 화면으로 이동한다.  
TabLayout으로 구성되어 있고, 하위에는 아이디를 찾는 Fragment와 비밀번호를 찾는 Fragment로 구성되어 있다.  
    
- 아이디 찾기 실패  
![계정 찾기 2아이디 찾기 실패](https://user-images.githubusercontent.com/44915367/176997652-e80c6eb9-2ea9-41b1-861d-ad9f8485995c.gif)  

가입한 계정의 이름과 이메일을 입력받아 동시에 일치하는 계정이 있는지 검사한다.  
없으면 Snackbar를 통해 찾을 수 없다고 알려준다. 
    
- 아이디 찾기 성공  
![계정 찾기 3아이디 찾기 성공](https://user-images.githubusercontent.com/44915367/176997702-8a0f7597-d556-427b-ae0d-ce311b38476f.gif)  
    
성공하면 Snackbar를 통해 알려준다.  
    
- 비밀번호 찾기 실패  
![계정 찾기 4비밀번호 찾기 실패](https://user-images.githubusercontent.com/44915367/176997730-d623189a-a629-41bb-9408-40f067a429f8.gif)  
    
이름과 아이디를 받는다.  
나머진 아이디와 동일하다.  
    
- 비밀번호 찾기 성공  
![계정 찾기 5비밀번호 찾기 성공](https://user-images.githubusercontent.com/44915367/176997771-e0a18c54-1ac9-4cc9-9637-bc7f7df61a61.gif)  
    
성공도 아이디와 동일하다.  
보통 이렇게는 안 하고 비밀번호를 변경하라고 하지만, 나는 이렇게 했다.  
    
</details>

<details>
<summary>로그인, 로그아웃</summary>
    
- 로그인 실패  
![로그인 1로그인 실패](https://user-images.githubusercontent.com/44915367/176997855-4db6398c-24b4-44e1-b119-ded68dc90aed.gif)  
    
아이디와 비밀번호가 동시에 일치하는 계정이 있는지 검사한다.  
없으면 없다고 Snackbar를 통해 알려준다.  
    
- 로그인 성공  
![로그인 2로그인 성공](https://user-images.githubusercontent.com/44915367/176997984-276b6b65-f7f7-4af1-92c2-70ecb7267788.gif)  
    
성공하면 게시글 목록이 보이는 화면으로 이동한다.  
    
- 로그 아웃  
![로그아웃 1로그아웃 성공](https://user-images.githubusercontent.com/44915367/176998139-f599226d-0586-40a5-8f5d-3908d1b8f679.gif)  
    
Toolbar 메뉴를 누르면 로그아웃을 할 수 있다.  
AlertDialog를 통해 정말 로그아웃을 할 건지 물어보고, 확인을 누르면 로그인 화면으로 이동한다.  
    
</details>

<details>
<summary>게시글</summary>

 - 전체 게시글 목록  
![게시글 목록](https://user-images.githubusercontent.com/44915367/176998183-0d7b50d0-558a-476f-bde7-f7c1aac0f283.gif)  

전체 게시글 목록을 표시한다.  
RecyclerView를 SwipeRefreshLayout으로 감싸고 있는 형태이기 때문에, 맨 위로 스크롤 하면 새로고침을 해 목록을 다시 불러온다.  
게시글 로딩도 DB에 있는 모든 게시글을 다 가져오지는 않고, 마치 페이징 처리를 한 것처럼 15개씩 끊어서 가져온다.  
Adapter에 Item이 Bind 될 때 position 값을 이용해서 끝인지 아닌지 판단하고 LivaData를 변화시켜서 가져오고 있는데, 솔직히 이게 맞는 방법인지는 모르겠다.  
    
- 게시글 등록  
![게시글 등록](https://user-images.githubusercontent.com/44915367/176998513-fb15e1b9-09e0-4f40-822c-9cf20ce3747d.gif)  
    
오른쪽 아래 FloatingActionButton을 누르면 게시글 등록 화면으로 이동한다.  
등록을 하면 게시글 목록 화면으로 이동하고, 게시글을 즉시 새로고침한다.  

- 게시글 상세  
![게시글 상세](https://user-images.githubusercontent.com/44915367/176998692-5e9a8a1d-b5e9-4107-898b-50542d1b0ee7.gif)    
![게시글 상세2 스크롤 뷰](https://user-images.githubusercontent.com/44915367/176998626-5ce72282-73bf-4fb9-a964-66d9d97d3e28.gif)  
    
게시글 항목을 누르면 게시글 상세 페이지로 이동한다.  
게시글이 본인이 작성한 거면 수정과 삭제 버튼이 표시된다.  
또한 TextView를 ScrollView로 감싸서 긴 글을 커버할 수 있게 했다.  
아래쪽에는 댓글을 표시하는 Fragment가 위치해 있다.  
    
- 게시글 수정  
![게시글 수정](https://user-images.githubusercontent.com/44915367/176998767-eafbb470-090e-4272-8e7d-27a4dac30c46.gif)  
    
게시글은 본인만 수정할 수 있다.  
    
- 게시글 삭제  
![게시글 삭제](https://user-images.githubusercontent.com/44915367/176998818-cd09a73a-b2d5-4924-90d6-357b2bebe73b.gif)  
    
게시글은 본인만 삭제할 수 있다.  
AlertDialog를 통해 한번 물어보고 삭제한다.  
   
- 게시글 검색 화면 이동  
![게시글 검색 1검색 화면 이동](https://user-images.githubusercontent.com/44915367/176998871-0e558b14-5ac4-4a6e-9a50-d2b998d14b25.gif)  

Toolbar에 돋보기 모양 버튼을 누르면 검색 화면으로 이동한다.  
처음 이동하면 TextView에 검색에 대한 설명을 보여준다.  
    
- 게시글 검색 실패  
![게시글 검색 2글자 수 부족](https://user-images.githubusercontent.com/44915367/176998948-f8d39db1-f023-4be2-a85c-6cbdcc7131aa.gif)  
    
게시글 검색을 위해선 두 글자 이상이 필요하다.  
두 글자 미만으로 입력 시 Snackbar를 통해 알려준다.  
    
- 게시글 검색 결과 없음  
![게시글 검색 3검색 결과 없음](https://user-images.githubusercontent.com/44915367/176998991-6b7baa8f-5cb7-4b51-9df5-9485aac9eeac.gif)  
    
검색 결과가 없는 경우 TextView에 없다는 설명을 보여준다.  
    
- 게시글 검색 성공  
![게시글 검색 4성공](https://user-images.githubusercontent.com/44915367/176999040-5a87a13e-fee5-4686-a46a-708fa7e55486.gif)  
    
검색에 성공하면 해당 항목을 보여준다.  
여기도 마찬가지로 페이징이 적용되어 있다.  
    
</details>

<details>
<summary>댓글</summary>

- 댓글 등록  
![댓글 등록 1댓글 등록](https://user-images.githubusercontent.com/44915367/176999186-6ee7054c-f372-4891-87d4-f60abf2633f0.gif)  

댓글을 등록하면 현재 게시글을 즉시 다시 불러온다.  
댓글 목록도 마찬가지로 RecyclerView로 구성되어 있다.  
    
- 댓글 삭제  
![댓글 삭제](https://user-images.githubusercontent.com/44915367/176999230-700c97d6-e7a3-4081-8cc7-ae602451bc5d.gif)  
    
본인의 댓글만 삭제 버튼이 보인다.  
AlertDialog를 통해 한번 물어보고 삭제한다.
    
</details>

<details>
<summary>좋아요</summary>

- 좋아요 하기  
![좋아요](https://user-images.githubusercontent.com/44915367/176999288-a5d6bbe7-5557-42aa-901f-8922a8ae942f.gif)  
    
빨간색 엄지 버튼을 클릭하면 AlertDialog를 통해 한번 물어보고 좋아요를 1개 올린다.  
한 게시글 당 한 번씩만 가능하다.  
두 번부터는 Snackbar를 통해 안된다고 알려준다.  

</details>

---
 
## 7. 후기 및 회고  
백엔드 쪽은 대학교 2학년 때 PHP 조금 끄적여본 게 다라 걱정을 많이 했었다.  
인프런에서 유료 강의도 사서 듣고, 무료 강의는 무료 강의대로 듣고, 근데 별로 돈이 아깝지는 않다.  
물론 강의가 다 Java 베이스라 처음에는 좀 헷갈렸는데 계속 듣다 보니 금방 익숙해지는 듯했다.  
Spring Boot는 참 어려운 친구인 것 같다.  
물론 쉽게 쓰라고 개발자가 할 일 프레임워크가 다 해 주지만, 제공해주는 기능이 너무 많아 문제가 생겨 검색해도 다 제각각이었다.  
그래도 생각보다는 금방 익숙해져서 서버는 금방 만들었던 것 같다.  
코드부터가 클라이언트보다 한참 적기도 하고.

<br/> 

Spring Boot건 안드로이드건 다 코딩이라 어떻게든 해결은 봤는데 DB쪽이 정말 어려웠다.  
Spring Data JPA나 Hibernate가 다 해줘서 내가 SQL 문 작성할 건 없었지만 외래키 개념 등장하면서 멘탈이 가루가 됐었다.  
향 관계 양방향 관계 이런 거 말이다...  
DB 뭐 별거 있겠나 이런 생각 하던 내가 부끄럽다. 괜히 DB 개발자가 따로 있는 게 아닌가 보다.  
근데 현업에서는 외래키 안 쓴다던데 정말 그런가? 뭐 가보면 알겠지.
    
<br/> 
    
테스트 코드 작성법도 어느 정도 알았다.  
살면서 테스트 코드라는 것을 처음 작성해봤는데 많이 어려운 듯하다.  
서버 개발 말미엔 테스트 코드 작성을 거의 포기했었다.  
요즘에는 아예 테스트 코드를 먼저 작성해버리는 TDD라는 방법으로도 개발한다고 하니 테스트 관련 공부 좀 해야겠다.
    
<br/> 
    
서버 이야기는 이쯤에서 줄이도록 하고, 클라이언트에 대해 할 말이 참 많다.  
클라이언트는 안드로이드를 택했었는데 2가지 이유가 있다.  
첫 번째, 물론 템플릿 엔진이 다 해주겠지만 난 웹 프론트엔드 기술을 잘 몰랐다. HTML, CSS, JavaScript, React, vue.JS, 등등 이름만 알고 있다.  
여기서 백엔드 공부해가지고 프로젝트 진행하는데, 프론트엔드까지 공부하긴 어려울 것 같았다.  
두 번째, 근데 난 학교에서 안드로이드 프로그래밍을 많이 들었고 희망 진로가 안드로이드 개발자다.  
가끔 구인·구직 사이트들을 아이쇼핑 하는데, 안드로이드 개발자 자격 요건에 REST API 관련 지식을 요구하는 곳이 많다. 그래서 그 둘을 연계해서 진행했다.  
Coroutine이야 Retrofit 쓰니까 당연히 비동기 통신 해야 해서 쓰는 거고, MVVM패턴에 익숙해지는 게 목표 중 하나였다.  
근데 잘 적용 된 건지 모르겠다. View와 ViewModel은 n:1 관계라고 하던데 좋아요 때문에 ViewModel이 2개 있는 곳도 있다.  
또한 RecyclerView에 아이템이 변경됐다고 알려주는 부분을 View에서 하고 있는데 이래도 되나 모르겠다.  
클릭 이벤트 때문에 View에서 adapter 객체를 가지고 있긴 해야 하는데 말이지...  
뭐 이러니저러니 해도 실력이 늘어난 기분이 든다. AAC ViewModel과 MVVM패턴에서의 ViewModel이 다르다는 것도 이번 프로젝트 안 해봤으면 몰랐을 것이다.  
DataBinding을 못써본 게 아쉽긴 하다.  
여러 명이 동시 사용하라고 만든 게시판 특성상 화면의 보이는 값을 막 바꿔버리면 나중에 로딩했을 때 달라질까 봐 일일이 새로고침하게 했다.  
    
<br/>  
   
마지막으로 앞으로 이렇게 서버-클라이언트를 한꺼번에 개발할 날이 올지는 모르겠는데, 서버 수정하고 재시작하는 거를 잊지 말아야겠다.  
서버 재시작 안 하고 클라이언트에서 테스트해서 삽질한 게 한두 번이 아니다.

---

## 진행 기간
2022.06.17 ~ 2022.07.02
