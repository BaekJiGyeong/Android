package com.ktds.jgbaek.practice02.db;

import com.ktds.jgbaek.practice02.vo.AddressVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 206-008 on 2016-06-08.
 */
public class AddressDB {

    private static Map<String, AddressVO> db =new HashMap<String,AddressVO>();

    public static void addAddress(String index, AddressVO addressVO) {
        db.put(index, addressVO);
    }

    public static AddressVO getAddress(String name){
        Iterator<String> keys = db.keySet().iterator();
        String key = "";
        AddressVO addressVO = null;
        while (keys.hasNext()){
            key = keys.next();
            addressVO = db.get(key);
            if(name.equals(addressVO.getName())){
                return addressVO;
            }
        }
        return null;
    }

    public static List<String> getIndexes() {
        Iterator<String> keys = db.keySet().iterator();

        List<String> keyList = new ArrayList<String>();
        String key="";
        while (keys.hasNext()){
            key=keys.next();
            keyList.add(key);
        }
        return keyList;
    }

    public static List<String> getNames() {
        Iterator<String> keys = db.keySet().iterator();

        List<String> nameList = new ArrayList<String>();
        String key="";
        String name="";
        AddressVO addressVO = null;
        while (keys.hasNext()){
            key = keys.next();
            addressVO = db.get(key);
            name = addressVO.getName();
            nameList.add(name);
        }
        return nameList;
    }

}
