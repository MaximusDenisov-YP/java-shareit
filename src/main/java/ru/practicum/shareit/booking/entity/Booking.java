package ru.practicum.shareit.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.cglib.core.Local;
import ru.practicum.shareit.item.entity.Item;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter @ToString
@Builder
@AllArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_date")
    private LocalDateTime start;
    @Column(name = "end_date")
    private LocalDateTime end;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    @NotNull
    @Column(name = "booker_id")
    private Long bookerId; // userId?
    private BookingStatus status;

    public Booking() {
    }
}
