package com.example.cardatabase;

import com.example.cardatabase.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@SpringBootApplication
public class CardatabaseApplication  implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(
			CardatabaseApplication.class
	);

	private final CarRepository repository;
	private final OwnerRepository ownerRepository;
	private final AppUserRepository userRepository;


    public CardatabaseApplication(CarRepository repository, OwnerRepository ownerRepository, AppUserRepository userRepository) {
        this.repository = repository;
		this.ownerRepository = ownerRepository;
        this.userRepository = userRepository;

    }

    public static void main(String[] args) {
		SpringApplication.run(CardatabaseApplication.class, args);
		logger.info("Application Started ! / 애플리케이션이 실행되었습니다.");
	}


	@Override
	public void run(String... args) throws Exception {
		// 소유자 객체 추가
		Owner owner1 = new Owner("일","김");
		Owner owner2 = new Owner("이","김");
		// 다수의 객체를 한 번에 저장하는 메서드 사용
		ownerRepository.saveAll(Arrays.asList(owner1,owner2));


		// db에 저장
		// 그리고 owner 추가
		repository.save(new Car("Kia", "Seltos", "Chacol", "370SU5690", 2020, 30000000, owner1));
		repository.save(new Car("Hyundai", "Sonata", "White", "123456", 2025, 25000000, owner2));
		repository.save(new Car("Honda", "CR-V", "Black-White", "987654", 2024, 45000000,owner2));



		for (Car car : repository.findAll()) {
			logger.info("brand : {}, model : {}", car.getBrand(), car.getModel());
		}

		// AppUser 더미 데이터를 추가
		// 저 위에 보시면 Owner의 경우에는 owner1 / owner2 만들어서 ownerRepository 저장
		userRepository.save(new AppUser("user", "$2a$12$9TEVhmZP0N.swI8RsI7UsOXhpWIDH6BIzIeHMYo5d0qAgj77WrNum", "USER"));
		userRepository.save(new AppUser("admin","$2a$12$FT4QoUFjpR3ASjo0ZB.LpeZhwTytNjqWwf0o0BbbZnPDSYWSf/Fy.", "ADMIN"));
	}


}
