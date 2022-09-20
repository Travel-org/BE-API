package com.ssu.travel.security.oauth2;

import com.ssu.travel.security.oauth2.dto.requset.EmailPasswordRequest;
import com.ssu.travel.security.oauth2.dto.response.LoginSuccessResponse;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @GetMapping("/v1/oauth2/authorization/kakao")
    public JSONObject kakaoLogin(HttpServletRequest request, @RequestParam("code") String code) {
        String origin = request.getHeader(HttpHeaders.ORIGIN);
        return authService.kakaoAuthentication(origin, code);
    }

    @PostMapping("/v1/login")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공", examples = @Example(
                    @ExampleProperty(value = "{\n" +
                            "\t\"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJ1c2VySWRcIjoxfSIsImlhdCI6MTY1MzU2MDQwNSwiZXhwIjoxNjU0MTY1MjA1fQ.HGC-6YVPo0jeK7W8HLulKP9_srsECJD1giODgihNmxg\"\n" +
                            "}")
            )),
            @ApiResponse(code = 400, message = "잘못된 이메일", examples = @Example(
                    @ExampleProperty(value = "{\n" +
                            "\t\"status\": 400,\n" +
                            "\t\"message\": \"USER RECORD IS NOT FOUND\",\n" +
                            "\t\"exceptionMessage\": \"해당 이메일을 가진 사용자를 찾을 수 없습니다\"\n" +
                            "}")
            )),
            @ApiResponse(code = 401, message = "잘못된 비밀번호", examples = @Example(
                    @ExampleProperty(value = "{\n" +
                            "\t\"status\": 401,\n" +
                            "\t\"message\": \"INVALID PASSWORD\",\n" +
                            "\t\"exceptionMessage\": \"잘못된 비밀번호 입니다.\"\n" +
                            "}")
            ))
    })
    public ResponseEntity<LoginSuccessResponse> login(@RequestBody @Valid EmailPasswordRequest request) {
        LoginSuccessResponse responseDto = authService.login(request);
        return ResponseEntity.ok(responseDto);
    }
}
