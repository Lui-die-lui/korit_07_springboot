package com.example.cardatabase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@ToString
@Entity // 테이블이 될 클래스
// 무시하는 요소가 있다는 뜻
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@Data
@Getter
@Setter

public class Owner {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long ownerId;

   @NonNull
   private final String firstName;
   @NonNull
   private final String lastName;

   // 소유자는 다수의 차들을 가질 수 있기 때문에 Collections를 사용.
   @JsonIgnore // 이건 json 화 시키지 않음
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
   private List<Car> cars;

}
