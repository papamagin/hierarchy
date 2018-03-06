package hierarchy.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import hierarchy.client.ActionInfo;

/**
 * Tree abstraction
 */

public abstract class Tree<T> {
    protected Node<T> root;

    public abstract void addNode(final T nodeData) throws Exception;

    public abstract void deleteNode(final T nodeData) throws Exception;

    public abstract void moveNode(final T nodeData) throws Exception;

    public abstract void print();

    public abstract List<T> applyQuery(final ActionInfo query);

    public static class Node<T> {
        private T data = null;
        private Node<T> parent = null;
        private Map<String, Node<T>> children = new TreeMap<>();

        public void setData(T data) {
            this.data = data;
        }

        public void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public void setChildren(Map<String, Node<T>> children) {
            this.children = children;
        }

        public T getData() {
            return data;
        }

        public Node<T> getParent() {
            return parent;
        }

        public Map<String, Node<T>> getChildren() {
            return children;
        }

        public Node<T> data(T data) {
            this.data = data;
            return this;
        }

        public Node<T> parent(Node<T> parent) {
            this.parent = parent;
            return this;
        }

        public Node<T> children(Map<String, Node<T>> children) {
            this.children = children;
            return this;
        }
    }
}

