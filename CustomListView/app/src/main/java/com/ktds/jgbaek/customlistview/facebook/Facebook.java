package com.ktds.jgbaek.customlistview.facebook;

import android.content.Context;
import android.util.Log;

import com.ktds.jgbaek.customlistview.MainActivity;
import com.ktds.jgbaek.customlistview.WritePostActivity;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import com.restfb.types.Post;
import com.restfb.types.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 206-008 on 2016-06-14.
 */
public class Facebook {
    /**인증과 관련된 상수들
     *
     */
    private static final String APP_ID = "437795256418001";
    private static final String APP_SECRET = "9073f49115fbcf6ef9aa912150555b68";
    private static final String ACCESS_TOKEN = "EAAGOLCaZBPtEBABPvBP96utjMCRO4a72czCIC9ZAtVc4BpDRgM4gFckK7q3ZAC4cuBB7Hj7AqAB1eGutSajKm5WCMZBMB5BasFNrFJ4ohrtvNhIEt1LUc8Yvo90GhB7f7Xvv6KZB4nZCqdPqs5WGVqp0oWQVdJw8wyw4XBBdLtwAZDZD";

    private Context context;


    /**
     * Facebook 인증 객체
     */
    private FacebookClient myFacebook;

    private boolean isLogin;

    public Facebook(Context context) {
        this.context = context;
    }

    /**
     * Facevook으로 로그인 한다.
     * @return : 로그인 성공시 true
     */
    public void auth(final After after){

        new Thread(new Runnable() {
            @Override
            public void run() {
                //Facebook 로그인
                myFacebook = new DefaultFacebookClient(ACCESS_TOKEN, Version.LATEST);

                // 로그인이 성공했는지 체크하기
                // 로그인된 계정의 정보를 가져온다.
                User me = myFacebook.fetchObject("me", User.class);
                Log.d("FACEBOOK", me.getName()+" 계정으로 로그인 함");

                isLogin = me!=null;

                if(isLogin){
                    after.doAfter(context);
                    if(context instanceof MainActivity) {
                        ((MainActivity) context).setTimeline();
                    }
                    else if (context instanceof WritePostActivity){
                        ((WritePostActivity)context).writePost();
                    }
                }
            }

        }).start();
    }

    public boolean isLogin(){
        return isLogin;
    }

    public void publishing(final String message, final After after){

        new Thread(new Runnable() {
            @Override
            public void run() {
                FacebookType facebookType = myFacebook.publish("me/feed", FacebookType.class, Parameter.with("message", message));
                after.doAfter(context);
            }
        }).start();
    }


    public void getTimeline(final TimelineSerializable timelineSerializable){

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 나의 타임라인에서 포스트 들을 가져온다.
                Connection<Post> feeds = myFacebook.fetchConnection("me/feed", Post.class, Parameter.with("fields","from,likes,message,story,link"));

                List<Post> postList = new ArrayList<Post>();

                for(List<Post> posts : feeds){
                    postList.addAll(posts);
                }
                timelineSerializable.serialize(postList);
            }

        }).start();
    }

    public interface TimelineSerializable{
        public void serialize(List<Post> posts);
    }
    public interface  After {
        public void doAfter(Context context);
    }
}
