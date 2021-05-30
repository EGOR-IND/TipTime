package egor_ind.apps.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import egor_ind.apps.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tipCalculateBtn.setOnClickListener { calculateTip() }
        binding.servCostEt.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    private fun calculateTip() {
        val costOfService =
            binding.servCostEt.text.toString().toDoubleOrNull()
        if (costOfService == null) {
            binding.tipAmountTxt.text = ""
            binding.servCostEtOutbox.error = "Required!"
            return
        }
        val tipPercent = when (binding.tipPercentRg.checkedRadioButtonId) {
            R.id.twenty_per_tip -> 0.20
            R.id.eighteen_per_tip -> 0.18
            else -> 0.15
        }

        var tipAmount = costOfService*tipPercent
        if (binding.tipRoundUpSwitch.isChecked) {
            tipAmount = ceil(tipAmount)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tipAmount)
        binding.tipAmountTxt.text = getString(R.string.tip_amount, formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}