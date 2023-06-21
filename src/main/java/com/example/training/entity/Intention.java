package com.example.training.entity;

import java.time.LocalDate;

public class Intention {
    private Integer id;
    private Integer customerId;
    private Integer courseId;
    private LocalDate intentionDate;
    private String evaluation;
    private Integer isdeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public LocalDate getIntentionDate() {
        return intentionDate;
    }

    public void setIntentionDate(LocalDate intentionDate) {
        this.intentionDate = intentionDate;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Integer getIsDeleted() {
        return isdeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isdeleted = isDeleted;
    }
}
