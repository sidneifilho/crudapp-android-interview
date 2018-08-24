package com.sidnei.crudapp.model;

import java.io.Serializable;

public class Person {

    public enum SEX{ FEMALE, MALE, OTHER}

    private int id;
    private String name;
    private String cpf;
    private String cep;
    private String uf;
    private String address;
    private SEX sex;

    public Person(){
        clearValues();
    }

    public Person(String name, String cpf, String cep, String uf, String address, SEX sex){
        this.name = name;
        this.cpf = cpf;
        this.cep = cep;
        this.uf  = uf;
        this.address = address;
        this.sex = sex;
    }

    public Person(int id, String name, String cpf, String cep, String uf, String address, SEX sex){
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.cep = cep;
        this.uf  = uf;
        this.address = address;
        this.sex = sex;
    }

    /// @todo create function to authenticate CPF and CEP

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf(){
        return uf;
    }

    public void setUf(String uf){
        this.uf = uf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SEX getSex() {
        return sex;
    }

    public void setSex(SEX sex) {
        this.sex = sex;
    }

    public void setSex(String sex){
        switch (sex){
            case "f":
                this.sex = SEX.FEMALE;
                break;
            case "m":
                this.sex = SEX.MALE;
                break;
            case "o":
                this.sex = SEX.OTHER;
                break;
            default:
                this.sex = SEX.OTHER;
                break;
        }
    }

    public void clearValues(){
        id = 0;
        name = "";
        cpf = "";
        cep = "";
        uf = "";
        address = "";
        sex = SEX.OTHER;
    }

    @Override
    public String toString(){
        /// @todo implements
        return "";
    }

    public void parseFromString(String personStr){
        /// @todo implements
    }
}
