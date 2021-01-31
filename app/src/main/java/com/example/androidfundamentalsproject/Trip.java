package com.example.androidfundamentalsproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Trip", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "User ID", onDelete = CASCADE))
public class Trip {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Destination")
    private String destination;

    @ColumnInfo(name = "Type")
    private String type;

    @ColumnInfo(name = "Price")
    private float price;

    @ColumnInfo(name = "Start_Data")
    @TypeConverters(DateConverter.class)
    private Date start_date;

    @ColumnInfo(name = "End_Data")
    @TypeConverters(DateConverter.class)
    private Date end_date;

    @ColumnInfo(name = "Rating")
    private int rating;

    @ColumnInfo(name = "Favourite")
    private boolean favourite;

    @ColumnInfo(name = "User ID")
    private int userId;

    public Trip() {
    }

    public Trip(int id, String name, String destination, String type, float price, Date start_date, Date end_date, int rating, boolean favourite, int userId) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.type = type;
        this.price = price;
        this.start_date = start_date;
        this.end_date = end_date;
        this.rating = rating;
        this.favourite = favourite;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", rating=" + rating +
                ", favourite=" + favourite +
                ", userId=" + userId +
                '}';
    }
}
