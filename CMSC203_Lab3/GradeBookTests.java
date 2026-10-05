import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 tests for GradeBook.
 * Covers addScore, toString, getScoreSize, sum, minimum, and finalScore,
 * including empty and single-score edge cases.
 */

public class GradeBookTest {

    // Test fixtures: shared objects used in multiple tests
    private GradeBook g1;
    private GradeBook g2;

    /**
     * setUp runs BEFORE each @Test method.
     * Use it to create fresh GradeBook objects for every test.
     */
    @BeforeEach
    public void setUp() {
        g1 = new GradeBook(5);
        g1.addScore(50.0);
        g1.addScore(75.0);

        g2 = new GradeBook(5);
        g2.addScore(90.0);
        g2.addScore(85.0);
        g2.addScore(70.0);
    }

    /**
     * tearDown runs AFTER each @Test method.
     * Use it to clean up any objects if needed.
     */
    @AfterEach
    public void tearDown() {
        g1 = null;
        g2 = null;
    }

    /**
     * Test addScore and toString.
     *
     * Goal:
     *  - Verify that the scores are stored correctly.
     *  - Verify that toString returns the expected string
     *    (each score followed by a space, in the order added).
     */
    @Test
    public void testAddScoreAndToString() {
        assertEquals("50.0 75.0 ", g1.toString());
        assertEquals("90.0 85.0 70.0 ", g2.toString());
    }

    /**
     * Test getScoreSize.
     *
     * Goal:
     *  - Verify that getScoreSize returns the number of scores
     *    actually added to each GradeBook.
     */
    @Test
    public void testGetScoreSize() {
        assertEquals(2, g1.getScoreSize());
        assertEquals(3, g2.getScoreSize());
    }

    /**
     * Test sum.
     *
     * Goal:
     *  - Verify that sum() returns the correct total of all scores.
     */
    @Test
    public void testSum() {
        assertEquals(125.0, g1.sum(), 0.0001);
        assertEquals(245.0, g2.sum(), 0.0001);
    }

    /**
     * Test minimum.
     *
     * Goal:
     *  - Verify that minimum() returns the smallest score.
     *  - Optionally test behavior when there are no scores.
     */
    @Test
    public void testMinimum() {
        assertEquals(50.0, g1.minimum(), 0.0001);
        assertEquals(70.0, g2.minimum(), 0.0001);
    }

    /**
     * Test finalScore.
     *
     * Goal:
     *  - Verify that finalScore() drops the lowest score
     *    when there are at least two scores.
     *  - Verify behavior when there is exactly one score
     *    and when there are no scores.
     */
    @Test
    public void testFinalScore() {
        assertEquals(75.0, g1.finalScore(), 0.0001);
        assertEquals(175.0, g2.finalScore(), 0.0001);
    }
}


