package ru.toster.hacker_rank.problem_solving.medium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ivan Rovenskiy
 * 21 October 2020
 */
public class JavaVisitorPattern {
    enum Color {
        RED, GREEN
    }

    abstract static class Tree {
        private int value;
        private Color color;
        private int depth;

        public Tree(int value, Color color, int depth) {
            this.value = value;
            this.color = color;
            this.depth = depth;
        }

        public int getValue() {
            return value;
        }

        public Color getColor() {
            return color;
        }

        public int getDepth() {
            return depth;
        }

        public abstract void accept(TreeVis visitor);
    }

    static class TreeNode extends Tree {

        private ArrayList<Tree> children = new ArrayList<>();

        public TreeNode(int value, Color color, int depth) {
            super(value, color, depth);
        }

        public void accept(TreeVis visitor) {
            visitor.visitNode(this);

            for (Tree child : children) {
                child.accept(visitor);
            }
        }

        public void addChild(Tree child) {
            children.add(child);
        }
    }

    static class TreeLeaf extends Tree {

        public TreeLeaf(int value, Color color, int depth) {
            super(value, color, depth);
        }

        public void accept(TreeVis visitor) {
            visitor.visitLeaf(this);
        }
    }

    static abstract class TreeVis {
        public abstract int getResult();

        public abstract void visitNode(TreeNode node);

        public abstract void visitLeaf(TreeLeaf leaf);

    }

    static class SumInLeavesVisitor extends TreeVis {
        private int sum = 0;

        public int getResult() {
            return sum;
        }

        public void visitNode(TreeNode node) {

        }

        public void visitLeaf(TreeLeaf leaf) {
            sum = sum + leaf.getValue();
        }
    }

    static class ProductOfRedNodesVisitor extends TreeVis {
        private int product = 1;

        public int getResult() {
            return product;
        }

        public void visitNode(TreeNode node) {
            productIfRed(node);
        }

        public void visitLeaf(TreeLeaf leaf) {
            productIfRed(leaf);
        }

        private void productIfRed(Tree tree) {
            if (tree.getColor() == Color.RED) {
                product = ((product * tree.getValue()) % (1_000_000_000 + 7));
            }
        }
    }

    static class FancyVisitor extends TreeVis {
        private int sumOfEvenNodes = 0;
        private int sumOfGreenLeaf = 0;

        public int getResult() {
            return Math.abs(sumOfEvenNodes - sumOfGreenLeaf);
        }

        public void visitNode(TreeNode node) {
            if (node.getDepth() % 2 == 0) {
                sumOfEvenNodes = sumOfEvenNodes + node.getValue();
            }
        }

        public void visitLeaf(TreeLeaf leaf) {
            if (leaf.getColor() == Color.GREEN) {
                sumOfGreenLeaf = sumOfGreenLeaf + leaf.getValue();
            }
        }
    }

    static class ParentToChild {
        private final int parent;
        private final int child;

        public ParentToChild(int parent, int child) {
            this.parent = parent;
            this.child = child;
        }

        public int getParent() {
            return parent;
        }

        public int getChild() {
            return child;
        }

    }

    private static List<Integer> valuesList;
    private static List<Color> colorList;
    private static List<ParentToChild> parentToChildList;
    private static Set<Integer> parentsSet;

    public static Tree solve() {
        int numberOfElements;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            numberOfElements = Integer.parseInt(br.readLine());
            valuesList = new ArrayList<>();
            for (String valueRaw : br.readLine().split(" ")) {
                valuesList.add(Integer.parseInt(valueRaw));
            }
            colorList = new ArrayList<>();
            for (String valueRaw : br.readLine().split(" ")) {
                colorList.add(valueRaw.equals("0") ? Color.RED : Color.GREEN);
            }

            parentToChildList = new ArrayList<>();
            parentsSet = new HashSet<>();
            for (int i = 0; i < numberOfElements - 1; i++) {
                final String[] parentAndChildRaw = br.readLine().split(" ");
                parentsSet.add(Integer.parseInt(parentAndChildRaw[0]));
                parentToChildList.add(new ParentToChild(
                        Integer.parseInt(parentAndChildRaw[0]),
                        Integer.parseInt(parentAndChildRaw[1])));
            }
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }

        final Tree resultTree = buildTree(
                new TreeNode(valuesList.get(0), colorList.get(0), 0),
                1);
        return resultTree;
    }

    private static Tree buildTree(TreeNode rootNode, int rootNodeId) {
        for (ParentToChild parentToChild : parentToChildList) {
            if (parentToChild.getParent() == rootNodeId) {
                if (parentsSet.contains(parentToChild.getChild())) {
                    rootNode.addChild(
                            buildTree(
                                    new TreeNode(
                                            valuesList.get(parentToChild.getChild() - 1),
                                            colorList.get(parentToChild.getChild() - 1),
                                            rootNode.getDepth() + 1),
                                    parentToChild.getChild())
                    );
                } else {
                    rootNode.addChild(
                            new TreeLeaf(
                                    valuesList.get(parentToChild.getChild() - 1),
                                    colorList.get(parentToChild.getChild() - 1),
                                    rootNode.getDepth() + 1
                            )
                    );
                }
            }
        }
        return rootNode;
    }

    public static void main(String[] args) {
        Tree root = solve();
        SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
        ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
        FancyVisitor vis3 = new FancyVisitor();

        root.accept(vis1);
        root.accept(vis2);
        root.accept(vis3);

        int res1 = vis1.getResult();
        int res2 = vis2.getResult();
        int res3 = vis3.getResult();

        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }
}
