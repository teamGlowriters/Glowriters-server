package com.glowriters.domain;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/* @MappedSuperclass : 엔티티가아니고 오로지 상속을위한 클래스임을 선언.
 * DB와 매핑되지 않고 오로지 상속받은 자식클래스의 필드로만 주입시킴.
 * 
 * private LocalDateTime created_date; Period클래스 자체의 필드는 DB와 매핑되지않음
 * private LocalDateTime updated_date; Period클래스 자체의 필드는 DB와 매핑되지않음
 * 즉 두 필드가 자식클래스 필드로 넣어지기만함.
 * */
@MappedSuperclass
public class Period {
	private LocalDateTime created_date;
	private LocalDateTime updated_date;

	//생성될때의 시간을 기록
	@PrePersist
  protected void onCreate() {
      created_date = LocalDateTime.now();
      updated_date = LocalDateTime.now();
  }

	//업데이트될때마다의 시간을 기록
  @PreUpdate
  protected void onUpdate() {
      updated_date = LocalDateTime.now();
  }
}
