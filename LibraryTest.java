import java.util.*;

// =============================
// Exception Classes
// =============================
class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String message) {
        super(message);
    }
}

class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) {
        super(message);
    }
}

// =============================
// Book Class
// =============================
class Book {
    private String title;
    private String author;
    private String genre;
    private boolean isAvailable;

    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
    }

    public boolean isAvailable() 
    {
        return isAvailable;
    }

    public void checkout() throws BookNotAvailableException 
    {
        // TODO: Throw exception if book is not available
        //DONE
        if (isAvailable == false) 
        // the book isnt avaliable, the user gets this message
        {
            throw new BookNotAvailableException("This Book is not avaliable."); 
        }

    }

    public void returnBook() 
    {
        //DONE
        // TODO: Mark book as available
        isAvailable = true; 
        //this just makes the book avaliable again after returning
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return title + " by " + author + " (" + genre + ")";
    }
}

// =============================
// LibraryCollection Class
// =============================
class LibraryCollection 
{
    private Map<String, List<Book>> genreMap;

    public LibraryCollection() {
        genreMap = new HashMap<>();
    }

    public void addBook(Book book) 
    {
        //DONE
        // TODO: Add books to genreMap
        String genre = book.getGenre(); 
        ArrayList<Book> list; 
        //gets the genre of the book, checks if the genre exists
        //if it doesn't it adds it to the list
        if(genreMap.get(genre) != null)
        {
            list = (ArrayList<Book>)genreMap.get(genre); 
        }
        else
        {
            list = new ArrayList<Book>(); 
            genreMap.put(genre, list); //storing the new updated list w new genre
        }

        list.add(book); 
        
    }

    public Iterator<Book> getGenreIterator(String genre) 
    {
        //DONE
        // TODO: Return custom iterator for available books in that genre
        ArrayList<Book> updatedList = new ArrayList<Book>(); 
        List<Book> booksGenre = genreMap.get(genre); 
        if(booksGenre != null)
        {
            for (Book bk : booksGenre)
            {
                if (bk.isAvailable())
                {
                    updatedList.add(bk);
                }
            }
        }
        return updatedList.iterator();
    }

    //DONE
    // TODO: Add methods to search and return books
    public Book searchBooks(String genre, String title) throws BookNotFoundException
    {
        List<Book> books = genreMap.get(genre);
        if(books != null)
        {
            for (Book bk : books)
            {
                if (bk.getTitle().equalsIgnoreCase(title))
                {
                    return bk; 
                }
            }
        }
        throw new BookNotFoundException("This book is not registered yet or found under that genre.");
    
    }

    public void returnBooks(String genre, String title) throws BookNotFoundException
    {
        Book bk = searchBooks(genre, title); 
        bk.returnBook(); 
    }


}

// =============================
// Main Program
// =============================
public class LibraryTest 
{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryCollection library = new LibraryCollection();

        // TODO: Add sample books to library
        //DONE
        library.addBook(new Book("A List Of Cages", "Robin Roe", "Sclice of Life")); 
        library.addBook(new Book("See you At the Cosmos", "Jack Cheng", "Sclice of Life")); 
        library.addBook(new Book("Lore Olympus", "Rachel Smythe", "Fantasy")); 
        library.addBook(new Book("The Outsiders", "S.E. Hinton", "Fiction"));

        // DONEE
        //TODO: Prompt user for genre, list available books using iterator
        System.out.print("Please type in the genre you want to browse: "); 
        String genre = scanner.nextLine(); 
        Iterator<Book> iterator = library.getGenreIterator(genre);
        while(iterator.hasNext())
        {
            Book bk = iterator.next(); 
            System.out.println(bk.getTitle());
        }
        
        // DONE
        //TODO: Allow checkout and return, handling exceptions
        System.out.println("Please enter the title of the book you want: ");
        String title = scanner.nextLine();
        try {
            Book bk = library.searchBooks(genre, title);
            bk.checkout(); 
            System.out.println("Book Checked out."); 
        }
        catch (BookNotAvailableException e)
        {
            System.out.println("Book unavaliable, currently checked out.");
        }
        catch (BookNotFoundException e)
        {
            System.out.println("Book not found in chosen genre.");
        }

        System.out.println("Please enter the title of the book to return: ");
        String returnTitle = scanner.nextLine(); 
        try
        {
            library.returnBooks(genre, returnTitle);
            System.out.println("Book succesfully returned."); 
        }
        catch (BookNotFoundException e)
        {
            System.out.println("This book is not in the system.");
        }

        scanner.close();
    }
}