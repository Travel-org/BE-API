package com.ssu.travel.domain.schedule.entity;

import com.ssu.travel.domain.branch.entity.Branch;
import com.ssu.travel.domain.place.entity.Place;
import com.ssu.travel.domain.post.entity.Post;
import com.ssu.travel.domain.schedulePhoto.entity.SchedulePhoto;
import com.ssu.travel.domain.travelDate.entity.TravelDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "date"),
            @JoinColumn(name = "travel_id")
    })
    private TravelDate travelDate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "travel_id")
//    private Travel travel;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Branch> branches = new ArrayList<>();

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchedulePhoto> photos = new ArrayList<>();

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @Setter
    private LocalTime startTime;

    @Setter
    private LocalTime endTime;

    public void addUser(Branch branch) {
        branch.updateSchedule(this);
    }

    public void removeUser(Branch branch) {
        this.branches.remove(branch);
    }

    public void addSchedulePhotos(List<SchedulePhoto> photos) {
        this.photos.addAll(photos);
    }

    public void removeSchedulePhotos(List<SchedulePhoto> photos) {
        this.photos.removeAll(photos);
    }


    private Schedule(TravelDate travelDate, Place place, LocalTime startTime, LocalTime endTime) {
        this.travelDate = travelDate;
        this.place = place;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Schedule of(TravelDate travelDate, Place place, LocalTime startTime, LocalTime endTime) {
        return new Schedule(travelDate, place, startTime, endTime);
    }
}
