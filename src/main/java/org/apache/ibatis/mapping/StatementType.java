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
package org.apache.ibatis.mapping;

/**
 * @author Clinton Begin
 */
public enum StatementType {
  // Statement对象是在executeUpdate或executeQuery方法时指定sql,此时将sql语句发送和执行。
  STATEMENT,

  // PrepareStatement对象是在创建时指定并发送sql，在executeUpdate或executeQuery方法时触发sql执行。
  // 为了防止sql注入，都会选用这种状态
  // PrepareStatement是一种预编译的Statement对象。
  PREPARED,

  // 一般用在存储过程
  CALLABLE
}
