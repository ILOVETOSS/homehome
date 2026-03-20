package com.example.daejin1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.daejin1.databinding.ActivityDayBinding


class DayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityDayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "$year-${month + 1}-$dayOfMonth"

            val intent = Intent(this, TimeActivity::class.java)
            // 이전 데이터(이름, 나이, 성별) 복사
            intent.putExtras(getIntent())
            // 생일 데이터 추가 (ResultActivity가 기다리는 키 'user_birthday'로 맞춤)
            intent.putExtra("user_birthday", selectedDate)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            finish() // 뒤로 가기
        }
    }
}