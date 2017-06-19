# 혼자서 웹 서비스 만들어 보기 

## 개발 환경

- Spring 4.2.x
- Gradle 2.14.x
- Java 1.8
- Hibernate 5.x
- Spring boot 1.3.x
- PostGresSql 9.4.x
- Rythm Template Engine 1.2.x
- IDE : Eclipse


## 개발 목적 

- 비밀

## 개발 기간 

- 1차 런칭 3개월

## 적용하기

### 1. application-dev.propertie
- /config/application-dev.properties.tmpl 파일은 스프링 부트 설정 파일입니다.
- tmpl을 삭제하시고 
- database.server-name = 사용자 server 주소 
- database.database-name = database 이름
- rythm.base-directory = template 폴더가 위치하는 full path
- rythm.mode = dev 
- logging.file = 로그파일이 저장될 장소  

### 2. Data Base 
- Entity를 참조해 만들어 보세요
- 추후 sql파일로 업데이트 예정

### 3. eclipse import 및 빌드
- git으로 받으신 폴더에서 gradlew eclipse 를 실행 (command 창에서 하는 것을 추천드립니다.)
- eclipse에서 import

