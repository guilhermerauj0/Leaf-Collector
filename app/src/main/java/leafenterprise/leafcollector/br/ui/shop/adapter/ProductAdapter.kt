package leafenterprise.leafcollector.br.ui.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import leafenterprise.leafcollector.br.databinding.ProductsItemBinding
import leafenterprise.leafcollector.br.domain.Product

class ProductAdapter(
    var context: Context,
    private val listProduct: MutableList<Product>
    ): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ProductsItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.shopitemTxtProductname.text = listProduct[position].name
        holder.binding.shopitemTxtPrice.text = listProduct[position].price.toString()

        holder.binding.shopitemTxtProductname.setOnClickListener {
            Toast.makeText(context, "Voce clicou no ${listProduct[position].name}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    inner class ViewHolder(val binding: ProductsItemBinding) : RecyclerView.ViewHolder(binding.root)
}