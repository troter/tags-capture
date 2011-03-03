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

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * キャプチャした文字列の出力を行います。
 * <p>
 * <code>name</code> 属性で指定した名前を利用して
 * contentForタグでキャプチャした文字列を出力します。
 * </p>
 */
public class YieldTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    /** キャプチャ名 */
    private String name;

    /**
     * @param name キャプチャ名
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int doEndTag() throws JspException {
        String name = CaptureUtil.captureName(this.name);
        @SuppressWarnings("unchecked")
        LinkedList<String> rawValue = (LinkedList<String>) pageContext.getAttribute(name, PageContext.REQUEST_SCOPE);
        LinkedList<String> value = CaptureUtil.nullToEmptyList(rawValue);

        String displayValue = convertToString(value);

        try {
            pageContext.getOut().print(displayValue);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new JspException("Error - tag.yield : IOException ");
        }

        return EVAL_PAGE;
    }

    @Override
    public void release() {
        super.release();
        name = null;
    }

    private String convertToString(LinkedList<String> value) {
        StringBuilder ret = new StringBuilder();
        for (String e: value) { ret.append(e); }

        return ret.toString();
    }
}
