package system.old.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vladimir on 17.02.2018.
 */
@Entity
@Table(name = "person")
public class Person implements Comparable<Person> {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Basic
    @Column(name = "fname", nullable = false)
    private String fname;

    @Basic
    @Column(name = "lname", nullable = false)
    private String lname;

    @Basic
    @Column(name = "sname")
    private String sname;

    @Basic
    @Column(name = "tel_number", nullable = false)
    private String telNumber;

    @Basic
    @Column(name = "city")
    private String city;

    @Basic
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public Person(String fname, String lname, String sname, String telNumber) {
        this.fname = fname;
        this.lname = lname;
        this.sname = sname;
        this.telNumber = telNumber;
        this.creationDate = Calendar.getInstance().getTime();
    }

    public Person(String fname, String lname, String sname, String telNumber, String city) {
        this.fname = fname;
        this.lname = lname;
        this.sname = sname;
        this.telNumber = telNumber;
        this.city = city;
        this.creationDate = Calendar.getInstance().getTime();
    }

    public Person(String telNumber) {
        this.telNumber = telNumber;
        this.creationDate = Calendar.getInstance().getTime();
    }

    public Person() {
        this.creationDate = Calendar.getInstance().getTime();
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getSname() {
        return sname;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public String getCity() {
        return city;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int compareTo(Person o) {
        return telNumber.compareTo(o.getTelNumber());
    }

    public String getFIO() {
        return String.format("%s %s %s", lname, fname, sname);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", sname='" + sname + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", city='" + city + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
