package es.upgrade.dao.api;

import java.util.List;

import es.upgrade.entidad.Product;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("productos")
    Call<List<Product>> getProducts();
}
