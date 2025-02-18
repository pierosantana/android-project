package es.upgrade.dao.api;

import java.util.List;

import es.upgrade.entidad.Product;
import es.upgrade.entidad.Step;
import retrofit2.Call;
import retrofit2.http.GET;


// This code snippet is defining a Java interface named `ApiService` that declares two methods
// annotated with `@GET`. These methods are specifying the HTTP GET request endpoints for retrieving
// lists of products and steps from a web service.
public interface ApiService {
    @GET("productos")
    Call<List<Product>> getProducts();
    @GET("steps")
    Call<List<Step>> getSteps();
}
