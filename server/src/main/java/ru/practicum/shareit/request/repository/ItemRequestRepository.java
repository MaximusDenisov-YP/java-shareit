package ru.practicum.shareit.request.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.entity.ItemRequest;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    @EntityGraph(attributePaths = "items")
    List<ItemRequest> findAllByRequesterIdOrderByCreatedDesc(Long requesterId);

    @EntityGraph(attributePaths = "items")
    List<ItemRequest> findAllByRequesterIdNotOrderByCreatedDesc(Long requesterId);
}
