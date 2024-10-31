package com.example.DoaAnTotNghiep.controller;

import com.example.DoaAnTotNghiep.entity.Room;
import com.example.DoaAnTotNghiep.repository.RoomRepository;
import com.example.DoaAnTotNghiep.repository.UserRepository;
import com.example.DoaAnTotNghiep.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    // Hiển thị trang chủ
    @GetMapping("/") // Root URL to show the home page
    public String viewHomePage() {
        return "123"; // Assuming you have a home.html view
    }

    // Hiển thị danh sách phòng
    @GetMapping("/rooms")
    public String viewRoomList(Model model) {
        List<Room> listRooms = roomService.getAllRooms();
        model.addAttribute("listRooms", listRooms);
        return "room/room_list"; // Tên view là room_list.html
    }

    // Hiển thị form thêm phòng mới
    @GetMapping("/rooms/new")
    public String showNewRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "room/new_room"; // Tên view cho form thêm phòng mới
    }

    // Lưu phòng mới
    @PostMapping("/rooms/save") // Đường dẫn đã được sửa đổi
    public String saveRoom(@ModelAttribute("room") Room room,
                           @RequestParam("photoFile") MultipartFile photoFile) {
        try {
            if (!photoFile.isEmpty()) {
                room.setPhoto(photoFile.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace(); // Xử lý lỗi nếu cần
        }
        roomService.save(room);
        return "redirect:/rooms"; // Redirect về danh sách phòng
    }

    @GetMapping("/rooms/getRoomPhoto/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getRoomPhoto(@PathVariable("id") Long id) {
        Room room = roomService.get(id);
        if (room != null && room.getPhoto() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Thay đổi loại nếu cần
                    .body(room.getPhoto());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Hiển thị form chỉnh sửa phòng
    @GetMapping("/rooms/edit/{id}")
    public String showEditRoomForm(@PathVariable("id") Long id, Model model) {
        Room room = roomService.get(id);
        model.addAttribute("room", room);
        return "room/edit_room"; // Tên view cho form chỉnh sửa phòng
    }

    // Cập nhật thông tin phòng
    @PostMapping("/rooms/update/{id}")
    public String updateRoom(@PathVariable("id") Long id,
                             @ModelAttribute("room") Room updatedRoom,
                             @RequestParam("photoFile") MultipartFile photoFile) {
        Room existingRoom = roomService.get(id);
        existingRoom.setRoomType(updatedRoom.getRoomType());
        existingRoom.setCapacity(updatedRoom.getCapacity());
        existingRoom.setStatus(updatedRoom.getStatus());
        existingRoom.setPrice(updatedRoom.getPrice());

        // Kiểm tra nếu có file ảnh mới
        if (!photoFile.isEmpty()) {
            try {
                existingRoom.setPhoto(photoFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace(); // Xử lý lỗi nếu cần
            }
        }

        roomService.save(existingRoom);
        return "redirect:/rooms"; // Redirect về danh sách phòng
    }

    // Xóa phòng
    @GetMapping("/rooms/delete/{id}")
    public String deleteRoom(@PathVariable("id") Long id) {
        roomService.delete(id);
        return "redirect:/rooms"; // Redirect về danh sách phòng
    }
}
