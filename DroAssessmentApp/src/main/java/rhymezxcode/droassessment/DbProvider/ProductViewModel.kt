package rhymezxcode.droassessment.DbProvider

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import rhymezxcode.droassessment.Models.Bag
import rhymezxcode.droassessment.Models.Product

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    var productRepository: ProductRepository? = null
    var productList: LiveData<List<Product>>? = null
    var bagList: LiveData<List<Bag>>? = null

    init {
        productRepository = ProductRepository(application)
        productList = productRepository!!.getAllProduct()
        bagList = productRepository!!.getAllBagProduct()
    }

    @JvmName("getAllProduct1")
    fun getAllProduct(): LiveData<List<Product>>?{
        return productRepository!!.getAllProduct()
    }

    @JvmName("getAllBagProduct1")
    fun getAllBagProduct(): LiveData<List<Bag>>?{
        return productRepository!!.getAllBagProduct()
    }

    val allBagProduct: LiveData<List<Bag>>
        get() = productRepository!!.getAllBagProduct()!!

    fun insertProduct(product: Product?) {
        productRepository!!.insertProduct(product)
    }

    fun insertBagProduct(bag: Bag?) {
        productRepository!!.insertBagProduct(bag)
    }

    fun updateProduct(product: Product?) {
        productRepository!!.updateProduct(product)
    }

    fun updateBagProduct(bag: Bag?) {
        productRepository!!.updateBagProduct(bag)
    }

    fun deleteProduct(product: Product?) {
        productRepository!!.deleteProduct(product)
    }

    fun deleteBagProduct(bag: Bag?) {
        productRepository!!.deleteBagProduct(bag)
    }
}