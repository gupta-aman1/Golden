package com.business.goldenfish.ledgerreopt;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ModelMainLedger {


        @SerializedName("Statuscode")
        @Expose
        private String statuscode;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("Data")
        @Expose
        private List<ModelDataLedger> data = null;

        public String getStatuscode() {
            return statuscode;
        }

        public void setStatuscode(String statuscode) {
            this.statuscode = statuscode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<ModelDataLedger> getData() {
            return data;
        }

        public void setData(List<ModelDataLedger> data) {
            this.data = data;
        }

}
