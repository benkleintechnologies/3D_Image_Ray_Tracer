package primitives;
import geometries.Polygon;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Vector class
 * @author Eli Hawk and Binyamin Klein
 * 563385586 & 576708589
 */
class VectorTest {

    @Test
    public void testConstructor() {

        // TC01: Correct number inputs for building a vector
        try {
            new Vector(1, 1, 1);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }
// =============== Boundary Values Tests ==================
        // TC02: Create Zero Vector
        assertThrows(IllegalArgumentException.class, //
                () -> new Vector(0,0,0), //
                "Cannot create a zero vector.");    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Point)}.
     */
    @Test
    void add() {

 // ============ Equivalence Partitions Tests ==============

        // TC01: Add 2 vectors with an accute angle between them
        Vector va = new Vector(1,1,1);
        Vector vb = new Vector(1/2, 1, 1);
        assertEquals(new Vector(3/2,2,2), va.add(vb), "Error: adding to vectors with an accute angle");

        // TC02: Add 2 vectors with an obtuse angle between them
        va = new Vector(1,1,1);
        vb = new Vector(-9, -4, 9);
        assertEquals(new Vector(-8, -3, 10), va.add(vb), "Error: adding to vectors with an obtuse angle");

// =============== Boundary Values Tests ==================
        // TC02: Add 2 vectors of same lengths, but opposite directions
        Vector v = new Vector(1, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> v.add(new Vector(-1, -1, -1)), "Error: can not construct a Zero vector");


        // TC03: Add 2 vectors of different lengths, but opposite directions
        Vector v1 = new Vector(1,1,1);
        Vector v2 = new Vector(-2, -2, -2);
        assertEquals(new Vector(-1, -1, -1), v1.add(v2), "Error in adding vectors of opposite direction but different length");


        // TC05: Add 2 vectors orthoginal to each other
        v1 = new Vector(1, 1, 1);
        v2 = new Vector(1, 0, 1);
        assertEquals(new Vector(2, 1, 2), v1.add(v2), "Error in adding orthoginal vectors");



    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void scale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Scale a vector of all positive values with a positive scalar
        Vector v1 = new Vector(1, 1, 1);
        double scalar = 5;
        assertEquals(new Vector(5, 5, 5), v1.scale(scalar), "Error scaling a positive vector with a positive scalar");

        // TC02: Scale a vector of all positive values with a negative scalar
        v1 = new Vector(1, 1, 1);
        scalar = -5;
        assertEquals(new Vector(-5, -5, -5), v1.scale(scalar), "Error scaling a positive vector with a negative scalar");

        // TC03: Scale a vector of all negative values with a positive scalar
        v1 = new Vector(-1, -1, -1);
        scalar = 5;
        assertEquals(new Vector(-5, -5, -5), v1.scale(scalar), "Error scaling a negative vector with a positive scalar");

        // TC04: Scale a vector of all negative values with a negative scalar
        v1 = new Vector(-1, -1, -1);
        scalar = -5;
        assertEquals(new Vector(5, 5, 5), v1.scale(scalar), "Error scaling a negative vector with a negative scalar");

        // =============== Boundary Values Tests ==================
        // TC05: scale a vector by 0
        Vector v = new Vector(1, 1, 1);
        int zeroScalar = 0;
        assertThrows(IllegalArgumentException.class, () -> v.scale(zeroScalar), "Error: created a zero vector");

        // TC06: scale a vector by .1
        v1 = new Vector(1, 1, 1);
        scalar = .1;
        assertEquals(new Vector(.1, .1, .1), v1.scale(scalar), "Error: scaling vector by .1");
        // TC07: scale a vector by -.1
        v1 = new Vector(1, 1, 1);
        scalar = .1;
        assertEquals(new Vector(.1, .1, .1), v1.scale(scalar), "Error: scaling vector by -.1");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Dot product of 2 vectors that form an accute angle between them
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(.5, 1, 1);
        assertEquals(2.5, v1.dotProduct(v2), "Error: in calculating vectors that form an accute angle");

        // TC02: Dot product between 2 vectors that form an obtuse angle between them
        v1 = new Vector(1, 1, 1);
        v2 = new Vector(-9, -4, 9);
        assertEquals(-4, v1.dotProduct(v2), "\"Error: in calculating vectors that form an obtuse angle\"");

        // =============== Boundary Values Tests ==================
        // TC03: Dot product between orthoginal vectors
        v1 = new Vector(1, 1, 1);
        v2 = new Vector(1, 0, 1);
        assertEquals(2, v1.dotProduct(v2), "Error: in calculating dot product of orthoginal vectors");

        // TC04: Dot product between vectors of same length, but opposite direction
        v1 = new Vector (1, 1, 1);
        v2 = new Vector (-1, -1, -1);
        assertEquals(-3, v1.dotProduct(v2), "Error: in calculating dot product of vectors of same length, but opposite directions");

        // TC05: Dot product of vectors of the same length and direction
        v1 = new Vector(1, 1, 1);
        v2 = new Vector(1, 1, 1);
        assertEquals(3, v1.dotProduct(v2), "Error: in calculating dot product of vectors of same length and direction");


    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals( v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(0 == vr.dotProduct(v1), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(0 == vr.dotProduct(v2), "crossProduct() result is not orthogonal to 2nd operand");
        // TC03: Test cross-product of non-orthoginal vectors
        v1 = new Vector(1,1,1);
        v2 = new Vector(1,2,1);
        assertTrue((new Vector(-1, 0, 1).equals(v1.crossProduct(v2))),"crossProduct() result is incorrect");

        // =============== Boundary Values Tests ==================
        // TC04: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        Vector v4 = new Vector(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> v4.crossProduct(v3), "crossProduct() for parallel vectors does not throw an exception");

        // TC05: Test cross-product of vectors of same length and direction
        Vector v5 = new Vector(1,1,1);
        Vector v6 = new Vector(-1, -1, -1);
        assertThrows(IllegalArgumentException.class, ()->v5.crossProduct(v6), "crossProduct() for vectors of same length and direction does not throw an exception");

        // TC06: Test cross-product of vectors of same length and opposite directions
        Vector v7 = new Vector(1,1,1);
        Vector v8 = new Vector(-1, -1, -1);
        assertThrows(IllegalArgumentException.class, ()->v7.crossProduct(v8), "crossProduct() for vectors of opposite direction and same length does not throw an exception");

    }


    /**
     * Test method for {@link Vector#lengthSquared()}.
     */
    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: getting length squared of a vector with all positive values
        Vector v = new Vector(1, 1, 1);
        assertEquals(3, v.lengthSquared(), "Error: calculating length squared of a positive vector");

        // TC02: getting length squared of a vector with all negative values
        v = new Vector(-1, -1, -1);
        assertEquals(3, v.lengthSquared(), "Error: calculating length squared of a negative vector");
        // =============== Boundary Values Tests ==================
        // Don't think we need any boundary cases here, we'll check
    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: getting length of a vector with all positive values
        Vector v = new Vector(1, 1, 1);
        assertEquals(1.73205080757, v.length(), .00001, "Error: calculating length squared of a positive vector");

        // TC02: getting length of a vector with all negative values
        v = new Vector(-1, -1, -1);
        assertEquals(1.73205080757, v.length(), .00001, "Error: calculating length squared of a negative vector");
        // =============== Boundary Values Tests ==================
        // Don't think we need any boundary cases here, we'll check
    }
    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    void normalize() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Normalize a vector with all positive values
        Vector v = new Vector(1,1,1);
        assertEquals(new Vector(0.57735026919, 0.57735026919, 0.57735026919), v.normalize(), "Error: normalizing a positive vector");

        // TC02: Normalize a vector with all negative values
        v = new Vector(-1, -1, -1);
        assertEquals(new Vector(-0.57735026919, -0.57735026919, -0.57735026919), v.normalize(), "Error: normalizing a negative vector");

        // TC03: Normalize a vector with a length less than 1
        v = new Vector(.1, .1, .1);
        assertEquals(new Vector(0.57735026919, 0.57735026919, 0.57735026919), v.normalize(), "Error: normalizing a vector with length less than 1");
        // =============== Boundary Values Tests ==================
        // Don't think we need any boundary cases here, we'll check


    }
}