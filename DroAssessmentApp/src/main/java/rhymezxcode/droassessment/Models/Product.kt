package rhymezxcode.droassessment.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
data class Product(
    @ColumnInfo(name = "ProductImage")
    var productImage: String,
    @ColumnInfo(name = "ProductName")
    var productName: String,
    @ColumnInfo(name = "ProductDescription")
    var productDescription: String,
    @ColumnInfo(name = "ProductGram")
    var productGram: String,
    @ColumnInfo(name = "PriceTag")
    var priceTag: String,
    @ColumnInfo(name = "ProductId")
    var productId: String
){
    @PrimaryKey(autoGenerate = true)
    private var id = 0

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

}

