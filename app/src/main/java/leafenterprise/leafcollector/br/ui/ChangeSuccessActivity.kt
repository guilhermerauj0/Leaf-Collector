package leafenterprise.leafcollector.br.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import leafenterprise.leafcollector.br.R
import leafenterprise.leafcollector.br.databinding.ActivityChangeSuccessBinding

class ChangeSuccessActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChangeSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.llProof.setOnClickListener{
            openProof()
        }

        // TODO "Implementar emissão de comprovante"
        // TODO "Implementar receber os dados da leitura do QRCODE"
    }

    private fun openProof() {
        val intent: Intent = Intent(this, ChangeProofActivity::class.java)
        startActivity(intent)
    }
}