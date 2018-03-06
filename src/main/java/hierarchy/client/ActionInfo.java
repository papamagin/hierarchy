package hierarchy.client;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Fields represent action from test client request
 */

public class ActionInfo {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("parent_id")
    private String parentId = null;

    @JsonProperty("new_parent_id")
    private String newParentId = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("min_depth")
    private Integer minDepth = null;

    @JsonProperty("max_depth")
    private Integer maxDepth = null;

    @JsonProperty("names")
    private List<String> names = new ArrayList<>();

    @JsonProperty("ids")
    private List<String> ids = new ArrayList<>();

    @JsonProperty("root_ids")
    private List<String> rootIds = new ArrayList<>();

    public void setId(String id) {
        this.id = id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNewParentId(String newParentId) {
        this.newParentId = newParentId;
    }

    public void setMinDepth(Integer minDepth) {
        this.minDepth = minDepth;
    }

    public void setMaxDepth(Integer maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public void setRootIds(List<String> rootIds) {
        this.rootIds = rootIds;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public String getNewParentId() {
        return newParentId;
    }

    public Integer getMinDepth() {
        return minDepth;
    }

    public Integer getMaxDepth() {
        return maxDepth;
    }

    public List<String> getNames() {
        return names;
    }

    public List<String> getIds() {
        return ids;
    }

    public List<String> getRootIds() {
        return rootIds;
    }
}
