package rhymezxcode.droassessment.Util

import android.net.Uri
import android.provider.BaseColumns
import rhymezxcode.droassessment.Models.Product
import rhymezxcode.droassessment.R


class products {
    var Products: Array<Product>
        get() = arrayOf(Keztil, Augmetin, pazeo, Zarontin, Panadol)
        set(value) = TODO()

    val Keztil = Product(
        Uri.parse("android.resource://rhymezxcode.droassessment/" + R.drawable.medicine).toString(),
        "Keztil",
        "1 pack of Keztil contains 3 units of 10 Tablet(s).",
        "550gm",
        "1000",
        getProductID()
    )

    val Augmetin = Product(
        Uri.parse("android.resource://rhymezxcode.droassessment/" + R.drawable.medicine).toString(),
        "Augmetin",
        "1 pack of Augmetin contains 3 units of 10 Tablet(s).",
        "600gm",
        "6000",
        getProductID()
    )

    val pazeo = Product(
        Uri.parse("android.resource://rhymezxcode.droassessment/" + R.drawable.medicine).toString(),
        "pazeo",
        "1 pack of pazeo contains 3 units of 10 Tablet(s).",
        "850gm",
        "550",
        getProductID()
    )

    val Zarontin = Product(
        Uri.parse("android.resource://rhymezxcode.droassessment/" + R.drawable.medicine).toString(),
        "Zarontin",
        "1 pack of Zarontin contains 3 units of 10 Tablet(s).",
        "1000gm",
        "2000",
        getProductID()
    )
    val Panadol = Product(
        Uri.parse("android.resource://rhymezxcode.droassessment/" + R.drawable.medicine).toString(),
        "Panadol",
        "3 pack of Panadol Extra contains 3 units of 10 Tablet(s).",
        "200gm",
        "300",
        getProductID()
    )

    fun getProductID(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..8).map { allowedChars.random() }.joinToString("")
    }

}