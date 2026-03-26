// C:/Kotlin_study/daejin12/app/src/main/java/com/example/daejin1/loveActivity.kt 수정

package com.example.daejin1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.daejin1.databinding.ActivityLoveBinding
import com.example.daejin1.databinding.ItemCardBinding
import org.json.JSONArray
import kotlin.math.abs

class loveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. JSON에서 전체 카드 데이터를 미리 불러와서 섞습니다.
        val jsonString = assets.open("loves_tarot_cards_data.json").bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)
        val allCards = mutableListOf<CardData>()

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            allCards.add(CardData(
                obj.getString("name"),
                // JSON 키가 'img'인지 'image'인지 확인 필요. 여기선 'img'로 작성됨.
                obj.optString("img", obj.optString("image", "")).replace(".png", ""),
                obj.optString("storytelling", obj.optString("description", "설명이 없습니다."))
            ))
        }
        allCards.shuffle()
        val selectedCards = allCards.take(9) // 9장만 선택

        // 2. ViewPager2 설정
        binding.viewPager.apply {
            adapter = Love_Taro2(selectedCards) // 섞인 카드 리스트를 어댑터에 전달
            offscreenPageLimit = 8
            setCurrentItem(4, false)

            setPageTransformer { page, position ->
                val absPos = abs(position)
                page.translationZ = position * 100f
                page.translationX = -position * (page.width * 0.85f)
                page.rotation = position * 8f
                page.translationY = ((absPos * absPos) * 15f) + 150f
            }
        }
    }

    data class CardData(val name: String, val imgName: String, val description: String)

    inner class Love_Taro2(private val items: List<CardData>) : RecyclerView.Adapter<Love_Taro2.CardViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
            val itemBinding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CardViewHolder(itemBinding)
        }

        override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
            val card = items[position]

            holder.binding.cardImageView.setOnClickListener {
                // 클릭 시 이미 가지고 있는 card 데이터를 바로 넘깁니다.
                val intent = Intent(this@loveActivity, FruitResultActivity::class.java).apply {
                    putExtra("NAME", card.name)
                    putExtra("IMG", card.imgName)
                    putExtra("DESC", card.description)
                }
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int = items.size

        inner class CardViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)
    }
}