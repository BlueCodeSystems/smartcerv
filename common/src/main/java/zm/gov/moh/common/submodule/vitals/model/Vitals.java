package zm.gov.moh.common.submodule.vitals.model;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Obs;

public class Vitals {

    double height;
    double weight;
    double temperature;
    double pulse;
    double respiratory;
    int systolicBloodPressure;
    int diastolicBloodPressure;
    double bloodOxygen;

    final long[] conceptIds = {5090, 5089, 5088, 5087, 5242, 5085,5086,5092};

        public Vitals(List<Obs> observations){


            //for(Obs getObs :observations)
                //if(getObs.concept_id == )

        }
}
