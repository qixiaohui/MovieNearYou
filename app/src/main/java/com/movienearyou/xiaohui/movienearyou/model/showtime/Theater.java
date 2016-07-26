package com.movienearyou.xiaohui.movienearyou.model.showtime;

/**
 * Created by TQi on 7/25/16.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Theater {

    @SerializedName("theater")
    @Expose
    private String theater;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("schedule")
    @Expose
    private List<Schedule> schedule = new ArrayList<Schedule>();

    /**
     *
     * @return
     * The theater
     */
    public String getTheater() {
        return theater;
    }

    /**
     *
     * @param theater
     * The theater
     */
    public void setTheater(String theater) {
        this.theater = theater;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The schedule
     */
    public List<Schedule> getSchedule() {
        return schedule;
    }

    /**
     *
     * @param schedule
     * The schedule
     */
    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

}
