# SSO Login (개인 프로젝트 / 개발기간 2025-04-06 ~)

### 프로젝트 개요
- 네이버의 SAML 2.0 프로세스를 참고하여 만든 SSO 로그인 인증/인가 서비스 프로그램입니다.
- SSO는 SP(Service Provider)로 구성이 되고 SsoServer는 IDP(Identity Provider)로 구성됩니다.
- SAML Response의 서명검증은 X.509인증서를 통해 검증이 되며, SloRequest는 HMACSHA-512로 검증됩니다.
- 비밀번호의 경우 SHA-256을 통해 Digest를 만들고 이후 Credential_salt를 통해 Credential을 만듭니다.