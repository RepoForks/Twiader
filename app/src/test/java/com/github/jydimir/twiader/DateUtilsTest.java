/*
 * Copyright 2016  Andrii Lisun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jydimir.twiader;

import com.github.jydimir.twiader.util.DateUtils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class DateUtilsTest {
    @Test
    public void parseTwitterUTCTest() {
        String twitterDate = "Sun Jul 03 02:48:28 +0000 2016";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 48);
        calendar.set(Calendar.SECOND, 28);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.YEAR, 2016);
        Date parsedDate = DateUtils.parseTwitterUTC(twitterDate);
        assertEquals(calendar.getTime().getTime(), parsedDate.getTime());
    }
}
