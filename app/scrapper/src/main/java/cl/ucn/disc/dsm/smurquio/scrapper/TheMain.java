/*
 * Copyright (c) 2021 Sebastián Murquio Castillo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cl.ucn.disc.dsm.smurquio.scrapper;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TheMain {

  /**
   * The JSON parser.
   */
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  /**
   *The starting point.
   *@param args to use.
   **/
  public static void main(String[] args) throws IOException {

    //Connect and get the Document
    String json = Jsoup.connect("http://127.0.0.1:8080/v1/news")
        .ignoreContentType(true)
        .execute()
        .body();

    FileUtils.writeStringToFile(
        new File("app/src/main/assets/news.json"),
        json,
        StandardCharsets.UTF_8);


    String data = FileUtils
        .readFileToString(new File("app/src/main/assets/news.json"), StandardCharsets.UTF_8);

    //Define the Type
    Type type = new TypeToken<List<News>>(){
    }.getType();

    //The List of News (string -> List<News>)
    List<News> newsList = GSON.fromJson(data, type);

  }
}