package com.edu.accountingteachingmaterial.entity;

import java.sql.Timestamp;

/**
 * 上传历史信息的实体类
 * Created by Administrator on 2016/12/9.
 */



    public class StudyHistoryVO {

        private Long userId;
    //在线考试为1,其他为0
        private int is_exam = 0;
    //课程id
        private Long courseId;
    //章id
        private Long chapterId;
    //节id
        private Long sectionId;

        private Long lessonId;

        private Integer lessonType;
        private Timestamp uploadTime;

        public Long getUserId() {
            return userId;
        }
        public void setUserId(Long userId) {
            this.userId = userId;
        }
        public int getIs_exam() {
            return is_exam;
        }
        public void setIs_exam(int is_exam) {
            this.is_exam = is_exam;
        }
        public Long getCourseId() {
            return courseId;
        }
        public void setCourseId(Long courseId) {
            this.courseId = courseId;
        }
        public Long getChapterId() {
            return chapterId;
        }
        public void setChapterId(Long chapterId) {
            this.chapterId = chapterId;
        }
        public Long getSectionId() {
            return sectionId;
        }
        public void setSectionId(Long sectionId) {
            this.sectionId = sectionId;
        }
        public Long getLessonId() {
            return lessonId;
        }
        public void setLessonId(Long lessonId) {
            this.lessonId = lessonId;
        }
        public Integer getLessonType() {
            return lessonType;
        }
        public void setLessonType(Integer lessonType) {
            this.lessonType = lessonType;
        }
        public Timestamp getUploadTime() {
            return uploadTime;
        }
        public void setUploadTime(Timestamp uploadTime) {
            this.uploadTime = uploadTime;
        }
}
