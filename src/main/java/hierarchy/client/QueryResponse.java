package hierarchy.client;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import hierarchy.model.NodeData;

public class QueryResponse {
    @JsonProperty
    private List<NodeData> nodes = new ArrayList<>();

    public void setNodes(List<NodeData> nodes) {
        this.nodes = nodes;
    }

    public List<NodeData> getNodes() {
        return nodes;
    }

    public QueryResponse nodes(List<NodeData> nodes) {
        this.nodes = nodes;
        return this;
    }
}
