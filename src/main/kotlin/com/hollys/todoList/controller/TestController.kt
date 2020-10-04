package com.hollys.todoList.controller

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.querydsl.jpa.impl.JPAQueryFactory
import io.jsonwebtoken.Jwts
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@Slf4j
@RestController
@RequestMapping("api")
class TestController(
        private var queryFactory: JPAQueryFactory
) {
    @GetMapping("test")
    fun selectLastYearAmount(): String {
        return "Test !!!";
    }

    @GetMapping("cicd_test")
    fun cicdTest(): String {
        return "Success CICD TODO List 2020-07-20 1시"
    }

    @GetMapping("testtt")
    fun testttt(@RequestParam("idToken") idToken: String
    ) {
        print("")

//        Jwts.parser().setSigningKey(appProperties.auth.tokenSecret).parseClaimsJws(idToken)


        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
//        val uid: String = decodedToken.uid
//        print(uid)
        print("")
    }




}