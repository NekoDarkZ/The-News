package cl.ucn.disc.dsm.smurquio.news;

import java.util.List;

public interface Contracts {

  /**
   * Returns all the news.
   * @return the news.
   */
  List<News> retrieveNews();
}
