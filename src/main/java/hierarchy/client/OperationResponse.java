package hierarchy.client;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response to test client
 */

public class OperationResponse {
    public static final OperationResponse OK = new OperationResponse().ok(true);
    public static final OperationResponse FALSE = new OperationResponse().ok(false);

    @JsonProperty("ok")
    private Boolean ok = null;

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Boolean getOk() {
        return ok;
    }

    public OperationResponse ok(final boolean ok) {
        this.ok = ok;
        return this;
    }
}
