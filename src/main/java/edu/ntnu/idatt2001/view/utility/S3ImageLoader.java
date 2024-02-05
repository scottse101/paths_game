package edu.ntnu.idatt2001.view.utility;

import java.io.InputStream;
import java.net.URL;
import javafx.scene.image.Image;

/**
 * This class is used to load images from S3.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class S3ImageLoader {
  private static S3ImageLoader instance = null;

  private S3ImageLoader() {
    // private constructor to prevent instantiation outside of this class
  }

  /**
   * Returns the instance of the S3ImageLoader class.
   *
   * @return the instance of the S3ImageLoader class.
   */
  public static S3ImageLoader getInstance() {
    if (instance == null) {
      instance = new S3ImageLoader();
    }
    return instance;
  }

  /**
   * Loads an image from S3.
   *
   * @param bucketName the name of the bucket.
   * @param key the key of the image.
   * @param region the region of the bucket.
   * @return the image.
   */
  public Image loadImageFromS3(String bucketName, String key, String region) {
    String encodedKey = key.replace(" ", "_").toLowerCase();
    String url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, encodedKey);
    InputStream stream = null;
    try {
      stream = new URL(url).openStream();
      Image image = new Image(stream);
      return image;
    } catch (Exception e) {
      return null;
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (Exception e) {
          // Handle exception
        }
      }
    }
  }
}