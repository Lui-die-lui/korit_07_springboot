package com.example.cardatabase.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

// extends - 상속 (부모 메서드를 자식이 쓸 수 있음)
public interface CarRepository  extends JpaRepository<Car, Long> { // repository - entity 연결됨

}
