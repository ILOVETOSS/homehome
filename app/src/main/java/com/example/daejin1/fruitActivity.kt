package com.example.daejin1

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.daejin1.databinding.ActivityFruitBinding
import org.json.JSONArray

class fruitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFruitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. JSON 로드 및 9장 랜덤 추출
        val jsonString = assets.open("fruit_tarot_cards_data.json").bufferedReader().use { it.readText() }
        val allCards = JSONArray(jsonString)
        val selectedIndices = (0 until allCards.length()).shuffled().take(9)

        // 2. 3x3 그리드 설정
        binding.grid3.columnCount = 3
        binding.grid3.useDefaultMargins = false // 시스템 기본 여백 제거

        for (i in selectedIndices) {
            val cardData = allCards.getJSONObject(i)
            val cardView = ImageButton(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 105f, resources.displayMetrics).toInt()
                    height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160f, resources.displayMetrics).toInt()

                    // [해결] 카드 간 간격을 4dp로 좁게 설정
                    setMargins(4, 4, 4, 4)
                }

                setImageResource(R.drawable.tarot_card_back) // 뒷면 이미지
                setBackgroundResource(android.R.color.transparent)

                // [해결] 이미지가 잘리지 않게 FIT_CENTER 설정
                scaleType = ImageView.ScaleType.FIT_CENTER
                adjustViewBounds = true

                // 3. [핵심] 카드 선택 시 결과 화면으로 즉시 이동
                setOnClickListener {
                    val intent = Intent(this@fruitActivity, FruitResultActivity::class.java)

                    // JSON에서 읽어온 데이터를 Intent에 담기
                    intent.putExtra("NAME", cardData.getString("name"))
                    intent.putExtra("IMG", cardData.getString("image"))
                    intent.putExtra("DESC", cardData.getString("storytelling"))

                    startActivity(intent) // 화면 전환
                    // finish() // 다시 선택 화면으로 못 오게 하려면 주석 해제
                }
            }
            binding.grid3.addView(cardView)
        }
    }
}