package zm.gov.moh.common.base;

public class Utils {

    public static String inspectObsValue(String value){

        if(value.equalsIgnoreCase("True"))
            return "Yes";
        else if(value.equalsIgnoreCase("False"))
            return "No";
        else if(value == null)
            return "N/A";
        else
            return value;
    }
}