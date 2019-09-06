package facades;

import dto.MovieDTO;
import entities.Movie;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private MovieFacade() {}
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public long getMovieCount()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            long movieCount = (long)em.createQuery("SELECT COUNT(r) FROM Movie r").getSingleResult();
            return movieCount;
        }
        finally
        {  
            em.close();
        }
        
    }
    
    public Movie getMovieById(long id)
    {
         //open a database connection (and creates a database, if one doesnt exists)
        EntityManager em = emf.createEntityManager();
        
        try
        {
            return em.find(Movie.class, id);
        }
        finally
        {
            em.close();
        }
    }
    
    public List<MovieDTO> getMovieByTitle(String title)
    {
         //open a database connection (and creates a database, if one doesnt exists)
        EntityManager em = emf.createEntityManager();
        
        try
        {
           TypedQuery<Movie> query = 
                       em.createQuery("Select m from Movie m WHERE m.title = :title",Movie.class);
           
           query.setParameter("title", title);
           
           List<Movie> movieByTitle =  query.getResultList();
           
           List<MovieDTO> movieByTitleDTO = new LinkedList<>();
           
           for(Movie m : movieByTitle)
           {
               movieByTitleDTO.add(new MovieDTO(m));
           }
           
           return movieByTitleDTO;
            
        }
        finally
        {
            em.close();
        }
    }
    
    public List<Movie> getAllMovies()
    {
        //open a database connection (and creates a database, if one doesnt exists)
        EntityManager em = emf.createEntityManager();
        
        try
        {
            TypedQuery<Movie> query = 
                       em.createQuery("Select m from Movie m",Movie.class);
            
            return query.getResultList();
        }
        finally
        {
            em.close();
        }
        
    }
    
    public Movie createMovie(String title, String author)
    {
        //open a database connection (and creates a database, if one doesnt exists)
        EntityManager em = emf.createEntityManager();
        
        try
        {
            //Instantiates a movie
            Movie movie = new Movie(title, author);
            
            //Persist movie
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            
            return movie; 
        }
        finally
        {
            em.close();
        }
    }

}
