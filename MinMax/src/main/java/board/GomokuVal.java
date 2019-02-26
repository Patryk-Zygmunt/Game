package board;

import com.google.gson.annotations.SerializedName;

public enum GomokuVal implements GameValue {

    @SerializedName("0")
    EMPTY(0),
    @SerializedName("1")
    BLACK(1),
    @SerializedName("2")
    WHITE(2);


    private final int val;


    GomokuVal(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

}
