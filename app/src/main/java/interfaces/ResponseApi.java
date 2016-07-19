package interfaces;

import org.json.JSONObject;

import modals.RegistrationResponse;
import modals.ResponseData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by C.limbachiya on 7/13/2016.
 */
public interface ResponseApi {

    @GET("/contacts")
    Call<ResponseData> getResponse();

    @FormUrlEncoded
    @POST("/webservice/testlogin")
    Call<RegistrationResponse> sendData(@Field("name") String name,
                                        @Field("email") String email);
}
