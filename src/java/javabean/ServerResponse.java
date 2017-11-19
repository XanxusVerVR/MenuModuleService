package javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ServerResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;

    public ServerResponse() {
    }

    /**
     *
     * @param status
     */
    public ServerResponse(Boolean status) {
        super();
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("status", status).toString();
    }

}
