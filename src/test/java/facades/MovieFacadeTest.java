package facades;

import dtos.MovieDTO;
import utils.EMF_Creator;
import entities.Movie;
import static entities.ghy636765.Movie_.actors;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MovieFacadeTest {

    private static EntityManagerFactory emf;
    private static MovieFacade facade;

    private Movie m1;
    private Movie m2;
    private Movie m3;

    public MovieFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = MovieFacade.getMovieFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Movie").executeUpdate();
            m1 = new Movie(2001, "Harry Potter and the Philosopher's Stone", new String[]{"Daniel Radcliffe", "Emma Watson", "Alan Rickman", "Rupert Grint"});
            m2 = new Movie(2002, "Harry Potter and the Chamber of Secrets", new String[]{"Daniel Radcliffe", "Emma Watson", "Alan Rickman", "Rupert Grint"});
            m3 = new Movie(2019, "Once Upon a Time... in Hollywood", new String[]{"Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"});
            em.persist(m1);
            em.persist(m2);
            em.persist(m3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testMovieCount() {
        assertEquals(3, facade.getMovieCount(), "Expects three movies in the database");
    }

    @Test
    public void testGetAllMovies() {
        List<MovieDTO> movies = facade.getAllMovies();
        assertEquals(3, facade.getMovieCount(), "Expects three movies in the database");
        assertThat(movies, hasItems( // or contains or containsInAnyOrder 
                Matchers.<MovieDTO>hasProperty("title", is("Harry Potter and the Philosopher's Stone")),
                Matchers.<MovieDTO>hasProperty("title", is("Harry Potter and the Chamber of Secrets"))
        )
        );
    }

    @Test
    public void testGetMovieById() {
        List<MovieDTO> movies = facade.getAllMovies();
        assertThat(movies, hasItems(
                Matchers.<MovieDTO>hasProperty("id", is(1))
        )
        );
    }

    @Test
    public void testMovieHasActors() {
        //You could use the method: arrayContaining(....
        arrayContaining(actors);
    }

    @Test
    public void getMoviesByTitle() {
        //Todo
        List<MovieDTO> movies = facade.getAllMovies();
        assertThat(movies, hasItems(
                Matchers.<MovieDTO>hasProperty("title", is("Once Upon a Time... in Hollywood"))
        )
        );
    }

}
