import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.ArrayList;

public class NewsFeedTest 
{

    private MessagePost messagePost;
    private PhotoPost photoPost;
    private NewsFeed newsFeed;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUp() 
    {
        messagePost = new MessagePost("alice", "Hello, world!");
        photoPost = new PhotoPost("bob", "image.jpg", "Nice view");
        newsFeed = new NewsFeed();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() 
    {
        System.setOut(originalOut);
    }

    @Test
    public void testMessagePostAttributes() 
    {
        assertEquals("alice", messagePost.getUsername());
        assertEquals("Hello, world!", messagePost.getText());

        messagePost.like();
        assertEquals(1, messagePost.getLikes());

        messagePost.addComment("Cool post!");
        ArrayList<String> comments = messagePost.printComments();
        assertEquals(1, comments.size());
        assertTrue(comments.contains("Cool post!"));
    }

    @Test
    public void testPhotoPostAttributes() 
    {
        assertEquals("bob", photoPost.getUsername());
        assertEquals("image.jpg", photoPost.getImageFile());
        assertEquals("Nice view", photoPost.getCaption());

        photoPost.like();
        assertEquals(1, photoPost.getLikes());

        photoPost.unlike();
        assertEquals(0, photoPost.getLikes());
    }

    @Test
    public void testMessagePostDisplayOutput() 
    {
        messagePost.display();
        String output = outContent.toString();
        assertTrue(output.contains("alice"));
        assertTrue(output.contains("Hello, world!"));
        assertTrue(output.contains("seconds ago") || output.contains("minutes ago"));
    }

    @Test
    public void testPhotoPostDisplayOutput() 
    {
        photoPost.display();
        String output = outContent.toString();
        assertTrue(output.contains("bob"));
        assertTrue(output.contains("image.jpg"));
        assertTrue(output.contains("Nice view"));
    }

    @Test
    public void testNewsFeedFunctionality() 
    {
        newsFeed.addPost(messagePost);
        newsFeed.addPost(photoPost);

        newsFeed.show();
        String output = outContent.toString();

        assertTrue(output.contains("Hello, world!"));
        assertTrue(output.contains("Nice view"));
        assertTrue(output.contains("image.jpg"));
    }

    @Test
    public void testMultipleLikesAndUnlikes() 
    {
        messagePost.like();
        messagePost.like();
        messagePost.unlike();
        assertEquals(1, messagePost.getLikes());

        photoPost.unlike(); // Should remain 0
        assertEquals(0, photoPost.getLikes());
    }

    @Test
    public void testAddMultipleComments() 
    {
        photoPost.addComment("Looks great!");
        photoPost.addComment("Where was this?");
        ArrayList<String> comments = photoPost.printComments();
        assertEquals(2, comments.size());
    }
}
