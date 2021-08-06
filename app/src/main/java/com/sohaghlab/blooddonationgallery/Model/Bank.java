package com.sohaghlab.blooddonationgallery.Model;

public class Bank {
    String bankname;
    String bankphone;
    String banklocation;


    public Bank() {
    }


    public Bank(String bankname, String bankphone, String banklocation) {
        this.bankname = bankname;
        this.bankphone = bankphone;
        this.banklocation = banklocation;
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
