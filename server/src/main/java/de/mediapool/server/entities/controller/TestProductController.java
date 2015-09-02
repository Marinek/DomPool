package de.mediapool.server.entities.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.mediapool.server.core.controller.MPController;
import de.mediapool.server.entities.lists.domain.ListNodeDTO;
import de.mediapool.server.entities.lists.repository.ListRepository;
import de.mediapool.server.entities.media.movies.domain.MovieMediaNodeDTO;
import de.mediapool.server.entities.media.movies.repository.MovieMediaRepository;
import de.mediapool.server.entities.persons.domain.PersonNodeDTO;
import de.mediapool.server.entities.persons.repository.PersonsRepository;
import de.mediapool.server.entities.product.domain.MediaType;
import de.mediapool.server.entities.product.domain.ProductNodeDTO;
import de.mediapool.server.entities.product.repository.ProductRepository;
import de.mediapool.server.entities.users.domain.UserNodeDTO;
import de.mediapool.server.entities.users.repository.UserRepository;
import de.mediapool.server.security.domain.MPUserDetails;
import de.mediapool.server.security.domain.PreAuthorization;

@RestController
@RequestMapping("/rest/testProduct")
public class TestProductController implements MPController {

	private static final Logger logger = LoggerFactory.getLogger(TestProductController.class);

	@Autowired
	private ProductRepository productMovieRepository;

	@Autowired
	private PersonsRepository personsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MovieMediaRepository movieRepository;

	@Autowired
	private ListRepository listRepository;

	@PostConstruct
	public void init() {
		logger.debug("Invoking: init()");
	}

	@RequestMapping
	public List<MovieMediaNodeDTO> findMovieByTitle(String name) {
		return new ArrayList<>();
	}

	@PreAuthorize(PreAuthorization.ROLE_USER)
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ProductNodeDTO getProductMovie(@PathVariable("id") Long id, @AuthenticationPrincipal MPUserDetails test) {
		logger.debug("Invoking: getMovie(id)");

		ProductNodeDTO productMovie = productMovieRepository.findOne(id);

		return productMovie;
	}

	@RequestMapping(value = "/deletePerson", method = RequestMethod.POST)
	public void deletePerson(String lastname) {

		List<PersonNodeDTO> personList = personsRepository.findByLastName(lastname);

		if (personList != null && personList.size() > 0) {
			for (PersonNodeDTO person : personList)
				personsRepository.delete(person);
		}
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public void deleteUser(String username) {

		List<UserNodeDTO> userList = userRepository.findAllByUsername(username);

		if (userList != null && userList.size() > 0) {
			for (UserNodeDTO user : userList) {
				userRepository.delete(user);
			}
		}

	}

	@RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
	public void deleteProduct(String title) {

		List<ProductNodeDTO> pmnl = productMovieRepository.findByTitle(title);

		if (pmnl != null && pmnl.size() > 0) {
			for (ProductNodeDTO pmn : pmnl)
				productMovieRepository.delete(pmn);
		}
	}

	@RequestMapping(value = "/deleteMovie", method = RequestMethod.POST)
	public void deleteMovie(String title) {

		List<MovieMediaNodeDTO> movieList = movieRepository.findByTitle(title);

		if (movieList != null && movieList.size() > 0) {
			for (MovieMediaNodeDTO movie : movieList) {
				movieRepository.delete(movie);
			}
		}
	}

	@RequestMapping(value = "/deleteList", method = RequestMethod.POST)
	public void deleteList(String title) {

		List<ListNodeDTO> listList = listRepository.findByTitle(title);

		if (listList != null && listList.size() > 0) {
			for (ListNodeDTO list : listList) {
				listRepository.delete(list);
			}
		}
	}

	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	public void deleteAll() {

		deleteProduct("Herr der Ringe Triologie");
		deleteMovie("Herr der Ringe - Die Gefährten");
		deleteMovie("Herr der Ringe - Die 2 Türme");
		deletePerson("Bloom");
		deletePerson("Tyler");
		deleteProduct("Star Wars Triologie");
		deleteMovie("Das Imperium schlägt zurück");
		deleteMovie("Krieg der Sterne");
		deletePerson("Ford");
		deletePerson("Fisher");
		deleteList("Wishlist");
		deleteUser("Test1");
		deleteUser("Test2");
		deleteUser("Test3");
		deleteUser("Test4");
		deleteUser("Test5");
	}

	private Date getDateForString(String dateValue) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd.MM.yyyy").parse(dateValue);
		} catch (ParseException ex) {
			logger.error("Wrong Dateformat: " + dateValue);
			date = new Date();
		}
		return date;
	}

	@RequestMapping(value = "/createAll", method = RequestMethod.POST)
	public void createAll() {
		logger.debug("Invoking: createAllStuff");

		UserNodeDTO newUser2 = new UserNodeDTO("Test2", "Test2");
		userRepository.save(newUser2);
		UserNodeDTO newUser3 = new UserNodeDTO("Test3", "Test3");
		userRepository.save(newUser3);
		UserNodeDTO newUser4 = new UserNodeDTO("Test4", "Test4");
		userRepository.save(newUser4);
		UserNodeDTO newUser5 = new UserNodeDTO("Test5", "Test5");
		userRepository.save(newUser5);

		UserNodeDTO newUser1 = new UserNodeDTO("Test1", "Test1");

		ProductNodeDTO newProductMovie1 = new ProductNodeDTO(MediaType.MOVIE, "Herr der Ringe Triologie", "Herr der Ringe Triologie", 2001, "Extended", "German", 10, "cover.jpg",
				"All Movies together", "000-000", "Blueray");

		MovieMediaNodeDTO newMovie1 = new MovieMediaNodeDTO("Herr der Ringe - Die Gefährten", "Herr der Ringe - Die Gefährten", 2001, "Fantasy", "english", "Oscar", "cover.jpg",
				"Trying to defeat Sauron", "Movie", 180, 12);

		newProductMovie1.addMovie(newMovie1);

		PersonNodeDTO newPerson1 = new PersonNodeDTO("Orlando", "Bloom", getDateForString("20.04.1982"), "USA", "w", "Actor", false, "image.jpg");
		newMovie1.addPerson(newPerson1);

		MovieMediaNodeDTO newMovie2 = new MovieMediaNodeDTO("Herr der Ringe - Die 2 Türme", "Herr der Ringe - Die 2 Türme", 2001, "Fantasy", "english", "Oscar", "cover.jpg", "Trying to defeat Sauron",
				"Movie", 180, 12);

		newProductMovie1.addMovie(newMovie2);

		PersonNodeDTO newPerson2 = new PersonNodeDTO("Liv", "Tyler", getDateForString("20.04.1982"), "USA", "w", "Actor", false, "image.jpg");
		newMovie2.addPerson(newPerson2);

		personsRepository.save(newPerson1);
		personsRepository.save(newPerson2);

		// logger.info("TESTTEST " + newMovie2.getPersons().toString() + "
		// TESTTEST");

		// logger.info("TESTTEST " + newMovie1.getPersons().toString() + "
		// TESTTEST");

		// logger.info("TESTTEST " + newProductMovie1.toString() + " TESTTEST");

		// logger.info("TESTTEST " +
		// newProductMovie1.getOwendProducts().toString() + " TESTTEST");

		productMovieRepository.save(newProductMovie1);

		newUser1.owens(newProductMovie1);

		userRepository.save(newUser1);

		ProductNodeDTO newProductMovie2 = new ProductNodeDTO(MediaType.MOVIE, "Star Wars Triologie", "Star Wars Triologie", 2001, "Extended", "German", 10, "cover.jpg", "All Movies together",
				"000-000", "Blueray");

		MovieMediaNodeDTO newMovie3 = new MovieMediaNodeDTO("Krieg der Sterne", "Krieg der Sterne", 2001, "ScienceFiction", "english", "Oscar", "cover.jpg", "Trying to defeat Sauron", "Movie", 180,
				12);

		newProductMovie2.addMovie(newMovie3);

		PersonNodeDTO newPerson3 = new PersonNodeDTO("Harrison", "Ford", getDateForString("20.04.1982"), "USA", "w", "Actor", false, "image.jpg");

		newMovie3.addPerson(newPerson3);

		MovieMediaNodeDTO newMovie4 = new MovieMediaNodeDTO("Das Imperium schlägt zurück", "Das Imperium schlägt zurück", 2001, "ScienceFiction", "english", "Oscar", "cover.jpg",
				"Trying to defeat Sauron", "Movie", 180, 12);

		newProductMovie2.addMovie(newMovie4);

		PersonNodeDTO newPerson4 = new PersonNodeDTO("Carrie", "Fisher", getDateForString("20.04.1982"), "USA", "w", "Actor", false, "image.jpg");

		newMovie4.addPerson(newPerson4);

		personsRepository.save(newPerson3);
		personsRepository.save(newPerson4);

		productMovieRepository.save(newProductMovie2);

		List<ProductNodeDTO> pmnl = productMovieRepository.findByTitle("Star Wars Triologie");

		ListNodeDTO list = new ListNodeDTO("Wishlist", newUser1);

		if (pmnl != null && pmnl.size() > 0) {
			for (ProductNodeDTO pmn : pmnl) {
				list.addToList(pmn);
			}
			listRepository.save(list);
		}

	}

}