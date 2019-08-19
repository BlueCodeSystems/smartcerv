package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

@Entity(tableName = "allergy_reaction")
public class AllergyReaction {

    @ColumnInfo(name = "allergy_reaction_id")
    @Json(name = "allergy_reaction_id")
    private long allergyReactionId;

    @ColumnInfo(name = "allergy_id")
    @Json(name = "allergy_id")
    private long allergyId;

    @ColumnInfo(name = "reaction_concept_id")
    @Json(name = "reaction_concept_id")
    private long reactionConceptId;

    @ColumnInfo(name = "reaction_concept_id")
    @Json(name = "reaction_concept_id")
    private String reactionNonCoded;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    public long getAllergyReactionId() {
        return allergyReactionId;
    }

    public void setAllergyReactionId(long allergyReactionId) {
        this.allergyReactionId = allergyReactionId;
    }

    public long getAllergyId() {
        return allergyId;
    }

    public void setAllergyId(long allergyId) {
        this.allergyId = allergyId;
    }

    public long getReactionConceptId() {
        return reactionConceptId;
    }

    public void setReactionConceptId(long reactionConceptId) {
        this.reactionConceptId = reactionConceptId;
    }

    public String getReactionNonCoded() {
        return reactionNonCoded;
    }

    public void setReactionNonCoded(String reactionNonCoded) {
        this.reactionNonCoded = reactionNonCoded;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
