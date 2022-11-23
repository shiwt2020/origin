package cn.esthe.test;


import java.io.Serializable;


public class Dog implements Serializable {
    private static final long serialVersionUID = 443139922299147L;
    private LinkInfo linkInfo;

    public LinkInfo getLinkInfo() {
        return linkInfo;
    }

    public void setLinkInfo(LinkInfo linkInfo) {
        this.linkInfo = linkInfo;
    }

    public static class LinkInfo {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
