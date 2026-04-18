# Rule
1. Be sure to ask and get permission before modifying the code
2. When checking the Spring setting, check not only the `application.yaml` but also the `application-*.yaml` file corresponding to the active profile
3. `application-prod.yaml` should remain a local file and must not be tracked by Git. If it is already tracked, remove it from the index with `git rm --cached application-prod.yaml`.
4. If you type `code-create` at the beginning, create and modify the code

# Code Style
1. Do not create short methods to reduce duplication of short codes
