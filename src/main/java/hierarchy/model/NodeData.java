package hierarchy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeData {
    @JsonProperty("name")
    private String name = null;

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("parent_id")
    private String parentId = null;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public NodeData name(String name) {
        this.name = name;
        return this;
    }

    public NodeData id(String id) {
        this.id = id;
        return this;
    }

    public NodeData parentId(String parentId) {
        this.parentId = parentId;
        return this;
    }
}
