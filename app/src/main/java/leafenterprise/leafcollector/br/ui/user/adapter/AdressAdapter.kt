package leafenterprise.leafcollector.br.ui.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import leafenterprise.leafcollector.br.databinding.RvAdressesItemBinding
import leafenterprise.leafcollector.br.domain.Adress

class AdressAdapter(
    var context: Context,
    private val listAdress: MutableList<Adress>
) : RecyclerView.Adapter<AdressAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvAdressesItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.rvitemAdress.text = listAdress[position].adress
        holder.binding.rvitemDistrict.text = listAdress[position].district
        holder.binding.rvitemNum.text = listAdress[position].num

        holder.binding.adressitemLayout.setOnClickListener {
            Toast.makeText(
                context,
                "Voce clicou no ${listAdress[position].adress}",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    override fun getItemCount(): Int {
        return listAdress.size
    }


    inner class ViewHolder(val binding: RvAdressesItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}