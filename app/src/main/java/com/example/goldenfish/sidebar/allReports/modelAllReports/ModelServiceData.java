package com.example.goldenfish.sidebar.allReports.modelAllReports;

public class ModelServiceData {
    String ServiceId;

    public String getServiceId() {
        return ServiceId;
    }

    public ModelServiceData(String serviceId, String serviceName) {
        ServiceId = serviceId;
        ServiceName = serviceName;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    String ServiceName;
}
