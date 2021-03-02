package com.example.mumbaemart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mumbaemart.adapter.CategoryAdapter;
import com.example.mumbaemart.adapter.HomePageAdapter;
import com.example.mumbaemart.model.CategoryModel;
import com.example.mumbaemart.model.HorizontalProductScrollModel;
import com.example.mumbaemart.model.HomePageModel;
import com.example.mumbaemart.model.SliderModel;
import com.example.mumbaemart.model.WishlistModel;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.mumbaemart.DBqueries.categoryModelList;
import static com.example.mumbaemart.DBqueries.lists;
import static com.example.mumbaemart.DBqueries.loadCategories;
import static com.example.mumbaemart.DBqueries.loadedCategoriesNames;
import static com.example.mumbaemart.DBqueries.loadFragmentData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {

    }

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    @SuppressLint("StaticFieldLeak")
    public static SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView categoryRecyclerView;
    private final List<CategoryModel> categoryModelFakeList = new ArrayList<>();
    private CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
    private RecyclerView homePageRecyclerView;
    private final List<HomePageModel> homePageModelFakeList = new ArrayList<>();
    private HomePageAdapter adapter;
    private ImageView noInternetConnection;
    private Button retryBtn;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        noInternetConnection = view.findViewById(R.id.no_internet_connection);
        categoryRecyclerView = view.findViewById(R.id.category_recyclerview);
        homePageRecyclerView = view.findViewById(R.id.home_page_recyclerview);
        retryBtn = view.findViewById(R.id.retry_btn);

        swipeRefreshLayout.setColorSchemeColors(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);

        //categoryModelFakeList.add(new CategoryModel("null", ""));
        //categoryModelFakeList.add(new CategoryModel("", ""));
        //categoryModelFakeList.add(new CategoryModel("", ""));
        //categoryModelFakeList.add(new CategoryModel("", ""));
        //categoryModelFakeList.add(new CategoryModel("", ""));
        //categoryModelFakeList.add(new CategoryModel("", ""));
        //categoryModelFakeList.add(new CategoryModel("", ""));
        //categoryModelFakeList.add(new CategoryModel("", ""));
        //categoryModelFakeList.add(new CategoryModel("", ""));
        //categoryModelFakeList.add(new CategoryModel("", ""));


        //List<SliderModel> sliderModelFakeList = new ArrayList<>();
        //sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        //sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        //sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        //sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        //sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));

        //List<HorizontalProductScrollModel> horizontalProductScrollModelFakeList = new ArrayList<>();
        //horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        //horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        //horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        //horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        //horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        //horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        //horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));

        //homePageModelFakeList.add(new HomePageModel(0, sliderModelFakeList));
        //homePageModelFakeList.add(new HomePageModel(1, "","#dfdfdf"));
        //homePageModelFakeList.add(new HomePageModel(2, "","#dfdfdf",horizontalProductScrollModelFakeList,new ArrayList<WishlistModel>()));
        //homePageModelFakeList.add(new HomePageModel(3, "","#dfdfdf",horizontalProductScrollModelFakeList));

        //categoryAdapter = new CategoryAdapter(categoryModelFakeList);

        //adapter = new HomePageAdapter(homePageModelFakeList);

        ConnectivityManager connectivityManager = (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            MainActivity.drawer.setDrawerLockMode(0);
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            if (categoryModelList.size() == 0) {
                loadCategories(categoryRecyclerView,getContext());
            }else {
                categoryAdapter = new CategoryAdapter(categoryModelList);
                categoryAdapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(categoryAdapter);

            if (lists.size() == 0) {
                loadedCategoriesNames.add("HOME");
                lists.add(new ArrayList<HomePageModel>());

                loadFragmentData(homePageRecyclerView,getContext(),0,"Home");
            }else {
                adapter = new HomePageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);

        }else {
            MainActivity.drawer.setDrawerLockMode(1);
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_internet_connection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            reloadPage();
        });

        retryBtn.setOnClickListener(v -> reloadPage());
        return view;
    }

    @SuppressLint("WrongConstant")
    private void reloadPage() {
        networkInfo = connectivityManager.getActiveNetworkInfo();
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();
        if (networkInfo != null && networkInfo.isConnected()) {
            MainActivity.drawer.setDrawerLockMode(0);
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            categoryAdapter = new CategoryAdapter(categoryModelFakeList);
            adapter = new HomePageAdapter(homePageModelFakeList);
            categoryRecyclerView.setAdapter(categoryAdapter);
            homePageRecyclerView.setAdapter(adapter);

            loadCategories(categoryRecyclerView,getContext());

            loadedCategoriesNames.add("HOME");
            lists.add(new ArrayList<>());
            loadFragmentData(homePageRecyclerView,getContext(),0,"Home");

        }else {
            MainActivity.drawer.setDrawerLockMode(1);
            Toast.makeText(getContext(), "No internet Connection!", Toast.LENGTH_SHORT).show();
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(Objects.requireNonNull(getContext())).load(R.drawable.no_internet_connection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
