package com.example.cardatabase.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

// extends - 상속 (부모 메서드를 자식이 쓸 수 있음)
// 같은 interface라서 상속 가능 <연관있는 엔티티,자료형>
@RepositoryRestResource
public interface CarRepository  extends JpaRepository<Car, Long> { // repository - entity 연결됨
    // 브랜드로 자동차 검색하는 쿼리 메서드
    List<Car> findByBrand(@Param("brand") String brand);

    // 색상으로 자동차 검색하는 쿼리 메서드
    List<Car> findByColor(@Param("color") String color);
}
