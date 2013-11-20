package bbc.iplayer.spring.mashery.iodocs;


import bbc.iplayer.spring.mashery.iodocs.model.TypedParameter;

public class Page implements TypedParameter<Integer> {


    private int page;

    public Page(int page) {
        this.page = page;
    }

}
