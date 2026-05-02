import org.junit.jupiter.api.Test;
import ru.netology.qa.AviaSouls;
import ru.netology.qa.Ticket;
import ru.netology.qa.TicketTimeComparator;

import static org.junit.jupiter.api.Assertions.*;

public class AviaSoulsTest {

    // --- Тесты compareTo ---

    @Test
    void compareToShouldReturnNegativeWhenFirstCheaper() {
        Ticket t1 = new Ticket("MOW", "LED", 3000, 10, 12);
        Ticket t2 = new Ticket("MOW", "LED", 5000, 10, 12);
        assertTrue(t1.compareTo(t2) < 0);
    }

    @Test
    void compareToShouldReturnPositiveWhenFirstMoreExpensive() {
        Ticket t1 = new Ticket("MOW", "LED", 7000, 10, 12);
        Ticket t2 = new Ticket("MOW", "LED", 5000, 10, 12);
        assertTrue(t1.compareTo(t2) > 0);
    }

    @Test
    void compareToShouldReturnZeroWhenSamePrice() {
        Ticket t1 = new Ticket("MOW", "LED", 5000, 10, 12);
        Ticket t2 = new Ticket("MOW", "LED", 5000, 10, 12);
        assertEquals(0, t1.compareTo(t2));
    }

    // --- Тесты search() — сортировка по цене ---

    @Test
    void searchShouldReturnTicketsSortedByPrice() {
        AviaSouls manager = new AviaSouls();
        Ticket t1 = new Ticket("MOW", "LED", 5000, 10, 12);
        Ticket t2 = new Ticket("MOW", "LED", 3000, 8, 10);
        Ticket t3 = new Ticket("MOW", "LED", 7000, 6, 9);
        manager.add(t1);
        manager.add(t2);
        manager.add(t3);

        Ticket[] result = manager.search("MOW", "LED");
        assertArrayEquals(new Ticket[]{t2, t1, t3}, result);
    }

    @Test
    void searchShouldReturnEmptyWhenNoMatch() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("MOW", "LED", 5000, 10, 12));

        Ticket[] result = manager.search("MOW", "AER");
        assertEquals(0, result.length);
    }

    @Test
    void searchShouldReturnOneTicket() {
        AviaSouls manager = new AviaSouls();
        Ticket t1 = new Ticket("MOW", "AER", 4000, 9, 13);
        manager.add(new Ticket("MOW", "LED", 5000, 10, 12));
        manager.add(t1);

        Ticket[] result = manager.search("MOW", "AER");
        assertArrayEquals(new Ticket[]{t1}, result);
    }

    // --- Тесты TicketTimeComparator ---

    @Test
    void comparatorShouldReturnNegativeWhenFirstFaster() {
        TicketTimeComparator comp = new TicketTimeComparator();
        Ticket t1 = new Ticket("MOW", "LED", 5000, 10, 12); // 2 часа
        Ticket t2 = new Ticket("MOW", "LED", 3000, 8, 12); // 4 часа
        assertTrue(comp.compare(t1, t2) < 0);
    }

    @Test
    void comparatorShouldReturnPositiveWhenFirstSlower() {
        TicketTimeComparator comp = new TicketTimeComparator();
        Ticket t1 = new Ticket("MOW", "LED", 5000, 6, 12); // 6 часов
        Ticket t2 = new Ticket("MOW", "LED", 3000, 8, 10); // 2 часа
        assertTrue(comp.compare(t1, t2) > 0);
    }

    @Test
    void comparatorShouldReturnZeroWhenSameDuration() {
        TicketTimeComparator comp = new TicketTimeComparator();
        Ticket t1 = new Ticket("MOW", "LED", 5000, 10, 12); // 2 часа
        Ticket t2 = new Ticket("MOW", "LED", 3000, 8, 10); // 2 часа
        assertEquals(0, comp.compare(t1, t2));
    }

    // --- Тесты searchAndSortBy() ---

    @Test
    void searchAndSortByShouldSortByDuration() {
        AviaSouls manager = new AviaSouls();
        Ticket t1 = new Ticket("MOW", "LED", 5000, 10, 12); // 2 часа
        Ticket t2 = new Ticket("MOW", "LED", 3000, 6, 12); // 6 часов
        Ticket t3 = new Ticket("MOW", "LED", 7000, 8, 12); // 4 часа
        manager.add(t1);
        manager.add(t2);
        manager.add(t3);

        Ticket[] result = manager.searchAndSortBy("MOW", "LED", new TicketTimeComparator());
        assertArrayEquals(new Ticket[]{t1, t3, t2}, result);
    }

    @Test
    void searchAndSortByShouldReturnEmptyWhenNoMatch() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("MOW", "LED", 5000, 10, 12));

        Ticket[] result = manager.searchAndSortBy("MOW", "AER", new TicketTimeComparator());
        assertEquals(0, result.length);
    }
}