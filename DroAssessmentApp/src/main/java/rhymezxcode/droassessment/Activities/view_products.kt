package rhymezxcode.droassessment.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_view_products.*
import rhymezxcode.droassessment.Adapters.view_products_adapter
import rhymezxcode.droassessment.Models.product
import rhymezxcode.droassessment.R

class view_products : AppCompatActivity() {
    var Allproducts = ArrayList<product>()
    var activity = this@view_products
    var back_pressed: Long? = 0
    var showing: Boolean = false

    private val adapter = view_products_adapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_products)
        setupProductRecyclerView()
        productData()
        addToList()


        allItems.text = """${Allproducts.size} items(s)"""
        back.setOnClickListener(View.OnClickListener {
           onBackPressed()
        })

        search_button.setOnClickListener(View.OnClickListener {
            if(showing){
                searchView.visibility = View.GONE
            }else{
                searchView.visibility = View.VISIBLE
            }
            showing = !showing

        })

        BottomSheetBehavior.from(products_bottom_sheet).apply {
            peekHeight*200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }


    }

    private fun setupProductRecyclerView() {
        with(all_products) {
            layoutManager = GridLayoutManager(activity.applicationContext, 2)
            adapter = activity.adapter
        }
    }

    private fun addToList() {
        adapter.setProducts(activity, Allproducts)
    }


    fun productData(){
        //parsing my static products, if it was a json array i would have used a loop.
        val productOne = product(R.drawable.medicine.toString(), "Keztil Susp",
            "1 pack of Keztil Susp contains 3 units of 10 Tablet(s).",
            "750gm", "3000", getProductID())
        Allproducts.add(productOne)
        val productTwo = product(R.drawable.medicine.toString(), "Keztil",
            "1 pack of Keztil contains 3 units of 10 Tablet(s).",
            "550gm", "1000", getProductID())
        Allproducts.add(productTwo)
        val productThree = product(R.drawable.medicine.toString(), "Garlic oil",
            "1 pack of Garlic oil contains 3 units of 10 Tablet(s).",
            "950gm", "5000", getProductID())
        Allproducts.add(productThree)
        val productFour = product(R.drawable.medicine.toString(), "Folic Acid",
            "1 pack of Folic acid contains 3 units of 10 Tablet(s).",
            "500gm", "400", getProductID())
        Allproducts.add(productFour)
        val productFive = product(R.drawable.medicine.toString(), "Augmetin",
            "1 pack of Augmetin contains 3 units of 10 Tablet(s).",
            "600gm", "6000", getProductID())
        Allproducts.add(productFive)
        val productSix = product(R.drawable.medicine.toString(), "pazeo",
            "1 pack of pazeo contains 3 units of 10 Tablet(s).",
            "850gm", "550", getProductID())
        Allproducts.add(productSix)
        val productSeven = product(R.drawable.medicine.toString(), "Zarontin",
            "1 pack of Zarontin contains 3 units of 10 Tablet(s).",
            "1000gm", "2000", getProductID())
        Allproducts.add(productSeven)
        val productEight = product(R.drawable.medicine.toString(), "Panadol Extra",
            "3 pack of Panadol Extra contains 3 units of 10 Tablet(s).",
            "200gm", "300", getProductID())
        Allproducts.add(productEight)
    }

    fun getProductID():String{
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..8).map { allowedChars.random() }.joinToString("")
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