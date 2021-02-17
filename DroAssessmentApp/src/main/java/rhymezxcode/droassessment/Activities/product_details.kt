package rhymezxcode.droassessment.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.activity_product_details.back
import kotlinx.android.synthetic.main.activity_view_products.*
import rhymezxcode.droassessment.DbProvider.ProductViewModel
import rhymezxcode.droassessment.Models.Bag
import rhymezxcode.droassessment.R
import rhymezxcode.droassessment.Util.SPmanager
import rhymezxcode.droassessment.Util.dialog
import rhymezxcode.droassessment.Util.products

class product_details : AppCompatActivity() {
    var back_pressed: Long? = 0
    var activity = this@product_details
    lateinit var bundle: Bundle
    lateinit var number_of_product: TextView
    var increment: Int = 1;
    private var name: String? = null
    lateinit var productViewModel: ProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        number_of_product = findViewById(R.id.number_of_products)
        bundle = intent.extras!!

        if (bundle != null) {
            val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)
            Glide.with(activity!!.applicationContext)
                .load(bundle.getString("productImage"))
                .apply(requestOptions)
                .into(drug_image)
            name = bundle.getString("productName")
            product_name.text = bundle.getString("productName")
            product_gram.text = bundle.getString("productGram")
            constituents.text = bundle.getString("productName")
            product_id.text = bundle.getString("productId")
            product_name_two.text = bundle.getString("productDescription")
            price.text = "\u20a6"+bundle.getString("priceTag")

        }

        productViewModel.getAllBagProduct()!!.observe(this, object : Observer<List<Bag>> {
            override fun onChanged(bagProducts: List<Bag>?) {
                if (bagProducts!!.size > 0) {
                   bagItems.text = bagProducts.size.toString()
                }
            }
        })

        back.setOnClickListener(View.OnClickListener {
            finish()
        })

        add_product.setOnClickListener(View.OnClickListener {
            increment++
            number_of_products.text = increment.toString()
            price.text = "\u20a6"+(bundle.getString("priceTag")!!.toInt() * increment).toString()

            val preference = activity.getSharedPreferences(SPmanager.preferenceName, Context.MODE_PRIVATE)
            val editor = preference.edit()
            editor.putString(SPmanager.BagIncrement, increment.toString())
            editor.apply()
        })

        minus_product.setOnClickListener(View.OnClickListener {
            increment--
            if (increment < 1) {
                increment = 1
            }
            number_of_products.text = increment.toString()
            price.text = "\u20a6"+(bundle.getString("priceTag")!!.toInt() * increment).toString()

            val preference = activity!!.getSharedPreferences(SPmanager.preferenceName, Context.MODE_PRIVATE)
            val editor = preference.edit()
            editor.putString(SPmanager.BagIncrement, increment.toString())
            editor.apply()
        })

        add_to_bag.setOnClickListener(View.OnClickListener {
            val preference = getSharedPreferences(SPmanager.preferenceName, Context.MODE_PRIVATE)
            val editor = preference.edit()
            val saveProduct = preference.getStringSet(SPmanager.Bag, HashSet<String>())
            val products = products()
            for(product in products.Products){
                if(!saveProduct!!.contains(name)){
                    saveProduct.add(name)
                    editor.putStringSet(SPmanager.Bag, saveProduct)
                    editor.apply()
                    dialog(this, name + " has been added to your bag").showDialog()
                }
            }

        })
    }


    override fun onBackPressed() {
        if (back_pressed!! + 2000 > System.currentTimeMillis()) {
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
            finishAffinity()
        } else {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Touch again to exit",
                Snackbar.LENGTH_SHORT
            ).show()
            back_pressed = System.currentTimeMillis()
        }
    }
}