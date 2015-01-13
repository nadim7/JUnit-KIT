package test.blatt5.books;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import test.InteractiveConsoleTest;
import test.TestObject;
import test.TestObject.SystemExitStatus;

/**
 * A test for the Interactive Console (Task B) <br>
 * <br>
 * People that checked this test for being correct and complete:
 * <ul>
 * <li>Roman Langrehr</li>
 * </ul>
 * <br>
 * <br>
 * Things that are currently not tested, but should be:
 * <ul>
 * <li>None</li>
 * </ul>
 * 
 * @author Roman Langrehr
 * @since 05.01.2015
 * @version 1.0
 *
 */
public class BooksInteractiveConsoleTest extends InteractiveConsoleTest {
  public static final String BASE_FILE_PATH = "";
  public static final File BOOK_1 = new File(BASE_FILE_PATH + "book1.txt");
  public static final File BOOK_2 = new File(BASE_FILE_PATH + "book2.txt");
  public static final File EMPTY_BOOK = new File(BASE_FILE_PATH + "empty_book.txt");
  public static final File BOOK_4 = new File(BASE_FILE_PATH + "book4.txt");
  public static final File BOOK_5 = new File(BASE_FILE_PATH + "book5.txt");
  public static final File BOOK_6_1 = new File(BASE_FILE_PATH + "book6_1.txt");
  public static final File BOOK_6_2 = new File(BASE_FILE_PATH + "book6_2.txt");
  public static final File BOOK_7 = new File(BASE_FILE_PATH + "book7.txt");

  @BeforeClass
  public static void createFiles() {
    try {
      PrintStream book1stream = new PrintStream(BOOK_1);
      book1stream.println("Seite1");
      book1stream.println("Lorem ipsum dolor sit amet consetetur sadipscing");
      book1stream.println("Lorem ipsum dolor sit amet consetetur sadipscing");
      book1stream.println("test1");
      book1stream.println("Seite2");
      book1stream.println("Lorem test2 amet");
      book1stream.close();
      PrintStream book2stream = new PrintStream(BOOK_2);
      book2stream.println("Seite1");
      book2stream.close();
      PrintStream book4stream = new PrintStream(BOOK_4);
      book4stream.println("Seite1");
      book4stream.println("Seite2");
      book4stream.println("Lorem test2 amet");
      book4stream.close();
      PrintStream book5stream = new PrintStream(BOOK_5);
      book4stream.println();
      book5stream.println("Seite1");
      book4stream.println();
      book5stream.close();
      book5stream.close();
      PrintStream book61stream = new PrintStream(BOOK_6_1);
      book61stream.println("Seite1");
      book61stream.println("word1");
      book61stream.close();
      PrintStream book62stream = new PrintStream(BOOK_6_2);
      book62stream.println("Seite1");
      book62stream.println("word1");
      book62stream.println("Seite2");
      book62stream.println("word1");
      book62stream.close();
      PrintStream book7stream = new PrintStream(BOOK_7);
      book7stream.println("Seite1");
      book7stream.println("b c a");
      book7stream.println("Seite2");
      book7stream.println("b d");
      book7stream.println("Seite3");
      book7stream.println("aa ca a e");
      book7stream.close();
      EMPTY_BOOK.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @AfterClass
  public static void deleteFiles() {
    BOOK_1.delete();
    BOOK_2.delete();
    EMPTY_BOOK.delete();
    BOOK_4.delete();
    BOOK_5.delete();
    BOOK_6_1.delete();
    BOOK_6_2.delete();
    BOOK_7.delete();
  }

  @Test
  public void testSimpleFileAndQuit() {
    easyTest("quit\n", "", BOOK_1.getAbsolutePath(), "traverse=pre-order");
  }

  @Test
  public void testNoArgs() {
    TestObject.allowSystemExit(SystemExitStatus.WITH_GREATER_THAN_0);// TODO will this setting be
                                                                     // reseted after test end?!
    crashTest("quit\n");
  }

  @Test
  public void testOnlyOneArg() {
    easyTest("quit\n", "", BOOK_1.getAbsolutePath());
  }

  @Test
  public void testOnlyOneArg2() {
    TestObject.allowSystemExit(SystemExitStatus.WITH_GREATER_THAN_0);
    crashTest("quit\n", "", "traverse=pre-order");
  }

  @Test
  public void testWrongArgs() {
    TestObject.allowSystemExit(SystemExitStatus.WITH_GREATER_THAN_0);
    crashTest("quit\n", BOOK_1.getAbsolutePath(), "blablabla");
  }

  @Test
  public void testWrongArgs2() {
    TestObject.allowSystemExit(SystemExitStatus.WITH_GREATER_THAN_0);
    crashTest("quit\n", BOOK_1.getAbsolutePath(), "traverse=blablabla");
  }

  @Test
  public void testWrongArgs3() {
    TestObject.allowSystemExit(SystemExitStatus.WITH_GREATER_THAN_0);
    crashTest("quit\n", BOOK_1.getAbsolutePath(), "traverse=pre-order=blabla");
  }
  
  @Test
  public void testFileDoesNotExist() {
    TestObject.allowSystemExit(SystemExitStatus.WITH_GREATER_THAN_0);
    crashTest("quit\n", "C:/DieseDateiHatHoffentlichNiemand.txt", "traverse=pre-order");
  }
  
  @Test
  public void search1() {
    easyTest("search ipsum\nquit\n", "ipsum:1"+System.lineSeparator(), BOOK_1.getAbsolutePath(), "traverse=pre-order");
  }

  @Test
  public void search2() {
    easyTest("search Lorem\nquit\n", "Lorem:1,2" + System.lineSeparator(),
        BOOK_1.getAbsolutePath(), "traverse=pre-order");
  }

  @Test
  public void searchNull() {
    easyTest("search muellll\nquit\n", "muellll:null" + System.lineSeparator(),
        BOOK_1.getAbsolutePath(), "traverse=pre-order");
  }

  @Test
  public void emptyPage() {
    easyTest("quit\n", "", BOOK_4.getAbsolutePath(), "traverse=pre-order");
  }

  @Test
  public void emptyFile() {
    easyTest("quit\n", "", EMPTY_BOOK.getAbsolutePath(), "traverse=pre-order");
  }

  @Test
  public void emptyLine() {
    easyTest("quit\n", "", BOOK_5.getAbsolutePath(), "traverse=pre-order");
  }

  @Test
  public void searchOnEmptyBook() {
    easyTest("search hiergiebtsnichtszufinden\nquit\n",
        "hiergiebtsnichtszufinden:null" + System.lineSeparator(),
 EMPTY_BOOK.getAbsolutePath(),
        "traverse=pre-order");
  }

  @Test
  public void searchOnEmptyLine() {
    easyTest("search amet\nquit\n", "amet:2" + System.lineSeparator(), BOOK_4.getAbsolutePath(),
        "traverse=pre-order");
  }

  @Test
  public void infoOnEmptyBookPreOrder() {
    easyTest("info\nquit\n", System.lineSeparator(), EMPTY_BOOK.getAbsolutePath(),
        "traverse=pre-order");
  }

  @Test
  public void infoOnEmptyBookInOrder() {
    easyTest("info\nquit\n", System.lineSeparator(), EMPTY_BOOK.getAbsolutePath(),
        "traverse=in-order");
  }

  @Test
  public void infoOnEmptyBookLevelOrder() {
    easyTest("info\nquit\n", System.lineSeparator(), EMPTY_BOOK.getAbsolutePath(),
        "traverse=level-order");
  }

  @Test
  public void infoWithPreOrder1() {
    easyTest("info\nquit\n", "word1:1" + System.lineSeparator(), BOOK_6_1.getAbsolutePath(),
        "traverse=pre-order");
  }

  @Test
  public void infoWithInOrder1() {
    easyTest("info\nquit\n", "word1:1" + System.lineSeparator(), BOOK_6_1.getAbsolutePath(),
        "traverse=in-order");
  }

  @Test
  public void infoWithLevelOrder1() {
    easyTest("info\nquit\n", "word1:1" + System.lineSeparator(), BOOK_6_1.getAbsolutePath(),
        "traverse=level-order");
  }

  @Test
  public void infoWithPreOrder2() {
    easyTest("info\nquit\n", "word1:1,2" + System.lineSeparator(), BOOK_6_2.getAbsolutePath(),
        "traverse=pre-order");
  }

  @Test
  public void infoWithInOrder2() {
    easyTest("info\nquit\n", "word1:1,2" + System.lineSeparator(), BOOK_6_2.getAbsolutePath(),
        "traverse=in-order");
  }

  @Test
  public void infoWithLevelOrder2() {
    easyTest("info\nquit\n", "word1:1,2" + System.lineSeparator(), BOOK_6_2.getAbsolutePath(),
        "traverse=level-order");
  }

  @Test
  public void infoWithPreOrder3() {
    easyTest("info\nquit\n", "b:1,2,a:1,3,aa:3,c:1,d:2,ca:3,e:3" + System.lineSeparator(),
        BOOK_7.getAbsolutePath(),
        "traverse=pre-order");
  }

  @Test
  public void infoWithInOrder3() {
    easyTest("info\nquit\n", "a:1,3,aa:3,b:1,2,c:1,ca:3,d:2,e:3" + System.lineSeparator(),
        BOOK_7.getAbsolutePath(), "traverse=in-order");
  }

  @Test
  public void infoWithLevelOrder3() {
    easyTest("info\nquit\n", "b:1,2,a:1,3,c:1,aa:3,d:2,ca:3,e:3" + System.lineSeparator(),
        BOOK_7.getAbsolutePath(), "traverse=level-order");
  }

  @Test
  public void searchNoArg() {
    crashTest("search\nquit\n", BOOK_1.getAbsolutePath(), "traverse=level-order");
  }
}