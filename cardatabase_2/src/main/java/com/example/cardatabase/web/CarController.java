//package com.example.cardatabase.web;
//
//import com.example.cardatabase.domain.Car;
//import com.example.cardatabase.domain.CarRepository;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class CarController {
//
//
//    private final CarRepository carRepository;
//    // 생성자 만들어줌
//    public CarController(CarRepository carRepository) {
//        this.carRepository = carRepository;
//    }
//
//    @GetMapping("/cars")
//    public Iterable<Car> getCars() {
//        // 복잡하게 할 거 없이 자동차들이 저장된 테이블에서 전체 명단을 가지고 오기
//        return carRepository.findAll();
//    }
//} - 이거 충돌때문에 swagger error 남
