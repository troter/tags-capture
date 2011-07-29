Simple capture library for JSP.
===============================

Railsのcontent_forのような動作をする、シンプルなキャプチャライブラリです。

pom.xmlに追記する設定
---------------------

**mavenリポジトリを追加**

    <repositories>
      <repository>
        <id>troter.jp/release</id>
        <name>TROTER.JP Release Maven2 Repository</name>
        <url>http://troter.jp/maven2/release</url>
      </repository>
    </repositories>

**依存関係を追加**

    <dependencies>
      <dependency>
        <groupId>jp.troter</groupId>
        <artifactId>tags-capture</artifactId>
        <version>1.0.1</version>
      </dependency>
    </dependencies>

利用方法
--------

**taglibディレクティブで宣言すれば利用できるようになります。**

    <%@taglib prefix="capture" uri="http://troter.jp/tags-capture"%>

サンプル
--------

**index.jsp**

    <%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
    <%@taglib prefix="capture" uri="http://troter.jp/tags-capture"%>
    
    <%-- 基本的な例  --%>
    <capture:contentFor name="title" value="TIM Labs" />
    
    <%-- value属性だけで無くbodyでも指定できます。 --%>
    <capture:contentFor name="javascript">
      alert("middle");
    </capture:contentFor>
    
    <%--
     | 同じ名前で複数キャプチャした場合は連結されます。
     |%-->
    <%-- 通常は末尾に追加されます --%>
    <capture:contentFor name="javascript">
      alert("last");
    </capture:contentFor>
    
    <%-- addFirst属性をtrueにすると先頭に追加されます --%>
    <capture:contentFor name="javascript" addFirst="true">
      alert("first");
    </capture:contentFor>
    
    <%-- 同じ文字列は追加されません --%>
    <capture:contentFor name="javascript">
      alert("first");
    </capture:contentFor>
    
    <%-- duplicate属性をtrueにすると追加できます --%>
    <capture:contentFor name="javascript" duplicate="true">
      alert("last");
    </capture:contentFor>
    
    <%-- キャプチャした文字列はcapture:yieldで出力できます。 --%>
    <html>
    <head>
      <title><capture:yield name="title" /></title>
      <script type="text/javascript">
        <capture:yield name="javascript" />
      </script>
    </head>
    <body>
     <p> alert </p>
    </body>
    </html>

**出力**

    <html>
    <head>
      <title>TIM Labs</title>
      <script type="text/javascript">
    
      alert("first");
    
      alert("middle");
    
      alert("last");
    
      alert("last");
    </script>
    </head>
    <body>
     <p> alert </p>
    </body>
    </html>
