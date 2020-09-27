package juniar.nicolas.simplekoin

import retrofit2.Call
import retrofit2.http.GET

interface NetworkService {
    @GET("users")
    fun getListUser():Call<ListUserModel>
}