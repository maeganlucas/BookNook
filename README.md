![BookNookLogo](https://github.com/maeganlucas/BookNook/blob/main/Resources/BookNookLogo.png)

Book Nook is a book review application for the use of both authors and readers that interacts with a database to store all information. Users create accounts based on whether they are an author or a reader, which will determine the functionalities available to them. 

Authors are able enter their books into the Book Nook database. From there, as readers write reviews for their books, authors are able to view rating breakdowns for each of their books. No reader information is shared to the author within these breakdowns. Authors can update or delete books in the database at anytime.

Readers are able to search books already entered into the database. Once they have selected the book they are searching for, they can select, edit, or delete a reading status. Once a reader marks a book as read, the reader can then review the book based on different categories. These reviews can be edited or deleted at anytime.

Both types of user can update or delete their profiles at anytime. For author users, any book already entered into the database will remain in the database after the account is deleted.

Keep reading for a more detailed description of Book Nook's features and demos.

---

## Contents

<details>
<summary>General Features</summary>
  
[General Features](#general-features) <br>
&emsp;[Creating an Account](#creating-an-account) <br>
&emsp;[Logging In](#logging-in) <br>
&emsp;[Updating an Account](#updating-an-account) <br>
&emsp;[Deleting an Account](#deleting-an-account) <br>
&emsp;[Logging Out](#logging-out) <br>
  
</details>

<details>
<summary>Author Features</summary>
  
[Author Features](#author-features) <br>
&emsp;[Creating a Book](#creating-a-book) <br>
&emsp;[Updating a Book](#updating-a-book) <br>
&emsp;[Deleting a Book](#deleting-a-book) <br>
&emsp;[Seeing Book Reviews](#seeing-book-reviews) <br>
&emsp;[Returning to the Author Homescreen](#returning-to-the-author-homescreen) <br>

</details>

<details>
<summary>Reader Features</summary>
  
[Reader Features](#reader-features) <br>
&emsp;[Searching Books](#searching-books) <br>
&emsp;[Read Books](#read-books) <br>
&emsp;[Currently Reading Books](#currently-reading-books) <br>
&emsp;[TBR Books](#tbr-books) <br>
&emsp;[Creating a Review](#creating-a-review) <br>
&emsp;[Updating a Review](#updating-a-review) <br>
&emsp;[Deleting a Review](#deleting-a-review) <br>
&emsp;[Seeing Reviews](#seeing-reviews) <br>
&emsp;[Reading Statuses](#reading-statuses) <br>
&emsp;[Returning to the Reader Homescreen](#returning-to-the-reader-homescreen) <br>

</details>

---

## General Features
These are features that are available to both author and reader users.

### Creating an Account
Upon opening Book Nook, all users will be taken to the login in page. From there, there are just six simple steps to create an account.

1. Click the _Click here to create a new user_ button.
2. Enter your preferred name.
3. Enter a username.
4. Enter a password.
5. Select the checkbox for the role that best suits you. (Please only select one.)
6. Click the _Create User!_ button.

You will be taken back to the login screen and a pop-up will inform you that your account was successfully created and to log in.

If you enter a username that is already in use by someone of your selected role, you will recieve a pop-up informing you that the username is taken and to try a different one.

### Logging In
Once you have a Book Nook account, simply type in your username and password and click _Login_! You will be brought to your homepage.

If you enter incorrect information, whether it be username or password, you will recieve a pop-up informing you that something was incorrect and to try again.
### Updating an Account
If you want to update any of your account information, click the _Settings_ tab on the menu bar at the top of the screen and press _Update Profile_. A popup will display with your current account information prefilled in the corresponding text fields, change any information you please and click _Update Profile_. 

### Deleting an Account
If you want to delete your profile all together, click the _Settings_ tab on the menu bar at the top of the screen and press _Delete Profile_. A popup will display to confirm that you want to delete your profile.
- To return to your homescreen without deleting your profile, click _Keep Profile_.
- To delete your profile, click _Delete Profile_.

For author users, when a profile is deleted, any books that they have entered into the database remain in the database. This is also stated in a special message on their confirmation popup.

### Logging Out
To log out of your account and return to the sign in screen, click the _Logout_ tab on the menu bar on the top of the screen and press _Logout_.

---

## Author Features

### Creating a Book
To create a book entry in the database, click the _Books_ tab on the menu bar at the top of the screen and press _Create Book_. Fill out the form with the appropriate information and select _Create Book_.

### Updating a Book
To update a book entry within the database, click the _Books_ tab on the menu bar at the top of the screen and press _Update Book_. A popup will display asking for the ISBN of the book you would like to update. Enter the ISBN number and press _Update_.

A form will display with the book details prefilled in their respective fields. Update any information and press _Update Book_.

### Deleting a Book
To update a book entry within the database, click the _Books_ tab on the menu bar at the top of the screen and press _Delete Book_. A popup will display asking for the ISBN of the book you would like to delete. Enter the ISBN number and press _Delete_. 

### Seeing Book Reviews
To see reviews for a book, click the _Reviews_ tab on the menu bar at the top of the screen and press _See Reviews_. Select the correct book from the dropdown menu and click _See Reviews_. Graphs of the different categories will appear.

### Returning to the Author Homescreen
To return to the author homescreen, click the _Logout_ tab on the menu bar at the top of the screen and press _Back to Home_.

---

## Reader Features

### Searching Books
To search for aa particular book within the database, click the _Books_ tab on the menu bar at the top of the screen and press _Search Books_. In the search bar, type in keywords about the book's title, author, or series and press _Search_. 

### Read Books
To see the books marked as _Read_, also the reader's homescreen, click the _Books_ tab on the menu bar at the top of the screen and press _Read Books_. 
 
### Currently Reading Books
To see the books marked as _Currently Reading_, click the _Books_ tab on the menu bar at the top of the screen and press _Currently Reading_.

### TBR Books
To see the books marked as _To Be Read_, click the _Books_ tab on the menu bar at the top of the screen and press _TBR_.

### Creating a Review
Once a book is marked as _Read_ (see [Reading Statuses](#reading-statuses)), a new screen will appear allowing you to create a review. Fill in the fields with the appropriate information then click _Done!_.

_Note:_ Only numerical reviews will be taken into consideration for review breakdowns for both authors and readers.

### Updating a Review
To update a review, click the _Reviews_ tab on the menu bar at the top of the screen and press _Update Review_. A popup will display asking for the ISBN of the book review to update. Enter the ISBN number and then click _Update_. A form will display with the fields prefilled with the review's information stored in the database. Update any information necessary and click _Done!_.

### Deleting a Review
To delete a review, click the _Reviews_ tab on the menu bar at the top of the screen and press _Delete Review_. A popup will display asking for the ISBN of the book review to delete. Enter the ISBN number and then click _Delete_.

### Seeing Reviews
To see reviews for a certain time range, click the _Reviews_ tab on the menu bar at the top of the screen and select _See Reviews_. Enter a beginning and ending date for the range and select _Get Review Breakdown_. Graphs of your reviews made between the given range will appear on the screen.

### Reading Statuses
To initially set a reading status for a book, first search for the book within the database (see [Searching Books](#searching-books)). Once you find the book you are looking for, click _Select Reading Status_. A popup will display asking for the book whose reading status you would like to set, which should prefill with the selected book's ISBN. A dropdown menu and three buttons will appear. Select the proper reading status from the dropdown menu and click _Set Reading Status_.

To edit a reading status for a book, first search for the book within the database (see [Searching Books](#searching-books)). Once you find the book you are looking for, click _Select Reading Status_. A popup will display asking for the book whose reading status you would like to set, which should prefill with the selected book's ISBN. A dropdown menu prefilled with the book's current reading status and three buttons will appear. Select the proper reading status from the dropdown menu and click _Edit Reading Status_.

To delete a reading status for a book, first search for the book within the database (see [Searching Books](#searching-books)). Once you find the book you are looking for, click _Select Reading Status_. A popup will display asking for the book whose reading status you would like to set, which should prefill with the selected book's ISBN. A dropdown menu prefilled with the book's current reading status and three buttons will appear. Select the proper reading status from the dropdown menu and click _Delete Status_.

### Returning to the Reader Homescreen
To return to the reader homescreen, click the _Books_ tab on the menu bar at the top of the screen and press _Read Books_. 
