package rhymezxcode.droassessment.Adapters

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import rhymezxcode.droassessment.Activities.product_details
import rhymezxcode.droassessment.Models.Product
import rhymezxcode.droassessment.R
import java.util.*
import kotlin.collections.ArrayList

class view_products_adapter() : RecyclerView.Adapter<view_products_adapter.product_holder>(),
    Filterable {
    var Products: MutableList<Product> = ArrayList()
    var Temp: MutableList<Product> = ArrayList()
    var activity: Activity? = Activity()

    fun setProducts(activity: Activity, Products: MutableList<Product>) {
        this.Temp = Products
        this.Products = Products
        this.activity = activity
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return Products.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): product_holder {
        val listItem = LayoutInflater.from(parent.context).inflate(R.layout.product, parent, false)
        return product_holder(listItem, Products)
    }

    override fun onBindViewHolder(holder: product_holder, position: Int) {
        val eachProduct = Products[position]
        val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)
        Glide.with(activity!!.applicationContext)
            .load(eachProduct.productImage)
            .apply(requestOptions)
            .into(holder.productImage)

        holder.productName.text = eachProduct.productName
        holder.productNameTwo.text = eachProduct.productName
        holder.productGram.text = eachProduct.productGram
        holder.priceTag.text = "\u20a6" + eachProduct.priceTag
    }


    inner class product_holder(itemView: View, data: MutableList<Product>) :
        RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productNameTwo: TextView = itemView.findViewById(R.id.product_name_two)
        val productGram: TextView = itemView.findViewById(R.id.product_gram)
        val priceTag: TextView = itemView.findViewById(R.id.price_tag)
        var bundle: Bundle = Bundle()


        init {
//            bundle.putString("productImage", productImage.drawable.toString())

            itemView.setOnClickListener {
                val position = adapterPosition
                val products = data[position]
                bundle.putString("productImage", products.productImage)
                bundle.putString("productName", products.productName)
                bundle.putString("productDescription", products.productDescription)
                bundle.putString("productGram", products.productGram)
                bundle.putString("priceTag", products.priceTag)
                bundle.putString("productId", products.productId)
                val intent = Intent(activity!!.applicationContext, product_details::class.java)
                intent.putExtras(bundle)
                activity!!.applicationContext.startActivity(intent)
            }
        }


    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
                if (!charSearch.isEmpty()) {
                    val resultList = ArrayList<Product>()
                    for (row in Products) {
                        if (row.productName.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    Products = resultList
                }else{
                    Products = Temp
                }
                val filterResults = FilterResults()
                filterResults.values = Products
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                Products = p1?.values as MutableList<Product>
                notifyDataSetChanged()
            }
        }
    }
}