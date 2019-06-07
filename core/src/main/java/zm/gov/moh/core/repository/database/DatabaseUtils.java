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
        StringBuilder combinedTerm  = new StringBuilder();
        StringBuilder segmentTerm  = new StringBuilder();

        for (String term:terms) {
            combinedTerm.append(term);
            splitTerm.append(term).append(" OR ");
        }


        for(int subtermLen = 2; subtermLen <= combinedTerm.length(); subtermLen++)
            segmentTerm.append(combinedTerm.substring(0,subtermLen)).append(" OR ");

        return segmentTerm.toString(); //+ splitTerm.toString();
    }
}
