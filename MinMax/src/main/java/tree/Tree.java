package tree;

import game.Board;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    List<Board> states = new ArrayList<>();

    public Tree(List<Board> states) {
        this.states = states;
    }
}
