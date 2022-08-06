package leafenterprise.leafcollector.br.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import leafenterprise.leafcollector.br.databinding.RvCartitemsItemBinding
import leafenterprise.leafcollector.br.domain.Product

class CartItemsAdapter(
    private val context: Context,
    private val listCart: MutableList<Product>

) : RecyclerView.Adapter<CartItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvCartitemsItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvitemProductname.text = listCart[position].name
        holder.binding.cartitemPrice.text = listCart[position].price.toString()
        holder.binding.tvitemQuantity.text = listCart[position].quantity.toString()

        holder.binding.imgitemRemovefromcart.setOnClickListener {
            listCart.removeAt(position)
        }
    }

    override fun getItemCount(): Int {
        return listCart.size
    }

    inner class ViewHolder(val binding: RvCartitemsItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}