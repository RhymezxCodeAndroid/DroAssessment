package rhymezxcode.droassessment.DbProvider

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.loader.content.AsyncTaskLoader
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
        AsyncTask.execute {
            database!!.ProductDao()!!.insertProduct(product)
        }
    }

    fun insertBagProduct(bag: Bag?) {
        AsyncTask.execute {
            database!!.ProductDao()!!.insertBagProduct(bag)
        }
    }

    fun deleteProduct(product: Product?) {
        AsyncTask.execute {
            database!!.ProductDao()!!.deleteProduct(product)
        }
    }

    fun deleteBagProduct(bag: Bag?) {
        AsyncTask.execute {
            database!!.ProductDao()!!.deleteBagProduct(bag)
        }
    }

    fun deleteBagById(id: Int?) {
        AsyncTask.execute {
            database!!.ProductDao()!!.deleteBagById(id)
        }
    }

    fun updateProduct(product: Product?) {
        AsyncTask.execute {
            database!!.ProductDao()!!.updateProduct(product)
        }
    }

    fun updateBagProduct(bag: Bag?) {
        AsyncTask.execute {
            database!!.ProductDao()!!.updateBagProduct(bag)
        }
    }
}