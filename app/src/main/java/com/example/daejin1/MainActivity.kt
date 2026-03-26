package com.example.daejin1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.daejin1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 1. 바인딩을 클래스 전역변수로 빼야 onResume에서도 쓸 수 있습니다.
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 2. 바인딩 설정 (setContentView 중복 제거)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 달 충전 화면 이동
        binding.imageButton.setOnClickListener {
            val intent = Intent(this, MoonActivity::class.java)
            startActivity(intent)
        }

        // 데일리 타로 소울 이동
        binding.imageButton7.setOnClickListener {
            val intent = Intent(this, SoulActivity::class.java)
            startActivity(intent)
        }

        // 데일리 마스터 (위키백과 열기)
        binding.imageButton8.setOnClickListener {
            val url = "https://ko.wikipedia.org/wiki/타로"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        binding.imageView29.setOnClickListener {
            val intent = Intent(this, loveActivity::class.java)
            startActivity(intent)
        }
        binding.imageView30.setOnClickListener {
            val intent = Intent(this, fruitActivity::class.java)
            startActivity(intent)
        }
    }

    // [핵심] 충전 화면에서 뒤로가기로 돌아왔을 때 숫자를 새로고침합니다.
    override fun onResume() {
        super.onResume()
        // MoonActivity에서 썼던 "MoonData" 파일명과 똑같이 불러옵니다.
        val sharedPref = getSharedPreferences("MoonData", Context.MODE_PRIVATE)
        val currentCount = sharedPref.getInt("moon_count", 10)

        // 메인 화면에서 달 개수를 보여주는 TextView의 ID를 넣으세요.
        // 만약 ID가 다르다면 아래 이름을 수정하세요!
        binding.textView6.text = currentCount.toString()
    }


}