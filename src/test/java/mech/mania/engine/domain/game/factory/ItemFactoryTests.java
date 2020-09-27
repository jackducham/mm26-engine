package mech.mania.engine.domain.game.factory;

import mech.mania.engine.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.MILLIS;
import static org.junit.Assert.assertTrue;

public class ItemFactoryTests {
    @Before
    public void setup(){

    }

    @After
    public void cleanup(){

    }

    /**
     * Ensure that we can generate 1000s of items within a turn
     */
    @Test
    public void testItemFactoryTiming(){
        Instant start = Instant.now();
        for(int i = 0; i < 1000; i++) {
            ItemFactory.generateItem(i/10);
        }

        long time = MILLIS.between(start, Instant.now());
        assertTrue(time < Long.parseLong(Config.getProperty("minMillisBetweenTurns")));
    }
}
