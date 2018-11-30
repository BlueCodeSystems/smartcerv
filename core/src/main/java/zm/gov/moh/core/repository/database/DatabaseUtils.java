package zm.gov.moh.core.repository.database;


import zm.gov.moh.core.utils.Supplier;

public class DatabaseUtils {

    public static long generateLocalId(Supplier<Long> getMaxIndex){

        final Long PARTITION_VALUE = Long.MAX_VALUE - 50000;

        final Long MAX_VALUE = getMaxIndex.get();

        if(MAX_VALUE >= PARTITION_VALUE)
            return MAX_VALUE + 1;
        else
            return PARTITION_VALUE;

    }
}
