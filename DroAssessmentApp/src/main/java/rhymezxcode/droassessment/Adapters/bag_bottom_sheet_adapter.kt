package rhymezxcode.droassessment.Adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import rhymezxcode.droassessment.Adapters.bag_bottom_sheet_adapter.bag_holder
import rhymezxcode.droassessment.DbProvider.ProductViewModel
import rhymezxcode.droassessment.Models.Bag
import rhymezxcode.droassessment.Models.Product
import rhymezxcode.droassessment.R
import rhymezxcode.droassessment.Util.SPmanager
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class bag_bottom_sheet_adapter : RecyclerView.Adapter<bag_holder>() {
    var bagItems: MutableList<Bag> = ArrayList()
    var activity: Activity? = Activity()
    lateinit var productViewModel: ProductViewModel

    fun setBagProducts(activity: Activity, bagItems: MutableList<Bag>, productViewModel: ProductViewModel) {
        this.bagItems = bagItems
        this.activity = activity
        this.productViewModel = productViewModel
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return bagItems.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): bag_holder {
        val listItem =
            LayoutInflater.from(parent.context).inflate(R.layout.bag_product, parent, false)
        return bag_holder(listItem)
    }

    override fun onBindViewHolder(holder: bag_holder, position: Int) {
        val eachProduct = bagItems[position]
        val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)
        Glide.with(activity!!.applicationContext)
            .load(eachProduct.productImage)
            .apply(requestOptions)
            .into(holder.productImage)
        holder.productName.text = eachProduct.productName
        holder.productTimes.text = "1X"
        holder.productGram.text = eachProduct.productGram
        holder.productPrice.text = "\u20a6"+eachProduct.priceTag
    }


    inner class bag_holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productTimes: TextView = itemView.findViewById(R.id.product_name_two)
        val productGram: TextView = itemView.findViewById(R.id.product_gram)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val delete: ImageView = itemView.findViewById(R.id.delete_bag)
        val minus: TextView = itemView.findViewById(R.id.minus_product)
        val numberOfProducts: TextView = itemView.findViewById(R.id.number_of_products)
        val add: TextView = itemView.findViewById(R.id.add_product)
        var isShowing: Boolean = false
        var increment: Int = 1;


        init {
            itemView.setOnClickListener {
                val eachProduct = bagItems[position]
                if(isShowing){
                    delete.visibility = View.GONE
                    minus.visibility = View.GONE
                    numberOfProducts.visibility = View.GONE
                    add.visibility = View.GONE
                }else{
                    delete.visibility = View.VISIBLE
                    minus.visibility = View.VISIBLE
                    numberOfProducts.visibility = View.VISIBLE
                    add.visibility = View.VISIBLE
                    delete.setOnClickListener(View.OnClickListener {
                        removeAt(position)
                    })
                    minus.setOnClickListener(View.OnClickListener {

                        increment--
                        if (increment < 1) {
                            increment = 1
                        }
                        numberOfProducts.text = increment.toString()
                        productTimes.text = increment.toString()+"X"
                        productPrice.text = "\u20a6"+(eachProduct.priceTag.toInt() * increment).toString()
                    })
                    add.setOnClickListener(View.OnClickListener {
                        increment++
                        numberOfProducts.text = increment.toString()
                        productTimes.text = increment.toString()+"X"
                        productPrice.text = "\u20a6"+(eachProduct.priceTag.toInt() * increment).toString()
                    })
                }
                isShowing = !isShowing

            }
        }

        fun removeAt(position: Int){
            val eachProduct = bagItems[position]
            val preference = activity!!.getSharedPreferences(SPmanager.preferenceName, Context.MODE_PRIVATE)
            val editor = preference.edit()
            val saveProduct = preference.getStringSet(SPmanager.Bag, HashSet<String>())

            saveProduct!!.remove(eachProduct.productName)
            editor.remove(SPmanager.Bag)
            editor.apply()
            editor.putStringSet(SPmanager.Bag, saveProduct)
            editor.apply()
            bagItems.removeAt(position)
            val bag = Bag(
                eachProduct.productImage,
                eachProduct.productName,
                eachProduct.productDescription,
                eachProduct.productGram,
                eachProduct.priceTag,
                eachProduct.productId
            )
            deleteBag{
                productViewModel.deleteBagProduct(bag)
            }.execute()
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, bagItems.size)
            bag_bottom_sheet_adapter().notifyDataSetChanged()
        }


    }

    @SuppressLint("StaticFieldLeak")
    class deleteBag(val handler: () -> Unit): AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg p0: Void?): Void? {
            handler()
            return null
        }
    }
}