package com.example.DoaAnTotNghiep.repository;

import com.example.DoaAnTotNghiep.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
