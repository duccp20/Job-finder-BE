package com.example.jobfinder.data.entity;//package com.example.jobfinderbe.data.entity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.EntityListeners;
//import jakarta.persistence.MappedSuperclass;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.io.Serializable;
//import java.util.Date;
//
//@AllArgsConstructor
//@Data
//@NoArgsConstructor
//@MappedSuperclass //đánh dấu đây là Supper class
//@EntityListeners(AuditingEntityListener.class)
//public abstract class Auditable implements Serializable {
//
//    @CreatedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_date", updatable = false)
//    private Date createdDate;
//
//    @CreatedBy
//    @Column(name = "created_by_user_id", updatable = false)
//    private Long createdBy;
//
//    @LastModifiedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "last_modified_date")
//    private Date lastModifiedDate;
//
//    @LastModifiedBy
//    @Column(name = "last_modified_by_user_id")
//    private Long lastModifiedBy;
//
//}
