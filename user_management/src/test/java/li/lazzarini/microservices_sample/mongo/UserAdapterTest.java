package li.lazzarini.microservices_sample.mongo;

import li.lazzarini.microservices_sample.entities.User;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * UserAdapter Test Class
 *
 * @since: 20.04.2019
 * @author: Matej Mrnjec
 */
class UserAdapterTest extends EntityAdapterTest<User>{
    public final static String USERNAME_TEST = "testUser";
    public final static String NAME_TEST = "Hans";
    public final static String LASTNAME_TEST = "Peter";
    public final static String PASSWORD_TEST = "Passwort";
    public final static String GROUPID_TEST = "1";
    public final static String STOREID_TEST = "1";
    public final static String NOID = "000000000000000000000000";
    public static final ObjectId TEST_ID = new ObjectId(NOID);


    @Override
    User getTestEntity() {
        return new User(TEST_ID, USERNAME_TEST, NAME_TEST, LASTNAME_TEST, PASSWORD_TEST, GROUPID_TEST, STOREID_TEST);
    }

    @Override
    Class<? extends EntityAdapter<User>> getAdapterClass() {
        return UserAdapter.class;
    }
    
    /**
     * Test method for {@link UserAdapter#getByUsername(String)} .
     */
    @Test
    void getByUserNameTest() throws IOException {
        // Test getByName Method in UserAdapter
        UserAdapter adapter = (UserAdapter) super.getAdapterForGetOneTest();
        assertTrue(getTestEntity().equals(adapter.getByUsername(NAME_TEST)));
    }

    /**
     * Test method for {@link UserAdapter#getByUsername(String)} .
     */
    @Test
    void getByUserNameFailsTest() throws IOException {
        // Test getByName Method in UserAdapter
        UserAdapter adapter = (UserAdapter) super.getAdapterForGetOneFailsTest();
        assertThrows(NoSuchElementException.class, () -> {
            adapter.getByUsername(USERNAME_TEST);
        });
    }

    /**
     * Test method for {@link UserAdapter#update(User)} .
     */
    @Test
    void updateTest() throws IOException {
        MongoAdapter<User> mongoAdapter = mock(MongoAdapter.class);

        // Test update Method in UserAdapter
        UserAdapter adapter = new UserAdapter(mongoAdapter);
        adapter.update(getTestEntity());
        verify(mongoAdapter, times(1)).update(any(Bson.class), any(User.class));
    }

    /**
     * Test method for {@link UserAdapter#update(User)} .
     */
    @Test
    void updateFailsTest() throws IOException {
        MongoAdapter<User> mongoAdapter = mock(MongoAdapter.class);
        doThrow(IOException.class).when(mongoAdapter).update(any(Bson.class), any(User.class));

        // Test update Method in UserAdapter
        UserAdapter adapter = new UserAdapter(mongoAdapter);
        assertThrows(IOException.class, () -> {
            adapter.update(getTestEntity());
        });
    }
}