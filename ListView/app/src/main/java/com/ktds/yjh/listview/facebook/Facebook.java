package com.ktds.yjh.listview.facebook;

import android.content.Context;
import android.util.Log;

import com.ktds.yjh.listview.MainActivity;
import com.ktds.yjh.listview.WritePostActivity;
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
 * Created by 206-022 on 2016-06-14.
 */
public class Facebook {

    /**
     * 인증과 관련된 상수들..
     */
    private static final String APP_ID = "437795256418001";
    private static final String APP_SECRET = "9073f49115fbcf6ef9aa912150555b68";
    // TOKEN 접속 시 얻는 거
    private static final String ACCESS_TOKEN = "EAAGOLCaZBPtEBAEQZBw2PnSTPh1FB60odZCf6TeXFtM1upMiSqjZCVYXZB9zptrZBbK8TSL8yHlV6sGI279XHPUJxGsz0ij6O86jAbqohk4QzGSBcfkbZAX7SoQjXZBmDIFZCv73YNhpGAOprtxOge6vtsdaSuzhBGsZAYG73wUqBcIwZDZD";
    private Context context;

    /**
     *  Facebook 인증 객체
     */
    private FacebookClient myFacebook;

    private boolean isLogin;

    public Facebook(Context context) {
        this.context = context;
    }

    /**
     * Facebook 으로 로그인 한다.
     * @return : 로그인 성공시 true
     */
    public void auth( final After after ) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Facebook Login
                myFacebook = new DefaultFacebookClient(ACCESS_TOKEN, Version.LATEST);

                // 로그인이 성공했는지 체크한다.
                // 로그인된 계정의 정보를 가져온다.
                User me = myFacebook.fetchObject("me", User.class);

                Log.d("FACEBOOK", me.getName() + " 계정으로 로그인 함.");

                isLogin = me != null;

                if( isLogin ) {
                    after.doAfter(context);
                    if ( context instanceof MainActivity ) {
                        ( (MainActivity) context).setTimeLine();
                    }
                    else if ( context instanceof WritePostActivity ) {
                        ( (WritePostActivity) context).writePost();
                    }
                }
            }
        }).start();

    }

    public boolean isLogin( ){
        return  isLogin;
    }



    public void getTimeLine(final TimelineSerializable timelineSerializable) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                // 나의 타임라인에서 포스트들을 가져온다.
                Connection<Post> feeds = myFacebook.fetchConnection("me/feed", Post.class, Parameter.with("fields", "id,from,likes,message,story, link"));

                List<Post> postList = new ArrayList<Post>();

                // 타임라인 정보들...
                for (List<Post> posts : feeds) {
                    postList.addAll(posts);


                    // 타임라인에 등록되어 있는 포스트 1건
//                    for (Post post : posts) {
//
//                        if(post == null){
//                            continue;
//                        }
//                        if( post.getMessage() != null ) {
//                            Log.d("FACEBOOK", post.getMessage());
//                        }
//                        if( post.getName() != null ) {
//                            Log.d("FACEBOOK", post.getName());
//                        }
//                        if( post.getStory() != null ) {
//                            Log.d("FACEBOOK", post.getStory());
//                        }
//                        if( post.getCreatedTime() != null ) {
//                            Log.d("FACEBOOK", post.getCreatedTime()+"" );
//                        }
//                        if( post.getLikesCount() != null ) {
//                            Log.d("FACEBOOK", post.getLikesCount()+"");
//                        }
//                    }
                }
                timelineSerializable.serialize(postList);
            }
        }).start();
    }


    public void publishing(final String message, final After after) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                FacebookType facebookType = myFacebook.publish("me/feed", FacebookType.class, Parameter.with("message", message));
                after.doAfter(context);
            }
        }).start();
    }

    public interface TimelineSerializable {
        public void serialize(List<Post> posts);
    }

    public interface After {
        public void doAfter(Context context);
    }


    public void getDetail(final String id, final DetailData detailData) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Post post = myFacebook.fetchObject(id, Post.class, Parameter.with("fields", "id,from,likes,message"));
                detailData.data(post);
            }
        }).start();
    }
    public interface DetailData {
        public void data(Post post);
    }
}
