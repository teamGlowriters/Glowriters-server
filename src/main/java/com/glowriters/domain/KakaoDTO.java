package com.glowriters.domain;

import lombok.Builder;
import lombok.Data;

/*
 * @Builder : 객체생성을 유연하고 가독성있게 해주는 디자인 패턴
 * 
 * 해당 클래스(KakaoDTO)에 대한 빌더 클래스가 자동으로 생성되어 객체 생성을 간편
 * 객체를 생성할 때 각 필드에 대한 값을 직접 지정하는 대신, 
 * 각 필드에 대한 메서드를 호출하여 값을 설정하고 
 * 마지막에 build() 메서드를 호출하여 객체를 생성
 * 
 * 즉 객체를 생성할 때 편리하게 사용하기위함
 * */
@Builder
@Data
public class KakaoDTO {

    private long id;
    private String email;
    private String nickname;
    private String profileImage;
    String accessToken;
    String refreshToken;
}