package com.sohaghlab.blooddonationgallery.Model;

public class Bank {
    String bankname;
    String bankphone;
    String banklocation;
    String bankcity;


    public Bank() {
    }


    public Bank(String bankname, String bankphone, String banklocation, String bankcity) {
        this.bankname = bankname;
        this.bankphone = bankphone;
        this.banklocation = banklocation;
        this.bankcity= bankcity;
    }

    public String getBankcity() {
        return bankcity;
    }

    public void setBankcity(String bankcity) {
        this.bankcity = bankcity;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankphone() {
        return bankphone;
    }

    public void setBankphone(String bankphone) {
        this.bankphone = bankphone;
    }

    public String getBanklocation() {
        return banklocation;
    }

    public void setBanklocation(String banklocation) {
        this.banklocation = banklocation;
    }
}
