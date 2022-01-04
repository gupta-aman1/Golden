package com.business.goldenfish.recharges;

public class Model_Class_For_Prepaid_Operator {
    public Model_Class_For_Prepaid_Operator()
    {

    }

    public String getOperatorName() {
        return OperatorName;
    }

    public void setOperatorName(String operatorName) {
        OperatorName = operatorName;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    // public  int id,ServiceId;
    public String OperatorName,ServiceId,Status,Image,Id;

}
