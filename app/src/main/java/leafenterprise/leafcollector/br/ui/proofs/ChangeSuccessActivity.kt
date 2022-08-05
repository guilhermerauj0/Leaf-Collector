package leafenterprise.leafcollector.br.ui.proofs

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import leafenterprise.leafcollector.br.databinding.ActivityChangeSuccessBinding
import leafenterprise.leafcollector.br.ui.home.view.HomeActivity

class ChangeSuccessActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChangeSuccessBinding
    private lateinit var dbReference : DatabaseReference
    private lateinit var mAuth : FirebaseAuth
    private lateinit var userDb : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recieveDataFromQrCode()
        binding.llProof.setOnClickListener{
            sendDataToChangeProof()
        }

        binding.changesuccessReturn.setOnClickListener{
            sendDataToHome()
            finish()
        }
    }

    private fun sendDataToHome() {
        val qrCodeValue : String? = intent.getStringExtra("qrcode_value")
        val value : Int? = qrCodeValue?.let { Integer.parseInt(it) }
        if (value != null) {
            val intent : Intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("qrcode_value", value)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this, "Valor nulo qrCode", Toast.LENGTH_SHORT).show()
        }

    }

    private fun recieveDataFromQrCode() {
        val qrCodeValue : String? = intent.getStringExtra("qrcode_value")
        val oldLeaf : Int = intent.getIntExtra("oldLeaf", 0)
        val valueLeaf : Int? = qrCodeValue?.let { Integer.parseInt(it) }
        if (valueLeaf != null ) {
            addValueToUser(valueLeaf, oldLeaf)
        }else{
            Toast.makeText(this, "Valor nulo qrCode", Toast.LENGTH_SHORT).show()
        }
    }


    private fun addValueToUser(leafValue: Int, oldLeaf : Int) {
        mAuth = FirebaseAuth.getInstance()
        val user : String = mAuth.currentUser!!.uid
        userDb = FirebaseDatabase.getInstance()
        dbReference = userDb.getReference("Users")

        val leafTotal = oldLeaf + leafValue
        dbReference.child(user).child("leafs").setValue(leafTotal)

        dbReference.child(user).get().addOnSuccessListener {
            binding.changesuccessTxtLeaf.text = leafValue.toString()
        }.addOnFailureListener {
            Toast.makeText(this, "Erro na leitura dos seus Leafs", Toast.LENGTH_SHORT).show()
        }

    }

    private fun sendDataToChangeProof() {
        val qrCodeValue : String? = intent.getStringExtra("qrcode_value")
        if (qrCodeValue != null) {
            val intent : Intent = Intent(this, ChangeProofActivity::class.java)
            intent.putExtra("qrcode_value", qrCodeValue)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this, "Valor nulo qrCode", Toast.LENGTH_SHORT).show()
        }
    }
}