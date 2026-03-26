package com.example.daejin1

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.daejin1.databinding.ActivityOnboardBinding

class OnboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 순차적으로 페이드인을 적용하는 함수
        fun applyFadeIn(view: android.view.View, delay: Long) {
            val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein)
            fadeIn.startOffset = delay
            view.startAnimation(fadeIn)
        }

        // 2. 요구사항에 맞춰 순차적으로 실행 (전체 2초 이내 시작)
        // 달과 구름 (가장 먼저)
        applyFadeIn(binding.imageView, 0)      // 달
        applyFadeIn(binding.imageView4, 200)   // 구름1
        applyFadeIn(binding.imageView5, 400)   // 구름2
        applyFadeIn(binding.imageView6, 600)   // 구름3
        applyFadeIn(binding.imageView7, 800)   // 구름4

        // 로고와 텍스트 (중간)
        applyFadeIn(binding.imageView2, 1000)  // Daily Tarot 로고
        applyFadeIn(binding.imageView3, 1000)  // Daily Tarot 심볼
        applyFadeIn(binding.textView, 1300)    // "운명을 엿볼 시간이에요"

        // 시작하기 버튼 (마지막)
        applyFadeIn(binding.button, 1600)      // 버튼
        applyFadeIn(binding.imageView8, 1600)  // 뒤로가기 버튼


        // 버튼 클릭 시 이동
        binding.button.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
    }
}