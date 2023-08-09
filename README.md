# OTP_Authenticate
An demo of authenticate by OTP and token base. We have two sever: authentication server (run in port 8080) and business logic sever (run in port 9090)
First request to get OTP read from log by authentication server
<img width="468" alt="image" src="https://github.com/DangQuangHuy277/OTP_Authenticate/assets/62865419/3d94a167-e9ba-496b-8c9d-1c553dd49485">
Second request with otp (by set code header to the otp)
<img width="397" alt="image" src="https://github.com/DangQuangHuy277/OTP_Authenticate/assets/62865419/0fc573e6-8890-40e7-be5d-ad2990ab9b0f">
Each request after contain Authorization header (JWT receive by second request) to authenticate and get resource
<img width="402" alt="image" src="https://github.com/DangQuangHuy277/OTP_Authenticate/assets/62865419/41c53e74-544a-4c31-b25f-bf24046dabe6">

