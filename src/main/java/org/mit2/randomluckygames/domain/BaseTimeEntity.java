package org.mit2.randomluckygames.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//각 엔티티마다 상속하여 칼럼을 추가해준다.
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    //생성 일시
    @CreatedDate
    private LocalDateTime createDate;

    //마지막 수정일시
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
