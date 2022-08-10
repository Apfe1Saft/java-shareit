package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    /*@Query("select new ru.practicum.shareit.item(it.userId, count(it.id))" +
            "from Item as it "+
            "where it.url like ?1 "+
            "group by it.userId "+
            "order by count(it.id) desc")*/
    //List<Item> findByDescriptionContainingIgnoreCaseAndNameContainingIgnoreCaseAndAvailableIs(String name,String description,boolean value);
    @Query(" select i from Item i " +
            "where lower(i.name) like lower(concat('%', :search, '%')) " +
            " or lower(i.description) like lower(concat('%', :search, '%')) " +
            " and i.available = true")
    List<Item> searchWithParams(@Param("search") String text);
}
