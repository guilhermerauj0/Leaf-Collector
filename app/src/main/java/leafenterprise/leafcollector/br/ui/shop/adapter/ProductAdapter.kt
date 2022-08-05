package leafenterprise.leafcollector.br.ui.shop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import leafenterprise.leafcollector.br.databinding.ProductsItemBinding
import leafenterprise.leafcollector.br.domain.Product
import leafenterprise.leafcollector.br.ui.ProductActivity

class ProductAdapter(
    var context: Context,
    private val listProduct: MutableList<Product>
    ): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ProductsItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name : String = listProduct[position].name
        val description : String = listProduct[position].description
        val quantity : Int = listProduct[position].quantity
        val price : Int = listProduct[position].price
        val image : String? = listProduct[position].image

        holder.binding.shopitemTxtProductname.text = name
        holder.binding.shopitemTxtPrice.text = price.toString()

        holder.binding.productitemLl.setOnClickListener{
            val intent : Intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("product_name", name)
            intent.putExtra("product_description", description)
            intent.putExtra("product_price", price)
            intent.putExtra("product_image", image)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    inner class ViewHolder(val binding: ProductsItemBinding) : RecyclerView.ViewHolder(binding.root)
}