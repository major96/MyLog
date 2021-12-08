package com.major.mylog.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;


@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //ID값의 자동 증가를 위한 설정 여러건의 insert를 한번에 할 때는 GenerationType.SEQUENCE가 젤 좋음
    //일반적으로는 GenerationType.IDENTITY를 사용
    //.TABLE은 테이블을 다 꺼내오기때문에 느리고 lock도 자주 걸린다(안좋음)
    private Long id;

    @NotNull
    @Size(min = 4, max = 20, message = "제목은 4글자 이상이어야 합니다.")
    private String title;

    private String text;
}
