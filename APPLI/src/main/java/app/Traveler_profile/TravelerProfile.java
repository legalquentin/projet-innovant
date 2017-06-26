package app.Traveler_profile;

import javax.persistence.*;

@Entity
public class TravelerProfile {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="dateStart", nullable=false)
    private String dateStart;

    @Column(name="dateEnd", nullable=false)
    private String dateEnd;


    //Getter and setter

    @Column
    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String date) {
        this.dateStart = date;
    }


    @Column
    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String date) {
        this.dateEnd = date;
    }


/*    @OneToOne
    @JoinColumn(name = "book_category_id")
    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }*/

}