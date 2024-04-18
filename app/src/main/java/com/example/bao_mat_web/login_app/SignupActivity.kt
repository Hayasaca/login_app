package com.example.bao_mat_web.login_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bao_mat_web.login_app.databinding.ActivitySignupBinding
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.signupButton.setOnClickListener {
            val signupUsername = binding.signupUsername.text.toString()
            val signupPassword = binding.signupPassword.text.toString()
            signupDatabase(signupUsername, signupPassword)
        }

        binding.loginRedirect.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validatePassword(password: String): Boolean {
        val regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[?/!@#\$%^&*():;\"'<>,.~`]).{8,}$".toRegex()
        return password.matches(regex)
    }

    private fun signupDatabase(username: String, password: String) {
        if (validatePassword(password)) {
            val insertRowId = databaseHelper.insertUser(username, databaseHelper.hashPassword(password))
            if (insertRowId != -1L){
                Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Password must have 8 or more characters, at least 1 uppercase/lowercase letter, 1 special character and 1 digit", Toast.LENGTH_LONG).show()
        }
    }
}