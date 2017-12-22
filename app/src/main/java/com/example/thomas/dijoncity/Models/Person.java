package com.example.thomas.dijoncity.Models;

import android.database.Cursor;

import java.util.Date;

/**
 * Created by Thomas on 04/12/2017.
 */

public class Person {
    private int id;
    private String lastname;
    private String firstname;
    private int age;
    private float weight;
    private Date updateDate;
    private String login;

    //region Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    //endregion

    //Cette méthode permet de convertir un cursor en une personne
    public static Person cursorToPerson(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //On créé une personne
        Person person = new Person();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        person.setId(c.getInt(0));
        person.setLastname(c.getString(1));
        person.setFirstname(c.getString(2));
        person.setAge(c.getInt(3));
        person.setWeight(c.getFloat(4));
        person.setUpdateDate(new Date(c.getLong(5)));
        person.setLogin(c.getString(6));

        //On retourne la personne
        return person;
    }
}
