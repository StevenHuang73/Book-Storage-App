# My Personal Project

## Reading List/Personal Bookshelf 

The reading list/personal bookshelf will have two primary functions. 
- The first will be to hold a list books that the user wishes to read, as well as some customizable options. 
- The second function will be to store read books into a "bookshelf" of sorts, where users will be able to give the book a review. 
    - Users can visit the "bookshelf" at any time to look at old reads, and will be able to see their review for given book.

The project is intended for personal use - anyone can use it, from experienced readers to people who are just beginning their journey into the world of books. The ability to reminisce on old reads is very useful, and is a tool I wished I had available to me this day.

As a child I developed a passion for reading, and would often spend lots of time hiding inside a book somewhere. As I grew up, I slowly drifted away from the books, often opting to spend time on my computer or phone. I believe having an application like this will be able to reignite my passion for books, and will help decrease my screen time as well.

## User Stories

- As a user, I want to be able to store books read into a "bookshelf" to look back on.
- As a user, I want to be able to write small personal reviews for all books that I read.
- As a user, I want to be able to add books onto a reading list.
- As a user, I want to be able to organize my books in the bookshelf into categories such as author, genre, series, etc.
- As a user, I want to be able to customize my reading list, like starring books I wish to read first
- As a user, I want to be able to save my bookshelf and reading list so I can look at them at a later time
- As a user, I want to be able to be given the option of loading my reading list and bookshelf from file.

## Citations

* Data persistence in this program is based off the UBC CPSC 210 edX Phase 2 Workroom example

# Instructions for Grader

- You can generate the second required action related to adding Xs to a Y by adding books to a BookStorage by clicking the plus, then save.
- You can generate the second required action related to adding Xs to a Y by removing books from a BookSorage by clicking a book in the list then remove.
- You can locate my visual component by looking at any of the pictures or logos on all of the menus.
- You can save the state of my application by trying to close the app, and clicking yes when prompted.
- You can reload the state of my application by clicking yes when prompted to at beginning of program.

# Phase 4: Task 2
Tue Apr 11 13:41:18 PDT 2023  
Added Book1 to Reading List  
Tue Apr 11 13:41:22 PDT 2023  
Added Book2 to Bookshelf  
Tue Apr 11 13:41:27 PDT 2023  
Added Book3 to Bookshelf  
Tue Apr 11 13:41:32 PDT 2023  
Added Book4 to Reading List  
Tue Apr 11 13:41:35 PDT 2023  
Sorted Bookshelf by author  
Tue Apr 11 13:41:38 PDT 2023  
Sorted Reading List by genre  
Tue Apr 11 13:41:43 PDT 2023  
Removed Book2 from Bookshelf  
Tue Apr 11 13:41:45 PDT 2023  
Removed Book4 from Reading List  


# Phase 4 Task 3

The first refactoring I would do is to combine the ReadingListGUI and BookShelfGUI into one abstract superclass. Their methods are essentially identical, with a few exceptions such as the BookStorage that they access or the background color. This will significantly reduce the amount of code necessary
to complete the program.   

The second refactoring that I could perform is using the singleton method to turn the BooksAppGUI into a singleton. This ensures that only one GUI will ever be created, and it will also remove the need for some of the static
fields that are initialized within the BooksApp class, namely the static BookShelf and ReadingList fields can just be regular fields.  

The third refactoring I could perform is combining all of the setSideBar methods, since a majority of the code between the three times it exists are the same. This will reduce the amount of code needed for the program.

The final refactoring is using exceptions in the code as well, especially within the BookStorage class, where errors may occur when adding, removing, or modifying books. Catching these exceptions will make it easy to identify what might go wrong within the program.

