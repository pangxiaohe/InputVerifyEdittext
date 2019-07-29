package com.study.inputverifyedittext

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.study.inputverifyedittext.databinding.ViewVerificationBinding

/**
 * 验证码输入 View
 * Created by hexiaopang on 2019-07-29 14:28.
 */
class VerificationCodeView : FrameLayout {
    private lateinit var binding: ViewVerificationBinding
    //用于存储每个输入框
    private val listEdit = mutableListOf<EditText>()
    //使用此控件，必须关心输入结果
    private var inputListener: OnInputListener? = null

    constructor(context: Context) : super(context) {
        // do nothing
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // do nothing
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defSyle: Int) : super(context, attrs, defSyle) {
        // do nothing
        initView()
    }

    private fun initView() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_verification, null, false)
        addView(binding.root)
        listEdit.add(binding.firstEt)
        listEdit.add(binding.secondEt)
        listEdit.add(binding.thirdEt)
        listEdit.add(binding.fourthEt)
        listEdit.add(binding.fifthEt)
        listEdit.add(binding.sixthEt)
    }

    //给每个输入框添加输入事件监听，以及删除键监听
    private fun initTextChangeListener() {
        listEdit.forEachIndexed { index, value ->
            value.addTextChangedListener(MyTextWatcher(index, listEdit, inputListener!!))
            value.setOnKeyListener(MyOnKeyListener(index, listEdit))
        }
    }

    class MyOnKeyListener(
        private val index: Int,
        private val listEdit: MutableList<EditText>
    ) : OnKeyListener {
        override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
            ////删除键按下以及抬起都会走入这个回调，我们只在按下时候输入框为空的时候做特殊处理
            if (p1 == KeyEvent.KEYCODE_DEL && p2?.action == KeyEvent.ACTION_DOWN && listEdit[index].text.toString().isBlank() && index > 0) {
                //如果当前输入框不是最后一个，且当前被删除的输入框本来就是空的，那么会让这个输入框前面的那个输入框也响应删除事件
                listEdit[index - 1].setText("")
                listEdit[index - 1].requestFocus()
            }
            return false
        }
    }

    class MyTextWatcher(
        private val index: Int,
        private val listEdit: MutableList<EditText>,
        private val inputListener: OnInputListener
    ) : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            p0?.let {
                if (it.toString().isEmpty()) {
                    return
                }
                //任意一个输入框输入完成后，只要list中有了6个元素，则会触发输入完成的接口回调
                if (listEdit.filter { li -> !li.text.toString().isBlank() }.size == 6) {
                    inputListener.onInputOver()
                } else {
                    //如果当前是非空输入，且当前不是最后一个，且当前下一个没有输入，那么会让下一个自动获取焦点
                    if (index != listEdit.size - 1 && listEdit[index + 1].text.toString().isEmpty()) {
                        listEdit[index + 1].requestFocus()
                    }
                }
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }
    }

    //设置输入相关的监听
    fun setOnInputListener(inputListener: OnInputListener) {
        this.inputListener = inputListener
        initTextChangeListener()
    }

    //获取输入内容
    fun getInputContent(): String {
        val stringBuffer = StringBuffer()
        listEdit.forEach {
            stringBuffer.append(it.text.toString())
        }
        return stringBuffer.toString()
    }

    //输入事件相关监听
    interface OnInputListener {
        //输入完成回调
        fun onInputOver()
    }
}