package android.seoulaccord.beacon;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Hashtable;

//serializable class UserInformation
public class UserInformation implements Parcelable {
    public static final int CORRECT = 0;
    public static final int ID_NOT_FOUND = 1;
    public static final int PASSWORD_ERROR = 2;

    private static Hashtable userData_table = new Hashtable();
    private String id, password;
    private String name, birth, phone_num;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(birth);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    UserInformation() {
    }

    UserInformation(String id, String password, String name, String birth, String phone_num) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.phone_num = phone_num;
    }

    UserInformation(Parcel in) {
        id = in.readString();
        password = in.readString();
        name = in.readString();
        birth = in.readString();
        phone_num = in.readString();
    }

    //get userdata when login_user call func
    String[] getUserData() {
        String[] user_data = {id, password, name, birth, phone_num};
        return user_data;
    }

    //check if id and password are correct
    public static int checkUserData(String id, String password) {
        UserInformation user;
        if (userData_table.containsKey(id)) {
            user = (UserInformation)userData_table.get(id);
            return (user.password.equals(password)) ? CORRECT : PASSWORD_ERROR;
        } else {
            return ID_NOT_FOUND;
        }
    }

    //sign up new user
    public static boolean registerUserData(String id, String password, String name, String birth, String phone_num) {
        if (userData_table.containsKey(id)) return false;
        else {
            UserInformation new_user = new UserInformation(id, password, name, birth, phone_num);
            userData_table.put(id, new_user);
            return true;
        }
    }

    //return UserInformation Object(User Object)
    public static UserInformation getLoginUserData(String id) {
        return (UserInformation) userData_table.get(id);
    }

    public static final Creator<UserInformation> CREATOR = new Creator<UserInformation>() {
        @Override
        public UserInformation createFromParcel(Parcel source) {
            return new UserInformation(source);
        }

        @Override
        public UserInformation[] newArray(int size) {
            return new UserInformation[size];
        }
    };
}