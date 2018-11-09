package zm.gov.moh.common.submodule.form.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FormData<K,V> implements Serializable {

    private Map<K,V> map;

    public FormData(){
        map = new HashMap<>();
    }

    public Map<K, V> getMap() {
        return map;
    }

    public void put(K key, V vaule){
        map.put(key,vaule);
    }

    public V get(K key){
        return map.get(key);
    }
}
