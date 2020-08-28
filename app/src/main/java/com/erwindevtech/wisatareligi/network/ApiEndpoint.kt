package com.erwindevtech.wisatareligi.network

import com.erwindevtech.wisatareligi.data.model.ResponseLogin
import com.erwindevtech.wisatareligi.data.model.home.DataModelResponseHome
import com.erwindevtech.wisatareligi.data.model.register.ResponseUserUpdate
import com.erwindevtech.wisatareligi.data.model.search.DataModelResponse
import com.erwindevtech.wisatareligi.ui.detail.model.PostResponseList
import com.erwindevtech.wisatareligi.ui.detail.model.post_comment.CreatePostResponse
import com.erwindevtech.wisatareligi.ui.home.get_detail.model.DetailResponseModel
import com.erwindevtech.wisatareligi.ui.user.update.model.EditProfilResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {
    @FormUrlEncoded
    @POST("http://192.168.43.239/wisataapp/api/login_pengguna")
    fun loginUser(
        @Field("username") username : String,
        @Field("password") password : String
    ):Call<ResponseLogin>

    @FormUrlEncoded
    @POST("http://192.168.43.239/wisataapp/api/register_pengguna")
    fun insertUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("agama") agama: String,
        @Field("password") password: String,
        @Field("password2") password2: String
    ):Call<ResponseUserUpdate>

    @FormUrlEncoded
    @POST("http://192.168.43.239/wisataapp/api/edit_pengguna/{user_id}")
    fun updateUser(
        @Path("user_id") user_id : Int,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("jenis_kelamin") jenis_kelamin: String,
        @Field("agama") agama: String,
        @Field("alamat") alamat: String,
        @Field("tanggal_lahir") tanggal_lahir: String,
        @Query("_method") _method: String

    ):Call<ResponseUserUpdate>


    @GET("http://192.168.43.239/wisataapp/api/home/islam")
    fun getData(): Call<DataModelResponse>

    @GET("http://192.168.43.239/wisataapp/api/home/{agama}")
    fun getDataHome(@Path("agama")agama: String): Call<DataModelResponseHome>

    //get comment
    @GET("http://192.168.43.239/wisataapp/api/komentar/{id_wisata}")
    fun getComment(
        @Path("id_wisata") id_wisata: Int
    ): Call<PostResponseList>

    //post comment
    @FormUrlEncoded
    @POST("http://192.168.43.239/wisataapp/api/wisata")
    fun createComment(
        @Field("id") id: Int,
        @Field("id_wisata") id_wisata : Int,
        @Field("komentar") komentar : String
    ):Call<CreatePostResponse>


    //edit profil
    @FormUrlEncoded
    @POST("http://192.168.43.239/wisataapp/api/edit_pengguna/{user_id}")
    fun edtiProfil(
        @Path("user_id") user_id : Int,
        @Field("name") name: String,
        @Field("email") email : String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("jenis_kelamin") jenis_kelamin: String,
        @Field("agama") agama: String,
        @Field("alamat") alamat: String,
        @Field("tanggal_lahir") tanggal_lahir : String
    ):Call<EditProfilResponse>

    //Get detail
    @GET("http://192.168.43.239/wisataapp/api/detail/{id_wisata}/{user_id}")
    fun getDetail(
        @Path("id_wisata") id_wisata: Int,
        @Path("user_id") user_id: Int
    ):Call<DetailResponseModel>

    @POST("home")
    fun home(
        @Field("agama") agama : String
    ):Call<DataModelResponseHome>





}