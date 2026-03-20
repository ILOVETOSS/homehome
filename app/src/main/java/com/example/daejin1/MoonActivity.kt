package com.example.daejin1

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.daejin1.databinding.ActivityMoonBinding

class MoonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. 바인딩 설정 (setContentView(R.layout.activity_main)은 삭제해야 합니다)
        val binding = ActivityMoonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. 저장소 불러오기 및 초기값 설정
        val sharedPref = getSharedPreferences("MoonData", Context.MODE_PRIVATE)
        // 변수를 리스너 바깥에 두어야 숫자가 계속 쌓입니다.
        var currentCount = sharedPref.getInt("moon_count", 10)

        // 시작할 때 현재 개수 보여주기
        binding.mooncout.text = currentCount.toString()

        // 3. 클릭 리스너 설정 (누적 로직 반영)

        binding.constraintLayout2.setOnClickListener {
            currentCount += 25 // 기존 값에 25 더하기
            sharedPref.edit().putInt("moon_count", currentCount).apply() // 저장
            binding.mooncout.text = currentCount.toString() // 화면 즉시 업데이트
            Toast.makeText(this, "25개 구매 완료", Toast.LENGTH_SHORT).show()
        }

        binding.constraintLayout3.setOnClickListener {
            currentCount += 50 // 기존 값에 50 더하기
            sharedPref.edit().putInt("moon_count", currentCount).apply()
            binding.mooncout.text = currentCount.toString()
            Toast.makeText(this, "50개 구매 완료", Toast.LENGTH_SHORT).show()
        }

        binding.constraintLayout4.setOnClickListener {
            currentCount += 75
            sharedPref.edit().putInt("moon_count", currentCount).apply()
            binding.mooncout.text = currentCount.toString()
            Toast.makeText(this, "75개 구매 완료", Toast.LENGTH_SHORT).show()
        }

        binding.constraintLayout5.setOnClickListener {
            currentCount += 100
            sharedPref.edit().putInt("moon_count", currentCount).apply()
            binding.mooncout.text = currentCount.toString()
            Toast.makeText(this, "100개 구매 완료", Toast.LENGTH_SHORT).show()
        }

        binding.constraintLayout6.setOnClickListener {
            currentCount += 200
            sharedPref.edit().putInt("moon_count", currentCount).apply()
            binding.mooncout.text = currentCount.toString()
            Toast.makeText(this, "200개 구매 완료", Toast.LENGTH_SHORT).show()
        }

        binding.constraintLayout7.setOnClickListener {
            currentCount += 350
            sharedPref.edit().putInt("moon_count", currentCount).apply()
            binding.mooncout.text = currentCount.toString()
            Toast.makeText(this, "350개 구매 완료", Toast.LENGTH_SHORT).show()
        }

        // 뒤로가기 버튼 (XML에 id가 imageButton4로 되어있다면 추가하세요)
        // binding.imageButton4.setOnClickListener { finish() }
        binding.imageView21.setOnClickListener { finish() }
    }
}