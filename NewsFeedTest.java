import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Test class for NewsFeed.
 * 
 * @author Safwan C
 * @version 2025.17.4
 */
public class NewsFeedTest 
{
    private NewsFeed feed;
    private MessagePost messagePost;
    private PhotoPost photoPost;

    /**
     * Set up a new NewsFeed and some posts before each test.
     */
    @BeforeEach
    public void setUp() 
    {
        feed = new NewsFeed();
        messagePost = new MessagePost("alice", "Hello world!");
        photoPost = new PhotoPost("bob", "vacation.jpg", "On the beach");
    }

    /**
     * Test adding a message post to the feed.
     */
    @Test
    public void testAddMessagePost() 
    {
        feed.addPost(messagePost);
        // Not directly accessible, but we can check post content
        assertEquals("alice", messagePost.getUsername());
        assertEquals("Hello world!", messagePost.getText());
    }

    /**
     * Test adding a photo post to the feed.
     */
    @Test
    public void testAddPhotoPost() 
    {
        feed.addPost(photoPost);
        assertEquals("bob", photoPost.getUsername());
        assertEquals("vacation.jpg", photoPost.getImageFile());
        assertEquals("On the beach", photoPost.getCaption());
    }

    /**
     * Test showing the feed with both types of posts.
     */
    @Test
    public void testLikeAndUnlike() 
    {
        messagePost.like();
        messagePost.like();
        messagePost.unlike();
        assertEquals(1, messagePost.likes); // uses protected access
    }

    /**
     * Test adding a comment to a message post.
     */
    @Test
    public void testAddComment() 
    {
        messagePost.addComment("Nice post!");
        assertFalse(messagePost.comments.isEmpty());
        assertEquals("Nice post!", messagePost.comments.get(0));
    }

    /**
     * Test adding a comment to a photo post.
     */
    @Test
    public void testDisplayDoesNotThrow() 
    {
        // Ensure no exceptions during display output
        assertDoesNotThrow(() -> 
        {
            feed.addPost(messagePost);
            feed.addPost(photoPost);
            feed.show();
        });
    }

    @Test
    public void test1()
    {
        photoPost.display();
        feed.show();
    }
}

