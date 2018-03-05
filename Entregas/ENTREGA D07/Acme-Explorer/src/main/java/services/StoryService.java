
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StoryRepository;
import domain.Attachment;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@Service
@Transactional
public class StoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private StoryRepository				storyRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ExplorerService				explorerService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Constructors -----------------------------------------------------------

	public StoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Story create(Trip trip) {
		Story result;
		Explorer explorerPrincipal;
		Collection<Attachment> attachments;

		explorerPrincipal = this.explorerService.findByPrincipal();
		Assert.notNull(explorerPrincipal);
		attachments = new ArrayList<Attachment>();

		result = new Story();
		result.setExplorer(explorerPrincipal);
		result.setAttachments(attachments);
		result.setTrip(trip);

		return result;
	}
	public Collection<Story> findAll() {
		Collection<Story> result;

		Assert.notNull(this.storyRepository);
		result = this.storyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Story findOne(final int storyId) {
		Story result;

		result = this.storyRepository.findOne(storyId);
		Assert.notNull(result);

		return result;
	}

	public Story save(final Story story) {
		Assert.notNull(story);

		Story result;

		result = this.storyRepository.save(story);

		return result;
	}

	public void delete(final Story story) {
		Assert.notNull(story);
		Assert.isTrue(story.getId() != 0);

		Assert.isTrue(this.storyRepository.exists(story.getId()));

		this.storyRepository.delete(story);
	}

	// Other business methods -------------------------------------------------
	public Collection<Attachment> urlAttachments(Story story) {
		Collection<Attachment> result;

		result = this.storyRepository.urlOfAttachments(story.getId());

		return result;

	}

	public Boolean storyContainsSpam(Explorer explorer) {
		Boolean result;
		Collection<Story> storysOfExplorer;
		Collection<String> spamWords;
		Collection<String> words;

		result = false;
		words = new ArrayList<String>();
		spamWords = this.configurationSystemService.spamWord();
		storysOfExplorer = this.storyRepository.storyOfExplore(explorer.getId());

		for (Story story : storysOfExplorer) {
			words.add(story.getText().toLowerCase());
			words.add(story.getTitle().toLowerCase());

		}

		for (String word : words)
			for (String spam : spamWords)
				if (word.indexOf(spam) > -1) {
					result = true;
					break;
				}
		return result;
	}

	public Collection<Story> findAllStoriesByTripId(int tripId) {
		Collection<Story> result;
		result = new ArrayList<Story>(this.storyRepository.findAllStoriesByTripId(tripId));
		return result;
	}
}
