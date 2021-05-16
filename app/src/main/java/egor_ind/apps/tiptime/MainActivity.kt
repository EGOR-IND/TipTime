package egor_ind.apps.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}