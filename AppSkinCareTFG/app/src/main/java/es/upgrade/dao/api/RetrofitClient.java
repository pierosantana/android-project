package es.upgrade.dao.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:3000/";  // Localhost

    private static Retrofit retrofit;

    /**
     * The function returns an instance of ApiService using Retrofit with a specified base URL and Gson
     * converter factory.
     * 
     * @return The method `getApiService()` is returning an instance of the `ApiService` interface
     * created by the Retrofit instance.
     */
    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())// GsonConverterFactory is used to convert JSON to Java objects.
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
