package apps.alesefs.com.pokedex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import apps.alesefs.com.pokedex.model.Pokemon;
import apps.alesefs.com.pokedex.service.ApiService;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> dataSet;
    private Context context;

    public ListaPokemonAdapter (Context context) {
        this.context = context;
        dataSet = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pokemon, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Pokemon p = dataSet.get(i);

        //image
        Glide.with(context)
                //.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumber() + ".png")
                .load(ApiService.ServiceApi.BASE_URL_IMG + p.getNumber() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.imageView);

        viewHolder.textViewNumber.setText(""+p.getNumber());
        viewHolder.textViewName.setText(p.getName());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void addListPokemon(ArrayList<Pokemon> pokemons) {
        dataSet.addAll(pokemons);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewNumber;
        private TextView textViewName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagePokemon);
            textViewNumber = itemView.findViewById(R.id.textNumber);
            textViewName = itemView.findViewById(R.id.textName);
        }
    }
}
