1.	Project Scope ● 
What is the estimated volume of data the application should handle (e.g., number of books and users)?
•	Books: Up to 1001 entries
•	Users: Up to 2001 entries
2.	Should the data be persistent between program executions?
•	Yes, persistence is required. Using JSON files to store data because they’re easy to work with and can handle enough data for this stage of the project.
3.	Clarify “etcetera” in the description?
•	Likely refers to additional fields – publisher, user address, phone number. What, can be added later.
4.	Are IDs required? How should they be handled?
•	Books: Use ISBN if available. If not, auto-generate a unique ID.
•	Users: Use auto-generated unique IDs to avoid duplication.
5.	Introduce Administrator and User roles:
•	Administrator: Add users, books and view all books list
•	User: View assigned books, issue, and return
•	User Not Found: View all books, search book by title
6.	Initial stock quantity?
•	Administrator should define quantity during book registration
7.	Record an issue date and give Max period 
•	Make it 14 days
8.	Maximum books per user
•	3 books per user
9.	Late returns or lost books
•	Log late returns, remove lost books, charge for fines if return late or book marked as lost

High Level Unit test has been added. 


