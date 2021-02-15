package rhymezxcode.droassessment.Activities

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_product_details.*
import rhymezxcode.droassessment.Models.bag
import rhymezxcode.droassessment.Models.product
import rhymezxcode.droassessment.R

class product_details : AppCompatActivity() {
    var back_pressed: Long? = 0
    var Allproducts = ArrayList<bag>()
    var activity = this@product_details
    lateinit var bundle: Bundle
    lateinit var number_of_product: TextView
    var increment: Int = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        number_of_product = findViewById(R.id.number_of_products)
        bundle = intent.extras!!

        if (bundle != null) {
//            drug_image.setImageDrawable(Drawable.createFromPath(bundle.getString("productImage")))
            product_name.text = bundle.getString("productName")
            product_gram.text = bundle.getString("productGram")
            constituents.text = bundle.getString("productName")
            product_id.text = bundle.getString("productId")
            product_description.text = bundle.getString("productDescription")
            price.text = bundle.getString("priceTag")
            bagItems.text = "5"

        }
        back.setOnClickListener(View.OnClickListener {
           finish()
        })

        add_product.setOnClickListener(View.OnClickListener {
            increment++
            number_of_products.text = increment.toString()
        })
        minus_product.setOnClickListener(View.OnClickListener {
            increment--
            if(increment < 1){
                increment = 1
            }
            number_of_products.text = increment.toString()


        })

        add_to_bag.setOnClickListener(View.OnClickListener {
            val productOne = bag(bundle.getString("productImage")!!, bundle.getString("productName")!!,
                number_of_product.toString(),
                bundle.getString("productGram")!!, bundle.getString("priceTag")!!)
            Allproducts.add(productOne)
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