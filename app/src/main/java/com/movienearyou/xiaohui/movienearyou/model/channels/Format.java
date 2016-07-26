package com.movienearyou.xiaohui.movienearyou.model.channels;

/**
 * Created by TQi on 7/26/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Format {

    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("pre_order")
    @Expose
    private Boolean preOrder;

    /**
     *
     * @return
     * The price
     */
    public String getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The format
     */
    public String getFormat() {
        return format;
    }

    /**
     *
     * @param format
     * The format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The preOrder
     */
    public Boolean getPreOrder() {
        return preOrder;
    }

    /**
     *
     * @param preOrder
     * The pre_order
     */
    public void setPreOrder(Boolean preOrder) {
        this.preOrder = preOrder;
    }

}
