package cl.ucn.disc.dsm.smurquio.news;

import net.openhft.hashing.LongHashFunction;

import org.threeten.bp.ZonedDateTime;
import lombok.Getter;

public final class News {

  @Getter
  private final Long id;

  @Getter
  private final String title;

  @Getter
  private final String source;

  @Getter
  private final String author;

  @Getter
  private final String url;

  @Getter
  private final String urlImage;

  @Getter
  private final String description;

  @Getter
  private final String content;

  @Getter
  private final ZonedDateTime published_at;

  /**
   * The Constructor of News.
   * @param title can't be null.
   * @param source can't be null.
   * @param author can't be null.
   * @param url can be null.
   * @param urlImage can be null.
   * @param description can't be null.
   * @param content can't be null.
   * @param published_at can't be null.
   */
  public News(final String title,
              final String source,
              final String author,
              final String url,
              final String urlImage,
              final String description,
              final String content,
              final ZonedDateTime published_at) {

    // Title
    if(title == null || title.length() < 2) {
      throw new IllegalArgumentException("Title Required.");
    }
    this.title = title;

    // Source
    if(source == null || source.length() < 2) {
      throw new IllegalArgumentException("Source Required.");
    }
    this.source = source;

    // Author
    if(author == null || author.length() < 3) {
      throw new IllegalArgumentException("Author Required.");
    }
    this.author = author;

    // ID: Hashing(title + | + source + | + author)
    this.id = LongHashFunction.xx().hashChars(title + "|" + source +  "|"+ author);


    this.url = url;
    this.urlImage = urlImage;

    // Description
    if(description == null || description.length() < 4) {
      throw new IllegalArgumentException("Description Required.");
    }
    this.description = description;

    // Content
    if(content == null || content.length() < 2) {
      throw new IllegalArgumentException("Content Required.");
    }
    this.content = content;

    if(published_at == null){
      throw new IllegalArgumentException("Published At Required");
    }
    this.published_at = published_at;
  }
}
