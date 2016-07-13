package cn.kyle.GRRemotePackage;

/**
 * Created by kyle on 16/7/13.
 */
public class UserInfo {
    private String id;
    private String name;
    private int age;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "id:"+this.getId()+",Name:"+this.getName()+",Age:"+this.getAge()+",Add:"+this.getAddress();
    }
}
