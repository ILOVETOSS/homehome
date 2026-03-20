package com.example.daejin1

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.daejin1.databinding.ActivityTimeBinding

class TimeActivity : AppCompatActivity() {

    private var isMinuteMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // [수정] 확장 함수를 사용하여 타임피커의 모든 자식 뷰에 리스너 설정
        binding.timepicker.setupRecursiveTouch {
            if (!isMinuteMode) {
                // 첫 번째 터치 업: 시간 선택 완료 -> 분 모드로 진입
                isMinuteMode = true
            } else {
                // 두 번째 터치 업: 분 선택 완료 -> 화면 이동
                val hour = binding.timepicker.hour
                val minute = binding.timepicker.minute
                val selectedTime = String.format("%02d:%02d", hour, minute)

                binding.root.postDelayed({
                    val intent = Intent(this, ResultActivity::class.java).apply {
                        putExtras(getIntent()) // 이전 데이터(이름, 나이 등) 릴레이
                        putExtra("selectedTime", selectedTime)
                    }
                    startActivity(intent)
                }, 400)
            }
        }

        // 이전 버튼 (단순히 finish()가 정석)
        binding.button.setOnClickListener {
            finish()
        }

        // 잘 모르겠어요 버튼
        binding.button2.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtras(getIntent())
                putExtra("selectedTime", "모름")
            }
            startActivity(intent)
        }
    }
    // [개선] 가독성 좋은 확장 함수 스타일 (if문 구조 유지)
    private fun View.setupRecursiveTouch(onUp: () -> Unit) {
        if (this is ViewGroup) {
            for (i in 0 until childCount) {
                getChildAt(i).setupRecursiveTouch(onUp)
            }
        }
        this.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                onUp()
            }
            false // 중요: false를 리턴해야 타임피커 본래 기능이 동작함
        }
    }
}