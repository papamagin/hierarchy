package hierarchy.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import hierarchy.client.ActionInfo;
import hierarchy.helper.JsonSerialization;

/**
 * Tree realisation due to instructions.
 * Methods look miserably because of business logic.
 */

public class HierarchyTree extends Tree<NodeData> {
    private Map<String, Node<NodeData>> nodes;

    public HierarchyTree() {
        this.root = null;
        this.nodes = new HashMap<>();
    }

    /**
     * HashSet/Map.contains() +  HashSet/Map.put() + TreeMap.get() + TreeMap.put() = O(1) + O(log N)
     * Complexity O(log N)
     */
    @Override
    public void addNode(final NodeData nodeData) throws Exception {
        final String id = nodeData.getId();
        final String name = nodeData.getName();
        if (isNullOrEmpty(id) || isNullOrEmpty(name)) {
            throw new RuntimeException("Name and ID must be specified and not empty strings");
        }
        if (nodes.keySet().contains(id)) {
            throw new RuntimeException("No two nodes in the tree can have the same ID");
        }
        if (nodeData.getParentId() == null || nodeData.getParentId().isEmpty()) {
            setRoot(nodeData);
        } else if (nodes.keySet().contains(nodeData.getParentId())) {
            Node<NodeData> targetNode = nodes.get(nodeData.getParentId());
            if (targetNode.getChildren().get(name) != null) {
                throw new RuntimeException("Two sibling nodes cannot have the same name");
            }
            Node<NodeData> newNode = new Node<NodeData>().data(nodeData).parent(targetNode);
            nodes.put(id, newNode);
            targetNode.getChildren().put(name, newNode);
        } else {
            throw new RuntimeException("Add a child node to nonexistent parent");
        }
    }

    /**
     * Complexity O(log N)
     */
    @Override
    public void deleteNode(final NodeData nodeData) throws Exception {
        final String id = nodeData.getId();
        if (id == null || id.isEmpty()) {
            throw new RuntimeException("ID must be specified");
        }
        if (!nodes.keySet().contains(id)) {
            throw new RuntimeException("Node must exist");
        }
        if (!nodes.get(id).getChildren().isEmpty()) {
            throw new RuntimeException("Node must not have children");
        }
        final Node<NodeData> toDelete = nodes.get(id);
        nodes.remove(id);
        if (toDelete.getParent() != null) {
            toDelete.getParent().getChildren().remove(toDelete.getData().getName());
        } else {
            root = null;
        }
    }

    /**
     * Due to cycles check compexity O(n)
     */
    @Override
    public void moveNode(final NodeData nodeData) throws Exception {
        final String id = nodeData.getId();
        final String newParentId = nodeData.getParentId();
        final Node<NodeData> current = nodes.get(id);
        final Node<NodeData> newParent = nodes.get(newParentId);
        if (isNullOrEmpty(id) || isNullOrEmpty(newParentId)) {
            throw new RuntimeException("ID and new parent ID must be specified and not empty strings");
        }
        if (!nodes.keySet().contains(id) || !nodes.keySet().contains(newParentId)) {
            throw new RuntimeException("Both nodes must exist");
        }
        if (id.equals(newParentId)) {
            throw new RuntimeException("Move must not create a cycle in the tree");
        }
        if (getAllSubNodeIds(current).contains(newParentId)) {
            throw new RuntimeException("Move must not create a cycle in the tree");
        }
        final Set<String> newNodeChildrenNames = newParent.getChildren().values()
                .stream()
                .map(Node::getData)
                .map(NodeData::getName)
                .collect(Collectors.toSet());
        if (newNodeChildrenNames.contains(current.getData().getName())) {
            throw new RuntimeException("The name of the node to be moved must not be the same as those" +
                    " of any of the new parent's other children");
        }
        current.getParent().getChildren().remove(current.getData().getName());
        newParent.getChildren().put(current.getData().getName(), current);
        current.getData().setParentId(newParentId);
    }

    /**
     * Due to DFS compexity O(n)
     */
    @Override
    public List<NodeData> applyQuery(ActionInfo query) {
        List<NodeData> res = new ArrayList<>();
        final int minDepth = Optional.ofNullable(query.getMinDepth()).orElse(0);
        final int maxDepth = Optional.ofNullable(query.getMaxDepth()).orElse(Integer.MAX_VALUE);
        final Set<String> namesFilter = new HashSet<>(query.getNames());
        final Set<String> idsFilter = new HashSet<>(query.getIds());
        final List<String> rootIds = query.getRootIds();
        if (rootIds.isEmpty()) {
            rootIds.add(root.getData().getId());
        }
        final List<String> existedRootIds = query.getRootIds()
                .stream()
                .filter(id -> nodes.keySet().contains(id))
                .collect(Collectors.toList());
        existedRootIds.forEach(id ->
                res.addAll(
                        applyQueryForNode(
                                nodes.get(id),
                                minDepth,
                                maxDepth,
                                0,
                                namesFilter,
                                idsFilter
                        )
                )
        );

        return res;
    }

    private List<NodeData> applyQueryForNode(
            final Node<NodeData> node,
            final int minDepth,
            final int maxDepth,
            final int currDepth,
            final Set<String> namesFilter,
            final Set<String> idsFilter) {
        final List<NodeData> res = new ArrayList<>();
        if (minDepth <= 0) {
            final boolean fitsByName = namesFilter.contains(node.getData().getName()) || namesFilter.isEmpty();
            final boolean fitsById = idsFilter.contains(node.getData().getId()) || idsFilter.isEmpty();
            if (fitsById && fitsByName) {
                res.add(node.getData());
            }
        }
        if (currDepth == maxDepth || node.getChildren().isEmpty()) {
            return res;
        } else {
            node.getChildren().values().forEach(n ->
                    res.addAll(
                            applyQueryForNode(
                                    n,
                                    minDepth - 1,
                                    maxDepth,
                                    currDepth + 1,
                                    namesFilter,
                                    idsFilter
                            )
                    ));
        }

        return res;
    }

    private void setRoot(final NodeData nodeData) {
        Node<NodeData> newRoot = new Node<NodeData>().data(nodeData);
        if (this.root != null) {
            throw new RuntimeException("Root already exists");
        }
        this.root = newRoot;
        nodes.put(nodeData.getId(), newRoot);
    }

    @Override
    public void print() {
        if (root != null) {
            printSubTree(root, "-----");
        } else {
            System.err.println("Empty tree.");
        }
    }

    private void printSubTree(final Node<NodeData> node, final String prefix) {
        System.err.println(prefix + JsonSerialization.serializeToJson(node.getData()));
        if (!node.getChildren().isEmpty()) {
            node.getChildren().values().forEach(c -> printSubTree(c, prefix + prefix));
        }
    }

    private Set<String> getAllSubNodeIds(final Node<NodeData> currentNode) {
        final Set<String> ids = new HashSet<>();
        if (!currentNode.getChildren().isEmpty()) {
            ids.addAll(
                    currentNode.getChildren().values()
                            .stream()
                            .map(Node::getData)
                            .map(NodeData::getId)
                            .collect(Collectors.toSet())
            );
            currentNode.getChildren().values().forEach(n -> ids.addAll(getAllSubNodeIds(n)));
        }
        return ids;
    }

    private boolean isNullOrEmpty(final String s) {
        return s == null || s.isEmpty();
    }
}
