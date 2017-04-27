package com.edu.accountingteachingmaterial.bean;

import com.edu.library.data.BaseData;

import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */

public class TemplateData extends BaseData{


    /**
     * flag : 0
     * timeStamp : 2016-12-13 14:35:59
     * bitmap : http://192.168.1.142:80/resources/files/background/bh_0001.png
     * remark : null
     * name : 招商银行现金支票
     * blanksDatas : [{"remark":"1","width":25,"score":1,"answer":null,"uAnswer":null,"type":13,"editable":false,"id":69,"textSize":0,"height":0,"right":false,"y":198,"x":117},{"remark":"1","width":25,"score":1,"answer":null,"uAnswer":null,"type":13,"editable":false,"id":70,"textSize":0,"height":0,"right":false,"y":198,"x":164},{"remark":"1","width":25,"score":1,"answer":null,"uAnswer":null,"type":13,"editable":false,"id":71,"textSize":0,"height":0,"right":false,"y":198,"x":200}]
     */

    private int flag;
    private String timeStamp;
    private String bitmap;
    private String remark;
    private String name;
    private List<BlanksDatasBean> blanksDatas;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BlanksDatasBean> getBlanksDatas() {
        return blanksDatas;
    }

    public void setBlanksDatas(List<BlanksDatasBean> blanksDatas) {
        this.blanksDatas = blanksDatas;
    }

    public static class BlanksDatasBean {
        /**
         * remark : 1
         * width : 25.0
         * score : 1.0
         * answer : null
         * uAnswer : null
         * type : 13
         * editable : false
         * id : 69
         * textSize : 0
         * height : 0.0
         * right : false
         * y : 198.0
         * x : 117.0
         */

        private String remark;
        private double width;
        private double score;
        private String answer;
        private String uAnswer;
        private int type;
        private boolean editable;
        private int id;
        private int textSize;
        private double height;
        private boolean right;
        private double y;
        private double x;
        private String content;

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getuAnswer() {
            return uAnswer;
        }

        public void setuAnswer(String uAnswer) {
            this.uAnswer = uAnswer;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isEditable() {
            return editable;
        }

        public void setEditable(boolean editable) {
            this.editable = editable;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTextSize() {
            return textSize;
        }

        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public boolean isRight() {
            return right;
        }

        public void setRight(boolean right) {
            this.right = right;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }
}
