package com.library.bookservice.service;

import com.library.bookservice.errorhandler.BookExistsException;
import com.library.bookservice.errorhandler.BookNotFoundException;
import com.library.bookservice.model.Book;
import com.library.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/*
Add appropriate annotation/s to create a bean for service layer
Implement all the functionality based on the BookService Interface
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    /*
      Inject the repository bean
     */
    @Autowired
    private BookRepository repository;

    @Override
    public Book addBook(Book newBook) throws BookExistsException {
        Optional<Book> optional=repository.findBookByAuthorIdAndBookPublishedDate(newBook.getAuthorId(), newBook.getBookPublishedDate());
        if(optional.isPresent()){
            throw new BookExistsException();
        }
        return repository.save(newBook);
    }

    @Override
    public Book getBookById(Integer id) throws BookNotFoundException {
        Optional<Book> optional=repository.findById(id);
        if(!optional.isPresent()){
            throw new BookNotFoundException();
        }
        return optional.get();
    }

    @Override
    public List<Book> getBooksByAuthorId(String authorId) throws BookNotFoundException {
        List<Book> desired=repository.findBookByAuthorId(authorId);
        if(desired.isEmpty()){
            throw new BookNotFoundException();
        }
        return desired;
    }

    @Override
    public List<Book> getBooksByLibraryAndBookPublishedDate(String libraryId, LocalDate bookPublishedDate) throws BookNotFoundException {
        List<Book> desired=repository.findBookByLibraryIdAndBookPublishedDate(libraryId,bookPublishedDate);
        if(desired.isEmpty()){
            throw new BookNotFoundException();
        }
        return desired;
    }

    @Override
    public Book updateBookStatus(int bookId, String status) throws BookNotFoundException {
        Book book=getBookById(bookId);
        book.setStatus(status);
        return repository.save(book);
    }
}
