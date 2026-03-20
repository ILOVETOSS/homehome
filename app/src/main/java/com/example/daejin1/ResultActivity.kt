package com.example.daejin1

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.daejin1.databinding.ActivityAgeBinding
import com.example.daejin1.databinding.ActivityDayBinding
import com.example.daejin1.databinding.ActivityGenderBinding
import com.example.daejin1.databinding.ActivityInfoBinding
import com.example.daejin1.databinding.ActivityResultBinding
import com.example.daejin1.databinding.ActivityTimeBinding

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("user_name") ?: "이름 없음"
        val age = intent.getIntExtra("user_age", 0)
        val gender = intent.getStringExtra("user_gender") ?: "미선택"
        val birthday = intent.getStringExtra("user_birthday") ?: "날짜 모름"
        val time = intent.getStringExtra("selectedTime") ?: "시간 모름"

        binding.textView2.text = "이름: $name"
        binding.textView3.text = "나이: $age"
        binding.textView4.text = "생일: $birthday"

        if (gender == "남성") {
            // 남성 아이콘으로 변경
            binding.imageView10.setImageResource(R.drawable.ic_female)
        } else {
            // 여성 아이콘으로 변경 (여성 아이콘도 똑같이 만드셨다면)
            binding.imageView10.setImageResource(R.drawable.ic_male1)
        }

        binding.textView5.text = "시간: $time"

        binding.button3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}