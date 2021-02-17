package rhymezxcode.droassessment.DbProvider

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import rhymezxcode.droassessment.Models.Bag
import rhymezxcode.droassessment.Models.Product

@Dao
interface ProductDao {
    @Insert
    fun insertProduct(product: Product?)

    @Insert
    fun insertBagProduct(bag: Bag?)

    @Delete
    fun deleteProduct(product: Product?)

    @Delete
    fun deleteBagProduct(bag: Bag?)

    @Query("DELETE FROM Bag WHERE id = :id")
    fun deleteBagById(id: Int?)

    @Update
    fun updateProduct(product: Product?)

    @Update
    fun updateBagProduct(bag: Bag?)

    @Query("SELECT * from Product")
    fun getAllProduct(): LiveData<List<Product>>

    @Query("SELECT * from Bag")
    fun getAllBagProduct(): LiveData<List<Bag>>





//    @Query("""SELECT * FROM Bag WHERE ${Bag().priceTag} = PriceTag""")
//    fun getAllPrices(): LiveData<Array<String>>
}