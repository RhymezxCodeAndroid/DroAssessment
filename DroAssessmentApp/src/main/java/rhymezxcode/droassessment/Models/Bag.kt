package rhymezxcode.droassessment.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Bag")
data class Bag(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
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
)

