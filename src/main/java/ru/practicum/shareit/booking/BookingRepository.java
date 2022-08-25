package ru.practicum.shareit.booking;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerId(Long ownerId, Pageable pageable);
    @Query(
            " select i from Booking i " +
                    "where i.booker.id!= :ownerId"
    )
    List<Booking> getOwnerBookings(@Param("ownerId") Long ownerId, Pageable pageable);
}
