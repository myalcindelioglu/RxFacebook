package com.cesarferreira.rxfacebook.library;


import android.os.Bundle;

import com.cesarferreira.rxfacebook.library.data.FacebookUser;
import com.cesarferreira.rxfacebook.library.utils.Utils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class RxFacebook {

    private static RxFacebook sSingleton;
    private AccessToken mAccessToken;
    private AccessTokenTracker mAccessTokenTracker;

    public static RxFacebook getInstance(@NotNull AccessToken accessToken) {
        if (sSingleton == null) {
            sSingleton = new RxFacebook();
        }
        sSingleton.mAccessToken = accessToken;
        return sSingleton;
    }

    private RxFacebook() {
    }

    public Observable<FacebookUser> request(String... args) {
        return Observable.just(sSingleton.mAccessToken)
                .map(accessToken -> graphRequest(Utils.convert(args)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<AccessToken> startAccessTokenTracking(){
        return Observable.create(subscriber -> {
            if(mAccessTokenTracker==null){
                mAccessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                        subscriber.onNext(currentAccessToken);
                    }
                };
            }
            if(!mAccessTokenTracker.isTracking()){
                mAccessTokenTracker.startTracking();
            }
        });
    }

    public void stopAccessTokenTracking(){
        if(mAccessTokenTracker!=null && mAccessTokenTracker.isTracking()){
            mAccessTokenTracker.stopTracking();
        }
    }

    private FacebookUser friendsList() {
        return graphRequest("user_friends");
    }

    private FacebookUser graphRequest(String fields) {

        GraphRequest request = GraphRequest.newMeRequest(
                sSingleton.mAccessToken,
                (object, response) -> {
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", fields);

        request.setParameters(parameters);
        GraphResponse graphResponse = request.executeAndWait();

        return new Gson().fromJson(String.valueOf(graphResponse.getJSONObject()), FacebookUser.class);
    }
}
