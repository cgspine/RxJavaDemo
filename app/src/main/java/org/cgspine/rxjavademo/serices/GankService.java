package org.cgspine.rxjavademo.serices;

import org.cgspine.rxjavademo.model.Response;


import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by cgspine on 16/1/15.
 */
public interface GankService {
    @GET("{type}/{count}/{page}")
    Observable<Response> getGanks(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page
    );
}
