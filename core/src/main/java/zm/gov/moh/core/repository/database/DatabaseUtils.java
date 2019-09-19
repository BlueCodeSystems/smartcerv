package zm.gov.moh.core.repository.database;


import zm.gov.moh.core.Constant;
import zm.gov.moh.core.utils.Supplier;

public class DatabaseUtils {

    public static long generateLocalId(Supplier<Long> getMaxIndex){

        final Long PARTITION_OFFSET = Constant.LOCAL_ENTITY_ID_OFFSET;

        final Long MAX_VALUE = getMaxIndex.get();

        if(MAX_VALUE != null && MAX_VALUE >= PARTITION_OFFSET)
            return MAX_VALUE + 1;
        else
            return PARTITION_OFFSET;

    }

    public static String buildSearchTerm(String... terms){

        StringBuilder splitTerm  = new StringBuilder();

        for (String term:terms)
            if(term != null) {

                int termLength = term.length();

                if (termLength == 2)
                    splitTerm.append(term).append(" OR ");
                else if (termLength > 2)
                    for (int i = 2; i <= termLength; i++)
                        splitTerm.append(term.substring(0, i)).append(" OR ");
            }

        if(terms.length == 1)
            return splitTerm.toString();
        return splitTerm.toString() + buildSearchTerm(terms[0]+" "+terms[1]);
    }
}
