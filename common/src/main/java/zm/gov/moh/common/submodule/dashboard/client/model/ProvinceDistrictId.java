package zm.gov.moh.common.submodule.dashboard.client.model;

public class ProvinceDistrictId {

   private Long provinceId;
   private Long districtId;

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public Long getProvinceId() {
        return provinceId;
    }
}
