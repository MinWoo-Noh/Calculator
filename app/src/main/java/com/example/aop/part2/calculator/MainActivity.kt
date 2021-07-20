package com.example.aop.part2.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // TextView 2를 초기화개
    private val expressionTextView: TextView by lazy {
        findViewById<TextView>(R.id.expressionTextView)
    }
    private val resultTextView: TextView by lazy {
        findViewById<TextView>(R.id.resultTextView)
    }

    private var isOperator = false // 숫자와 Operator 압력 구분을 위한 변수
    private var hasOperator = false // Operator 를 한번만 입력 하기위한 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 0~9 까지의 숫자와 %,/,X,-,+ 연산
    fun buttonClicked(v: View) {
        when (v.id) {
            R.id.button0 -> numberButtonClicked("0")
            R.id.button1 -> numberButtonClicked("1")
            R.id.button2 -> numberButtonClicked("2")
            R.id.button3 -> numberButtonClicked("3")
            R.id.button4 -> numberButtonClicked("4")
            R.id.button5 -> numberButtonClicked("5")
            R.id.button6 -> numberButtonClicked("6")
            R.id.button7 -> numberButtonClicked("7")
            R.id.button8 -> numberButtonClicked("8")
            R.id.button9 -> numberButtonClicked("9")
            R.id.buttonPlus -> operatorButton("+")
            R.id.buttonMinus -> operatorButton("-")
            R.id.buttonMulti -> operatorButton("*")
            R.id.buttonDivider -> operatorButton("/")
            R.id.buttonModulo -> operatorButton("%")
        }
    }

    // buttonClicked 메소드에서 숫자가 눌렸을때 처리하는 함수
    private fun numberButtonClicked(number: String) {
        // extView 변수 (TextView 의 text 를 split() 함수를 사용하여 " "(빈 공백)을 구분자로 두어 문자열을 구분한다.)
        val expressionText = expressionTextView.text.split(" ")

        // 숫자를 입력하고 뛰워쓰기를 해 주어야 하기때문에
        if (isOperator) {
            expressionTextView.append(" ")
        }
        isOperator = false
        // expressionText 가 공백이 아니고(&&) 길이가 15 이하일때 true
        if (expressionText.isNotEmpty() && expressionText.last().length >= 15) {
            Toast.makeText(this, "15자리까지만 사용 할 수 있습니다.", Toast.LENGTH_SHORT).show()
            return
            // 숫자가 0이고 expressionText 의 끝자리가 비어있으면
        } else if (expressionText.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0은 제일 앞자리에 올 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        // 정상기능 동작 textView 에 숫자가 입력된다.
        expressionTextView.append(number)

        // TODO resultTextView 에 실시간으로 계산결과를 넣어야 하는 기

    }

    // buttonClicked 메서드에서 연산자 -,+,/,% 가 눌렸을때 처리하는 함수
    private fun operatorButton(operator: String) {
        // 연산자가 먼저 클릭이 돼었을 경우 무시하기위한 예외처리
        if (expressionTextView.text.isEmpty()) {
            return
        }

        when {
            isOperator -> { // isOperator 가 true 인 경우
                val text = expressionTextView.text.toString()
                // dropLast() 함수는 뒤에서부터 n 개의 문자를 제거하는 함수이다.
                // dropLast() 함수를 쓴 이유는 Operator 은 한번만 쓸 수 있기때문이다.
                expressionTextView.text = text.dropLast(1) + operator
            }
            hasOperator -> {
                Toast.makeText(this, "연산자는 한번만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                // isOperator 가 false 이고 hasOperator 가 false 인경우 즉, 숫자만 입력이 되었을때
                expressionTextView.append(" $operator")
            }
        }

        // 연산자일 경우 문자 색을 봐꺼주기 위해 span 이란 기능을 사용한다.
        val ssb = SpannableStringBuilder(expressionTextView.text)
        ssb.setSpan(
                ForegroundColorSpan(getColor(R.color.green)),
                expressionTextView.text.length -1,
                expressionTextView.text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        expressionTextView.text = ssb

        // 연산자가 입력이 되었기 때문에 true
        isOperator = true
        hasOperator = true
    }

    fun resultButtonClicked(v: View) {

    }

    fun clearButtonClicked(v: View) {

    }

    fun historyButtonClicked(v: View) {

    }
}