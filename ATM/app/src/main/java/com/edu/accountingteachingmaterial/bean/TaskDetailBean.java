package com.edu.accountingteachingmaterial.bean;

import com.edu.library.data.BaseData;
import com.edu.subject.BASE_URL;

import java.util.List;

/**
 * 任务详情类
 * Created by Administrator on 2017/3/10.
 */

public class TaskDetailBean  extends BaseData {


    /**
     * answer : 456TVT v
     * comment : null
     * comment_date : null
     * content : <p>中午吃什么</p>
     * filelist : [{"id":4,"pic":"/resources/upload/cover/task/1489559558615.jpg"},{"id":8,"pic":"/resources/upload/cover/task/1489640245094.jpg"}]
     * history : [{"create_date":null,"name":"小七"},{"create_date":null,"name":"小屋"},{"create_date":null,"name":"筱柳"}]
     * stu_count : 0
     * studentlist : [{"answer":null,"create_date":null,"name":"小屋","picture":null,"team_name":"小组1"},{"answer":null,"create_date":null,"name":"筱柳","picture":null,"team_name":"小组1"},{"answer":null,"create_date":null,"name":"小七","picture":null,"team_name":"小组1"}]
     * task_name : 吃饭吃好
     * team_id : 2
     */

    private String answer;
    private String comment;
    private String comment_date;
    private String content;
    private int stu_count;
    private String task_name;
    private int team_id;
    private List<FileListBean> filelist;
    private List<HistoryBean> history;
    private List<StudentlistBean> studentlist;
    /**
     * filelist : []
     * history : [{"create_date":null,"name":"二二"}]
     * paras : {"text":"会计是以货币为主要计量单位，运用专门的方法，核算和监督一个单位经济活动的一种经济管理工作。单位是国家机关、社会团体、公司、企业、事业单位和其他组织的统称。未特别说明时，本大纲主要以《企业会计准则》为依据介绍企业经济业务的会计处理。会计已经成为现代企业一项重要的管理工作。企业的会计工作主要是通过一系列会计程序，对企业的经济活动和财务收支进行核算和监督，反映企业财务状况、经营成果和现金流量，反映企业管理层受托责任履行情况，为会计信息使用者提供决策有用的信息，并积极参与经营管理决策，提高企业经济效益，促进市场经济的健康有序发展。","imgSrc":["http://192.168.1.142:8080/eduExam/resources/upload/image/20170323/1490248663424081354.jpg","http://192.168.1.142:8080/eduExam/resources/upload/image/20170323/1490253849819043694.jpg","http://192.168.1.142:8080/eduExam/resources/upload/image/20170323/1490253856647087364.jpg","http://192.168.1.142:8080/eduExam/resources/upload/image/20170323/1490253863008013150.jpg"]}
     * studentlist : [{"answer":null,"create_date":null,"name":"二二","picture":null,"team_name":"小组 1"}]
     */

    private ParasBean paras;

    public String getAnswer()  {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStu_count() {
        return stu_count;
    }

    public void setStu_count(int stu_count) {
        this.stu_count = stu_count;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public List<FileListBean> getFilelist() {
        return filelist;
    }

    public void setFilelist(List<FileListBean> filelist) {
        this.filelist = filelist;
    }

    public List<HistoryBean> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryBean> history) {
        this.history = history;
    }

    public List<StudentlistBean> getStudentlist() {
        return studentlist;
    }

    public void setStudentlist(List<StudentlistBean> studentlist) {
        this.studentlist = studentlist;
    }

    public ParasBean getParas() {
        return paras;
    }

    public TaskDetailBean setParas(ParasBean paras) {
        this.paras = paras;
        return this;
    }

    public static class FileListBean extends BaseData{
        /**
         * id : 4
         * pic : /resources/upload/cover/task/1489559558615.jpg
         */

        private String pic;
        private boolean isFoot;

        public String getPic() {
            return BASE_URL.getBaseImageUrl() + pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public boolean isFoot() {
            return isFoot;
        }

        public FileListBean setFoot(boolean foot) {
            isFoot = foot;
            return this;
        }
    }

    public static class HistoryBean  extends BaseData{
        /**
         * create_date : null
         * name : 小七
         */

        private String create_date;
        private String name;

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class StudentlistBean extends BaseData{
        /**
         * answer : null
         * create_date : null
         * name : 小屋
         * picture : null
         * team_name : 小组1
         */

        private String answer;
        private String create_date;
        private String name;
        private String picture;
        private String team_name;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicture() {
            return BASE_URL.getBaseImageUrl() +picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getTeam_name() {
            return team_name;
        }

        public void setTeam_name(String team_name) {
            this.team_name = team_name;
        }
    }

    public static class ParasBean extends BaseData{
        /**
         * text : 会计是以货币为主要计量单位，运用专门的方法，核算和监督一个单位经济活动的一种经济管理工作。单位是国家机关、社会团体、公司、企业、事业单位和其他组织的统称。未特别说明时，本大纲主要以《企业会计准则》为依据介绍企业经济业务的会计处理。会计已经成为现代企业一项重要的管理工作。企业的会计工作主要是通过一系列会计程序，对企业的经济活动和财务收支进行核算和监督，反映企业财务状况、经营成果和现金流量，反映企业管理层受托责任履行情况，为会计信息使用者提供决策有用的信息，并积极参与经营管理决策，提高企业经济效益，促进市场经济的健康有序发展。
         * imgSrc : ["http://192.168.1.142:8080/eduExam/resources/upload/image/20170323/1490248663424081354.jpg","http://192.168.1.142:8080/eduExam/resources/upload/image/20170323/1490253849819043694.jpg","http://192.168.1.142:8080/eduExam/resources/upload/image/20170323/1490253856647087364.jpg","http://192.168.1.142:8080/eduExam/resources/upload/image/20170323/1490253863008013150.jpg"]
         */

        private String text;
        private List<String> imgSrc;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<String> getImgSrc() {
            return imgSrc;
        }

        public void setImgSrc(List<String> imgSrc) {
            this.imgSrc = imgSrc;
        }
    }
}