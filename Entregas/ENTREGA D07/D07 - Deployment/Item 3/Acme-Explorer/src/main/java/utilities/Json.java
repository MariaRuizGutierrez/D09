
package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import security.UserAccount;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import domain.Administrator;
import domain.ApplicationFor;
import domain.AuditRecord;
import domain.Auditor;
import domain.Category;
import domain.ContactEmergency;
import domain.Curricula;
import domain.DomainEntity;
import domain.EndorserRecord;
import domain.Explorer;
import domain.LegalText;
import domain.Manager;
import domain.Message;
import domain.MessageFolder;
import domain.MiscellaneousRecord;
import domain.Note;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.Ranger;
import domain.SocialIdentity;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Stage;
import domain.Story;
import domain.Tag;
import domain.Trip;

public class Json {

	static final List<DomainEntity>	objectList	= new ArrayList<DomainEntity>();
	static int						id			= 0;


	public static void main(final String[] args) {
		//Para parsear las fechas
		//final Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		final BufferedReader reader;
		final JsonParser parser;
		try {
			parser = new JsonParser();
			reader = new BufferedReader(new FileReader("./src/main/java/utilities/json.txt"));
			final JsonElement datos = parser.parse(reader);
			Json.dumpJSONElement(datos);
			System.out.println(Json.objectList);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	public static void dumpJSONElement(final JsonElement element) {
		DomainEntity object;
		final JsonObject jsonObject = element.getAsJsonObject();
		final java.util.Set<java.util.Map.Entry<String, JsonElement>> inputs = jsonObject.entrySet();
		for (final java.util.Map.Entry<String, JsonElement> map : inputs)
			if (map.getValue().isJsonArray()) {
				//Si es un array significa que hay más de un objeto de esa clase
				final JsonArray array = map.getValue().getAsJsonArray();
				final java.util.Iterator<JsonElement> iter = array.iterator();
				while (iter.hasNext()) {
					final JsonElement input = iter.next();
					object = Json.devuelveObjetoJava(map.getKey(), input);
					if (object != null) {
						Json.id++;
						object.setId(Json.id);
						Json.objectList.add(object);
					} else
						System.out.println("ERROR: Don't exist the domain with name: " + map.getKey());
				}
			} else {
				object = Json.devuelveObjetoJava(map.getKey(), map.getValue());
				if (object != null) {
					Json.id++;
					object.setId(Json.id);
					Json.objectList.add(object);
				} else
					System.out.println("ERROR: Don't exist the domain with name: " + map.getKey());

			}
	}
	private static DomainEntity devuelveObjetoJava(final String domain, final JsonElement jsonElement) {
		final Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		DomainEntity res = null;
		switch (domain) {
		case "UserAccount":
			res = gson.fromJson(jsonElement, UserAccount.class);
			break;
		case "Administrator":
			res = gson.fromJson(jsonElement, Administrator.class);
			break;
		case "ApplicationFor":
			res = gson.fromJson(jsonElement, ApplicationFor.class);
			break;
		case "Auditor":
			res = gson.fromJson(jsonElement, Auditor.class);
			break;
		case "AuditRecord":
			res = gson.fromJson(jsonElement, AuditRecord.class);
			break;
		case "Category":
			res = gson.fromJson(jsonElement, Category.class);
			break;
		case "ContactEmergency":
			res = gson.fromJson(jsonElement, ContactEmergency.class);
			break;
		case "Curricula":
			res = gson.fromJson(jsonElement, Curricula.class);
			break;
		case "DomainEntity":
			res = gson.fromJson(jsonElement, DomainEntity.class);
			break;
		case "EndorserRecord":
			res = gson.fromJson(jsonElement, EndorserRecord.class);
			break;
		case "Explorer":
			res = gson.fromJson(jsonElement, Explorer.class);
			break;
		case "LegalText":
			res = gson.fromJson(jsonElement, LegalText.class);
			break;
		case "Manager":
			res = gson.fromJson(jsonElement, Manager.class);
			break;
		case "Message":
			res = gson.fromJson(jsonElement, Message.class);
			break;
		case "MessageFolder":
			res = gson.fromJson(jsonElement, MessageFolder.class);
			break;
		case "MiscellaneousRecord":
			res = gson.fromJson(jsonElement, MiscellaneousRecord.class);
			break;
		case "Note":
			res = gson.fromJson(jsonElement, Note.class);
			break;
		case "PersonalRecord":
			res = gson.fromJson(jsonElement, PersonalRecord.class);
			break;
		case "ProfessionalRecord":
			res = gson.fromJson(jsonElement, ProfessionalRecord.class);
			break;
		case "Ranger":
			res = gson.fromJson(jsonElement, Ranger.class);
			break;
		case "SocialIdentities":
			res = gson.fromJson(jsonElement, SocialIdentity.class);
			break;
		case "Sponsor":
			res = gson.fromJson(jsonElement, Sponsor.class);
			break;
		case "SponsorShip":
			res = gson.fromJson(jsonElement, Sponsorship.class);
			break;
		case "Stage":
			res = gson.fromJson(jsonElement, Stage.class);
			break;
		case "Story":
			res = gson.fromJson(jsonElement, Story.class);
			break;
		case "Trip":
			res = gson.fromJson(jsonElement, Trip.class);
			break;
		case "Tag":
			res = gson.fromJson(jsonElement, Tag.class);
			break;

		}

		return res;
	}
}
