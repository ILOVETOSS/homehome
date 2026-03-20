package com.example.daejin1

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.daejin1.databinding.ActivityAgeBinding

class AgeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityAgeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fun validateAndMove() {
            val ageInput = binding.editId.text.toString().trim()
            val ageInt = ageInput.toIntOrNull() // int 로 가져오기

            if (ageInput.isEmpty()) {
                Toast.makeText(this, "나이를 입력해주세요!", Toast.LENGTH_SHORT).show()
                return
            }

            if (ageInt == null) {
                Toast.makeText(this, "숫자만 입력 가능합니다.", Toast.LENGTH_SHORT).show()
                return
            }

            val intent = Intent(this, GenderActivity::class.java)

            intent.putExtras(getIntent())
            intent.putExtra("user_age", ageInt)
            startActivity(intent)
        }

        binding.progressBar2.progress = 2

        binding.editId.setOnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                validateAndMove()
                true
            } else false
        }

        binding.button.setOnClickListener {
            finish() // 단순 뒤로가기
        }
    }
}