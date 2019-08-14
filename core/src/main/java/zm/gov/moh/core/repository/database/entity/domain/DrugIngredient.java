package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "drug_ingredient")
public class DrugIngredient {

    @PrimaryKey
    @ColumnInfo(name = "drug_id")
    @Json(name = "drug_id")
    private long drug_Id;

    @ColumnInfo(name = "ingredient_id")
    @Json(name = "ingredient_id")
    private long ingredientId;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    @ColumnInfo(name = "strength")
    @Json(name = "strength")
    private String strength;

    @ColumnInfo(name = "units")
    @Json(name = "units")
    private int units;

    public long getDrug_Id() {
        return drug_Id;
    }

    public void setDrug_Id(long drug_Id) {
        this.drug_Id = drug_Id;
    }

    public long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
