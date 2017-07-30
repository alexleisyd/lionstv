package au.com.lionslogistics.lionstv.service;

/**
 * Created by alex-daphne on 29/07/2017.
 * All rights reserved
 */

public class UserService {
    private static UserService instance=null;
    private String token="Basic YWRtaW46bHVja3kxMTA5";
    private UserService(){

    }

    public static UserService getInstance(){
        if (instance==null){
            instance=new UserService();
        }
        return instance;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token=token;
    }

}
