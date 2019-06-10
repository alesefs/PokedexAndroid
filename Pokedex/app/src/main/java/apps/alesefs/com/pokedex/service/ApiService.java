package apps.alesefs.com.pokedex.service;

import android.util.Log;

import apps.alesefs.com.pokedex.model.Pokemon;
import apps.alesefs.com.pokedex.model.PokemonModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiService {
    //private String BASE_URL = "https://pokeapi.co/api/v2";

    public ServiceApi getApi() {
        Retrofit retrofit = new Retrofit
            .Builder()
            .baseUrl(ServiceApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        return retrofit.create(ServiceApi.class);
    }

    public interface ServiceApi {

        public static final String BASE_URL = "https://pokeapi.co/api/v2/";

        public static final String BASE_URL_IMG = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";

        @GET("pokemon")
        Call<PokemonModel> listPokemon(
                @Query("limit") int limit,
                @Query("offset") int offset);

    }
}
