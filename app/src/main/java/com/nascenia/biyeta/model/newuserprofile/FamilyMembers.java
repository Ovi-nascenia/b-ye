
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class FamilyMembers implements Serializable {

    @SerializedName("father")
    @Expose
    private Father father;
    @SerializedName("mother")
    @Expose
    private Mother mother;
    @SerializedName("other")
    @Expose
    private Other other;
    @SerializedName("number_of_brothers")
    @Expose
    private int numberOfBrothers;
    @SerializedName("brothers")
    @Expose
    private ArrayList<Brother> brothers = null;
    @SerializedName("number_of_sisters")
    @Expose
    private int numberOfSisters;
    @SerializedName("sisters")
    @Expose
    private ArrayList<Sister> sisters = null;

    @SerializedName("number_of_dada")
    @Expose
    private int numberOfDada;
    @SerializedName("dada")
    @Expose
    private List<Dada> dadas = null;

    @SerializedName("number_of_kaka")
    @Expose
    private int numberOfKaka;
    @SerializedName("kaka")
    @Expose
    private List<Kaka> kakas = null;

    @SerializedName("number_of_nana")
    @Expose
    private int numberOfNana;
    @SerializedName("nana")
    @Expose
    private List<Nana> nanas = null;

    @SerializedName("number_of_mama")
    @Expose
    private int numberOfMama;
    @SerializedName("mama")
    @Expose
    private List<Mama> mamas = null;

    @SerializedName("number_of_fufa")
    @Expose
    private int numberOfFufa;
    @SerializedName("fufa")
    @Expose
    private List<Fufa> fufas = null;

    @SerializedName("number_of_khalu")
    @Expose
    private int numberOfKhalu;
    @SerializedName("khalu")
    @Expose
    private List<Khalu> khalus = null;

    @SerializedName("number_of_others")
    @Expose
    private int numberOfOthers;
    @SerializedName("others")
    @Expose
    private List<Other> others = null;

    @SerializedName("number_of_child")
    @Expose
    private int numberOfChild;
    @SerializedName("child_lives_with_you")
    @Expose
    private boolean childLivesWithYou;


    private final static long serialVersionUID = -8373681973129498886L;

    public Father getFather() {
        return father;
    }

    public void setFather(Father father) {
        this.father = father;
    }

    public FamilyMembers withFather(Father father) {
        this.father = father;
        return this;
    }

    public Mother getMother() {
        return mother;
    }

    public void setMother(Mother mother) {
        this.mother = mother;
    }

    public FamilyMembers withMother(Mother mother) {
        this.mother = mother;
        return this;
    }


    public Other getOther() {
        return other;
    }

    public void setOther(Other other) {
        this.other = other;
    }

    public FamilyMembers withOther(Other other) {
        this.other = other;
        return this;
    }

    public int getNumberOfBrothers() {
        return numberOfBrothers;
    }

    public void setNumberOfBrothers(int numberOfBrothers) {
        this.numberOfBrothers = numberOfBrothers;
    }

    public FamilyMembers withNumberOfBrothers(int numberOfBrothers) {
        this.numberOfBrothers = numberOfBrothers;
        return this;
    }

    public ArrayList<Brother> getBrothers() {
        return brothers;
    }

    public void setBrothers(ArrayList<Brother> brothers) {
        this.brothers = brothers;
    }

    public FamilyMembers withBrothers(ArrayList<Brother> brothers) {
        this.brothers = brothers;
        return this;
    }

    public int getNumberOfSisters() {
        return numberOfSisters;
    }

    public void setNumberOfSisters(int numberOfSisters) {
        this.numberOfSisters = numberOfSisters;
    }

    public FamilyMembers withNumberOfSisters(int numberOfSisters) {
        this.numberOfSisters = numberOfSisters;
        return this;
    }

    public ArrayList<Sister> getSisters() {
        return sisters;
    }

    public void setSisters(ArrayList<Sister> sisters) {
        this.sisters = sisters;
    }

    public FamilyMembers withSisters(ArrayList<Sister> sisters) {
        this.sisters = sisters;
        return this;
    }


    public int getNumberOfChild() {
        return numberOfChild;
    }

    public void setNumberOfChild(int numberOfChild) {
        this.numberOfChild = numberOfChild;
    }

    public FamilyMembers withNumberOfChild(int numberOfChild) {
        this.numberOfChild = numberOfChild;
        return this;
    }


    public boolean isChildLivesWithYou() {
        return childLivesWithYou;
    }

    public void setChildLivesWithYou(boolean childLivesWithYou) {
        this.childLivesWithYou = childLivesWithYou;
    }

    public FamilyMembers withChildLivesWithYou(boolean childLivesWithYou) {
        this.childLivesWithYou = childLivesWithYou;
        return this;
    }


    public int getNumberOfDada() {
        return numberOfDada;
    }

    public void setNumberOfDada(int numberOfDada) {
        this.numberOfDada = numberOfDada;
    }

    public List<Dada> getDadas() {
        return dadas;
    }

    public void setDadas(List<Dada> dadas) {
        this.dadas = dadas;
    }

    public int getNumberOfKaka() {
        return numberOfKaka;
    }

    public void setNumberOfKaka(int numberOfKaka) {
        this.numberOfKaka = numberOfKaka;
    }

    public List<Kaka> getKakas() {
        return kakas;
    }

    public void setKakas(List<Kaka> kakas) {
        this.kakas = kakas;
    }

    public int getNumberOfNana() {
        return numberOfNana;
    }

    public void setNumberOfNana(int numberOfNana) {
        this.numberOfNana = numberOfNana;
    }

    public List<Nana> getNanas() {
        return nanas;
    }

    public void setNanas(List<Nana> nanas) {
        this.nanas = nanas;
    }

    public int getNumberOfMama() {
        return numberOfMama;
    }

    public void setNumberOfMama(int numberOfMama) {
        this.numberOfMama = numberOfMama;
    }

    public List<Mama> getMamas() {
        return mamas;
    }

    public void setMamas(List<Mama> mamas) {
        this.mamas = mamas;
    }

    public int getNumberOfFufa() {
        return numberOfFufa;
    }

    public void setNumberOfFufa(int numberOfFufa) {
        this.numberOfFufa = numberOfFufa;
    }

    public List<Fufa> getFufas() {
        return fufas;
    }

    public void setFufas(List<Fufa> fufas) {
        this.fufas = fufas;
    }

    public int getNumberOfKhalu() {
        return numberOfKhalu;
    }

    public void setNumberOfKhalu(int numberOfKhalu) {
        this.numberOfKhalu = numberOfKhalu;
    }

    public int getNumberOfOthers() {
        return numberOfOthers;
    }

    public void setNumberOfOthers(int numberOfOthers) {
        this.numberOfOthers = numberOfOthers;
    }

    public List<Khalu> getKhalus() {
        return khalus;
    }

    public void setKhalus(List<Khalu> khalus) {
        this.khalus = khalus;
    }

    public List<Other> getOthers() {
        return others;
    }

    public void setOthers(List<Other> others) {
        this.others = others;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
