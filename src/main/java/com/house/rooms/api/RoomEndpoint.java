package com.house.rooms.api;

import com.house.rooms.data.Room;
import com.house.rooms.data.RoomRepository;
import com.house.rooms.data.Student;
import com.house.rooms.data.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rooms")
public class RoomEndpoint {
    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;

    public RoomEndpoint(RoomRepository roomRepository, StudentRepository studentRepository) {
        this.roomRepository = roomRepository;
        this.studentRepository = studentRepository;
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

    @PostMapping("/{roomId}/students/{studentId}")
    Room addStudentToRoom  (
            @PathVariable long roomId,
            @PathVariable long studentId
    ) throws RoomNotFoundException, StudentNotFoundException {
        // Retrieve the room and student entities from the repository
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);

        // Check if the room already has three students

        if (room.getStudents().size() >= 3) {
            throw new MaximumStudentsReachedException();
        }

        // Check if the room is empty or if the student's gender matches the room's gender
        if (room.getStudents().isEmpty() || room.getStudents().get(0).getGender() == student.getGender()) {
            // Check if the room is empty or if the student's house matches the house of students in the room
            if (room.getStudents().isEmpty() || room.getStudents().get(0).getHouse() == student.getHouse()) {
                // Add the student to the room
                room.getStudents().add(student);
                student.setRoom(room);

                // Save the updated room and student entities
                roomRepository.save(room);
                studentRepository.save(student);

                return room;
            } else {
                throw new InvalidStudentGenderException();
            }
        } else {
            throw new InvalidStudentGenderException();
        }
    }
}
