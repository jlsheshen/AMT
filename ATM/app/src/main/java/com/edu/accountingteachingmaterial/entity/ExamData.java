package com.edu.accountingteachingmaterial.entity;

import com.edu.library.data.BaseData;

import java.util.List;

/**
 * Created by Administrator on 2016/12/9.
 */

public class ExamData extends BaseData {


    /**
     * totalRow : 3
     * pageNumber : 1
     * firstPage : true
     * lastPage : true
     * totalPage : 1
     * pageSize : 10000
     * list : [{"creator":5929,"creator_name":"李兴","end_time":"2017-02-03 18:51:00","exam_id":1189,"exam_name":"表格题作答","exam_paper_id":478,"exam_type":3,"is_read":0,"last_time":86868,"publish_time":"2016-12-05 11:02:37","sendscore":1,"show_answer":0,"start_time":"2016-12-05 11:03:00","sub_status":0,"u_id":5926},{"creator":5929,"creator_name":"李兴","end_time":"2017-01-20 17:30:00","exam_id":1188,"exam_name":"会计测验 20161205","exam_paper_id":451,"exam_type":3,"is_read":0,"last_time":66666,"publish_time":"2016-12-05 10:23:45","sendscore":0,"show_answer":0,"start_time":"2016-12-05 10:24:00","sub_status":0,"u_id":5926},{"creator":219,"creator_name":"通用","end_time":"2017-01-25 04:44:00","exam_id":1094,"exam_name":"会计测验 20161124","exam_paper_id":436,"exam_type":3,"is_read":0,"last_time":88888,"publish_time":"2016-11-24 11:15:16","sendscore":0,"show_answer":1,"start_time":"2016-11-24 11:16:00","sub_status":0,"u_id":5926}]
     */

    private int totalRow;
    private int pageNumber;
    private boolean firstPage;
    private boolean lastPage;
    private int totalPage;
    private int pageSize;
    private List<ListBean> list;


    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public class ListBean extends BaseData {
        /**
         * 另外添加 答题状态
         * @return
         */
        private int State;
        /**
         * creator : 5929
         * creator_name : 李兴
         * end_time : 2017-02-03 18:51:00
         * exam_id : 1189
         * exam_name : 表格题作答
         * exam_paper_id : 478
         * exam_type : 3
         * is_read : 0
         * last_time : 86868
         * publish_time : 2016-12-05 11:02:37
         * sendscore : 1
         * show_answer : 0
         * start_time : 2016-12-05 11:03:00
         * sub_status : 0
         * u_id : 5926
         */

        private int creator;
        private String creator_name;
        private String end_time;
        private int exam_id;
        private String exam_name;
        private int exam_paper_id;
        private int exam_type;
        private int is_read;
        private int last_time;
        private String publish_time;
        private int sendscore;
        private int show_answer;
        private String start_time;
        private int sub_status;
        private int u_id;

        public int getState() {
            return State;
        }

        public void setState(int state) {
            State = state;
        }

        public int getCreator() {
            return creator;
        }

        public void setCreator(int creator) {
            this.creator = creator;
        }

        public String getCreator_name() {
            return creator_name;
        }

        public void setCreator_name(String creator_name) {
            this.creator_name = creator_name;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getExam_id() {
            return exam_id;
        }

        public void setExam_id(int exam_id) {
            this.exam_id = exam_id;
        }

        public String getExam_name() {
            return exam_name;
        }

        public void setExam_name(String exam_name) {
            this.exam_name = exam_name;
        }

        public int getExam_paper_id() {
            return exam_paper_id;
        }

        public void setExam_paper_id(int exam_paper_id) {
            this.exam_paper_id = exam_paper_id;
        }

        public int getExam_type() {
            return exam_type;
        }

        public void setExam_type(int exam_type) {
            this.exam_type = exam_type;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public int getLast_time() {
            return last_time;
        }

        public void setLast_time(int last_time) {
            this.last_time = last_time;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public int getSendscore() {
            return sendscore;
        }

        public void setSendscore(int sendscore) {
            this.sendscore = sendscore;
        }

        public int getShow_answer() {
            return show_answer;
        }

        public void setShow_answer(int show_answer) {
            this.show_answer = show_answer;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public int getSub_status() {
            return sub_status;
        }

        public void setSub_status(int sub_status) {
            this.sub_status = sub_status;
        }

        public int getU_id() {
            return u_id;
        }

        public void setU_id(int u_id) {
            this.u_id = u_id;
        }
    }


}
