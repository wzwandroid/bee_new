package com.bee.admin.vo;

/**
 * @author mark
 * @date 2015-06-09
 */
public class SelectVo {

    private String value;
    private String text;

    public SelectVo() {

    }

    public SelectVo(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SelectVo selectVo = (SelectVo) o;

        if (text != null ? !text.equals(selectVo.text) : selectVo.text != null) return false;
        if (value != null ? !value.equals(selectVo.value) : selectVo.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
