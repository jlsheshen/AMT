package com.edu.accountingteachingmaterial.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */

public class SubjectData {


    /**
     * answerDatas : null
     * pid : null
     * oid : null
     * multi : []
     * score : 2.0
     * richAnswerMVO : [{"answerId":7698,"billList":[],"richContent":{"text":"<html>\n <head><\/head>\n <body>\n 仰韶文化\n <\/body>\n<\/html>","imgSrc":[]},"id":null,"oid":null,"contentHtml":null,"templateId":null,"content":null,"selected":false,"isCorrect":0,"order":null},{"answerId":7699,"billList":[],"richContent":{"text":"<html>\n <head><\/head>\n <body>\n 河姆渡文化\n <\/body>\n<\/html>","imgSrc":[]},"id":null,"oid":null,"contentHtml":null,"templateId":null,"content":null,"selected":false,"isCorrect":1,"order":null},{"answerId":7700,"billList":[],"richContent":{"text":"<html>\n <head><\/head>\n <body>\n 龙山文化\n <\/body>\n<\/html>","imgSrc":[]},"id":null,"oid":null,"contentHtml":null,"templateId":null,"content":null,"selected":false,"isCorrect":0,"order":null},{"answerId":7701,"billList":[],"richContent":{"text":"<html>\n <head><\/head>\n <body>\n 良渚文化\n <\/body>\n<\/html>","imgSrc":[]},"id":null,"oid":null,"contentHtml":null,"templateId":null,"content":null,"selected":false,"isCorrect":0,"order":null}]
     * uscore : null
     * richQuestion : {"text":"<html>\n <head><\/head>\n <body>\n 下列文化遗存中,反映长江流域母系氏族社会面貌的是( )\n <\/body>\n<\/html>","imgSrc":[]}
     * judge : []
     * id : null
     * key : null
     * order : 1
     * question : null
     * one : []
     * fill : []
     * analysis : null
     * topicType : 1
     * uanswer : null
     * bankId : null
     * topicId : 2839
     * serial : 0
     * ask : []
     * subType : null
     * bsList : []
     * richAnalysis : null
     * isCorrect : null
     */

    private Object answerDatas;
    private Object pid;
    private Object oid;
    private double score;
    private Object uscore;
    private RichQuestionBean richQuestion;
    private Object id;
    private Object key;
    private int order;
    private Object question;
    private Object analysis;
    private int topicType;
    private Object uanswer;
    private Object bankId;
    private int topicId;
    private int serial;
    private Object subType;
    private Object richAnalysis;
    private Object isCorrect;
    private List<?> multi;
    private List<RichAnswerMVOBean> richAnswerMVO;
    private List<?> judge;
    private List<?> one;
    private List<?> fill;
    private List<?> ask;
    private List<?> bsList;

    public Object getAnswerDatas() {
        return answerDatas;
    }

    public void setAnswerDatas(Object answerDatas) {
        this.answerDatas = answerDatas;
    }

    public Object getPid() {
        return pid;
    }

    public void setPid(Object pid) {
        this.pid = pid;
    }

    public Object getOid() {
        return oid;
    }

    public void setOid(Object oid) {
        this.oid = oid;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Object getUscore() {
        return uscore;
    }

    public void setUscore(Object uscore) {
        this.uscore = uscore;
    }

    public RichQuestionBean getRichQuestion() {
        return richQuestion;
    }

    public void setRichQuestion(RichQuestionBean richQuestion) {
        this.richQuestion = richQuestion;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Object getQuestion() {
        return question;
    }

    public void setQuestion(Object question) {
        this.question = question;
    }

    public Object getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Object analysis) {
        this.analysis = analysis;
    }

    public int getTopicType() {
        return topicType;
    }

    public void setTopicType(int topicType) {
        this.topicType = topicType;
    }

    public Object getUanswer() {
        return uanswer;
    }

    public void setUanswer(Object uanswer) {
        this.uanswer = uanswer;
    }

    public Object getBankId() {
        return bankId;
    }

    public void setBankId(Object bankId) {
        this.bankId = bankId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public Object getSubType() {
        return subType;
    }

    public void setSubType(Object subType) {
        this.subType = subType;
    }

    public Object getRichAnalysis() {
        return richAnalysis;
    }

    public void setRichAnalysis(Object richAnalysis) {
        this.richAnalysis = richAnalysis;
    }

    public Object getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Object isCorrect) {
        this.isCorrect = isCorrect;
    }

    public List<?> getMulti() {
        return multi;
    }

    public void setMulti(List<?> multi) {
        this.multi = multi;
    }

    public List<RichAnswerMVOBean> getRichAnswerMVO() {
        return richAnswerMVO;
    }

    public void setRichAnswerMVO(List<RichAnswerMVOBean> richAnswerMVO) {
        this.richAnswerMVO = richAnswerMVO;
    }

    public List<?> getJudge() {
        return judge;
    }

    public void setJudge(List<?> judge) {
        this.judge = judge;
    }

    public List<?> getOne() {
        return one;
    }

    public void setOne(List<?> one) {
        this.one = one;
    }

    public List<?> getFill() {
        return fill;
    }

    public void setFill(List<?> fill) {
        this.fill = fill;
    }

    public List<?> getAsk() {
        return ask;
    }

    public void setAsk(List<?> ask) {
        this.ask = ask;
    }

    public List<?> getBsList() {
        return bsList;
    }

    public void setBsList(List<?> bsList) {
        this.bsList = bsList;
    }

    public static class RichQuestionBean {
        /**
         * text : <html>
         <head></head>
         <body>
         下列文化遗存中,反映长江流域母系氏族社会面貌的是( )
         </body>
         </html>
         * imgSrc : []
         */

        private String text;
        private List<?> imgSrc;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<?> getImgSrc() {
            return imgSrc;
        }

        public void setImgSrc(List<?> imgSrc) {
            this.imgSrc = imgSrc;
        }
    }

    public static class RichAnswerMVOBean {
        /**
         * answerId : 7698
         * billList : []
         * richContent : {"text":"<html>\n <head><\/head>\n <body>\n 仰韶文化\n <\/body>\n<\/html>","imgSrc":[]}
         * id : null
         * oid : null
         * contentHtml : null
         * templateId : null
         * content : null
         * selected : false
         * isCorrect : 0
         * order : null
         */

        private int answerId;
        private RichContentBean richContent;
        private Object id;
        private Object oid;
        private Object contentHtml;
        private Object templateId;
        private Object content;
        private boolean selected;
        private int isCorrect;
        private Object order;
        private List<?> billList;

        public int getAnswerId() {
            return answerId;
        }

        public void setAnswerId(int answerId) {
            this.answerId = answerId;
        }

        public RichContentBean getRichContent() {
            return richContent;
        }

        public void setRichContent(RichContentBean richContent) {
            this.richContent = richContent;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getOid() {
            return oid;
        }

        public void setOid(Object oid) {
            this.oid = oid;
        }

        public Object getContentHtml() {
            return contentHtml;
        }

        public void setContentHtml(Object contentHtml) {
            this.contentHtml = contentHtml;
        }

        public Object getTemplateId() {
            return templateId;
        }

        public void setTemplateId(Object templateId) {
            this.templateId = templateId;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public int getIsCorrect() {
            return isCorrect;
        }

        public void setIsCorrect(int isCorrect) {
            this.isCorrect = isCorrect;
        }

        public Object getOrder() {
            return order;
        }

        public void setOrder(Object order) {
            this.order = order;
        }

        public List<?> getBillList() {
            return billList;
        }

        public void setBillList(List<?> billList) {
            this.billList = billList;
        }

        public static class RichContentBean {
            /**
             * text : <html>
             <head></head>
             <body>
             仰韶文化
             </body>
             </html>
             * imgSrc : []
             */

            private String text;
            private List<?> imgSrc;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public List<?> getImgSrc() {
                return imgSrc;
            }

            public void setImgSrc(List<?> imgSrc) {
                this.imgSrc = imgSrc;
            }
        }
    }
}
