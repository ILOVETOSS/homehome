package com.example.daejin1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.daejin1.databinding.ActivityMysoulBinding
import org.json.JSONArray

class MysoulActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMysoulBinding
    private val TAG = "SOUL_LOG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMysoulBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.groupFirst.visibility = View.VISIBLE
        binding.groupSecond.visibility = View.GONE

        val year = intent.getIntExtra("YEAR", 2026)
        val month = intent.getIntExtra("MONTH", 4)
        val day = intent.getIntExtra("DAY", 1)

        val soulNum = calculateSoulNumber(year, month, day)
        setupCardData(soulNum, year, month, day)

        // 4. 5초(5000ms) 대기 후 애니메이션 실행
        Handler(Looper.getMainLooper()).postDelayed({
            startSoulAnimation()
        }, 5000)

        binding.imageView28.setOnClickListener { finish() }
    }

    private fun calculateSoulNumber(y: Int, m: Int, d: Int): Int {
        var total = y + m + d
        while (total >= 10) {
            total = total.toString().map { it.digitToInt() }.sum()
        }
        return total
    }

    private fun setupCardData(num: Int, y: Int, m: Int, d: Int) {
        try {
            val jsonString = assets.open("soul_cards_data.json").bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                if (obj.getInt("number") == num) {
                    val name = obj.optString("name", "Unknown")
                    val desc = obj.optString("storytelling", "설명이 없습니다.")
                    val imgName = obj.optString("image", "").replace(".png", "")
                    val resId = resources.getIdentifier(imgName, "drawable", packageName)

                    binding.apply {
                        textViewFirst.text = name
                        if (resId != 0) {
                            imageView24First.setImageResource(resId)
                            imageView26.setImageResource(resId)
                        }
                        textView32.text = "${y}년 ${m}월 ${d}일생,"
                        textView36.text = "${name} 입니다."
                        textView37.text = desc
                    }
                    break
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "데이터 세팅 중 오류: ${e.message}")
        }
    }

    private fun startSoulAnimation() {
        // [추가] 두 번째 화면의 카드 위치(imageView26) 좌표 계산
        // 이동해야 할 거리 = (목적지 좌표 - 현재 좌표)
        val targetX = binding.imageView26.x - binding.imageView24First.x
        val targetY = binding.imageView26.y - binding.imageView24First.y

        // 크기 비율 계산 (첫 번째 카드를 두 번째 카드 크기에 맞춤)
        val targetScale = binding.imageView26.width.toFloat() / binding.imageView24First.width.toFloat()

        // 1. 첫 번째 카드 애니메이션: 돌면서 목적지로 빨려 들어감
        binding.imageView24First.animate()
            .rotationY(720f)        // 두 바퀴 회전
            .translationX(targetX)  // X축 이동
            .translationY(targetY)  // Y축 이동
            .scaleX(targetScale)    // 크기 축소
            .scaleY(targetScale)    // 크기 축소
            .setDuration(2000)      // 2초 동안 진행
            .withEndAction {
                // 이동 완료 후 그룹 교체
                binding.groupFirst.visibility = View.GONE
                binding.groupSecond.apply {
                    visibility = View.VISIBLE
                    alpha = 0f
                    animate().alpha(1f).setDuration(800).start()
                }
            }
            .start()

        // 2. 첫 화면의 다른 요소들(텍스트, 로고)은 서서히 사라짐
        binding.textViewFirst.animate()
            .alpha(0f)
            .setDuration(1000)
            .start()

        binding.imageView3First.animate()
            .alpha(0f)
            .setDuration(1000)
            .start()
    }
}