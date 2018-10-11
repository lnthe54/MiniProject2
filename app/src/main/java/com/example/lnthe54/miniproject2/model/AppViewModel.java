package com.example.lnthe54.miniproject2.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.lnthe54.miniproject2.service.MusicService;

/**
 * @author lnthe54 on 10/8/2018
 * @project MiniProject2
 */
public class AppViewModel extends AndroidViewModel {

    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<Long> duration = new MutableLiveData<>();
    private MutableLiveData<Long> currentPosition = new MutableLiveData<>();
    private AsyncTask<Void, Void, Void> asyncTask;

    public AppViewModel(@NonNull Application application) {
        super(application);
    }

    public void update(final MusicService service) {
        if (asyncTask != null) {
            asyncTask.cancel(true);
        }

        asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                while (true) {
                    publishProgress();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        };
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<Long> getDuration() {
        return duration;
    }

    public MutableLiveData<Long> getCurrentPosition() {
        return currentPosition;
    }
}
