package rhymezxcode.droassessment.DbProvider

import android.app.Application
import androidx.lifecycle.LiveData
import rhymezxcode.droassessment.DbProvider.Database.Companion.buildDatabase
import rhymezxcode.droassessment.DbProvider.Database.Companion.getDatabase
import rhymezxcode.droassessment.Models.Bag
import rhymezxcode.droassessment.Models.Product

class ProductRepository(application: Application?) {
    private var productDao: ProductDao? = null
    private var database: Database? = null
    private var productsList: LiveData<List<Product>>? = null

    init {
        database = getDatabase(application)
        productDao = database!!.ProductDao()
        productsList = productDao!!.getAllProduct()
    }

    fun getAllProduct(): LiveData<List<Product>>? {
        return productDao!!.getAllProduct()
    }

    fun getAllBagProduct(): LiveData<List<Bag>>? {
        return productDao!!.getAllBagProduct()
    }

    fun insertProduct(product: Product?) {
        database!!.ProductDao()!!.insertProduct(product)
    }

    fun insertBagProduct(bag: Bag?) {
        database!!.ProductDao()!!.insertBagProduct(bag)
    }

    fun deleteProduct(product: Product?) {
        database!!.ProductDao()!!.deleteProduct(product)
    }

    fun deleteBagProduct(bag: Bag?) {
        database!!.ProductDao()!!.deleteBagProduct(bag)
    }

    fun updateProduct(product: Product?) {
        database!!.ProductDao()!!.updateProduct(product)
    }

    fun updateBagProduct(bag: Bag?) {
        database!!.ProductDao()!!.updateBagProduct(bag)
    }
}