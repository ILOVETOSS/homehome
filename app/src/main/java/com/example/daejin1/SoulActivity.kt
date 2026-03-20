package com.example.daejin1

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.daejin1.databinding.ActivitySoulBinding
import java.util.*

class SoulActivity : AppCompatActivity() {

    // 선택된 날짜 저장 (0이면 미선택)
    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivitySoulBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 뒤로가기 버튼 설정
        binding.imageView21.setOnClickListener { finish() }

        // 2. 상단 설명 텍스트 강조 (textView35)
        val description = "소울 넘버는 생년월일의 숫자를 모두 더해\n" +
                "얻는 최종적인 한 자리 숫자로,\n" +
                "당신의 핵심적인 에너지와 삶의 테마를 나타냅니다.\n\n" +
                "이 소울 넘버에 해당하는\n" +
                "메이저 아르카나 타로 카드가 바로 소울 카드이며,\n" +
                "이는 당신의 타고난 성격, 기질,\n" +
                "그리고 삶의 목적을 상징합니다.\n\n" +
                "즉, 소울 넘버는 당신의 영혼의 번호이고,\n" +
                "소울 카드는 그 번호가 의미하는\n" +
                "영혼의 본질을 보여주는 상징인 셈입니다."

        setHighlightText(binding.textView35, description, listOf("소울 넘버", "소울 카드", "영혼의 번호", "영혼의 본질"), Color.parseColor("#E4C176"))

        // 3. [생년월일 선택] 버튼 클릭 시 다이얼로그 팝업 (button4)
        binding.button4.setOnClickListener {
            val cal = Calendar.getInstance()

            // 테마 ID 16973935 를 사용하면 무조건 '굴리는 방식'의 팝업이 뜹니다.
            val datePicker = DatePickerDialog(
                this,
                16973935, // Holo Dark Spinner 테마의 고유 번호 (에러 방지용)
                { _, y, m, d ->
                    selectedYear = y
                    selectedMonth = m + 1
                    selectedDay = d
                    // 선택한 날짜를 버튼 텍스트에 표시
                    binding.button4.text = "${y}년 ${m + 1}월 ${d}일"
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )

            // 미래 날짜 선택 방지
            datePicker.datePicker.maxDate = cal.timeInMillis

            // 배경 투명하게 (테마 적용 시 생기는 흰 사각형 제거)
            datePicker.window?.setBackgroundDrawableResource(android.R.color.transparent)

            datePicker.show() // 버튼을 눌렀을 때만 여기서 팝업이 뜸!
        }

        // 4. [달 10개로 소울찾기] 버튼 클릭 로직 (button5)
        binding.button5.setOnClickListener {
            if (selectedYear == 0) {
                Toast.makeText(this, "생년월일을 먼저 선택해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("MoonData", Context.MODE_PRIVATE)
            var currentMoons = sharedPref.getInt("moon_count", 0)

            if (currentMoons >= 10) {
                currentMoons -= 10
                sharedPref.edit().putInt("moon_count", currentMoons).apply()

                val intent = Intent(this, MysoulActivity::class.java)
                intent.putExtra("YEAR", selectedYear)
                intent.putExtra("MONTH", selectedMonth)
                intent.putExtra("DAY", selectedDay)
                startActivity(intent)
            } else {
                Toast.makeText(this, "달이 부족합니다! (현재: ${currentMoons}개)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setHighlightText(textView: TextView, fullText: String, words: List<String>, color: Int) {
        val builder = SpannableStringBuilder(fullText)
        for (word in words) {
            var start = fullText.indexOf(word)
            while (start != -1) {
                builder.setSpan(ForegroundColorSpan(color), start, start + word.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                start = fullText.indexOf(word, start + word.length)
            }
        }
        textView.text = builder
    }
}