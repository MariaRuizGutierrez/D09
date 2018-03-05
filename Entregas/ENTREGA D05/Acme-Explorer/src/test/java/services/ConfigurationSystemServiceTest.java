
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.ConfigurationSystem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ConfigurationSystemServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------	

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Supporting services ----------------------------------------------------

	@Test
	public void testFindOne() {
		final ConfigurationSystem res;
		res = this.configurationSystemService.findOne();
		Assert.notNull(res.getBanner());
		Assert.notNull(res.getSpamWords().contains("viagra"));
	}

	@Test
	public void testFindOne2() {
		final ConfigurationSystem res;
		res = this.configurationSystemService.findOne(super.getEntityId("configurationSystem"));
		Assert.notNull(res.getBanner());
		Assert.notNull(res.getSpamWords().contains("viagra"));
	}
	@Test
	public void testSave() {
		ConfigurationSystem res;
		res = this.configurationSystemService.findOne();
		res.getSpamWords().add("testing");
		res = this.configurationSystemService.save(res);

	}

	@Test
	public void testFindAll() {
		Collection<ConfigurationSystem> res;
		res = this.configurationSystemService.findAll();
		Assert.isTrue(res.size() == 1);

	}

	@Test
	public void testSpamWord() {
		Collection<String> res;
		res = this.configurationSystemService.spamWord();
		Assert.notNull(res);
	}

	@Test
	public void testGetVat() {
		Double res;

		res = this.configurationSystemService.getVat();
		Assert.notNull(res);
	}

	@Test
	public void testDefaultCategory() {
		Collection<Category> res;
		res = this.configurationSystemService.defaultCategories();
		Assert.notNull(res);
	}
}
