package guru.springframework.bootstrap;

import guru.springframework.common.Address;
import guru.springframework.domain.Author;
import guru.springframework.domain.Book;
import guru.springframework.domain.Publisher;
import guru.springframework.repositories.AuthorRepository;
import guru.springframework.repositories.BookRepository;
import guru.springframework.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Author rob = new Author("Rob","Robsen");
        Book robsDdd = new Book("Domain Driven Design","123456");

        Address pAddress = new Address("Blutwurstraße 1","Entenhausen","Hessen", 63150L);
        Publisher hansWurst = new Publisher("Hanswurst", pAddress.getStreet(), pAddress.getCity(), pAddress.getState(), pAddress.getZipCode());

        publisherRepository.save(hansWurst);
        addPublisherValues(hansWurst, rob, robsDdd);

        Author rod = new Author("Rod","Johnsen");
        Book noEJB = new Book("JEEE Development wihtout EJB","132553");

        addPublisherValues(hansWurst, rod, noEJB);

        System.out.println("Started in Bootstrap");
        System.out.println("Count Authors " + authorRepository.count());
        System.out.println("Count Books " + bookRepository.count());
        System.out.println("Count Publisher " + publisherRepository.count());

        System.out.println("Publisher " + hansWurst + " Number of Books " + hansWurst.getBooks().size());

    }

    private void addPublisherValues(Publisher publisher, Author author, Book book) {
        author.getBooks().add(book);
        book.getAuthors().add(author);
        book.setPublisher(publisher);
        publisher.getBooks().add(book);

        authorRepository.save(author);
        bookRepository.save(book);
        publisherRepository.save(publisher);
    }
}
