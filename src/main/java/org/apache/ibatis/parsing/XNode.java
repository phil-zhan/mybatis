/*
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 对org.w3c.dom.Node的包装
 *
 * @author Clinton Begin
 */
public class XNode {

  //org.w3c.dom.Node
  private final Node node;
  // Node节点名称
  private final String name;
  // Node节点内容
  private final String body;
  // 节点属性集合
  private final Properties attributes;
  // mybatis-config.xml配置文件中<properties>节点下定义的键值对
  private final Properties variables;
  // XPathParser对象，该Node对象由此XPathParser对象生成
  private final XPathParser xpathParser;

  //在构造时就把一些信息（属性，body）全部解析好，以便我们直接通过getter函数取得
  public XNode(XPathParser xpathParser, Node node, Properties variables) {
    this.xpathParser = xpathParser;
    this.node = node;
    this.name = node.getNodeName();
    this.variables = variables;
    this.attributes = parseAttributes(node);
    this.body = parseBody(node);
  }

  public XNode newXNode(Node node) {
    return new XNode(xpathParser, node, variables);
  }

  public XNode getParent() {
    //调用Node.getParentNode,如果取到，包装一下，返回XNode
    Node parent = node.getParentNode();
    if (!(parent instanceof Element)) {
      return null;
    } else {
      return new XNode(xpathParser, parent, variables);
    }
  }

  //取得完全的path (a/b/c)
  public String getPath() {
    //循环依次取得节点的父节点，然后倒序打印,也可以用一个堆栈实现
    StringBuilder builder = new StringBuilder();
    Node current = node;
    while (current instanceof Element) {
      if (current != node) {
        builder.insert(0, "/");
      }
      builder.insert(0, current.getNodeName());
      current = current.getParentNode();
    }
    return builder.toString();
  }

  //取得标示符   ("resultMap[authorResult]")
  //XMLMapperBuilder.resultMapElement调用
  //	<resultMap id="authorResult" type="Author">
  //	  <id property="id" column="author_id"/>
  //	  <result property="username" column="author_username"/>
  //	  <result property="password" column="author_password"/>
  //	  <result property="email" column="author_email"/>
  //	  <result property="bio" column="author_bio"/>
  //	</resultMap>
  public String getValueBasedIdentifier() {
    StringBuilder builder = new StringBuilder();
    XNode current = this;
    while (current != null) {
      if (current != this) {
        builder.insert(0, "_");
      }
      //先拿id，拿不到再拿value,再拿不到拿property
      String value = current.getStringAttribute("id",
          current.getStringAttribute("value",
              current.getStringAttribute("property", (String) null)));
      if (value != null) {
        value = value.replace('.', '_');
        builder.insert(0, "]");
        builder.insert(0,
            value);
        builder.insert(0, "[");
      }
      builder.insert(0, current.getName());
      current = current.getParent();
    }
    return builder.toString();
  }

  //以下方法都是把XPathParser的方法再重复一遍
  public String evalString(String expression) {
    return xpathParser.evalString(node, expression);
  }

  public Boolean evalBoolean(String expression) {
    return xpathParser.evalBoolean(node, expression);
  }

  public Double evalDouble(String expression) {
    return xpathParser.evalDouble(node, expression);
  }

  public List<XNode> evalNodes(String expression) {
    return xpathParser.evalNodes(node, expression);
  }

  public XNode evalNode(String expression) {
    return xpathParser.evalNode(node, expression);
  }

  public Node getNode() {
    return node;
  }

  public String getName() {
    return name;
  }

  public String getStringBody() {
    return getStringBody(null);
  }

  public String getStringBody(String def) {
    return body == null ? def : body;
  }

  public Boolean getBooleanBody() {
    return getBooleanBody(null);
  }

  public Boolean getBooleanBody(Boolean def) {
    return body == null ? def : Boolean.valueOf(body);
  }

  public Integer getIntBody() {
    return getIntBody(null);
  }

  public Integer getIntBody(Integer def) {
    return body == null ? def : Integer.valueOf(body);
  }

  public Long getLongBody() {
    return getLongBody(null);
  }

  public Long getLongBody(Long def) {
    return body == null ? def : Long.valueOf(body);
  }

  public Double getDoubleBody() {
    return getDoubleBody(null);
  }

  public Double getDoubleBody(Double def) {
    return body == null ? def : Double.valueOf(body);
  }

  public Float getFloatBody() {
    return getFloatBody(null);
  }

  public Float getFloatBody(Float def) {
    return body == null ? def : Float.valueOf(body);
  }

  public <T extends Enum<T>> T getEnumAttribute(Class<T> enumType, String name) {
    return getEnumAttribute(enumType, name, null);
  }

  public <T extends Enum<T>> T getEnumAttribute(Class<T> enumType, String name, T def) {
    String value = getStringAttribute(name);
    return value == null ? def : Enum.valueOf(enumType,value);
  }

  /**
   * Return a attribute value as String.
   *
   * <p>
   * If attribute value is absent, return value that provided from supplier of default value.
   *
   * @param name
   *          attribute name
   * @param defSupplier
   *          a supplier of default value
   * @return the string attribute
   * @since 3.5.4
   */
  public String getStringAttribute(String name, Supplier<String> defSupplier) {
    String value = attributes.getProperty(name);
    return value == null ? defSupplier.get() : value;
  }

  public String getStringAttribute(String name) {
    return getStringAttribute(name, (String) null);
  }

  public String getStringAttribute(String name, String def) {
    String value = attributes.getProperty(name);
    return value == null ? def : value;
  }

  public Boolean getBooleanAttribute(String name) {
    return getBooleanAttribute(name, null);
  }

  public Boolean getBooleanAttribute(String name, Boolean def) {
    String value = attributes.getProperty(name);
    return value == null ? def : Boolean.valueOf(value);
  }

  public Integer getIntAttribute(String name) {
    return getIntAttribute(name, null);
  }

  public Integer getIntAttribute(String name, Integer def) {
    String value = attributes.getProperty(name);
    return value == null ? def : Integer.valueOf(value);
  }

  public Long getLongAttribute(String name) {
    return getLongAttribute(name, null);
  }

  public Long getLongAttribute(String name, Long def) {
    String value = attributes.getProperty(name);
    return value == null ? def : Long.valueOf(value);
  }

  public Double getDoubleAttribute(String name) {
    return getDoubleAttribute(name, null);
  }

  public Double getDoubleAttribute(String name, Double def) {
    String value = attributes.getProperty(name);
    return value == null ? def : Double.valueOf(value);
  }

  public Float getFloatAttribute(String name) {
    return getFloatAttribute(name, null);
  }

  public Float getFloatAttribute(String name, Float def) {
    String value = attributes.getProperty(name);
    return value == null ? def : Float.valueOf(value);
  }

  //得到孩子，原理是调用Node.getChildNodes
  public List<XNode> getChildren() {
    List<XNode> children = new ArrayList<>();
    NodeList nodeList = node.getChildNodes();
    if (nodeList != null) {
      for (int i = 0, n = nodeList.getLength(); i < n; i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          children.add(new XNode(xpathParser, node, variables));
        }
      }
    }
    return children;
  }

  //得到孩子，返回Properties，孩子的格式肯定都有name,value属性
  public Properties getChildrenAsProperties() {
    Properties properties = new Properties();
    for (XNode child : getChildren()) {
      String name = child.getStringAttribute("name");
      String value = child.getStringAttribute("value");
      if (name != null && value != null) {
        properties.setProperty(name, value);
      }
    }
    return properties;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    toString(builder, 0);
    return builder.toString();
  }

  //打印信息，为了调试用
  private void toString(StringBuilder builder, int level) {
    builder.append("<");
    builder.append(name);
    for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
      builder.append(" ");
      builder.append(entry.getKey());
      builder.append("=\"");
      builder.append(entry.getValue());
      builder.append("\"");
    }
    List<XNode> children = getChildren();
    if (!children.isEmpty()) {
      builder.append(">\n");
      for (XNode child : children) {
        indent(builder, level + 1);
        child.toString(builder, level + 1);
      }
      indent(builder, level);
      builder.append("</");
      builder.append(name);
      builder.append(">");
    } else if (body != null) {
      builder.append(">");
      builder.append(body);
      builder.append("</");
      builder.append(name);
      builder.append(">");
    } else {
      builder.append("/>");
      indent(builder, level);
    }
    builder.append("\n");
  }

  private void indent(StringBuilder builder, int level) {
    for (int i = 0; i < level; i++) {
      builder.append("    ");
    }
  }

  //以下2个方法在构造时就解析
  private Properties parseAttributes(Node n) {
    Properties attributes = new Properties();
    // 获取节点的属性集合
    NamedNodeMap attributeNodes = n.getAttributes();
    if (attributeNodes != null) {
      for (int i = 0; i < attributeNodes.getLength(); i++) {
        Node attribute = attributeNodes.item(i);
        // 使用PropertyParser处理每个属性中的占位符
        String value = PropertyParser.parse(attribute.getNodeValue(), variables);
        attributes.put(attribute.getNodeName(), value);
      }
    }
    return attributes;
  }

  private String parseBody(Node node) {
    // 取不到body，循环取孩子的body，只要取到第一个，立即返回
    String data = getBodyData(node);
    // 当前节点不是文本节点
    if (data == null) {
      // 处理子节点
      NodeList children = node.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        Node child = children.item(i);
        data = getBodyData(child);
        if (data != null) {
          break;
        }
      }
    }
    return data;
  }

  private String getBodyData(Node child) {
    // 只处理文本内容
    if (child.getNodeType() == Node.CDATA_SECTION_NODE
        || child.getNodeType() == Node.TEXT_NODE) {
      String data = ((CharacterData) child).getData();
      // 使用PropertyParser处理文本节点中的占位符
      data = PropertyParser.parse(data, variables);
      return data;
    }
    return null;
  }

}
