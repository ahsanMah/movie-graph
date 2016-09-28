# movie-graph

Use any CLI to compile and run the program:

java Movies <filename>

The data structure that I used in my program is a hashMap with Strings as keys and HashSets as values. This allowed to me to substantially improve runtimes for most of the methods (as can be seen below) since hashSets allow Random Read Access as they use hashCodes to mark index positions.

getMovies()  --> O(1)
getActors()  --> O(1)
getCoStars() --> O(m+c)
isMatch()    --> O(1)
getMovieLinks() --> O(a)
