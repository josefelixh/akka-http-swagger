/**
 * Copyright 2014 Getty Imges, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tecsisa.akka.http.swagger.samples

import javax.ws.rs.Path

import akka.actor.Actor
import akka.http.scaladsl.server.Directives
import akka.stream.scaladsl.ImplicitMaterializer
import com.tecsisa.akka.http.swagger.utils.JsonMarshalling
import com.wordnik.swagger.annotations._


abstract class TestApiWithNoAnnotation

@Api(value = "/test")
abstract class TestApiDoesNotExtendHttpService

@Api(value = "/test")
abstract class TestApiWithOnlyDataType {
  _: Actor with ImplicitMaterializer with Directives =>

  @ApiOperation(value = "testApiOperation", httpMethod = "GET")
  @ApiImplicitParams(Array(new ApiImplicitParam(name = "test", value = "test param", dataType = "TestModel", paramType = "query")))
  def testOperation
}

@Api(value = "/test")
abstract class TestApiWithPathOperation {
  _: Actor with ImplicitMaterializer with Directives
    with JsonMarshalling =>

  @Path("/sub/{someParam}/path/{anotherParam}")
  @ApiOperation(value = "subPathApiOperation", httpMethod = "GET", notes = "some notes")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "someParam", value = "some param", dataType = "TestModel", paramType = "path"),
    new ApiImplicitParam(name = "anotherParam", value = "another param", dataType = "TestModel", paramType = "path")
  ))
  def subPathOperation

  @Path("/other/sub/{someParam}/path/{anotherParam}")
  @ApiOperation(value = "otherSubPathApiOperation", httpMethod = "GET")
  def otherSubPathOperation
  }

@Api(value = "/test")
abstract class TestApiWithParamsHierarchy {
  _: Actor with ImplicitMaterializer with Directives
    with JsonMarshalling =>

  @Path("/paramHierarchyOperation")
  @ApiOperation(value = "paramHierarchyOperation", httpMethod = "GET", response = classOf[ModelExtension])
  def paramHierarchyOperation
}

// because the swagger output format has the operations listed under paths, the most sensible
// option for ordering seems to be to order them correctly within a particular path, and then
// order the paths by the lowest position of an operation they contain, hence why the expected
// order here (as indicated by `value`) doesn't match the position attributes
@Api(value = "/test")
abstract class TestApiWithOperationPositions {
  _: Actor with ImplicitMaterializer with Directives
    with JsonMarshalling =>

  @Path("/path1")
  @ApiOperation(position = 3, value = "order3", httpMethod = "GET", response = classOf[ModelBase])
  def operation4
  @Path("/path0")
  @ApiOperation(position = 0, value = "order0", httpMethod = "GET", response = classOf[ModelBase])
  def operation0
  @Path("/path1")
  @ApiOperation(position = 1, value = "order2", httpMethod = "GET", response = classOf[ModelBase])
  def operation1
  @Path("/path0")
  @ApiOperation(position = 2, value = "order1", httpMethod = "GET", response = classOf[ModelBase])
  def operation3
  @Path("/path2")
  @ApiOperation(position = 4, value = "order4", httpMethod = "GET", response = classOf[ModelBase])
  def operation2

}

@Api(value = "/test", basePath = "http://override.com/api")
abstract class TestApiWithBasePathAnnotation {
  _: Actor with ImplicitMaterializer with Directives
    with JsonMarshalling =>

  @ApiOperation(value = "testApiOperation", httpMethod = "GET")
  @ApiImplicitParams(Array(new ApiImplicitParam(name = "pathParam", value = "test param", dataType = "string", paramType = "path")))
  def testOperation
}
