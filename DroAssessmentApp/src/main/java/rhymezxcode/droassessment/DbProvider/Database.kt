package rhymezxcode.droassessment.DbProvider

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import rhymezxcode.droassessment.Models.Bag
import rhymezxcode.droassessment.Models.Product
import rhymezxcode.droassessment.Util.products
import java.util.concurrent.Executors

@androidx.room.Database(entities = arrayOf(Product::class, Bag::class), version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun ProductDao(): ProductDao?

    companion object {
        @Volatile
        var INSTANCE: Database? = null

        @JvmStatic
        fun getDatabase(context: Context?): Database? {
            if (INSTANCE == null) {
                synchronized(Database::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context!!, Database::class.java, "Product.db"
                        ).build()
                        INSTANCE = buildDatabase(context)
                    }
                }
            }
            return INSTANCE
        }

        fun buildDatabase(context: Context): Database {
            return Room.databaseBuilder(context, Database::class.java, "Product.db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newSingleThreadScheduledExecutor().execute {
                            INSTANCE?.let {
                                val products = products()
                                for (product in products.Products) {
                                    it.ProductDao()!!.insertProduct(product)
                                }
                            }
                        }
                    }
                }).build()
        }
    }
}