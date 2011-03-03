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

import org.apache.commons.lang.StringUtils;

/**
 * キャプチャで利用するユーティリティ
 */
public class CaptureUtil {

    /** プレフィックス */
    public static final String CONTENTS_FOR_PREFIX = "_contentFor";

    /**
     * キャプチャに利用する名前を取得する
     * @param name
     * @return
     */
    public static String captureName(String name) {
        return CaptureUtil.CONTENTS_FOR_PREFIX + StringUtils.capitalize(name);
    }

    public static String nullToEmpty(String string) {
        return (string == null) ? "" : string;
    }

    public static LinkedList<String> nullToEmptyList(LinkedList<String> list) {
        return (list == null) ? new LinkedList<String>() : list;
    }
}
