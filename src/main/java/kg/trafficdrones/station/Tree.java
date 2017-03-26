package kg.trafficdrones.station;

abstract class Tree<T extends Comparable<T>> {
    private static final Tree EMPTY = new EmptyTree();

    abstract T value();

    abstract Tree<T> left();

    abstract Tree<T> right();

    abstract Tree<T> insert(T t);

    abstract T nearest(T t);

    abstract boolean isEmpty();

    @SuppressWarnings("unchecked")
    static <T extends Comparable<T>> Tree<T> empty() {
        return EMPTY;
    }

    private static class EmptyTree<T extends Comparable<T>> extends Tree<T> {

        private EmptyTree() {
        }

        @Override
        T value() {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override
        Tree<T> left() {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override
        Tree<T> right() {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override
        Tree<T> insert(T t) {
            return new BinarySearchTree<>(t, empty(), empty());
        }

        @Override
        T nearest(T t) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override
        boolean isEmpty() {
            return true;
        }
    }

    private static class BinarySearchTree<T extends Comparable<T>> extends Tree<T> {
        private final T value;
        private final Tree<T> left;
        private final Tree<T> right;

        BinarySearchTree(T value, Tree<T> left, Tree<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        @Override
        T value() {
            return value;
        }

        @Override
        Tree<T> left() {
            return left;
        }

        @Override
        Tree<T> right() {
            return right;
        }

        @Override
        BinarySearchTree<T> insert(T T) {
            if (T.compareTo(value) < 0) {
                return new BinarySearchTree<>(value, left.insert(T), right);
            } else if (T.compareTo(value) > 0) {
                return new BinarySearchTree<>(value, left, right.insert(T));
            } else {
                return new BinarySearchTree<>(T, left, right);
            }
        }

        @Override
        T nearest(T T) {
            if (T.compareTo(value) < 0) {
                return left.isEmpty() ? value : left.nearest(T);
            } else if (T.compareTo(value) > 0) {
                return right.isEmpty() ? value : right.nearest(T);
            }
            return value;
        }

        @Override
        boolean isEmpty() {
            return false;
        }
    }
}
