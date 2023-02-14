package vn.coffee.eatclean.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.coffee.eatclean.Food;
import vn.coffee.eatclean.FoodAdapter;
import vn.coffee.eatclean.R;


public class FoodFragment extends Fragment {

    RecyclerView recyclerView;
    List<Food> mList;
    FoodAdapter foodAdapter;
    SearchView searchView;

    public FoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);


        recyclerView = view.findViewById(R.id.ds);
        foodAdapter = new FoodAdapter(mList,getContext());
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchView = view .findViewById(R.id.edt_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                foodAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                foodAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList=new ArrayList<>();
        mList .add(new Food(R.drawable.a,getString(R.string.Pan),"410","50","40","6",getString(R.string.chicken),"150g",getString(R.string.Boiled),"300g",getString(R.string.Uncooked),"50g"));
        mList .add(new Food(R.drawable.b,getString(R.string.Salad),"350","65","6","50",getString(R.string.chicken),"200g",getString(R.string.Lettuce),"50g",getString(R.string.grapefruit),"350g"));
        mList .add(new Food(R.drawable.c,getString(R.string.breast),"410","41","12","34",getString(R.string.Duck),"100g",getString(R.string.vermicelli),"50g",getString(R.string.mushrooms),"70g"));
        mList .add(new Food(R.drawable.d,getString(R.string.bone),"150","60","20","37",getString(R.string.Thigh),"200g",getString(R.string.carrots),"50g",getString(R.string.shiitake),"350g"));
        mList .add(new Food(R.drawable.e,getString(R.string.marinated),"460","55","9","40",getString(R.string.sturgeon),"200g",getString(R.string.beans),"200g",getString(R.string.brown),"50g"));
        mList .add(new Food(R.drawable.f,getString(R.string.Egg),"430","55","14","21",getString(R.string.Egg1),getString(R.string.egg),getString(R.string.sauteed),"250",getString(R.string.brown),"50g"));
        mList .add(new Food(R.drawable.g,getString(R.string.Boneless),"510","55","11","50",getString(R.string.Carp),"100g",getString(R.string.Pork),"50g",getString(R.string.Pumpkin),"200g"));
        mList .add(new Food(R.drawable.h,getString(R.string.Pump),"558","57","21","38",getString(R.string.Beef),"100g",getString(R.string.flower),"150",getString(R.string.brown),"50g"));
        mList .add(new Food(R.drawable.i,getString(R.string.Dried),"418","34","8","52",getString(R.string.rice),"50g",getString(R.string.chayote),"100g",getString(R.string.chicken),"200g"));
        mList .add(new Food(R.drawable.j,getString(R.string.Luffa),"416","60","35","4",getString(R.string.chicken),"100g",getString(R.string.Luffa1),"100g",getString(R.string.rice1),"50g"));
    }

}