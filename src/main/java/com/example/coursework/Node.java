package com.example.coursework;

public class Node {
    public Node(People p1, Relationship re, People p2) {
        this.p1 = p1;
        this.re = re;
        this.p2 = p2;
    }

    People p1;
    Relationship re;
    People p2;

    @Override
    public String toString() {
        return String.format("<%s %s %s>", p1, re, p2);
    }

}