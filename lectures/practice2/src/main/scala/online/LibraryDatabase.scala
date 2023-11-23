package online

case class LibraryDatabase(clients: List[Client], books: List[Book], issuedBoos: Map[Book, Client])
