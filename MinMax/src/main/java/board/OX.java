package board;

import com.google.gson.annotations.SerializedName;

public enum OX implements GameValue {
    @SerializedName("0")
    EMPTY(0),
    @SerializedName("1")
    X(1),
    @SerializedName("2")
    O(2);


    private final int val;


    OX(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
