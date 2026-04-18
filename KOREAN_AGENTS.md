# Rule
1. 코드를 수정하기 전에 반드시 물어보고 허락을 받을 것
2. Spring 설정을 확인할 때는 `application.yaml`뿐 아니라 활성 profile에 해당하는 `application-*.yaml` 파일도 함께 확인할 것
3. `application-prod.yaml`파일은 File System을 사용해서 접근할 것 (git -rm -r cached 적용해둠)
3. 맨 처음에 `code-create`를 입력하면 코드를 생성 및 수정해 

# Code Style
1. 짧은 코드의 중복을 줄이기 위해서 짧은 메소드를 만들지 말 것
