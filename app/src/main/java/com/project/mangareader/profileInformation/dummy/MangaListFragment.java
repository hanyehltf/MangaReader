package com.project.mangareader.profileInformation.dummy;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.mangareader.DatabaseManagment.DataBaseControler;
import com.project.mangareader.R;



public class MangaListFragment extends Fragment {

    DataBaseControler dataBaseControler;
    private Context context;

    public MangaListFragment(Context context) {
        this.context = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manga_items, container, false);
        dataBaseControler = new DataBaseControler(context);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            if (dataBaseControler.getMangas().size() != 0) {
                recyclerView.setAdapter(new MyMangaListRecyclerViewAdapter(dataBaseControler.getMangas()));
            }
        }
        return view;
    }
}