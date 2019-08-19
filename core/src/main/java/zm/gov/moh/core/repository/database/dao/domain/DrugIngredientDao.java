package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.DrugIngredient;

@Dao
public interface DrugIngredientDao {

    @Query("SELECT * FROM drug_ingredient")
    DrugIngredient getDrugIngredient();

    @Insert
    void insert(DrugIngredient drugIngredient);
}
