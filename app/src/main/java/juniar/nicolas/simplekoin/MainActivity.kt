package juniar.nicolas.simplekoin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.rv_user
import kotlinx.android.synthetic.main.viewholder_user.view.tv_email
import kotlinx.android.synthetic.main.viewholder_user.view.tv_name
import kotlinx.android.synthetic.main.viewholder_user.view.tv_username
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private val retrofit: Retrofit by inject()

    private val listUser = arrayListOf<UserModel>()

    private val userAdapter by lazy {
        GeneralRecyclerViewAdapter(
            R.layout.viewholder_user, listUser,
            { _, _, _ -> },
            { user, view ->
                with(view) {
                    tv_name.text = "Name : " + user.name
                    tv_username.text = "Username : " + user.username
                    tv_email.text = "Email : " + user.email
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        with(rv_user) {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        fetchApi1()
    }

    private fun fetchApi1() {
        val gson = GsonBuilder.provideGson()
        val okHttpClient = OkHttpClientBuilder.provideOkHttpClient()
        RetrofitBuilder.provideRetrofit(gson, okHttpClient).create(NetworkService::class.java).getListUser()
            .enqueue(object : Callback<ListUserModel> {
                override fun onFailure(call: Call<ListUserModel>, t: Throwable) {
                    Log.d("fail", t.localizedMessage)
                }

                override fun onResponse(call: Call<ListUserModel>, response: Response<ListUserModel>) {
                    response.body()?.let {
                        listUser.addAll(it)
                        userAdapter.notifyDataSetChanged()
                    }
                }
            })
    }

    private fun fetchApi2() {
        retrofit.create(NetworkService::class.java).getListUser()
            .enqueue(object : Callback<ListUserModel> {
                override fun onFailure(call: Call<ListUserModel>, t: Throwable) {
                    Log.d("fail", t.localizedMessage)
                }

                override fun onResponse(call: Call<ListUserModel>, response: Response<ListUserModel>) {
                    response.body()?.let {
                        listUser.addAll(it)
                        userAdapter.notifyDataSetChanged()
                    }
                }
            })
    }
}