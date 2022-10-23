# 전공기초프로젝트2 A10 - 도서 관리 프로그램
* 건국대학교 2022-2학기 전공기초프로젝트2 수업의 팀프로젝트입니다.
## 실행
### 환경
* idk 11 환경에서 작성한 프로그램입니다. 실행 시 jdk 버전을 11 이상으로 설정해주세요.
### 빌드 및 실행
* gradle로 빌드 후 실행할 수 있습니다.

``` shell
gradlew clean build
java -jar build/libs/ICEP2-2022-1.0-SNAPSHOT.jar
```

* 어노테이션 기반 코드 생성 라이브러리인 Lombok 1.18.24 버전이 사용되었습니다.
* IDE 환경에서 코드를 돌려보기 위해서는 몇 가지 설정이 추가로 필요합니다. 설명은 인텔리제이 기준으로 드립니다.
1. 인텔리제이 플러그인 탭에서 Lombok 인스톨
2. `File -> Settings (맥은 ⌘ + ,) -> Build, Execution, Deployment -> Compiler -> Annotation Processors 탭의 Enable annotation processing` 체크박스 클릭 후 `apply`
3. Gradle 새로고침

## 실행 가이드
프로그램 실행 후 관리자로 로그인하기 위해서는 메인 프롬프트에서 `$ login admin admin`  을 입력해주세요.

## Reference
[IntelliJLombok(롬복) 라이브러리 설치하기](https://hajoung56.tistory.com/14)