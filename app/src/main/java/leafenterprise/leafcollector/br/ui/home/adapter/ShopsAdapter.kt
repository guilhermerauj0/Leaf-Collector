package leafenterprise.leafcollector.br.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import leafenterprise.leafcollector.br.databinding.HomeRvShopsItemBinding
import leafenterprise.leafcollector.br.domain.Shop

class ShopsAdapter(
    private val context: Context,
    private val listShops: MutableList<Shop>
): RecyclerView.Adapter<ShopsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HomeRvShopsItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.homeRvitemShop.text = listShops[position].name
        holder.binding.homeRvitemCategory.text = listShops[position].category
    }

    override fun getItemCount(): Int {
        return listShops.size
    }

    inner class ViewHolder (val binding: HomeRvShopsItemBinding) : RecyclerView.ViewHolder(binding.root)

}