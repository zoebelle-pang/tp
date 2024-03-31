package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicateDateTimeException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniquePersonAndDateTimeListTest {

    private final UniquePersonAndDateTimeList uniquePersonandDateTimeList = new UniquePersonAndDateTimeList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonandDateTimeList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonandDateTimeList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonandDateTimeList.add(ALICE);
        assertTrue(uniquePersonandDateTimeList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonandDateTimeList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePersonandDateTimeList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonandDateTimeList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonandDateTimeList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonandDateTimeList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonandDateTimeList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonandDateTimeList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonandDateTimeList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonandDateTimeList.add(ALICE);
        uniquePersonandDateTimeList.setPerson(ALICE, ALICE);
        UniquePersonAndDateTimeList expectedUniquePersonAndDateTimeList = new UniquePersonAndDateTimeList();
        expectedUniquePersonAndDateTimeList.add(ALICE);
        assertEquals(expectedUniquePersonAndDateTimeList, uniquePersonandDateTimeList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonandDateTimeList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePersonandDateTimeList.setPerson(ALICE, editedAlice);
        UniquePersonAndDateTimeList expectedUniquePersonAndDateTimeList = new UniquePersonAndDateTimeList();
        expectedUniquePersonAndDateTimeList.add(editedAlice);
        assertEquals(expectedUniquePersonAndDateTimeList, uniquePersonandDateTimeList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonandDateTimeList.add(ALICE);
        uniquePersonandDateTimeList.setPerson(ALICE, BOB);
        UniquePersonAndDateTimeList expectedUniquePersonAndDateTimeList = new UniquePersonAndDateTimeList();
        expectedUniquePersonAndDateTimeList.add(BOB);
        assertEquals(expectedUniquePersonAndDateTimeList, uniquePersonandDateTimeList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonandDateTimeList.add(ALICE);
        uniquePersonandDateTimeList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonandDateTimeList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonandDateTimeList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonandDateTimeList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonandDateTimeList.add(ALICE);
        uniquePersonandDateTimeList.remove(ALICE);
        UniquePersonAndDateTimeList expectedUniquePersonAndDateTimeList = new UniquePersonAndDateTimeList();
        assertEquals(expectedUniquePersonAndDateTimeList, uniquePersonandDateTimeList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonandDateTimeList.setPersons((UniquePersonAndDateTimeList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonandDateTimeList.add(ALICE);
        UniquePersonAndDateTimeList expectedUniquePersonAndDateTimeList = new UniquePersonAndDateTimeList();
        expectedUniquePersonAndDateTimeList.add(BOB);
        uniquePersonandDateTimeList.setPersons(expectedUniquePersonAndDateTimeList);
        assertEquals(expectedUniquePersonAndDateTimeList, uniquePersonandDateTimeList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonandDateTimeList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonandDateTimeList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniquePersonandDateTimeList.setPersons(personList);
        UniquePersonAndDateTimeList expectedUniquePersonAndDateTimeList = new UniquePersonAndDateTimeList();
        expectedUniquePersonAndDateTimeList.add(BOB);
        assertEquals(expectedUniquePersonAndDateTimeList, uniquePersonandDateTimeList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonandDateTimeList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void setPersons_listWithDuplicateDateTime_throwsDuplicateDateTimeException() {
        Person alice = new PersonBuilder(ALICE).withDateTimes("2024-02-03 2100")
                .build();
        Person bob = new PersonBuilder(BOB).withDateTimes("2024-02-03 2100")
                .build();
        uniquePersonandDateTimeList.add(alice);
        assertThrows(DuplicateDateTimeException.class, () -> uniquePersonandDateTimeList.add(bob));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniquePersonandDateTimeList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePersonandDateTimeList.asUnmodifiableObservableList().toString(), uniquePersonandDateTimeList.toString());
    }
}
