package com.hollys.todoList.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream
import java.io.IOException


@Configuration
class FirebaseAdminConfig {
    @Bean
    @Throws(IOException::class)
    fun createFireBaseApp() {
        val serviceAccount = FileInputStream("src/main/resources/firebase_todo_list.json")

        val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://todo-list-438fd.firebaseio.com")
                .build()

        FirebaseApp.initializeApp(options)
    }


}