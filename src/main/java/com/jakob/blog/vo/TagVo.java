package com.jakob.blog.vo;

/**
 * Tag值对象
 * @author Jakob
 */
public class TagVo {
    private String name;
    private Long count;

    public TagVo(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
