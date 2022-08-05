package leafenterprise.leafcollector.br.ui.proofs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import leafenterprise.leafcollector.br.databinding.ActivityBuyProofBinding


class BuyProofActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBuyProofBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBuyProofBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}