package com.example.daejin1

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.daejin1.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // [핵심] 유효성 검사 로직을 '함수'로 만듭니다. (중복 방지)
        fun validateAndMove() {

            // 실행되는 시점의 입력값을 가져와야 함 (중요!)
            val name = binding.editId.text.toString().trim()

            // 2 & 4번 요구사항: 이름 미입력 및 글자 수 검사
            if (name.isEmpty()) {
                Toast.makeText(this, "이름은 공백불가능.", Toast.LENGTH_SHORT).show()
            }
            else if (name.length < 2 || name.length > 20) {
                Toast.makeText(this, "이름은 최소 2자, 최대 20자까지 가능하다.", Toast.LENGTH_SHORT).show()
            }
            else {

                // 5번 요구사항: 성공 시 다음 화면 이동

                val intent = Intent(this, AgeActivity::class.java)
                intent.putExtra("user_name", name)
                startActivity(intent)
            }
        }

        // 진행상태 바
        binding.progressBar2.progress = 1


        // 3번 요구사항: 키보드 Enter 버튼 클릭 시 검사
        binding.editId.setOnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                validateAndMove()
                true
            } else false
        }



        // 6번 요구사항: 이전 버튼 클릭 시 온보딩 화면으로 이동
        binding.button.setOnClickListener {
            val intent = Intent(this, OnboardActivity::class.java)
            startActivity(intent)
            finish() // 현재 화면 종료
        }
    }
}