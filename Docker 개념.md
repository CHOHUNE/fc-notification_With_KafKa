# 도커 
- 컨테이너 (가상화) 프로그램
- 하나의 배포 서버에서 여러 소프트웨어를 운영
  - 격리 환경
- 컨테이너를 사용한 빠른 배포 
- 개발, 테스트, 프로덕션이 동일한 일관성 있는 환경 제공

### 도커 이미지
- 컨테이너 생성 지침이 포함된 읽기 전용 템플릿
- 이미지의 특정 버전을 저장하고 관리
- 애플리케이션에 필요한 라이브러리와 종속성 포함

### 도커 컨테이너
- 도커 이미지를 실행해 생성된 인스턴스
  - 격리된 환경 
  - 빠른 시작 : VM 보다 빠른 시작

### Docker - Container Port, Host Port 
`docker run -e MYSQL_ROOT_PASSWORD=1 -p 80:3306 --name test.mysql mysql:latest`

`docker run -p 55000:27017 --name notificationMongomongo:5.0`
> 도커를설치한 호스트(로컬 PC)의 55000번 포트를, 컨테이너의 27017번 포트(MongoDB의 고정된 포트)에 연결한다는 뜻
> 55000번 포트는 Testcontainers가 MongoDB Docker 컨테이너를 실행하면서 random 지정한 포트다.





