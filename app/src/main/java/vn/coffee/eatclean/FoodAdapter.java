package vn.coffee.eatclean;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.coffee.eatclean.addFoodList.AddFoodListActivity;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> implements Filterable {

    List<Food> mList;
    List<Food> mListSearch;
    Context context;

    public FoodAdapter(List<Food> mList, Context context) {
        this.mList = mList;
        this.mListSearch = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.food,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.img.setImageResource(mList.get(position).getImageid());
        holder.ten.setText(mList.get(position).getTen());
        holder.calo.setText(mList.get(position).getCalo());
        holder.carbs.setText(mList.get(position).getCarbs());
        holder.fat.setText(mList.get(position).getFat());
        holder.pro.setText(mList.get(position).getPro());
        holder.name.setText(mList.get(position).getName());
        holder.gram.setText(mList.get(position).getGram());
        holder.name1.setText(mList.get(position).getName1());
        holder.gram1.setText(mList.get(position).getGram1());
        holder.name2.setText(mList.get(position).getName2());
        holder.gram2.setText(mList.get(position).getGram2());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddFoodListActivity.class);
                intent.putExtra("img",mList.get(position).getImageid());
                intent.putExtra("ten",mList.get(position).getTen());
                intent.putExtra("calo",mList.get(position).getCalo());
                intent.putExtra("carbs",mList.get(position).getCarbs());
                intent.putExtra("fat",mList.get(position).getFat());
                intent.putExtra("pro",mList.get(position).getPro());
                intent.putExtra("name",mList.get(position).getName());
                intent.putExtra("name1",mList.get(position).getName1());
                intent.putExtra("name2",mList.get(position).getName2());
                intent.putExtra("gram",mList.get(position).getGram());
                intent.putExtra("gram1",mList.get(position).getGram1());
                intent.putExtra("gram2",mList.get(position).getGram2());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    mList = mListSearch;
                }else {
                    List<Food>  list = new ArrayList<>();
                    for (Food food : mListSearch){
                        if (food.getTen().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(food);
                        }
                    }
                    mList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mList = (List<Food>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView ten,calo,
                carbs,fat,pro,name,gram,name1,gram1,name2,gram2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img =  itemView.findViewById(R.id.img);
            ten = itemView.findViewById(R.id.ten);
            calo = itemView.findViewById(R.id.Cal);
            carbs = itemView.findViewById(R.id.carbs);
            fat = itemView.findViewById(R.id.fat);
            pro = itemView.findViewById(R.id.protein);
            name = itemView.findViewById(R.id.name);
            name1 = itemView.findViewById(R.id.name1);
            name2 = itemView.findViewById(R.id.name2);
            gram = itemView.findViewById(R.id.gram);
            gram1 = itemView.findViewById(R.id.gram1);
            gram2 = itemView.findViewById(R.id.gram2);
        }
    }
}
