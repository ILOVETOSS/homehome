package com.example.daejin1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.daejin1.databinding.ActivityGenderBinding

class GenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityGenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButton2.setOnClickListener {
            val intent = Intent(this, DayActivity::class.java)

            // 이전 데이터(이름, 나이)를 복사해서 넘김
            intent.putExtras(getIntent())
            // 성별 데이터 추가
            intent.putExtra("user_gender", "남성")
            startActivity(intent)
        }

        binding.imageButton3.setOnClickListener {
            val intent = Intent(this, DayActivity::class.java)
            // 이전 데이터(이름, 나이)를 복사해서 넘김
            intent.putExtras(getIntent())
            // 성별 데이터 추가
            intent.putExtra("user_gender", "여성")
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            finish() // 단순 뒤로가기
        }
    }
}