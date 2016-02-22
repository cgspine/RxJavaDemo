package org.cgspine.rxjavademo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cgspine on 16/1/15.
 */
public class Response {
    private boolean error;
    @SerializedName("results") private List<Gank> ganks;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Gank> getGanks() {
        return ganks;
    }

    public void setGanks(List<Gank> ganks) {
        this.ganks = ganks;
    }
}
