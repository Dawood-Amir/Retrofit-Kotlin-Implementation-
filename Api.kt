package com.dawoodamir.tibgouser.global_db

import com.dawoodamir.tibgouser.responses.*
import com.dawoodamir.tibgouser.utils.Constants
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface Api {


    @FormUrlEncoded
    @POST(Constants.ORDERS_PHARMACY)
    suspend fun getOrdersPharmacyApiCall(
        @Header("Authorization") token: String,
        @Field("pageNo") pageNo: Int
    ): Response<OrdersPharmacyResponse>


    @FormUrlEncoded
    @POST(Constants.ORDERS_APPOINTMENT)
    suspend fun getOrderAppointmentsCall(
        @Header("Authorization") token: String,
        @Field("pageNo") pageNo: Int
    ): Response<OrderAllAppointmentResponse>

    @FormUrlEncoded
    @POST(Constants.ORDERS_LAB_REPORT)
    suspend fun getOrderLabReportCall(
        @Header("Authorization") token: String,
        @Field("pageNo") pageNo: Int
    ): Response<OrdersLabReportsResponse>


    @Multipart
    @POST(Constants.PHARMACY_ORDER)
    suspend fun pharmacyOrderRequest(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part?,
        @Part("orderStatus") orderStatus: Int,
        @Part("orderPlacedDate") orderPlacedDate: RequestBody,
        @Part("orderPlacedTime") orderPlacedTime: RequestBody,
        @Part("fk_pharmacyId") fk_pharmacyId: Int,
        @Part("userLatLng") userLatLng: RequestBody,
        @Part("deliveryAddress") deliveryAddress: RequestBody,
        @Part("instructionNote") instructionNote: RequestBody
    ): Response<PharmacyOrderResponse>


    @FormUrlEncoded
    @POST(Constants.USER_LOGIN)
    suspend fun loginUserApiCall(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<UserLoginResponse>


    @FormUrlEncoded
    @POST(Constants.CANCEL_LAB_REQUEST)
    suspend fun cancelLabRequestApiRequest(
        @Field("lab_report_id") labReportId: Int
    ): Response<CancelResponse>


    @FormUrlEncoded
    @POST(Constants.CANCEL_APPOINTMENT)
    suspend fun cancelAppointmentApiRequest(
        @Field("appointmentId") appointmentId: Int
    ): Response<CancelResponse>


    @FormUrlEncoded
    @POST(Constants.CANCEL_PHARMACY_ORDER)
    suspend fun cancelPharmacyOrderApiRequest(
        @Field("order_id") orderId: Int
    ): Response<CancelResponse>


    @FormUrlEncoded
    @POST(Constants.UPDATE_ADT)
    suspend fun updateADTApiCall(
        @Header("Authorization") content_type: String,
        @Field("ADT") adt: String
    ): Response<UpdateADTResponse>

    @FormUrlEncoded
    @POST(Constants.LAB_ORDER)
    suspend fun labOrderApiCall(
        @Header("Authorization") token: String,
        @Field("labId") labId: Int,
        @Field("testTakenDate") testTakenDate: String,
        @Field("latLng") latLng: String,
        @Field("bill") bill: Int,
        @Field("chargePerVisit") chargePerVisit: Int,
        @Field("testList") testList: String
    ): Response<LabOrderResponse>

    @FormUrlEncoded
    @POST(Constants.ADD_APPOINTMENT)
    suspend fun appointDoctor(
        @Header("Authorization") token: String,
        @Field("latLng") latLng: String,
        @Field("address") address: String,
        @Field("describedIssue") describedIssue: String,
        @Field("chargePerVisit") chargePerVisit: Int,
        @Field("fk_d_id") docID: Int
    ): Response<AppointDocResponse>


    @FormUrlEncoded
    @POST(Constants.USER_REGISTRATION)
    suspend fun registerUserApiCall(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("userType") userType: String,
        @Field("ADT") ADT: String
    ): Response<SignUpResponse>


    @FormUrlEncoded
    @POST(Constants.GET_ALL_DOCTORS)
    suspend fun getAllDoctors(
        @Field("latLng") latLng: String,
        @Field("docType") docType: String
    ): Response<DoctorsResponse>

    @FormUrlEncoded
    @POST(Constants.GET_ALL_TESTS)
    suspend fun getAllTest(
        @Field("labId") labId: Int
    ): Response<TestsLabResponse>


    @FormUrlEncoded
    @POST(Constants.GET_ALL_PHARMACIES)
    suspend fun getAllPharmacies(
        @Field("latLng") latLng: String,
        @Field("pageNo") pageNo: Int
    ): Response<PharmaciesResponse>


    @FormUrlEncoded
    @POST(Constants.GET_ALL_LABS)
    suspend fun getAllLabs(
        @Field("latLng") latLng: String
    ): Response<LabListResponse>

    companion object {

        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): Api {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.REQUEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)

        }
    }
}
