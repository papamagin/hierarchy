package hierarchy;

import hierarchy.client.QueryResponse;
import hierarchy.client.Request;
import hierarchy.client.OperationResponse;
import hierarchy.helper.JsonSerialization;
import hierarchy.model.HierarchyTree;
import hierarchy.model.NodeData;
import hierarchy.model.Tree;

public class RequestProcessorImpl implements RequestProcessor {
    private final Tree tree = new HierarchyTree();

    @Override
    @SuppressWarnings("unchecked")
    public void processRequest(final String reqString) {
        try {
            final Request req = JsonSerialization.parseJson(reqString, Request.class);
            if (req.isQuery()) {
                final QueryResponse res = new QueryResponse().nodes(tree.applyQuery(req.getQuery()));
                System.out.println(JsonSerialization.serializeToJson(res));
            } else {
                if (req.isAdd()) {
                    tree.addNode(
                            new NodeData()
                                    .id(req.getAddNode().getId())
                                    .name(req.getAddNode().getName())
                                    .parentId(req.getAddNode().getParentId())
                    );
                }
                if (req.isDelete()) {
                    tree.deleteNode(
                            new NodeData().id(req.getDeleteNode().getId())
                    );
                }
                if (req.isMove()) {
                    tree.moveNode(
                            new NodeData()
                                    .id(req.getMoveNode().getId())
                                    .parentId(req.getMoveNode().getNewParentId())
                    );
                }
                System.out.println(JsonSerialization.serializeToJson(OperationResponse.OK));
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.out.println(JsonSerialization.serializeToJson(OperationResponse.FALSE));
        }
//        System.err.println(" ");
//        tree.print();
//        System.err.println(" ");
    }
}
