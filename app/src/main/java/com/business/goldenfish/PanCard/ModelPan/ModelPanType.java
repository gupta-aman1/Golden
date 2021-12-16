package com.business.goldenfish.PanCard.ModelPan;

public class ModelPanType {
    String Id;

    @Override
    public String toString() {
        return "ModelPanType{" +
                "Id='" + Id + '\'' +
                ", OperatorName='" + OperatorName + '\'' +
                '}';
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOperatorName() {
        return OperatorName;
    }

    public void setOperatorName(String operatorName) {
        OperatorName = operatorName;
    }

    String OperatorName;
}
