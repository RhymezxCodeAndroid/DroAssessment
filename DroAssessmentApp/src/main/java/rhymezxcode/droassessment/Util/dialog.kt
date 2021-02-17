package rhymezxcode.droassessment.Util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import rhymezxcode.droassessment.Activities.view_products
import rhymezxcode.droassessment.R

data class dialog(var context: Context, var info: String) {

    @SuppressLint("InflateParams")
    fun showDialog(){
        val view = LayoutInflater.from(context).inflate(R.layout.success_dialog, null, false)
        val dialog = Dialog(context)
        dialog.setCancelable(false)
        dialog.setContentView(view)
        val view_bag = view.findViewById<Button>(R.id.view_bag)
        val done = view.findViewById<Button>(R.id.done)
        val product_info = view.findViewById<TextView>(R.id.product_info)

        product_info.text = info

        view_bag.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, view_products::class.java)
            context.startActivity(intent)
        })
        done.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        dialog.show()
    }


}