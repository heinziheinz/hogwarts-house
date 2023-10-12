package com.house.rooms.api;

import com.house.rooms.data.Room;
import com.house.rooms.data.RoomRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rooms")
public class RoomEndpoint {
    private final RoomRepository roomRepository;

    public RoomEndpoint(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping
    List<Room> findAll() {
        return roomRepository.findAll();
    }

    @GetMapping("/name/{name}")
    Room findByName(@PathVariable String name) throws RoomNotFoundException {
        return roomRepository.findByName(name)
                .orElseThrow(RoomNotFoundException::new);
    }

    @GetMapping("/price/{min}/{max}")
    List<Room> findByPrice(@PathVariable double min, @PathVariable double max) {
        return roomRepository.findByPriceBetween(min, max);
    }

    @PostMapping
    Room save(@RequestBody Room room) {
        return roomRepository.save(room);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        roomRepository.deleteById(id);
    }
}
