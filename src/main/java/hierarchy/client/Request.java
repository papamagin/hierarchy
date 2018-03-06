package hierarchy.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request from test client (assumes that program will never receive invalid JSON as a test case)
 * Looks weird to simplify deserialization
 */

public class Request {
    @JsonProperty("add_node")
    private ActionInfo addNode = null;

    @JsonProperty("delete_node")
    private ActionInfo deleteNode = null;

    @JsonProperty("move_node")
    private ActionInfo moveNode = null;

    @JsonProperty("query")
    private ActionInfo query = null;

    public void setAddNode(ActionInfo addNode) {
        this.addNode = addNode;
    }

    public void setDeleteNode(ActionInfo deleteNode) {
        this.deleteNode = deleteNode;
    }

    public void setMoveNode(ActionInfo moveNode) {
        this.moveNode = moveNode;
    }

    public void setQuery(ActionInfo query) {
        this.query = query;
    }

    public ActionInfo getAddNode() {
        return addNode;
    }

    public ActionInfo getDeleteNode() {
        return deleteNode;
    }

    public ActionInfo getMoveNode() {
        return moveNode;
    }

    public ActionInfo getQuery() {
        return query;
    }

    @JsonIgnore
    public boolean isAdd() {
        return addNode != null;
    }

    @JsonIgnore
    public boolean isDelete() {
        return deleteNode != null;
    }

    @JsonIgnore
    public boolean isMove() {
        return moveNode != null;
    }

    @JsonIgnore
    public boolean isQuery() {
        return query != null;
    }
}
