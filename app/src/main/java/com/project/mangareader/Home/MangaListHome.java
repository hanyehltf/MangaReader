package com.project.mangareader.Home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.mangareader.DatabaseManagment.DataBaseControler;
import com.project.mangareader.R;


public class MangaListHome extends Fragment {
    private DataBaseControler dataBaseControler;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;
    private Context context;
    ProgressDialog progressDialog;
    Handler handler = new Handler();

    public MangaListHome(Context context) {
        this.context = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manga_list__home_list, container, false);
        dataBaseControler = new DataBaseControler(context);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            if (dataBaseControler.getMangas().size() == 0) {
                dataBaseControler.AddManga();
            }

            if (dataBaseControler.getMangas().size() != 0) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {


                        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(dataBaseControler.getMangas(), context));

                    }
                };
                runnable.run();


            }
        }
        return view;
    }
}