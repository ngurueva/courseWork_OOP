package com.example.coursework;

import java.util.ArrayList;

public class GeoTree {

    private ArrayList<Node> tree = new ArrayList<>();

    public ArrayList<Node> getTree() {
        return tree;
    }

    // связь родитель - ребенок
    public void appendPerentChild(People parent, People children) {
        tree.add(new Node(parent, Relationship.parent, children));
        tree.add(new Node(children, Relationship.children, parent));
    }

    // связь муж - жена
    public void appendWifeHusband(People wife, People husband) {
        tree.add(new Node(wife, Relationship.wife, husband));
        tree.add(new Node(husband, Relationship.husband, wife));
    }

    // связь муж - жена
    public void appendSisterBrother(People sister, People brother) {
        tree.add(new Node(sister, Relationship.sister, brother));
        tree.add(new Node(brother, Relationship.brother, sister));
    }

}