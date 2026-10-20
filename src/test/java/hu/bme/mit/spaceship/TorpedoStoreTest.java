package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TorpedoStoreTest {

    @Test
    void initialCountIsStored() {
        TorpedoStore store = new TorpedoStore(3, 0.0);
        assertEquals(3, store.getTorpedoCount());
        assertFalse(store.isEmpty());
    }

    @Test
    void zeroCountIsEmpty() {
        TorpedoStore store = new TorpedoStore(0, 0.0);
        assertEquals(0, store.getTorpedoCount());
        assertTrue(store.isEmpty());
    }

    @Test
    void firingOneDecreasesCountByOne() {
        TorpedoStore store = new TorpedoStore(3, 0.0);
        assertTrue(store.fire(1));
        assertEquals(2, store.getTorpedoCount());
        assertFalse(store.isEmpty());
    }

    @Test
    void firingSeveralDecreasesCountByRequestedAmount() {
        TorpedoStore store = new TorpedoStore(5, 0.0);
        assertTrue(store.fire(3));
        assertEquals(2, store.getTorpedoCount());
        assertFalse(store.isEmpty());
    }

    @Test
    void firingAllMakesStoreEmpty() {
        TorpedoStore store = new TorpedoStore(3, 0.0);
        assertTrue(store.fire(3));
        assertEquals(0, store.getTorpedoCount());
        assertTrue(store.isEmpty());
    }

    @Test
    void failedFiringPreservesCount() {
        TorpedoStore store = new TorpedoStore(3, 1.0);
        assertFalse(store.fire(2));
        assertEquals(3, store.getTorpedoCount());
        assertFalse(store.isEmpty());
    }

    @Test
    void zeroAmountIsRejected() {
        TorpedoStore store = new TorpedoStore(3, 0.0);
        assertThrows(IllegalArgumentException.class, () -> store.fire(0));
        assertEquals(3, store.getTorpedoCount());
    }

    @Test
    void negativeAmountIsRejected() {
        TorpedoStore store = new TorpedoStore(3, 0.0);
        assertThrows(IllegalArgumentException.class, () -> store.fire(-1));
        assertEquals(3, store.getTorpedoCount());
    }

    @Test
    void amountAboveStockIsRejected() {
        TorpedoStore store = new TorpedoStore(3, 0.0);
        assertThrows(IllegalArgumentException.class, () -> store.fire(4));
        assertEquals(3, store.getTorpedoCount());
    }

    @Test
    void emptyStoreCannotFire() {
        TorpedoStore store = new TorpedoStore(0, 0.0);
        assertThrows(IllegalArgumentException.class, () -> store.fire(1));
        assertEquals(0, store.getTorpedoCount());
        assertTrue(store.isEmpty());
    }

    @Test
    void consecutiveShotsConsumeStock() {
        TorpedoStore store = new TorpedoStore(2, 0.0);
        assertTrue(store.fire(1));
        assertEquals(1, store.getTorpedoCount());
        assertFalse(store.isEmpty());
        assertTrue(store.fire(1));
        assertEquals(0, store.getTorpedoCount());
        assertTrue(store.isEmpty());
        assertThrows(IllegalArgumentException.class, () -> store.fire(1));
        assertEquals(0, store.getTorpedoCount());
    }
}