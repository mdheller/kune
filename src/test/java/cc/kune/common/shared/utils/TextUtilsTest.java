/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package cc.kune.common.shared.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class TextUtilsTest.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TextUtilsTest {

  /** The Constant DOMAIN_REGEXP. */
  private static final String DOMAIN_REGEXP = "^http([s]|)://localhost/.*";

  /**
   * Test de accent.
   */
  @Test
  public void testDeAccent() {
    assertTrue(TextUtils.deAccent("áéíóúàèìòùâêîôûäëïöüÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÄËÏÖÜñÑçÇ").equals(
        "aeiouaeiouaeiouaeiouAEIOUAEIOUAEIOUAEIOUnNcC"));
  }

  /**
   * Test email.
   */
  @Test
  public void testEmail() {
    assertTrue("kk@ex.com".matches(TextUtils.EMAIL_REGEXP));
    assertTrue("kk@local.net".matches(TextUtils.EMAIL_REGEXP));
  }

  /**
   * Test email list.
   */
  @Test
  public void testEmailList() {
    assertTrue("kk@ex.com,kk@ex2.com".matches(TextUtils.EMAIL_REGEXP_LIST));
    assertTrue("kk@ex.com, kk@ex2.com".matches(TextUtils.EMAIL_REGEXP_LIST));
    assertTrue("kk@ex.com,,,, kk@ex2.com".matches(TextUtils.EMAIL_REGEXP_LIST));
    assertTrue("kk@ex.com,   kk@ex2.com".matches(TextUtils.EMAIL_REGEXP_LIST));
    assertTrue("kk@ex.com,   kk@ex2.com, kk@ex3.com".matches(TextUtils.EMAIL_REGEXP_LIST));
    assertFalse("kk@ex.com   ;kk@ex2.com".matches(TextUtils.EMAIL_REGEXP_LIST));
    assertFalse("kk@ex.com;kk@ex2.com".matches(TextUtils.EMAIL_REGEXP_LIST));
    // with Carriage Return, etc:
    assertTrue("kk@ex.com, \t  \n  \f  \r kk@ex2.com".matches(TextUtils.EMAIL_REGEXP_LIST));
  }

  /**
   * Test remove http.
   */
  @Test
  public void testRemoveHttp() {
    assertEquals("localhost", TextUtils.removeHttp("http://localhost/"));
    assertEquals("localhost", TextUtils.removeHttp("https://localhost/"));
    assertEquals("localhost", TextUtils.removeHttp("http://localhost"));
    assertEquals("localhost", TextUtils.removeHttp("https://localhost"));
    assertEquals("localhost:8080", TextUtils.removeHttp("http://localhost:8080/"));
    assertEquals("localhost:8080", TextUtils.removeHttp("https://localhost:8080/"));
  }

  /**
   * Test url domain.
   */
  @Test
  public void testUrlDomain() {
    assertTrue("http://localhost/kk".matches(DOMAIN_REGEXP));
    assertTrue("https://localhost/kk".matches(DOMAIN_REGEXP));
    assertFalse("http://localhost:9898/kk".matches(DOMAIN_REGEXP));
    assertFalse("http://localhost".matches(DOMAIN_REGEXP));
    assertFalse("ftp://localhost/kk".matches(DOMAIN_REGEXP));
  }
}
