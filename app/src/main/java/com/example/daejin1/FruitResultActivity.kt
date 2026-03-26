package com.example.daejin1

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.daejin1.databinding.ActivityFruitresultBinding

class FruitResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFruitresultBinding
    private var cardStorytelling: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFruitresultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // [수정] 처음 진입 시 보여야 할 것들을 명시적으로 보임 처리
        binding.imageView24.visibility = View.VISIBLE // 카드 이미지
        binding.textView33.visibility = View.VISIBLE // 카드 이름
        binding.button4.visibility = View.VISIBLE    // 결과 확인 버튼
        binding.imageView3.visibility = View.VISIBLE  // 상단 그래픽
        binding.textView.visibility = View.VISIBLE    // 안내 문구

        // 상세 결과 뷰들은 처음엔 숨김
        binding.textView38.visibility = View.GONE
        binding.textView43.visibility = View.GONE
        binding.textView44.visibility = View.GONE
        binding.button6.visibility = View.GONE
        binding.imageView25.visibility = View.GONE // 쓰지 않는 뷰 숨김

        // 데이터 수신
        val name = intent.getStringExtra("NAME") ?: "알 수 없는 카드"
        val imgName = intent.getStringExtra("IMG")?.replace(".png", "") ?: ""
        cardStorytelling = intent.getStringExtra("DESC") ?: "상세 설명이 없습니다."

        binding.textView33.text = name
        if (imgName.isNotEmpty()) {
            val resId = resources.getIdentifier(imgName, "drawable", packageName)
            if (resId != 0) binding.imageView24.setImageResource(resId)
        }

        // 결과 확인 버튼 클릭
        binding.button4.setOnClickListener {
            binding.textView43.text = name
            binding.textView44.text = cardStorytelling
            startResultAnimation(name)

            // 안내 문구와 버튼은 숨김
            binding.textView.visibility = View.GONE
            binding.button4.visibility = View.GONE
        }

        binding.button6.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    private fun startResultAnimation(name: String) {
        binding.apply {
            // 1. 번호와 이름 세팅
            val cardNumber = intent.getIntExtra("SELECTED_CARD_NUMBER", 1)
            textView43.text = "${cardNumber}. ${name}"
            textView44.text = cardStorytelling

            // 2. 불필요한 뷰 즉시 제거
            textView33.visibility = View.GONE
            imageView3.visibility = View.GONE
            textView.visibility = View.GONE
            button4.visibility = View.GONE

            // 3. 결과 뷰 보이기 (페이드인 준비)
            val detailViews = listOf(textView38, textView43, textView44, button6)
            detailViews.forEach {
                it.visibility = View.VISIBLE
                it.alpha = 0f
            }

            val animSet = AnimatorSet()

            // 🚀 [위치 대폭 수정]
            // 이미지2를 보니 더 올라가야 합니다. -1250f 정도로 상단 밀착 시킵니다.
            val moveImgY = ObjectAnimator.ofFloat(imageView24, "translationY", 0f, -1250f)

            // 카드 크기를 이미지1과 비슷하게 살짝 더 줄임 (0.4배)
            val scaleImgX = ObjectAnimator.ofFloat(imageView24, "scaleX", 1f, 0.4f)
            val scaleImgY = ObjectAnimator.ofFloat(imageView24, "scaleY", 1f, 0.4f)

            // 상세 설명들 페이드인
            val fadeAnims = detailViews.map { ObjectAnimator.ofFloat(it, "alpha", 0f, 1f) }

            animSet.apply {
                playTogether(moveImgY, scaleImgX, scaleImgY, *fadeAnims.toTypedArray())
                duration = 1000
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }
        }
    }
}