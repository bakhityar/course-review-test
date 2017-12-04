package com.teamtreehouse.courses.dao;

import static org.junit.Assert.*;

import com.teamtreehouse.courses.model.Course;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Sql2oCourseDaoTest {

  private Sql2oCourseDao dao;
  private Connection conn;
  @Before
  public void setUp() throws Exception {
    String connectiobString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
    Sql2o sql2o = new Sql2o(connectiobString, "", "");
    dao = new Sql2oCourseDao(sql2o);
    //Keep connection open throught test
    conn = sql2o.open();
  }

  @After
  public void tearDown() throws Exception {
    conn.close();
  }

  @Test
  public void addingCourseSetsId() throws Exception {
    Course course = newTestCourse();
    int originalId = course.getId();
    dao.add(course);

    assertNotEquals(originalId, course.getId());
  }

  @Test
  public void AddedCoursesAreReturnedFromFindAll() throws Exception {
    Course course = newTestCourse();
    dao.add(course);

    assertEquals(1, dao.findAll().size());

  }

  @Test
  public void noCoursesReturnsEmptyList() throws Exception {
    assertEquals(0, dao.findAll().size());
  }

  @Test
  public void existingCoursesCanBeFindById() throws Exception {
    Course course = newTestCourse();
    dao.add(course);

    Course foundCourse = dao.findById(course.getId());
    assertEquals(course, foundCourse);

  }

  private Course newTestCourse() {
    return new Course("Test", "http://test.com");
  }
}