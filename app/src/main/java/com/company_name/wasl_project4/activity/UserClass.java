package com.company_name.wasl_project4.activity;

public class UserClass {

    String ID;
    private String name;
    private String email;
    private String username;
    private String phone_number;
    private String password;
    private String deanship;
    private String type;
    private String imgUrl;


    public UserClass(){}

    public UserClass ( String name, String username , String email , String phone , String password , String deanship ,String type ) {
        this.name=name;
        this.username =username;
        this.email=email;
        this.phone_number=phone;
        this.password=password;
        this.deanship=deanship;
        this.type=type;
    }

    public UserClass (String username , String email , String phone , String password  ,String type ) {
        this.name=name;
        this.username =username;
        this.email=email;
        this.phone_number=phone;
        this.password=password;
        this.type=type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPhone_number (){return this.phone_number ;}
    public String getUsername (){return this.username ;}
    public String getPassword (){return this.password ;}
    public String getName (){return this.name;}
    public String getDeanship (){return this.deanship ;}
    public String getType (){return this.type ;}
    public String getEmail () {return this.email ;}


    public void setName(String name ) {this.name=name;}
    public void setUsername(String username) {this.username = username; }
    public void setEmail(String email ){this.email=email;}
    public void setPhone_number(String phone ){this.phone_number=phone;}
    public void setPassword(String password ){this.password=password;}
    public void setDeanship(String deanship ){this.deanship=deanship;}
    public void setType(String type ){this.type=type;}


}
