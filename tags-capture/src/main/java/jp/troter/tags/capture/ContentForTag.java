/*
 * Copyright 2011 Takumi IINO.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.troter.tags.capture;

import java.util.LinkedList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * 文字列のキャプチャを行います。
 * <p>
 * <code>name</code> 属性で指定した名前を利用して
 * <code>body</code>に含めたJSP、もしくは <code>value</code> 属性の値をキャプチャします。<br />
 * contentForタグでキャプチャした文字列はyieldタグで出力できます。
 * </p>
 */
public class ContentForTag extends BodyTagSupport {

    private static final long serialVersionUID = 1L;

    /** キャプチャ名 */
    private String name;

    /** bodyを利用しない場合は指定 */
    private String value;

    /** body */
    private String body;

    /** 先頭に追加するか */
    private boolean addFirst = false;

    /** i:contentForで囲んだbodyもしくはvalueの重複を許すか */
    private boolean duplicate;

    /**
     * @param name キャプチャ名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param value キャプチャする値
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 先頭に追加するか
     * @param addFirst 先頭に追加する場合trueを設定する。
     */
    public void setAddFirst(boolean addFirst) {
        this.addFirst = addFirst;
    }

    /**
     * 重複を許すか
     * @param duplicate
     */
    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    /**
     * コンストラクタ
     */
    public ContentForTag() {
        super();
    }

    @Override
    public int doStartTag() throws JspException {
        // clear body content
        body = null;

        // Do we need to evaluate body ?
        if (value == null) {
            return EVAL_BODY_BUFFERED;
        }

        // Value is set, don't evaluate body.
        return SKIP_BODY;
    }

    @Override
    public int doAfterBody() throws JspException {
        if (bodyContent != null) {
            body = bodyContent.getString();
        }
        return (SKIP_BODY);
    }

    @Override
    public int doEndTag() throws JspException {
        String name = CaptureUtil.captureName(this.name);
        @SuppressWarnings("unchecked")
		LinkedList<String> rawCurrent = (LinkedList<String>) pageContext.getAttribute(name, PageContext.REQUEST_SCOPE);
        LinkedList<String> current = CaptureUtil.nullToEmptyList(rawCurrent);

        concatToContextValue(name, current);

        return EVAL_PAGE;
    }

    @Override
    public void release() {
        super.release();
        name = null;
        value = null;
        addFirst = false;
        duplicate = false;
        super.release();
    }

    public void concatToContextValue(String name, LinkedList<String> current) {
        String value = StringUtils.isBlank(this.value) ? this.body : this.value;
        value = CaptureUtil.nullToEmpty(value);
        if (!duplicate && current.contains(value)) { return; }

        if (addFirst) { current.addFirst(value); }
        else { current.add(value); }

        pageContext.setAttribute(name, current, PageContext.REQUEST_SCOPE);
    }
}
