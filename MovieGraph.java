/*
 * 
 MovieGraph.java
 COSC 102, Colgate University
 
 Implements a sparse matrix using hash tables.
 Your code goes here.
 See instructions for explanation of methods.
 
 You should change DEBUG to false when your testing is complete.
 */

import java.io.*;
import java.util.*;

public class MovieGraph
{
  private static HashMap<String, HashSet<String>> movieToActor;
  private static HashMap<String, HashSet<String>> actorToMovies;
  private static HashSet<String> tempActorSet;
  private static HashSet<String> tempMovieSet;
  
  
  
  // Debugging output
  private static final boolean DEBUG = true;
  private void pdbg(String s)
  {
    if (DEBUG)
      System.err.println(s);
  }
  
  // Constructor
  public MovieGraph(String filename) throws IOException, SecurityException, FileNotFoundException
  {
    String movieInfo = ""; //Temporarily saves info for each movie
    String movieTitle = "";
    String actor = "";
    
    actorToMovies = new HashMap<String, HashSet<String>>();
    movieToActor = new HashMap<String, HashSet<String>>();
    tempActorSet = new HashSet<String>();
    tempMovieSet = new HashSet<String>();

    Scanner scanFile = new Scanner(new File(filename));

    while(scanFile.hasNextLine()){
      movieInfo = scanFile.nextLine().replace("\n", "/");
      Scanner scanMovie = new Scanner(movieInfo).useDelimiter("/");
      
      movieTitle = scanMovie.next();
      tempActorSet = new HashSet<String>();
      
      while(scanMovie.hasNext()){
        actor = scanMovie.next();
        tempActorSet.add(actor);
        
        if(actorToMovies.containsKey(actor)){
          tempMovieSet = actorToMovies.get(actor);
          tempMovieSet.add(movieTitle);
          actorToMovies.put(actor,tempMovieSet);
        }
        else{
          tempMovieSet = new HashSet<String>();
          tempMovieSet.add(movieTitle);
          actorToMovies.put(actor,tempMovieSet);
        }
      }   
      movieToActor.put(movieTitle, tempActorSet);
    }
   
    // for debugging
    pdbg(filename);
    
  }
  
  // return the movies in which an actor has appeared
  public Iterable<String> getMovies(String actor)
  {
    // for debugging
    pdbg(actor);
    
    return actorToMovies.get(actor);
  }
  
  // return the actors in a movie
  public Iterable<String> getActors(String movie)
  {
    // for debugging
    pdbg(movie);
    
    return movieToActor.get(movie);
  }
  
  // return the co-stars of an actor (over all movies)
  public Iterable<String> getCoStars(String actor)
  {
  tempActorSet = new HashSet<String>();
  tempMovieSet = new HashSet<String>();
  ArrayList<String> list = new ArrayList<String>();
  
    
    tempMovieSet = actorToMovies.get(actor);
    //System.out.println(tempMovieSet);
    
    Iterator hashMovieIterator = tempMovieSet.iterator();
    //System.out.println(movieToActor.get("10 from Your Show of Shows (1973)"));
    
    while(hashMovieIterator.hasNext()){
     Iterator hashActorIterator = movieToActor.get(hashMovieIterator.next()).iterator();
      while(hashActorIterator.hasNext())
        tempActorSet.add(hashActorIterator.next().toString());
    }
    tempActorSet.remove(actor);

    // for debugging
    pdbg(actor);
    
    return tempActorSet;
  }
  
  // return whether an actor has been in a movie
  public boolean isMatch(String actor, String movie)
  {
    // for debugging
    pdbg(actor + " " + movie);
    
    return movieToActor.get(movie).contains(actor);
  }
  
  // return the movies in which two given actors have appeared together
  public Iterable<String> getMovieLinks(String actor1, String actor2)
  {
    
    tempMovieSet = new HashSet<String>();
    String movie = "";
    Iterator hashMovieIterator = actorToMovies.get(actor1).iterator();
    
    while (hashMovieIterator.hasNext()){
     movie = hashMovieIterator.next().toString();
      if(actorToMovies.get(actor2).contains(movie))
        tempMovieSet.add(movie); 
    }
    
    if (tempMovieSet.size() == 0)
      tempMovieSet.add("No movie links found.");
    
    // for debugging
    pdbg(actor1 + " " + actor2);
    
    return tempMovieSet;
  }
}
