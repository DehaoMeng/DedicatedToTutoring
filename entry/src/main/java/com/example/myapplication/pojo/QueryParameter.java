package com.example.myapplication.pojo;

import java.util.HashMap;
import java.util.Set;

public class QueryParameter {
    HashMap<String,String> Parameter;
    public QueryParameter(){
        Parameter = new HashMap<>();
    }
    public void add(String key,String value){
        Parameter.put(key,value);
    }
    public Set<String> getkeys(){
        return Parameter.keySet();
    }
    public String getvalue(String key){
        return Parameter.get(key);
    }
    public boolean isempty(){
        return Parameter.isEmpty();
    }
}
