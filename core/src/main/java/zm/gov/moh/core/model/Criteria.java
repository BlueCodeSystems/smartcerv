package zm.gov.moh.core.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Criteria {

    Class mClass;
    Map<String, String> condition;
    ArrayList<Boolean> evaluations;

    public Criteria(Map<String, String> condition){

        evaluations = new ArrayList<>();

        this.condition = condition;
    }

    public boolean evaluate(Object sample) throws Exception{

        mClass = sample.getClass();

        for (Map.Entry key : condition.entrySet())  {
            String hashkey = key.getKey().toString();

            Field field = mClass.getDeclaredField(hashkey);
            field.setAccessible(true);
            String value = (String) field.get(sample);

            if(condition.get(hashkey).equals(value))
                evaluations.add(true);
            else
                evaluations.add(false);
        }

        return !evaluations.contains(false);
    }
}
