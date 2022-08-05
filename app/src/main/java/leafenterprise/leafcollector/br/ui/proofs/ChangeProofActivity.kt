package leafenterprise.leafcollector.br.ui.proofs

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import leafenterprise.leafcollector.br.databinding.ActivityChangeProofBinding

class ChangeProofActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChangeProofBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProofBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recieveDataFromQrCode()
        binding.changeproofReturn.setOnClickListener {
            finish()
        }

    }

    private fun recieveDataFromQrCode() {
        val qrCodeValue : String? = intent.getStringExtra("qrcode_value")
        if (qrCodeValue != null) {
            binding.changeproofTxtPrice.text = qrCodeValue.toString()
        }else{
            Toast.makeText(this, "Valor nulo qrCode", Toast.LENGTH_SHORT).show()
        }
    }
}
