package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

@Entity(tableName = "order_type_class_map")
public class OrderTypeClassMap {

    @PrimaryKey
    @ColumnInfo(name = "order_type_id")
    @Json(name = "order_type_id")
    private Long orderTypeId;

    @ColumnInfo(name = "concept_class_id")
    @Json(name = "order_type_id")
    private Long conceptClassId;

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getConceptClassId() {
        return conceptClassId;
    }

    public void setConceptClassId(Long conceptClassId) {
        this.conceptClassId = conceptClassId;
    }
}
