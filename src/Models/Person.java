/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;

/**
 *
 * @author asus
 */

public abstract class Person implements Serializable {
    
    String name;
    int age;
    String gender;
    String address;
    String phone;
    public Person() {
    }
    public Person(String name, int age, String gender, String address, String phone) {
        
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "person{" + "name=" + name + ", age=" + age + ", gender=" + gender + ", address=" + address + ", phone=" + phone + '}';
    }
    
}
