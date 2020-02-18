package br.com.testeandroid.muralapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.testeandroid.muralapp.Adapter.MyPostsAdapter
import br.com.testeandroid.muralapp.Common.Common
import br.com.testeandroid.muralapp.Interface.RetrofitService
import com.google.android.gms.tasks.OnFailureListener
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var mService: RetrofitService
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyPostsAdapter
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mService = Common.retrofitService
//        recycle_view_main.setHasFixedSize(true)
//        layoutManager = LinearLayoutManager(this)
//        recycle_view_main.layoutManager = layoutManager

//        dialog = SpotsDialog.Builder().setCancelable(false).setContext(this).build()

       // getAllPostsList()

    }

    private fun getAllPostsList() {
        dialog.show()
        mService.getPostsList().enqueue(object : Callback<MutableList<Posts>> {
            override fun onFailure(call: Call<MutableList<Posts>>, t: Throwable){

            }
            override fun onResponse(call: Call<MutableList<Posts>>, response: Response<MutableList<Posts>>){
                adapter = MyPostsAdapter(baseContext, response.body() as MutableList<Posts>)
                adapter.notifyDataSetChanged()
                recycle_view_main.adapter = adapter

                dialog.dismiss()
            }
        })
    }
}
