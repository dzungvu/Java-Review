package com.example.dxq;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.DataAdapter;
import com.example.interfaces.RecyclerItemClickListener;
import com.example.models.Data;
import com.example.services.DataServices;
import com.example.services.ServiceGenerator;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerItemClickListener {

    private RecyclerView rcvData;
    private ArrayList<Data> arrData = new ArrayList();
    private DataAdapter adapter;
    private DataServices dataServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_container, new QuynhFragment(), "AddFragment").addToBackStack("App");
        ft.commit();

        setContentView(R.layout.activity_main);
        rcvData = findViewById(R.id.rcvData);
        adapter = new DataAdapter(this, arrData, this);
        rcvData.setAdapter(adapter);
        rcvData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        for (int i = 0; i < 20; i++) {
            arrData.add(new Data(String.format("Quynh %s", i), i));
        }

        adapter.notifyDataSetChanged();

        dataServices = ServiceGenerator.createService(DataServices.class);
        fetchData();
    }

    private void fetchData() {
        dataServices.fetchData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Response<List<Data>>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
//                                d.dispose();
                            }

                            @Override
                            public void onNext(Response<List<Data>> dataResponse) {
                                Log.d("Activity", "Success");
                                arrData.clear();
                                if (dataResponse.code() == HttpURLConnection.HTTP_OK) {
                                    if (dataResponse.body() != null) {
                                        arrData.addAll(dataResponse.body());
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                Log.d("Activity", "Error");
                            }

                            @Override
                            public void onComplete() {
                                Log.d("Activity", "Complete");
                            }
                        }
                );
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Log.d("Activity", "Click on item " + position);
    }
}