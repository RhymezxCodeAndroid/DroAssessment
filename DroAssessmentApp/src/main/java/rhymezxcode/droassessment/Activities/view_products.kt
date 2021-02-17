package rhymezxcode.droassessment.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.activity_view_products.*
import kotlinx.android.synthetic.main.activity_view_products.back
import kotlinx.android.synthetic.main.activity_view_products.bag
import rhymezxcode.droassessment.Adapters.bag_bottom_sheet_adapter
import rhymezxcode.droassessment.Adapters.view_products_adapter
import rhymezxcode.droassessment.DbProvider.ProductViewModel
import rhymezxcode.droassessment.Models.Bag
import rhymezxcode.droassessment.Models.Product
import rhymezxcode.droassessment.R
import rhymezxcode.droassessment.Util.PreferenceConstant
import rhymezxcode.droassessment.Util.products


class view_products : AppCompatActivity(), bag_bottom_sheet_adapter.ItemClicked{
    var activity = this@view_products
    var back_pressed: Long? = 0
    var showing: Boolean = false
    lateinit var productViewModel: ProductViewModel
    lateinit var all_products: RecyclerView
    lateinit var product_list: RecyclerView
    private val productAdapter = view_products_adapter()
    private val bagAdapter = bag_bottom_sheet_adapter()
    lateinit var searchView: SearchView

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_products)

        all_products = findViewById(R.id.all_products)
        product_list = findViewById(R.id.product_list)
        searchView = findViewById(R.id.searchView)
        searchView.isSubmitButtonEnabled()
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                productAdapter.filter.filter(p0)
                return false
            }
        })

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        productViewModel.getAllProduct()!!.observe(this, object : Observer<List<Product>> {
            override fun onChanged(products: List<Product>?) {
                if (products!!.size > 0) {
                    setupProductRecyclerView()
                    allItems.text = products.size.toString()+" item(s)"
                    productAdapter.setProducts(activity, products.toMutableList())
                }
            }
        })

        productViewModel.getAllBagProduct()!!.observe(this, object : Observer<List<Bag>> {
            override fun onChanged(bagProducts: List<Bag>?) {
                if (bagProducts!!.size > 0) {
                    setupBagProductRecyclerView()
                    val iterator = bagProducts.listIterator()
                    var totalPrice = 0
                    for(product in iterator){
                       totalPrice += product.priceTag.toInt()
                    }
                    if(bagProducts.size == null){
                        bottom_sheet_number.text = "0"
                    }else{
                        bottom_sheet_number.text = bagProducts.size.toString()
                    }

                        product_price.text = "\u20a6"+totalPrice.toString()

                    bagAdapter.setBagProducts(activity, bagProducts.toMutableList())
                    bagAdapter.notifyDataSetChanged()
                }
            }
        })

        back.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        search_button.setOnClickListener(View.OnClickListener {
            if (showing) {
                searchView.visibility = View.GONE
            } else {
                searchView.visibility = View.VISIBLE
            }
            showing = !showing

        })

        BottomSheetBehavior.from(products_bottom_sheet).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }


    }

    private fun setupProductRecyclerView() {
        with(all_products) {
            layoutManager = GridLayoutManager(activity.applicationContext, 2)
            adapter = activity.productAdapter
            productAdapter.notifyDataSetChanged()
        }
    }

    private fun setupBagProductRecyclerView() {
        with(product_list) {
            layoutManager = LinearLayoutManager(activity.applicationContext)
            adapter = activity.bagAdapter
            bagAdapter.notifyDataSetChanged()
        }
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

    override fun deleteClicked(bag: Bag?) {
           productViewModel.deleteBagProduct(bag)
    }

}