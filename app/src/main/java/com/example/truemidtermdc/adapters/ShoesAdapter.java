package com.example.truemidtermdc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truemidtermdc.R;
import com.example.truemidtermdc.pojos.Shoes;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ShoesAdapter extends RecyclerView.Adapter<ShoesAdapter.ViewHolder> {

    public List<Shoes> shoesList;
    public Context context;

    public ShoesAdapter(List<Shoes> shoesList, Context context) {
        this.shoesList = shoesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShoesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.shoes_recyclerview_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Shoes shoes = shoesList.get(position);

    holder.shoezzz.setText(shoes.getId());
    holder.brand.setText(shoes.getBrand());
    holder.name.setText(shoes.getName());
    holder.prize.setText(shoes.getPrize());
    }

    @Override
    public int getItemCount() {
        return shoesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView shoezzz, brand, name, prize;
        private MaterialButton cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shoezzz = itemView.findViewById(R.id.shoes);
            brand = itemView.findViewById(R.id.nike);
            name = itemView.findViewById(R.id.namenike);
            prize = itemView.findViewById(R.id.pricenike);
            cart = itemView.findViewById(R.id.cartButton);
        }
    }
}
