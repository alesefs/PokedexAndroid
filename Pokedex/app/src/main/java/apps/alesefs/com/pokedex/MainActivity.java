package apps.alesefs.com.pokedex;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;

import apps.alesefs.com.pokedex.model.Pokemon;
import apps.alesefs.com.pokedex.model.PokemonModel;
import apps.alesefs.com.pokedex.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";
    private ApiService service;

    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;

    private int start = 0;
    private int limit = 20;
    private int offset = 0;

    private boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = new ApiService();
        getDataApi(offset);
        createRecycleView();
    }



    private void getDataApi(int offset) {
        isLoading = false;

        service.getApi().listPokemon(limit, offset).enqueue(new Callback<PokemonModel>() {
            @NonNull
            @Override
            public void onResponse(Call<PokemonModel> call, Response<PokemonModel> response) {
                isLoading = true;
                if (response.isSuccessful()) {
                    PokemonModel pokemonModel = response.body();
                    ArrayList<Pokemon> pokemons = pokemonModel.getResults();

                    /*for  (int i = 0; i < pokemons.size(); i++) {
                        Pokemon p = pokemons.get(i);
                        Log.i(TAG, "Pokemons " + i + " - " + p.getName());
                    }*/

                    listaPokemonAdapter.addListPokemon(pokemons);

                } else {
                    Log.w(TAG, "Error Unsuccessful " + response.body());
                }
            }

            @Override
            public void onFailure(Call<PokemonModel> call, Throwable t) {
                isLoading = true;
                Log.e(TAG, "Error Failure " + t.getMessage());
            }
        });
    }


    private void createRecycleView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        listaPokemonAdapter = new ListaPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (isLoading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, "END");

                            isLoading = false;
                            offset += 20;
                            getDataApi(offset);
                        }
                    }
                }
            }
        });
    }

}
