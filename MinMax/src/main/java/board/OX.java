package board;

import com.google.gson.annotations.SerializedName;

public enum OX implements GameValue {
    @SerializedName("0")
    EMPTY,
    @SerializedName("1")
    X,
    @SerializedName("2")
    O

}
