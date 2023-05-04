import scala.collection.mutable.ListBuffer

object Library {
  case class Customer(id: Int, name: String, balance: Double)
  case class Book(title: String, author: String, var quantity: Int)
  case class Student(name: String, id: Int)

  private val customers = ListBuffer[Customer]()
  private val books = ListBuffer[Book]()
  private val registeredStudents = ListBuffer[Student]()
  private val checkedOutBooks = ListBuffer[(Int, String)]()

  def main(args: Array[String]): Unit = {
    println("Welcome to the Library Management System!")
    menu()
  }

  def menu(): Unit = {
    println("\nSelect an action:")
    println("1. Show customer details")
    println("2. Add or remove a customer account")
    println("3. Deposit money")
    println("4. Add a new book")
    println("5. Upgrade quantity of a book")
    println("6. Search a book")
    println("7. Show all books")
    println("8. Register a student")
    println("9. Show all registered students")
    println("10. Check out a book")
    println("11. Check in a book")
    println("12. Exit")

    val choice = scala.io.StdIn.readInt()

    choice match {
      case 1 => showCustomerDetails()
      case 2 => addOrRemoveAccount()
      case 3 => 
        println("Enter customer ID:")
        val id = scala.io.StdIn.readInt()
        println("Enter deposit amount:")
        val amount = scala.io.StdIn.readDouble()
        depositMoney(id, amount)
      case 4 => 
        println("Enter book title:")
        val title = scala.io.StdIn.readLine()
        println("Enter author name:")
        val author = scala.io.StdIn.readLine()
        println("Enter quantity:")
        val quantity = scala.io.StdIn.readInt()
        val book = Book(title, author, quantity)
        addNewBook(book)
      case 5 => 
        println("Enter book title:")
        val title = scala.io.StdIn.readLine()
        println("Enter new quantity:")
        val quantity = scala.io.StdIn.readInt()
        upgradeBookQuantity(title, quantity)
      case 6 => 
        println("Enter book title:")
        val title = scala.io.StdIn.readLine()
        searchBook(title)
      case 7 => showAllBooks()
      case 8 => 
        println("Enter student:")
        val studentName = scala.io.StdIn.readLine()
        println("Enter student ID:")
        val id = scala.io.StdIn.readInt()
        val student = Student(studentName, id)
        registerStudent(student)
      case 9 => showAllRegisteredStudents()
      case 10 => 
        println("Enter Customer ID:")
        val customerId = scala.io.StdIn.readInt()
        println("Enter book title:")
        val book = scala.io.StdIn.readLine()
        checkOutBook(customerId, book)
      case 11 => 
        println("Enter Customer ID:")
        val customerId = scala.io.StdIn.readInt()
        println("Enter book title:")
        val book = scala.io.StdIn.readLine()
        checkInBook(customerId, book)
      case 12 => System.exit(0)
      case _ => println("Invalid choice. Please try again.")
    }

    menu()
  }

  def showCustomerDetails(): Unit = {
    println("Enter customer ID (0 for all customers):")
    val id = scala.io.StdIn.readInt()

    if (id == 0) {
      println("Showing details of all customers:")
      for (customer <- customers) {
        println(s"Customer ID: ${customer.id}, Name: ${customer.name}, Balance: ${customer.balance}")
      }
    } else {
      customers.find(_.id == id) match {
        case Some(customer) =>
          println(s"Customer ID: ${customer.id}, Name: ${customer.name}, Balance: ${customer.balance}")
        case None => println("Customer not found.")
      }
    }
  }

  def addOrRemoveAccount(): Unit = {
    println("Select an action:")
    println("1. Add a new customer account")
    println("2. Remove an existing customer account")

    val choice = scala.io.StdIn.readInt()

    choice match {
      case 1 => addCustomer()
      case 2 => removeCustomer()
      case _ => println("Invalid choice. Please try again.")
    }
  }

  def addCustomer(): Unit = {
    println("Enter customer name:")
    val name = scala.io.StdIn.readLine()
    println("Enter initial balance:")
    val balance = scala.io.StdIn.readDouble()

    val id = if (customers.isEmpty) 1 else customers.maxBy(_.id).id + 1
    customers += Customer(id, name, balance)

    println("New customer account created.")
  }

  def removeCustomer(): Unit = {
    println("Enter customer ID to remove:")
    val id = scala.io.StdIn.readInt()
  }

  def depositMoney(customerId: Int, amount: Double): Unit = {
    val customerIndex = customers.indexWhere(_.id == customerId)
    if (customerIndex != -1) {
      val customer = customers(customerIndex)
      val updatedCustomer = customer.copy(balance = customer.balance + amount)
      customers(customerIndex) = updatedCustomer
    } else {
      println("Customer not found.")
    }
}


  def addNewBook(book: Book): Unit = {
    books += book
  }

  def upgradeBookQuantity(bookTitle: String, quantity: Int): Unit = {
    val book = books.find(_.title == bookTitle)
    if (book.isDefined) {
      book.get.quantity += quantity
    } else {
      println("Book not found.")
    }
  }

  def searchBook(searchQuery: String): Any = {
    val results = books.filter(book =>
      book.title.toLowerCase.contains(searchQuery.toLowerCase) ||
        book.author.toLowerCase.contains(searchQuery.toLowerCase))
    if (results.nonEmpty) {
      results.foreach(book => println(s"Title: ${book.title}, Author: ${book.author}, Quantity: ${book.quantity}"))
    } else {
      println("No results found.")
    }
  }

  def showAllBooks(): Unit = {
    books.foreach(book => println(s"Title: ${book.title}, Author: ${book.author}, Quantity: ${book.quantity}"))
  }

  def registerStudent(student: Student): Unit = {
    registeredStudents += student
  }

  def showAllRegisteredStudents(): Unit = {
    registeredStudents.foreach(student => println(s"Name: ${student.name}, ID: ${student.id}"))
  }

  def checkOutBook(customerId: Int, bookTitle: String): Unit = {
    val customer = customers.find(_.id == customerId).getOrElse(return)
    val book = books.find(_.title.equalsIgnoreCase(bookTitle)).getOrElse(return)
    if (book.quantity > 0) {
      book.quantity -= 1
      checkedOutBooks += ((customer.id, book.title))
      println(s"${customer.name} has checked out ${book.title}")
    } else {
      println(s"Sorry, ${book.title} is out of stock.")
    }
  }

  def checkInBook(customerId: Int, bookTitle: String): Unit = {
    val customer = customers.find(_.id == customerId).getOrElse(return)
    val book = books.find(_.title.equalsIgnoreCase(bookTitle)).getOrElse(return)
    if (checkedOutBooks.contains((customer.id, book.title))) {
      book.quantity += 1
      checkedOutBooks -= ((customer.id, book.title))
      println(s"${customer.name} has returned ${book.title}")
    } else {
      println(s"${customer.name} did not check out ${book.title}")
    }
  }

}
